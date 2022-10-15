package net.silentchaos512.loginar.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.silentchaos512.loginar.LoginarMod;
import net.silentchaos512.loginar.entity.LoginarEntity;

public class LoginarModel extends EntityModel<LoginarEntity> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(LoginarMod.getId("loginar"), "main");
	private final ModelPart body;

	public LoginarModel(ModelPart root) {
		this.body = root.getChild("body");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-6.0F, -18.0F, -6.0F, 12.0F, 10.0F, 12.0F, new CubeDeformation(0.0F))
				.texOffs(36, 0).addBox(-4.0F, -20.0F, -4.0F, 8.0F, 2.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(0, 22).addBox(-6.5F, -9.0F, -6.5F, 13.0F, 2.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition tentacleRightFront = body.addOrReplaceChild("tentacleRightFront", CubeListBuilder.create().texOffs(24, 37).addBox(-4.0F, -8.0F, -5.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition tentacleRightMiddle = body.addOrReplaceChild("tentacleRightMiddle", CubeListBuilder.create().texOffs(16, 37).addBox(-5.0F, -8.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition tentacleRightRear = body.addOrReplaceChild("tentacleRightRear", CubeListBuilder.create().texOffs(8, 37).addBox(-4.0F, -9.0F, 3.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition tentacleLeftFront = body.addOrReplaceChild("tentacleLeftFront", CubeListBuilder.create().texOffs(0, 37).addBox(2.0F, -8.0F, -5.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition tentacleLeftMiddle = body.addOrReplaceChild("tentacleLeftMiddle", CubeListBuilder.create().texOffs(0, 22).addBox(3.0F, -8.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition tentacleLeftRear = body.addOrReplaceChild("tentacleLeftRear", CubeListBuilder.create().texOffs(0, 0).addBox(2.0F, -8.0F, 3.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition antennaRight = body.addOrReplaceChild("antennaRight", CubeListBuilder.create().texOffs(68, -10).mirror().addBox(0.0F, -10.0F, -9.5F, 0.0F, 10.0F, 10.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(88, 0).addBox(-1.0F, -7.0F, -10.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, -20.0F, -3.5F, 0.0F, 0.5236F, 0.0F));

		PartDefinition antennaLeft = body.addOrReplaceChild("antennaLeft", CubeListBuilder.create().texOffs(68, -10).mirror().addBox(0.0F, -10.0F, -9.5F, 0.0F, 10.0F, 10.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(88, 0).addBox(-1.0F, -7.0F, -10.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, -20.0F, -3.5F, 0.0F, -0.5236F, 0.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(LoginarEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}