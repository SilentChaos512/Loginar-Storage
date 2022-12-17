package net.silentchaos512.loginar.block.urn;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class LoginarUrnBackpackScreen extends AbstractContainerScreen<LoginarUrnBackpackMenu> {
    private static final ResourceLocation CONTAINER_TEXTURE = new ResourceLocation("textures/gui/container/generic_54.png");

    private final int containerRows;

    public LoginarUrnBackpackScreen(LoginarUrnBackpackMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        this.passEvents = false;
        int i = 222;
        int j = 114;
        this.containerRows = menu.getRowCount();
        this.imageHeight = 114 + this.containerRows * 18;
        this.inventoryLabelY = this.imageHeight - 94;
    }

    public void render(PoseStack stack, int p_98419_, int p_98420_, float p_98421_) {
        this.renderBackground(stack);
        super.render(stack, p_98419_, p_98420_, p_98421_);
        this.renderTooltip(stack, p_98419_, p_98420_);
    }

    protected void renderBg(PoseStack stack, float p_98414_, int p_98415_, int p_98416_) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, CONTAINER_TEXTURE);
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        this.blit(stack, i, j, 0, 0, this.imageWidth, this.containerRows * 18 + 17);
        this.blit(stack, i, j + this.containerRows * 18 + 17, 0, 126, this.imageWidth, 96);
    }
}
