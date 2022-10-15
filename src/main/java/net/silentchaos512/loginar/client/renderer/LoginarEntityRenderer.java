package net.silentchaos512.loginar.client.renderer;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.silentchaos512.loginar.LoginarMod;
import net.silentchaos512.loginar.client.model.LoginarModel;
import net.silentchaos512.loginar.entity.LoginarEntity;

public class LoginarEntityRenderer extends MobRenderer<LoginarEntity, LoginarModel> {
    private static final ResourceLocation TEXTURE = LoginarMod.getId("textures/entity/loginar.png");

    public LoginarEntityRenderer(EntityRendererProvider.Context context) {
        super(context, new LoginarModel(context.bakeLayer(LoginarModel.LAYER_LOCATION)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(LoginarEntity entity) {
        return TEXTURE;
    }
}
