package net.silentchaos512.loginar.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.silentchaos512.lib.util.Color;
import net.silentchaos512.loginar.LoginarMod;
import net.silentchaos512.loginar.client.model.LoginarModel;
import net.silentchaos512.loginar.entity.LoginarEntity;

// The glowy ball portion on the end of the loginar entity model's antennae
public class LoginarAntennaeLayer extends EyesLayer<LoginarEntity, LoginarModel> {
    private static final RenderType LOGINAR_ANTENNAE = RenderType.eyes(LoginarMod.getId("textures/entity/loginar_antennae.png"));

    public LoginarAntennaeLayer(RenderLayerParent<LoginarEntity, LoginarModel> parent) {
        super(parent);
    }

    @Override
    public RenderType renderType() {
        return LOGINAR_ANTENNAE;
    }

    @Override
    public void render(PoseStack stack, MultiBufferSource multiBufferSource, int par3, LoginarEntity entity, float par5, float par6, float par7, float par8, float par9, float par10) {
        VertexConsumer vertexconsumer = multiBufferSource.getBuffer(this.renderType());
        // TODO: The color could be changed to any color based on properties of the entity.
        //  Might be a fun easter egg to add in the future?
        Color color = new Color(0x99FFFF);
        this.getParentModel().renderToBuffer(
                stack,
                vertexconsumer,
                15728640,
                OverlayTexture.NO_OVERLAY,
                color.getColor()
        );
    }
}
