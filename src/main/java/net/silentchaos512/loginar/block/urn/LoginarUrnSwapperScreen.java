package net.silentchaos512.loginar.block.urn;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.silentchaos512.lib.util.Color;

// TODO: Make it so JEI doesn't show on the side...
public class LoginarUrnSwapperScreen extends AbstractLoginarUrnScreen<LoginarUrnSwapperMenu> {
    private final int inventoryRows;

    public LoginarUrnSwapperScreen(LoginarUrnSwapperMenu container, Inventory playerInventory, Component title) {
        var rowCount = container.getRowCount();
        super(container, playerInventory, title, 180, 119 + rowCount * 20);
        this.inventoryRows = rowCount;
        setGuiTexture(container.urnType().size(), true);
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;
        graphics.blit(this.guiTexture, x, y, 0, 0, this.imageWidth, 17 + this.inventoryRows * 20, 256, 256);
    }

    @Override
    protected void extractLabels(GuiGraphicsExtractor graphics, int xm, int ym) {
        graphics.text(this.font, this.title, this.titleLabelX, this.titleLabelY, Color.VALUE_WHITE);
    }
}
