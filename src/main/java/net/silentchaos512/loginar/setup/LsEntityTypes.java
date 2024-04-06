package net.silentchaos512.loginar.setup;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.silentchaos512.loginar.LoginarMod;
import net.silentchaos512.loginar.entity.LoginarEntity;

public class LsEntityTypes {
    public static final DeferredRegister<EntityType<?>> REGISTER = DeferredRegister.create(Registries.ENTITY_TYPE, LoginarMod.MOD_ID);

    public static final DeferredHolder<EntityType<?>, EntityType<LoginarEntity>> LOGINAR = REGISTER.register("loginar", () ->
            EntityType.Builder.of(LoginarEntity::new, MobCategory.MONSTER)
                    .sized(0.875f, 1.25f)
                    .fireImmune()
                    .build(LoginarMod.getId("loginar").toString()));
}
