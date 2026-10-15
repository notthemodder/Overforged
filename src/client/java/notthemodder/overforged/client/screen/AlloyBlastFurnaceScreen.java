package notthemodder.overforged.client.screen;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import notthemodder.overforged.AlloyBlastFurnaceMenu;

public final class AlloyBlastFurnaceScreen extends AbstractContainerScreen<AlloyBlastFurnaceMenu> {
    private static final Identifier TEXTURE = Identifier.withDefaultNamespace("textures/gui/container/blast_furnace.png");
    private static final Identifier LIT_PROGRESS = Identifier.withDefaultNamespace("container/blast_furnace/lit_progress");
    private static final Identifier BURN_PROGRESS = Identifier.withDefaultNamespace("container/blast_furnace/burn_progress");

    public AlloyBlastFurnaceScreen(AlloyBlastFurnaceMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title, 176, 166);
        titleLabelX = 8;
        titleLabelY = 6;
        inventoryLabelX = 8;
        inventoryLabelY = 72;
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float delta) {
        graphics.blit(RenderPipelines.GUI_TEXTURED, TEXTURE, leftPos, topPos, 0.0F, 0.0F, 176, 166, 256, 256);

        // Copy the vanilla input-slot frame to make room for the Alloy Furnace's second ingredient.
        graphics.blit(RenderPipelines.GUI_TEXTURED, TEXTURE,
                leftPos + 34, topPos + 17, 56.0F, 17.0F, 18, 18, 256, 256);

        int flameHeight = menu.getBurnProgress(14);
        if (flameHeight > 0) {
            graphics.blitSprite(RenderPipelines.GUI_TEXTURED, LIT_PROGRESS,
                    14, 14, 0, 14 - flameHeight,
                    leftPos + 56, topPos + 36 + 14 - flameHeight, 14, flameHeight);
        }

        int arrowWidth = menu.getCookProgress(24);
        if (arrowWidth > 0) {
            graphics.blitSprite(RenderPipelines.GUI_TEXTURED, BURN_PROGRESS,
                    24, 16, 0, 0, leftPos + 79, topPos + 34, arrowWidth, 16);
        }
    }
}
