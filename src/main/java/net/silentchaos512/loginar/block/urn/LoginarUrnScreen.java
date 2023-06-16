package net.silentchaos512.loginar.block.urn;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.silentchaos512.loginar.LoginarMod;

public class LoginarUrnScreen extends AbstractContainerScreen<LoginarUrnMenu> {
    private static final ResourceLocation CONTAINER_TEXTURE = LoginarMod.getId("textures/gui/urn.png");

    private final int containerRows;

    public LoginarUrnScreen(LoginarUrnMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        this.containerRows = menu.getRowCount();
        this.imageHeight = 114 + this.containerRows * 18;
        this.inventoryLabelY = this.imageHeight - 94;
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float p_97788_, int p_97789_, int p_97790_) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, CONTAINER_TEXTURE);
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        graphics.blit(CONTAINER_TEXTURE, i, j, 0, 0, this.imageWidth, this.containerRows * 18 + 17);
        graphics.blit(CONTAINER_TEXTURE, i, j + this.containerRows * 18 + 17, 0, 126, this.imageWidth, 96);
    }

    @Override
    public void render(GuiGraphics graphics, int p_98419_, int p_98420_, float p_98421_) {
        this.renderBackground(graphics);
        super.render(graphics, p_98419_, p_98420_, p_98421_);
        this.renderTooltip(graphics, p_98419_, p_98420_);
    }
}
