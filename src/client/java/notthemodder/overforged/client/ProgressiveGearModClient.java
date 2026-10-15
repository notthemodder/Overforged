package notthemodder.overforged.client;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screens.MenuScreens;
import notthemodder.overforged.ModMenus;
import notthemodder.overforged.client.screen.AlloyBlastFurnaceScreen;
import notthemodder.overforged.client.screen.ForgerScreen;

/**
 * Client-side mod initializer. Registers the GUI screens for the
 * Alloy Blast Furnace and Forger menus.
 */
public class ProgressiveGearModClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		MenuScreens.register(ModMenus.ALLOY_BLAST_FURNACE, AlloyBlastFurnaceScreen::new);
		MenuScreens.register(ModMenus.FORGER, ForgerScreen::new);
	}
}
