package notthemodder.overforged;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

/**
 * Defines the valid catalysts and targets for the Forger, and resolves
 * the output item for a given catalyst/target combination.
 */
public final class ForgerRecipes {
    private ForgerRecipes() {}

    /**
     * Returns {@code true} if the given item is a valid Forger catalyst
     * (Crystal, Ruby, or vanilla Emerald).
     *
     * @param item the item to check
     */
    public static boolean isCatalyst(Item item) {
        return item == ModItems.CRYSTAL || item == ModItems.RUBY || item == Items.EMERALD;
    }

    /**
     * Returns {@code true} if the given item is a valid Forger target
     * (a registered mod ingot or a base mod sword).
     *
     * @param item the item to check
     */
    public static boolean isTarget(Item item) {
        return ModForgerItems.isIngot(item) || ModEquipment.isModSword(item);
    }

    /**
     * Resolves the Forger output for the given catalyst and target stacks.
     * Returns {@link ItemStack#EMPTY} if no recipe matches.
     *
     * @param catalyst the catalyst stack (slot 0)
     * @param target   the target stack (slot 1)
     * @return the resulting {@link ItemStack}, or empty
     */
    public static ItemStack result(ItemStack catalyst, ItemStack target) {
        if (catalyst.isEmpty() || target.isEmpty()) return ItemStack.EMPTY;

        Item catalystItem = catalyst.getItem();
        Item targetItem = target.getItem();
        if (catalystItem == ModItems.CRYSTAL) {
            Item ingot = ModForgerItems.crystallizedIngot(targetItem);
            if (ingot != null) return new ItemStack(ingot);
        }

        Item sword = ModEquipment.enhancedSword(targetItem, catalystItem);
        return sword == null ? ItemStack.EMPTY : new ItemStack(sword);
    }
}
