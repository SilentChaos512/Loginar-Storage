package net.silentchaos512.loginar.setup;

import com.mojang.serialization.Codec;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.world.item.component.ItemContainerContents;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.silentchaos512.lib.util.Color;
import net.silentchaos512.loginar.LoginarMod;

import java.util.function.Supplier;

public class LsDataComponents {
    public static final DeferredRegister.DataComponents REGISTRAR = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, LoginarMod.MOD_ID);

    public static final Supplier<DataComponentType<ItemContainerContents>> CONTAINED_ITEMS = REGISTRAR.registerComponentType(
            "contained_items",
            builder -> builder
                    .persistent(ItemContainerContents.CODEC)
                    .networkSynchronized(ItemContainerContents.STREAM_CODEC)
    );
    public static final Supplier<DataComponentType<ItemContainerContents>> URN_UPGRADES = REGISTRAR.registerComponentType(
            "urn_upgrades",
            builder -> builder
                    .persistent(ItemContainerContents.CODEC)
                    .networkSynchronized(ItemContainerContents.STREAM_CODEC)
    );
    public static final Supplier<DataComponentType<Color>> URN_CLAY_COLOR = REGISTRAR.registerComponentType(
            "urn_clay_color",
            builder -> builder
                    .persistent(Color.CODEC)
                    .networkSynchronized(Color.STREAM_CODEC)
    );
    public static final Supplier<DataComponentType<Color>> URN_GEM_COLOR = REGISTRAR.registerComponentType(
            "urn_gem_color",
            builder -> builder
                    .persistent(Color.CODEC)
                    .networkSynchronized(Color.STREAM_CODEC)
    );
    public static final Supplier<DataComponentType<Integer>> USE_SLOT = REGISTRAR.registerComponentType(
            "use_slot",
            builder -> builder
                    .persistent(Codec.INT)
                    .networkSynchronized(ByteBufCodecs.VAR_INT)
    );
}
