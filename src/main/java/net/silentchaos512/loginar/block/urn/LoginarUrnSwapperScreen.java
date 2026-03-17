package net.silentchaos512.loginar.block.urn;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.silentchaos512.lib.util.Color;

// TODO: Make it so JEI doesn't show on the side...
public class LoginarUrnSwapperScreen extends AbstractLoginarUrnScreen<LoginarUrnSwapperMenu> {
    private final int inventoryRows;

    public LoginarUrnSwapperScreen(LoginarUrnSwapperMenu container, Inventory playerInventory, Component title) {
        super(container, playerInventory, title);
        this.inventoryRows = container.getRowCount();
        this.imageWidth = 180;
        this.imageHeight = 119 + this.inventoryRows * 20;
        setGuiTexture(container.urnType().size(), true);
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        this.renderTooltip(pGuiGraphics, pMouseX, pMouseY);
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float p_97788_, int p_97789_, int p_97790_) {
        if (minecraft == null) return;
        RenderSystem.clearColor(1, 1, 1, 1);
        RenderSystem.setShaderTexture(0, this.guiTexture);
        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;
        graphics.blit(this.guiTexture, x, y, 0, 0, this.imageWidth, 17 + this.inventoryRows * 20);
    }

    @Override
    protected void renderLabels(GuiGraphics graphics, int xIn, int yIn) {
        graphics.drawString(this.font, this.title, this.titleLabelX, this.titleLabelY, Color.VALUE_WHITE);
    }

    @Override
    public boolean mouseClicked(double p_97748_, double p_97749_, int p_97750_) {
        return super.mouseClicked(p_97748_, p_97749_, p_97750_);
    }
}
