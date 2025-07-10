package net.silentchaos512.loginar.item.container;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class ContainerItemScreen extends AbstractContainerScreen<ContainerItemMenu> {
    private static final ResourceLocation TEXTURE = ResourceLocation.withDefaultNamespace("textures/gui/container/generic_54.png");

    private final Inventory playerInventory;
    private final int inventoryRows;

    public ContainerItemScreen(ContainerItemMenu container, Inventory playerInventory, Component title) {
        super(container, playerInventory, title);
        this.playerInventory = playerInventory;
        this.inventoryRows = container.getInventoryRows();
        this.imageHeight = 114 + this.inventoryRows * 18;
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float p_97788_, int p_97789_, int p_97790_) {
        if (minecraft == null) return;
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        graphics.blit(RenderType::guiTextured, TEXTURE, i, j, 0, 0, this.imageWidth, this.inventoryRows * 18 + 17, 256, 256);
        graphics.blit(RenderType::guiTextured, TEXTURE, i, j + this.inventoryRows * 18 + 17, 0, 126, this.imageWidth, 96, 256, 256);
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        super.render(graphics, mouseX, mouseY, partialTicks);
        this.renderTooltip(graphics, mouseX, mouseY);
    }

    @Override
    protected void renderLabels(GuiGraphics graphics, int x, int y) {
        graphics.drawString(this.font, title.getString(), 8, 6, 4210752, false);
        graphics.drawString(this.font, playerInventory.getDisplayName().getString(), 8, this.imageHeight - 96 + 2, 4210752, false);
    }
}
