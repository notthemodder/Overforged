package notmodder.progressivegearmod;

import java.util.List;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

public final class AlloyBlastFurnaceBlockEntity extends RandomizableContainerBlockEntity {
    public static BlockEntityType<AlloyBlastFurnaceBlockEntity> TYPE;
    private NonNullList<ItemStack> items = NonNullList.withSize(4, ItemStack.EMPTY);
    private int burnTime;
    private int cookTime;
    private static final int COOK_TIME = 100;
    private static final List<Item> METALS = List.of(
            Items.COPPER_INGOT,
            ModItems.TUNGSTEN_INGOT, ModItems.TITANIUM_INGOT, ModItems.TIN_INGOT,
            ModItems.BISMUTH_INGOT, ModItems.ALUMINUM_INGOT, ModItems.SILVER_INGOT,
            ModItems.PLATINUM_INGOT, ModItems.OSMIUM_INGOT
    );

    public static void initialize() {
        ResourceKey<BlockEntityType<?>> key = ResourceKey.create(Registries.BLOCK_ENTITY_TYPE, ProgressiveGearMod.id("alloy_blast_furnace"));
        TYPE = Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, key,
                FabricBlockEntityTypeBuilder.create(AlloyBlastFurnaceBlockEntity::new, ModBlocks.ALLOY_BLAST_FURNACE).build());
    }

    public AlloyBlastFurnaceBlockEntity(BlockPos pos, BlockState state) {
        super(TYPE, pos, state);
    }

    private final ContainerData data = new ContainerData() {
        @Override public int get(int index) {
            return switch (index) {
                case 0 -> burnTime;
                case 1 -> fuelDuration;
                case 2 -> cookTime;
                case 3 -> COOK_TIME;
                default -> 0;
            };
        }
        @Override public void set(int index, int value) {
            switch (index) {
                case 0 -> burnTime = value;
                case 1 -> fuelDuration = value;
                case 2 -> cookTime = value;
            }
        }
        @Override public int getCount() { return 4; }
    };
    private int fuelDuration;

    @Override
    public int getContainerSize() { return 4; }

    @Override
    protected Component getDefaultName() { return Component.translatable("container.overforged.alloy_blast_furnace"); }

    @Override
    protected NonNullList<ItemStack> getItems() { return items; }

    @Override
    protected void setItems(NonNullList<ItemStack> items) { this.items = items; }

    @Override
    protected AbstractContainerMenu createMenu(int id, Inventory inventory) {
        return new AlloyBlastFurnaceMenu(ModMenus.ALLOY_BLAST_FURNACE, id, inventory, this, data,
                ContainerLevelAccess.create(level, worldPosition));
    }

    @Override
    public boolean canPlaceItem(int slot, ItemStack stack) {
        return switch (slot) {
            case 0, 1 -> METALS.contains(stack.getItem());
            case 2 -> isFuel(stack);
            default -> false;
        };
    }

    @Override
    public boolean canTakeItem(Container target, int slot, ItemStack stack) { return true; }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        ContainerHelper.loadAllItems(input, items);
        burnTime = input.getIntOr("BurnTime", 0);
        fuelDuration = input.getIntOr("FuelDuration", 0);
        cookTime = input.getIntOr("CookTime", 0);
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        ContainerHelper.saveAllItems(output, items);
        output.putInt("BurnTime", burnTime);
        output.putInt("FuelDuration", fuelDuration);
        output.putInt("CookTime", cookTime);
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, AlloyBlastFurnaceBlockEntity furnace) {
        if (level.isClientSide()) return;
        if (furnace.burnTime > 0) furnace.burnTime--;
        ItemStack first = furnace.items.get(0);
        ItemStack second = furnace.items.get(1);
        Item output = alloyFor(first.getItem(), second.getItem());
        boolean canSmelt = output != null && canAcceptOutput(furnace.items.get(3), output);

        if (canSmelt && furnace.burnTime <= 0 && isFuel(furnace.items.get(2))) {
            furnace.burnTime = fuelDuration(furnace.items.get(2));
            furnace.fuelDuration = furnace.burnTime;
            furnace.items.get(2).shrink(1);
            if (furnace.items.get(2).isEmpty()) furnace.items.set(2, ItemStack.EMPTY);
        }
        boolean lit = furnace.burnTime > 0;
        if (state.getValue(AlloyBlastFurnaceBlock.LIT) != lit) {
            level.setBlock(pos, state.setValue(AlloyBlastFurnaceBlock.LIT, lit), 3);
        }
        if (canSmelt && furnace.burnTime > 0) {
            if (++furnace.cookTime >= COOK_TIME) {
                furnace.items.get(0).shrink(1);
                furnace.items.get(1).shrink(1);
                if (furnace.items.get(0).isEmpty()) furnace.items.set(0, ItemStack.EMPTY);
                if (furnace.items.get(1).isEmpty()) furnace.items.set(1, ItemStack.EMPTY);
                ItemStack result = furnace.items.get(3);
                if (result.isEmpty()) furnace.items.set(3, new ItemStack(output)); else result.grow(1);
                furnace.cookTime = 0;
            }
        } else if (!canSmelt) {
            furnace.cookTime = 0;
        }
        furnace.setChanged();
    }

    private static boolean canAcceptOutput(ItemStack result, Item output) {
        return result.isEmpty() || (result.is(output) && result.getCount() < result.getMaxStackSize());
    }

    private static Item alloyFor(Item first, Item second) {
        if (isPair(first, second, ModItems.TITANIUM_INGOT, ModItems.TUNGSTEN_INGOT)) return ModItems.TITANSTENE_INGOT;
        if (isPair(first, second, ModItems.TIN_INGOT, ModItems.BISMUTH_INGOT)) return ModItems.BISMUTHANE_INGOT;
        if (isPair(first, second, ModItems.ALUMINUM_INGOT, ModItems.SILVER_INGOT)) return ModItems.SILVARIUM_INGOT;
        if (isPair(first, second, ModItems.PLATINUM_INGOT, ModItems.BISMUTH_INGOT)) return ModItems.PLATINE_INGOT;
        return null;
    }

    private static boolean isPair(Item first, Item second, Item a, Item b) {
        return (first == a && second == b) || (first == b && second == a);
    }

    private static boolean isFuel(ItemStack stack) {
        Item item = stack.getItem();
        return item == Items.COAL || item == Items.CHARCOAL || item == Items.BLAZE_ROD || item == Items.COAL_BLOCK;
    }

    private static int fuelDuration(ItemStack stack) {
        Item item = stack.getItem();
        if (item == Items.COAL_BLOCK) return 16000;
        if (item == Items.BLAZE_ROD) return 2400;
        return 1600;
    }
}
