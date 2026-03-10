package net.silentchaos512.loginar.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
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
    private static final RenderType LOGINAR_ANTENNAE = RenderTypes.eyes(LoginarMod.getId("textures/entity/loginar_antennae.png"));

    public LoginarAntennaeLayer(RenderLayerParent<S, LoginarModel<S>> parent) {
        super(parent);
    }

    @Override
    public RenderType renderType() {
        return LOGINAR_ANTENNAE;
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector nodeCollector, int packedLight, S state, float p_433542_, float p_435619_) {
        // TODO: The color could be changed to any color based on properties of the entity.
        //  Might be a fun easter egg to add in the future?
        int eyeColor = NORMAL_COLOR.getColor();
        nodeCollector.order(1)
                .submitModel(
                        this.getParentModel(), state, poseStack, this.renderType(), packedLight, OverlayTexture.NO_OVERLAY, eyeColor, null, state.outlineColor, null
                );
    }
}
