package notthemodder.overforged;

import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

/**
 * Container menu for the Forger. Exposes a catalyst slot, a target slot,
 * and a read-only output slot. Taking from the output slot triggers
 * {@link ForgerBlockEntity#takeInputs()} to consume both inputs.
 */
public final class ForgerMenu extends AbstractContainerMenu {
    private final Container container;
    private final ContainerLevelAccess access;

    public ForgerMenu(int id, Inventory inventory) {
        this(ModMenus.FORGER, id, inventory, new SimpleContainer(3), ContainerLevelAccess.NULL);
    }

    public ForgerMenu(net.minecraft.world.inventory.MenuType<?> type, int id, Inventory inventory,
                      Container container, ContainerLevelAccess access) {
        super(type, id);
        checkContainerSize(container, 3);
        this.container = container;
        this.access = access;

        addSlot(new Slot(container, 0, 27, 47));
        addSlot(new Slot(container, 1, 76, 47));
        addSlot(new Slot(container, 2, 134, 47) {
            @Override public boolean mayPlace(ItemStack stack) { return false; }
            @Override public void onTake(Player player, ItemStack stack) {
                super.onTake(player, stack);
                if (container instanceof ForgerBlockEntity forger) forger.takeInputs();
                else {
                    container.removeItem(0, 1);
                    container.removeItem(1, 1);
                }
            }
        });
        addStandardInventorySlots(inventory, 8, 84);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack moved = ItemStack.EMPTY;
        Slot slot = slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack stack = slot.getItem();
            moved = stack.copy();
            if (index < 3) {
                if (!moveItemStackTo(stack, 3, slots.size(), true)) return ItemStack.EMPTY;
            } else if (ForgerRecipes.isCatalyst(stack.getItem())) {
                if (!moveItemStackTo(stack, 0, 1, false)) return ItemStack.EMPTY;
            } else if (ForgerRecipes.isTarget(stack.getItem())) {
                if (!moveItemStackTo(stack, 1, 2, false)) return ItemStack.EMPTY;
            } else {
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
        return stillValid(access, player, ModBlocks.FORGER);
    }
}
