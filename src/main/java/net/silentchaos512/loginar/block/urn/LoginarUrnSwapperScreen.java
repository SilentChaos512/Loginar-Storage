package net.silentchaos512.loginar.block.urn;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

// TODO: Make it so JEI doesn't show on the side...
public class LoginarUrnSwapperScreen extends AbstractLoginarUrnScreen<LoginarUrnSwapperMenu> {
    private final int inventoryRows;

    public LoginarUrnSwapperScreen(LoginarUrnSwapperMenu container, Inventory playerInventory, Component title) {
        var rowCount = container.getRowCount();
        var type = container.urnType();
        super(container, playerInventory, title, type.size().width() * 20, 40 + type.size().height() * 20);
        this.inventoryRows = rowCount;
        setGuiTexture(type.size(), true);
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        super.extractBackground(graphics, mouseX, mouseY, a);
        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;
        graphics.blit(RenderPipelines.GUI_TEXTURED, this.guiTexture, x, y, 0f, 0f, this.imageWidth, 17 + this.inventoryRows * 20, 256, 256);
    }

    @Override
    protected void extractLabels(GuiGraphicsExtractor graphics, int xm, int ym) {
        graphics.text(this.font, this.title, this.titleLabelX, this.titleLabelY, -1);
    }
}
