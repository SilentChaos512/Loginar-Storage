package net.silentchaos512.loginar.client.renderer;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Mob;
import net.silentchaos512.loginar.LoginarMod;
import net.silentchaos512.loginar.client.model.LoginarModel;
import net.silentchaos512.loginar.entity.Loginar;

public class LoginarRenderer<T extends Mob & Loginar> extends MobRenderer<T, LoginarModel<T>> {
    private static final ResourceLocation TEXTURE = LoginarMod.getId("textures/entity/loginar.png");

    public LoginarRenderer(EntityRendererProvider.Context context) {
        super(context, new LoginarModel<>(context.bakeLayer(LoginarModel.LAYER_LOCATION)), 0.5f);
        this.addLayer(new LoginarAntennaeLayer<>(this));
    }

    @Override
    public ResourceLocation getTextureLocation(T entity) {
        return TEXTURE;
    }
}
