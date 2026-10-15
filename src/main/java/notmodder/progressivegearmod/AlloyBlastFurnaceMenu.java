package notmodder.progressivegearmod;

import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public final class AlloyBlastFurnaceMenu extends AbstractContainerMenu {
    private final Container container;
    private final ContainerData data;
    private final ContainerLevelAccess access;

    public AlloyBlastFurnaceMenu(int id, Inventory inventory) {
        this(ModMenus.ALLOY_BLAST_FURNACE, id, inventory, new SimpleContainer(4),
                new net.minecraft.world.inventory.SimpleContainerData(4), ContainerLevelAccess.NULL);
    }

    public AlloyBlastFurnaceMenu(net.minecraft.world.inventory.MenuType<?> type, int id, Inventory inventory,
                                 Container container, ContainerData data, ContainerLevelAccess access) {
        super(type, id);
        checkContainerSize(container, 4);
        checkContainerDataCount(data, 4);
        this.container = container;
        this.data = data;
        this.access = access;

        addSlot(new Slot(container, 0, 56, 17));
        addSlot(new Slot(container, 1, 34, 17));
        addSlot(new Slot(container, 2, 56, 53));
        addSlot(new Slot(container, 3, 116, 35) {
            @Override public boolean mayPlace(ItemStack stack) { return false; }
        });
        addStandardInventorySlots(inventory, 8, 84);
        addDataSlots(data);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack moved = ItemStack.EMPTY;
        Slot slot = slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack stack = slot.getItem();
            moved = stack.copy();
            if (index < 4) {
                if (!moveItemStackTo(stack, 4, slots.size(), true)) return ItemStack.EMPTY;
            } else if (container.canPlaceItem(0, stack) || container.canPlaceItem(1, stack)) {
                if (!moveItemStackTo(stack, 0, 2, false)) return ItemStack.EMPTY;
            } else if (container.canPlaceItem(2, stack)) {
                if (!moveItemStackTo(stack, 2, 3, false)) return ItemStack.EMPTY;
            } else if (index < 31) {
                if (!moveItemStackTo(stack, 31, slots.size(), false)) return ItemStack.EMPTY;
            } else if (!moveItemStackTo(stack, 4, 31, false)) {
                return ItemStack.EMPTY;
            }
            if (stack.isEmpty()) slot.set(ItemStack.EMPTY);
            else slot.setChanged();
            if (stack.getCount() == moved.getCount()) return ItemStack.EMPTY;
            slot.onTake(player, stack);
        }
        return moved;
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(access, player, ModBlocks.ALLOY_BLAST_FURNACE);
    }

    public boolean isBurning() { return data.get(0) > 0; }

    public int getBurnProgress(int pixels) {
        int remaining = data.get(0);
        int total = data.get(1);
        return total <= 0 || remaining <= 0 ? 0 : remaining * pixels / total;
    }

    public int getCookProgress(int pixels) {
        int total = Math.max(1, data.get(3));
        return data.get(2) * pixels / total;
    }
}
