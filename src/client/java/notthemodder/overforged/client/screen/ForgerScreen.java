package notthemodder.overforged.client.screen;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;

import notthemodder.overforged.ForgerMenu;

public final class ForgerScreen extends AbstractContainerScreen<ForgerMenu> {
    private static final Identifier TEXTURE = Identifier.withDefaultNamespace("textures/gui/container/anvil.png");

    public ForgerScreen(ForgerMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title, 176, 166);
        titleLabelX = 8;
        titleLabelY = 6;
        inventoryLabelX = 8;
        inventoryLabelY = 72;
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float delta) {
        graphics.blit(RenderPipelines.GUI_TEXTURED, TEXTURE, leftPos, topPos,
                0.0F, 0.0F, 176, 166, 256, 256);
    }
}
