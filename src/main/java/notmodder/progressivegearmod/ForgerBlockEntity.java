package notmodder.progressivegearmod;

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
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.item.ItemStack;

public final class ForgerBlockEntity extends RandomizableContainerBlockEntity {
    public static BlockEntityType<ForgerBlockEntity> TYPE;
    private NonNullList<ItemStack> items = NonNullList.withSize(3, ItemStack.EMPTY);

    public static void initialize() {
        ResourceKey<BlockEntityType<?>> key = ResourceKey.create(Registries.BLOCK_ENTITY_TYPE, ProgressiveGearMod.id("forger"));
        TYPE = Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, key,
                FabricBlockEntityTypeBuilder.create(ForgerBlockEntity::new, ModBlocks.FORGER).build());
    }

    public ForgerBlockEntity(BlockPos pos, BlockState state) { super(TYPE, pos, state); }

    @Override public int getContainerSize() { return 3; }
    @Override protected Component getDefaultName() { return Component.translatable("container.overforged.forger"); }
    @Override protected NonNullList<ItemStack> getItems() { return items; }
    @Override protected void setItems(NonNullList<ItemStack> items) { this.items = items; }

    @Override
    protected AbstractContainerMenu createMenu(int id, Inventory inventory) {
        return new ForgerMenu(ModMenus.FORGER, id, inventory, this,
                ContainerLevelAccess.create(level, worldPosition));
    }

    @Override
    public boolean canPlaceItem(int slot, ItemStack stack) {
        return switch (slot) {
            case 0 -> ForgerRecipes.isCatalyst(stack.getItem());
            case 1 -> ForgerRecipes.isTarget(stack.getItem());
            default -> false;
        };
    }

    @Override public boolean canTakeItem(Container target, int slot, ItemStack stack) { return true; }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        ContainerHelper.loadAllItems(input, items);
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        ContainerHelper.saveAllItems(output, items);
    }

    public static void serverTick(Level level, BlockPos pos, ForgerBlockEntity forger) {
        if (level.isClientSide()) return;
        ItemStack preview = ForgerRecipes.result(forger.items.get(0), forger.items.get(1));
        ItemStack current = forger.items.get(2);
        if (!ItemStack.matches(preview, current)) {
            forger.items.set(2, preview);
            forger.setChanged();
        }
    }

    public void takeInputs() {
        items.get(0).shrink(1);
        items.get(1).shrink(1);
        if (items.get(0).isEmpty()) items.set(0, ItemStack.EMPTY);
        if (items.get(1).isEmpty()) items.set(1, ItemStack.EMPTY);
        setChanged();
    }

    @Override
    public void preRemoveSideEffects(BlockPos pos, BlockState state) {
        items.set(2, ItemStack.EMPTY);
        super.preRemoveSideEffects(pos, state);
    }
}
