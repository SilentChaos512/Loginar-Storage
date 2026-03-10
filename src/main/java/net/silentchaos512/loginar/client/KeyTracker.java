package net.silentchaos512.loginar.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;
import net.neoforged.neoforge.client.settings.KeyConflictContext;
import net.silentchaos512.loginar.LoginarMod;
import net.silentchaos512.loginar.network.OpenBackpackUrnPayload;
import net.silentchaos512.loginar.network.OpenUrnForItemSwapPayload;
import org.lwjgl.glfw.GLFW;

@EventBusSubscriber(value = Dist.CLIENT)
public class KeyTracker {
    private static final KeyMapping.Category CATEGORY = new KeyMapping.Category(LoginarMod.getId("keys"));
    public static final KeyMapping OPEN_BACKPACK = createKeyBinding("openBackpack", GLFW.GLFW_KEY_I);
    public static final KeyMapping SWAP_URN_ITEMS = createKeyBinding("swapUrnItems", GLFW.GLFW_KEY_X);

    private static KeyMapping createKeyBinding(String description, int key) {
        return new KeyMapping(
                "key." + LoginarMod.MOD_ID + "." + description,
                KeyConflictContext.IN_GAME,
                InputConstants.Type.KEYSYM,
                key,
                CATEGORY
        );
    }

    @SubscribeEvent
    public static void onRegisterKeyMappings(RegisterKeyMappingsEvent event) {
        event.registerCategory(CATEGORY);

//        event.register(OPEN_BACKPACK);
//        event.register(SWAP_URN_ITEMS);
    }

    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key event) {
        //noinspection ConstantConditions
        if (Minecraft.getInstance() == null || Minecraft.getInstance().getConnection() == null) {
            return;
        }

        if (event.getAction() == GLFW.GLFW_PRESS && Minecraft.getInstance().screen == null)
            if (event.getKey() == OPEN_BACKPACK.getKey().getValue()) {
                ClientPacketDistributor.sendToServer(new OpenBackpackUrnPayload());
            } else if (event.getKey() == SWAP_URN_ITEMS.getKey().getValue()) {
                ClientPacketDistributor.sendToServer(new OpenUrnForItemSwapPayload());
            }
    }
}
