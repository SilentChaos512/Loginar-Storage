package net.silentchaos512.loginar.setup;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;
import net.minecraftforge.registries.RegistryObject;
import net.silentchaos512.gear.SilentGear;
import net.silentchaos512.loginar.LoginarMod;

public class LsSounds {
    public static final DeferredRegister<SoundEvent> REGISTER = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, LoginarMod.MOD_ID);

    public static final RegistryObject<SoundEvent> LOGINAR_ATTACK = register("entity.loginar.attack");
    public static final RegistryObject<SoundEvent> LOGINAR_DEATH = register("entity.loginar.death");
    public static final RegistryObject<SoundEvent> LOGINAR_HURT = register("entity.loginar.hurt");
    public static final RegistryObject<SoundEvent> LOGINAR_IDLE = register("entity.loginar.idle");
    public static final RegistryObject<SoundEvent> URN_LID = register("block.loginar.urn.lid");
    public static final RegistryObject<SoundEvent> URN_OPEN = register("block.loginar.urn.open");

    private static RegistryObject<SoundEvent> register(String name) {
        return REGISTER.register(name, () -> new SoundEvent(LoginarMod.getId(name)));
    }
}
