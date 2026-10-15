package notmodder.progressivegearmod;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public final class ForgerRecipes {
    private ForgerRecipes() {}

    public static boolean isCatalyst(Item item) {
        return item == ModItems.CRYSTAL || item == ModItems.RUBY || item == Items.EMERALD;
    }

    public static boolean isTarget(Item item) {
        return ModForgerItems.isIngot(item) || ModEquipment.isModSword(item);
    }

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
