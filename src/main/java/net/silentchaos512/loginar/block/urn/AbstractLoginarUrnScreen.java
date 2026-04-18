package net.silentchaos512.loginar.block.urn;

import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import net.silentchaos512.loginar.LoginarMod;
import net.silentchaos512.loginar.util.UrnScreenRenderInfo;
import net.silentchaos512.loginar.util.UrnSize;

public abstract class AbstractLoginarUrnScreen<T extends AbstractLoginarUrnMenu> extends AbstractContainerScreen<T> {
    public static final Identifier TEXTURE_URN_9X6 = LoginarMod.getId("textures/gui/urn.png");
    public static final Identifier TEXTURE_URN_9X9 = LoginarMod.getId("textures/gui/urn_9x9.png");
    public static final Identifier TEXTURE_URN_12X9 = LoginarMod.getId("textures/gui/urn_12x9.png");
    public static final Identifier TEXTURE_URN_SWAP_9X6 = LoginarMod.getId("textures/gui/urn_swap.png");
    public static final Identifier TEXTURE_URN_SWAP_9X9 = LoginarMod.getId("textures/gui/urn_swap_9x9.png");
    public static final Identifier TEXTURE_URN_SWAP_12X9 = LoginarMod.getId("textures/gui/urn_swap_12x9.png");

    protected Identifier guiTexture = TEXTURE_URN_9X6;
    protected UrnScreenRenderInfo urnScreenRenderInfo;

    public AbstractLoginarUrnScreen(T menu, Inventory playerInventory, Component title) {
        var type = menu.urnType;
        var renderInfo = type.renderInfo();
        this(menu, playerInventory, title, renderInfo.textureWidth(), renderInfo.textureHeight(type.size().height()));
    }

    public AbstractLoginarUrnScreen(T menu, Inventory playerInventory, Component title, int imageWidth, int imageHeight) {
        super(menu, playerInventory, title, imageWidth, imageHeight);
        this.urnScreenRenderInfo = menu.urnType.renderInfo();
    }

    public boolean isFlexibleTexture() {
        return this.urnScreenRenderInfo.isFlexibleTexture();
    }

    public void setGuiTexture(UrnSize size, boolean isSwapper) {
        if (isSwapper) {
            this.guiTexture = getSwapGuiTexture(size);
        } else {
            this.guiTexture = getGuiTexture(size);
        }
    }

    public static Identifier getGuiTexture(UrnSize size) {
        if (size.height() == 9) {
            if (size.width() == 12) {
                return TEXTURE_URN_12X9;
            } else if (size.width() == 9) {
                return TEXTURE_URN_9X9;
            }
        }
        return TEXTURE_URN_9X6;
    }

    public static Identifier getSwapGuiTexture(UrnSize size) {
        if (size.height() == 9) {
            if (size.width() == 12) {
                return TEXTURE_URN_SWAP_12X9;
            } else if (size.width() == 9) {
                return TEXTURE_URN_SWAP_9X9;
            }
        }
        return TEXTURE_URN_SWAP_9X6;
    }
}
