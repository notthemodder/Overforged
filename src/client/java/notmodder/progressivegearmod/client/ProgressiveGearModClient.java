package notmodder.progressivegearmod.client;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screens.MenuScreens;
import notmodder.progressivegearmod.ModMenus;
import notmodder.progressivegearmod.client.screen.AlloyBlastFurnaceScreen;
import notmodder.progressivegearmod.client.screen.ForgerScreen;

public class ProgressiveGearModClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		MenuScreens.register(ModMenus.ALLOY_BLAST_FURNACE, AlloyBlastFurnaceScreen::new);
		MenuScreens.register(ModMenus.FORGER, ForgerScreen::new);
	}
}
