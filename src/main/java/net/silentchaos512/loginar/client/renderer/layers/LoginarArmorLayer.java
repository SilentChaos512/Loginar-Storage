package net.silentchaos512.loginar.client.renderer.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EquipmentLayerRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.equipment.Equippable;
import net.silentchaos512.loginar.LoginarMod;
import net.silentchaos512.loginar.client.model.LoginarModel;
import net.silentchaos512.loginar.client.renderer.state.LoginarRenderState;
import net.silentchaos512.loginar.util.Const;

public class LoginarArmorLayer extends RenderLayer<LoginarRenderState, LoginarModel<LoginarRenderState>> {
    public static final ModelLayerLocation MODEL_LAYER = new ModelLayerLocation(LoginarMod.getId("loginar_armor"), "main");
    private final LoginarModel<LoginarRenderState> adultModel;
    private final EquipmentLayerRenderer equipmentRenderer;

    public LoginarArmorLayer(RenderLayerParent<LoginarRenderState, LoginarModel<LoginarRenderState>> renderer, EntityModelSet modelSet, EquipmentLayerRenderer equipmentRenderer) {
        super(renderer);
        this.adultModel = new LoginarModel<>(modelSet.bakeLayer(MODEL_LAYER));
        this.equipmentRenderer = equipmentRenderer;
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int lightCoords, LoginarRenderState state, float yRot, float xRot) {
        ItemStack armorItem = state.bodyArmorItem;
        Equippable armorEquippable = armorItem.get(DataComponents.EQUIPPABLE);
        if (armorEquippable != null && armorEquippable.assetId().isPresent() && !state.isBaby) {
            this.equipmentRenderer
                    .renderLayers(
                            Const.LOGINAR_BODY_LAYER.getValue(),
                            armorEquippable.assetId().get(),
                            this.adultModel,
                            state,
                            armorItem,
                            poseStack,
                            submitNodeCollector,
                            lightCoords,
                            state.outlineColor
                    );
        }
    }
}
