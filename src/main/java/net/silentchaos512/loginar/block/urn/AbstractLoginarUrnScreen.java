package net.silentchaos512.loginar.block.urn;

import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import net.silentchaos512.loginar.LoginarMod;
import net.silentchaos512.loginar.util.UrnSize;

public abstract class AbstractLoginarUrnScreen<T extends AbstractLoginarUrnMenu> extends AbstractContainerScreen<T> {
    public static final Identifier TEXTURE_URN_9X6 = LoginarMod.getId("textures/gui/urn.png");
    public static final Identifier TEXTURE_URN_9X9 = LoginarMod.getId("textures/gui/urn_9x9.png");
    public static final Identifier TEXTURE_URN_12X9 = LoginarMod.getId("textures/gui/urn_12x9.png");
    public static final Identifier TEXTURE_URN_SWAP_9X6 = LoginarMod.getId("textures/gui/urn.png");
    public static final Identifier TEXTURE_URN_SWAP_9X9 = LoginarMod.getId("textures/gui/urn_9x9.png");
    public static final Identifier TEXTURE_URN_SWAP_12X9 = LoginarMod.getId("textures/gui/urn_12x9.png");

    private static final int WIDTH_9X = 176;
    private static final int WIDTH_12X = 236;
    private static final int HEIGHT_X6 = 222;
    private static final int HEIGHT_X9 = 276;

    protected Identifier guiTexture = TEXTURE_URN_9X6;
    private boolean isFlexibleTexture = true;

    public AbstractLoginarUrnScreen(T menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    public boolean isFlexibleTexture() {
        return isFlexibleTexture;
    }

    public void setGuiTexture(UrnSize size, boolean isSwapper) {
        if (isSwapper) {
            this.guiTexture = getSwapGuiTexture(size);
        } else {
            this.guiTexture = getGuiTexture(size);
        }
        this.isFlexibleTexture = true;

        if (size.width() == 12) {
            this.imageWidth = WIDTH_12X;
            this.isFlexibleTexture = false;
        } else {
            this.imageWidth = WIDTH_9X;
        }

        if (size.height() == 9) {
            this.imageHeight = HEIGHT_X9;
            this.isFlexibleTexture = false;
        } else {
            this.imageHeight = HEIGHT_X6;
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
