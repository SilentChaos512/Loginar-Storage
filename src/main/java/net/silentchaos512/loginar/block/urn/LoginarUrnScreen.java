package net.silentchaos512.loginar.block.urn;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class LoginarUrnScreen extends AbstractContainerScreen<LoginarUrnMenu> {
    private static final ResourceLocation CONTAINER_TEXTURE = new ResourceLocation("textures/gui/container/shulker_box.png");

    public LoginarUrnScreen(LoginarUrnMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        ++this.imageHeight;
    }

    public void render(PoseStack stack, int p_99250_, int p_99251_, float p_99252_) {
        this.renderBackground(stack);
        super.render(stack, p_99250_, p_99251_, p_99252_);
        this.renderTooltip(stack, p_99250_, p_99251_);
    }

    @Override
    protected void renderBg(PoseStack stack, float p_97788_, int p_97789_, int p_97790_) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, CONTAINER_TEXTURE);
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        this.blit(stack, i, j, 0, 0, this.imageWidth, this.imageHeight);
    }
}
