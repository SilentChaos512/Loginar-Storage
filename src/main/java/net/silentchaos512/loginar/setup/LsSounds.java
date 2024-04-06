package net.silentchaos512.loginar.setup;

import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.silentchaos512.loginar.LoginarMod;

public class LsSounds {
    public static final DeferredRegister<SoundEvent> REGISTER = DeferredRegister.create(Registries.SOUND_EVENT, LoginarMod.MOD_ID);

    public static final DeferredHolder<SoundEvent, SoundEvent> LOGINAR_ATTACK = register("entity.loginar.attack");
    public static final DeferredHolder<SoundEvent, SoundEvent> LOGINAR_DEATH = register("entity.loginar.death");
    public static final DeferredHolder<SoundEvent, SoundEvent> LOGINAR_HURT = register("entity.loginar.hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> LOGINAR_IDLE = register("entity.loginar.idle");
    public static final DeferredHolder<SoundEvent, SoundEvent> URN_LID = register("block.loginar.urn.lid");
    public static final DeferredHolder<SoundEvent, SoundEvent> URN_OPEN = register("block.loginar.urn.open");

    private static DeferredHolder<SoundEvent, SoundEvent> register(String name) {
        return REGISTER.register(name, () -> SoundEvent.createVariableRangeEvent(LoginarMod.getId(name)));
    }
}
