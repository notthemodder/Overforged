package notthemodder.overforged;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;

/**
 * Registers the {@link AlloyBlastFurnaceMenu} and {@link ForgerMenu} menu types.
 * Must be referenced during mod initialization to ensure registration occurs
 * before registries freeze.
 */
public final class ModMenus {
    private static final ResourceKey<MenuType<?>> KEY = ResourceKey.create(Registries.MENU, ProgressiveGearMod.id("alloy_blast_furnace"));
    public static final MenuType<AlloyBlastFurnaceMenu> ALLOY_BLAST_FURNACE = Registry.register(
            BuiltInRegistries.MENU, KEY,
            new MenuType<>(AlloyBlastFurnaceMenu::new, FeatureFlags.VANILLA_SET));
    private static final ResourceKey<MenuType<?>> FORGER_KEY = ResourceKey.create(Registries.MENU, ProgressiveGearMod.id("forger"));
    public static final MenuType<ForgerMenu> FORGER = Registry.register(
            BuiltInRegistries.MENU, FORGER_KEY,
            new MenuType<>(ForgerMenu::new, FeatureFlags.VANILLA_SET));

    private ModMenus() {}

    public static void initialize() {
        // Referencing this class during mod initialization registers the menu type before registries freeze.
    }
}
