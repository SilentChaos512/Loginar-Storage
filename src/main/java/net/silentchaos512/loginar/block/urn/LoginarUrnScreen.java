package net.silentchaos512.loginar.block.urn;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class LoginarUrnScreen extends AbstractLoginarUrnScreen<LoginarUrnMenu> {
    private final int containerRows;

    public LoginarUrnScreen(LoginarUrnMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        this.containerRows = menu.getRowCount();
        this.imageHeight = 114 + this.containerRows * 18;
        this.inventoryLabelY = this.imageHeight - 94;
        setGuiTexture(menu.urnType().size(), false);
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float p_97788_, int p_97789_, int p_97790_) {
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        if (this.isFlexibleTexture()) {
            graphics.blit(this.guiTexture, i, j, 0, 0, this.imageWidth, this.containerRows * 18 + 17, 256, 256);
            graphics.blit(this.guiTexture, i, j + this.containerRows * 18 + 17, 0, 126, this.imageWidth, 96, 256, 256);
        } else {
            // Note the non-standard texture height of 276
            graphics.blit(this.guiTexture, i, j, 0, 0, this.imageWidth, this.imageHeight, 256, 276);
        }
    }

    @Override
    public void render(GuiGraphics graphics, int pMouseX, int pMouseY, float pPartialTick) {
        super.render(graphics, pMouseX, pMouseY, pPartialTick);
        this.renderTooltip(graphics, pMouseX, pMouseY);
    }
}
