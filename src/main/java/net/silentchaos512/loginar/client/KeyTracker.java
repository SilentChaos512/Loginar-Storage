package net.silentchaos512.loginar.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.silentchaos512.loginar.LoginarMod;
import net.silentchaos512.loginar.network.LsNetwork;
import net.silentchaos512.loginar.network.OpenUrnSwapperPacket;
import org.lwjgl.glfw.GLFW;

@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class KeyTracker {
    public static final KeyMapping SWAP_URN_ITEMS = createKeyBinding("swapUrnItems", GLFW.GLFW_KEY_X);

    private static KeyMapping createKeyBinding(String description, int key) {
        return new KeyMapping(
                "key." + LoginarMod.MOD_ID + "." + description,
                KeyConflictContext.IN_GAME,
                InputConstants.Type.KEYSYM,
                key,
                "key.categories." + LoginarMod.MOD_ID
        );
    }

    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key event) {
        //noinspection ConstantConditions
        if (Minecraft.getInstance() == null || Minecraft.getInstance().getConnection() == null) {
            return;
        }

        if (event.getAction() == GLFW.GLFW_PRESS && event.getKey() == SWAP_URN_ITEMS.getKey().getValue()) {
            handleSwapUrnItemsKeyPress();
        }
    }

    private static void handleSwapUrnItemsKeyPress() {
        LsNetwork.channel.sendToServer(new OpenUrnSwapperPacket());
    }
}
