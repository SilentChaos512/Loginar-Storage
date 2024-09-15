package net.silentchaos512.loginar.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.client.settings.KeyConflictContext;
import net.neoforged.neoforge.network.PacketDistributor;
import net.silentchaos512.loginar.LoginarMod;
import net.silentchaos512.loginar.network.OpenUrnForItemSwapPayload;
import net.silentchaos512.loginar.network.OpenBackpackUrnPayload;
import org.lwjgl.glfw.GLFW;

@EventBusSubscriber(value = Dist.CLIENT)
public class KeyTracker {
    public static final KeyMapping OPEN_BACKPACK = createKeyBinding("openBackpack", GLFW.GLFW_KEY_I);
    public static final KeyMapping SWAP_URN_ITEMS = createKeyBinding("swapUrnItems", GLFW.GLFW_KEY_X);

    private static KeyMapping createKeyBinding(String description, int key) {
        return new KeyMapping(
                "key." + LoginarMod.MOD_ID + "." + description,
                KeyConflictContext.IN_GAME,
                InputConstants.Type.KEYSYM,
                key,
                "key.category." + LoginarMod.MOD_ID
        );
    }

    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key event) {
        //noinspection ConstantConditions
        if (Minecraft.getInstance() == null || Minecraft.getInstance().getConnection() == null) {
            return;
        }

        if (event.getAction() == GLFW.GLFW_PRESS)
            if (event.getKey() == OPEN_BACKPACK.getKey().getValue()) {
                PacketDistributor.sendToServer(new OpenBackpackUrnPayload());
            } else if (event.getKey() == SWAP_URN_ITEMS.getKey().getValue()) {
                PacketDistributor.sendToServer(new OpenUrnForItemSwapPayload());
            }
    }
}
