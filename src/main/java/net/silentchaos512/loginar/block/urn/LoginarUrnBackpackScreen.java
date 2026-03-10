package net.silentchaos512.loginar.block.urn;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import net.silentchaos512.loginar.LoginarMod;

public class LoginarUrnBackpackScreen extends AbstractContainerScreen<LoginarUrnBackpackMenu> {
    private static final Identifier CONTAINER_TEXTURE = LoginarMod.getId("textures/gui/urn.png");

    private final int containerRows;

    public LoginarUrnBackpackScreen(LoginarUrnBackpackMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        this.containerRows = menu.getRowCount();
        this.imageHeight = 114 + this.containerRows * 18;
        this.inventoryLabelY = this.imageHeight - 94;
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float p_97788_, int p_97789_, int p_97790_) {
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        graphics.blit(RenderPipelines.GUI_TEXTURED, CONTAINER_TEXTURE, i, j, 0, 0, this.imageWidth, this.containerRows * 18 + 17, 256, 256);
        graphics.blit(RenderPipelines.GUI_TEXTURED, CONTAINER_TEXTURE, i, j + this.containerRows * 18 + 17, 0, 126, this.imageWidth, 96, 256, 256);
    }

    @Override
    public void render(GuiGraphics graphics, int pMouseX, int pMouseY, float pPartialTick) {
        super.render(graphics, pMouseX, pMouseY, pPartialTick);
        this.renderTooltip(graphics, pMouseX, pMouseY);
    }
}
