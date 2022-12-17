package net.silentchaos512.loginar.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.silentchaos512.loginar.LoginarMod;
import org.lwjgl.glfw.GLFW;

@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class KeyTracker {
    public static final KeyMapping SWAP_URN_ITEMS = createKeyBinding("swapUrnItems", GLFW.GLFW_KEY_R);

    private static KeyMapping createKeyBinding(String description, int key) {
        return new KeyMapping(
                "key." + LoginarMod.MOD_ID + "." + description,
                KeyConflictContext.GUI,
                InputConstants.Type.KEYSYM,
                key,
                "key.categories." + LoginarMod.MOD_ID
        );
    }

    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key event) {
        if (event.getAction() == GLFW.GLFW_PRESS && event.getKey() == SWAP_URN_ITEMS.getKey().getValue()) {
            handleSwapUrnItemsKeyPress();
        }
    }

    private static void handleSwapUrnItemsKeyPress() {
        // FIXME: This is disabled for publishing 0.2.0 because it doesn't work properly yet
//        LsNetwork.channel.sendToServer(new OpenUrnSwapperPacket());
    }
}
