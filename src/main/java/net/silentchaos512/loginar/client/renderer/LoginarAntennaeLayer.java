package net.silentchaos512.loginar.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.LivingEntity;
import net.silentchaos512.lib.util.Color;
import net.silentchaos512.loginar.LoginarMod;
import net.silentchaos512.loginar.client.model.LoginarModel;
import net.silentchaos512.loginar.client.renderer.state.LoginarRenderState;
import net.silentchaos512.loginar.entity.Loginar;

// The glowy ball portion on the end of the loginar entity model's antennae
public class LoginarAntennaeLayer<T extends LivingEntity & Loginar, S extends LoginarRenderState> extends EyesLayer<S, LoginarModel<S>> {
    public static final Color NORMAL_COLOR = new Color(0x99FFFF);
    private static final RenderType LOGINAR_ANTENNAE = RenderType.eyes(LoginarMod.getId("textures/entity/loginar_antennae.png"));

    public LoginarAntennaeLayer(RenderLayerParent<S, LoginarModel<S>> parent) {
        super(parent);
    }

    @Override
    public RenderType renderType() {
        return LOGINAR_ANTENNAE;
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource multiBufferSource, int part3, S state, float par5, float par6) {
        VertexConsumer vertexconsumer = multiBufferSource.getBuffer(this.renderType());
        // TODO: The color could be changed to any color based on properties of the entity.
        //  Might be a fun easter egg to add in the future?
        this.getParentModel().renderToBuffer(
                poseStack,
                vertexconsumer,
                15728640,
                OverlayTexture.NO_OVERLAY,
                NORMAL_COLOR.getColor()
        );
    }
}
