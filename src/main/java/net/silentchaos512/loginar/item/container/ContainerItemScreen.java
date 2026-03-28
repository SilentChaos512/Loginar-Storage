package net.silentchaos512.loginar.item.container;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;

public class ContainerItemScreen extends AbstractContainerScreen<ContainerItemMenu> {
    private static final Identifier TEXTURE = Identifier.withDefaultNamespace("textures/gui/container/generic_54.png");

    private final Inventory playerInventory;
    private final int inventoryRows;

    public ContainerItemScreen(ContainerItemMenu container, Inventory playerInventory, Component title) {
        var rows = container.getInventoryRows();
        super(container, playerInventory, container.getItem().getHoverName(), 176, 114 + rows * 18);
        this.playerInventory = playerInventory;
        this.inventoryRows = rows;
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;
        graphics.blit(RenderPipelines.GUI_TEXTURED, TEXTURE, x, y, 0, 0, this.imageWidth, this.inventoryRows * 18 + 17, 256, 256);
        graphics.blit(RenderPipelines.GUI_TEXTURED, TEXTURE, x, y + this.inventoryRows * 18 + 17, 0, 126, this.imageWidth, 96, 256, 256);
    }

//    @Override
//    protected void extractLabels(GuiGraphicsExtractor graphics, int xm, int ym) {
//        graphics.text(this.font, title.getString(), 8, 6, 0xFF404040, false);
//        graphics.text(this.font, playerInventory.getDisplayName().getString(), 8, this.imageHeight - 96 + 2, 0xFF404040, false);
//    }
}
