package net.silentchaos512.loginar.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.silentchaos512.loginar.LoginarMod;
import net.silentchaos512.loginar.block.urn.LoginarUrnSwapperMenu;
import net.silentchaos512.utils.Color;

// TODO: Make it so JEI doesn't show on the side...
public class LoginarUrnSwapperScreen extends AbstractContainerScreen<LoginarUrnSwapperMenu> {
    private static final ResourceLocation TEXTURE = LoginarMod.getId("textures/gui/urn_swap.png");

    private final int inventoryRows;

    public LoginarUrnSwapperScreen(LoginarUrnSwapperMenu container, Inventory playerInventory, Component title) {
        super(container, playerInventory, title);
        this.inventoryRows = container.getRowCount();
        this.imageWidth = 180;
        this.imageHeight = 119 + this.inventoryRows * 20;
    }

    @Override
    protected void renderLabels(PoseStack stack, int xIn, int yIn) {
        this.font.draw(stack, this.title, (float)this.titleLabelX, (float)this.titleLabelY, Color.VALUE_WHITE);
    }

    @Override
    protected void renderBg(PoseStack stack, float partialTicks, int xIn, int yIn) {
        if (minecraft == null) return;
        RenderSystem.clearColor(1, 1, 1, 1);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;
        blit(stack, x, y, 0, 0, this.imageWidth, 17 + this.inventoryRows * 20);
    }

    @Override
    public boolean mouseClicked(double p_97748_, double p_97749_, int p_97750_) {
        return super.mouseClicked(p_97748_, p_97749_, p_97750_);
    }
}
