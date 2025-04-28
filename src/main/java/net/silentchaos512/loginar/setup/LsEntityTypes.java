package net.silentchaos512.loginar.setup;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.silentchaos512.loginar.LoginarMod;
import net.silentchaos512.loginar.entity.FriendlyLoginar;
import net.silentchaos512.loginar.entity.WildLoginar;

public class LsEntityTypes {
    public static final DeferredRegister<EntityType<?>> REGISTER = DeferredRegister.create(Registries.ENTITY_TYPE, LoginarMod.MOD_ID);

    public static final DeferredHolder<EntityType<?>, EntityType<WildLoginar>> LOGINAR = REGISTER.register("loginar", () ->
            EntityType.Builder.of(WildLoginar::new, MobCategory.MONSTER)
                    .sized(0.875f, 1.25f)
                    .fireImmune()
                    .build(LoginarMod.getId("loginar").toString()));

    public static final DeferredHolder<EntityType<?>, EntityType<FriendlyLoginar>> FRIENDLY_LOGINAR = REGISTER.register("friendly_loginar", () ->
            EntityType.Builder.of(FriendlyLoginar::new, MobCategory.CREATURE)
                    .sized(0.875f, 1.25f)
                    .fireImmune()
                    .build(LoginarMod.getId("friendly_loginar").toString()));
}
