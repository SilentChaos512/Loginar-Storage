package net.silentchaos512.loginar.block.urn;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class LoginarUrnBackpackScreen extends AbstractLoginarUrnScreen<LoginarUrnBackpackMenu> {
    private final int containerRows;

    public LoginarUrnBackpackScreen(LoginarUrnBackpackMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        this.containerRows = menu.getRowCount();
        this.inventoryLabelY = this.imageHeight - 94;
        setGuiTexture(menu.urnType().size(), false);
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
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
}
