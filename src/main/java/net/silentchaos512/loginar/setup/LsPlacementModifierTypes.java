package net.silentchaos512.loginar.setup;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.silentchaos512.loginar.LoginarMod;
import net.silentchaos512.loginar.level.placement.LoginarChunkFilter;

public class LsPlacementModifierTypes {
    public static final DeferredRegister<PlacementModifierType<?>> REGISTER = DeferredRegister.create(Registries.PLACEMENT_MODIFIER_TYPE, LoginarMod.MOD_ID);

    public static final DeferredHolder<PlacementModifierType<?>, PlacementModifierType<LoginarChunkFilter>> LOGINAR_CHUNK = REGISTER.register(
            "loginar_chunk",
            id -> () -> LoginarChunkFilter.CODEC
    );
}
