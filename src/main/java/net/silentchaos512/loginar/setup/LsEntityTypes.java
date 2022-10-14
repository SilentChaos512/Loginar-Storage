package net.silentchaos512.loginar.setup;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.silentchaos512.loginar.LoginarMod;
import net.silentchaos512.loginar.entity.LoginarEntity;

public class LsEntityTypes {
    public static final DeferredRegister<EntityType<?>> REGISTER = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, LoginarMod.MOD_ID);

    public static final RegistryObject<EntityType<LoginarEntity>> LOGINAR = REGISTER.register("loginar", () ->
            EntityType.Builder.of(LoginarEntity::new, MobCategory.MONSTER)
                    .sized(0.875f, 1.25f)
                    .build(LoginarMod.getId("loginar").toString()));
}
