package net.silentchaos512.loginar.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.Entity;
import net.silentchaos512.loginar.LoginarMod;
import net.silentchaos512.loginar.entity.LoginarEntity;

public class LoginarModel extends EntityModel<LoginarEntity> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(LoginarMod.getId("loginar"), "main");
	private final ModelPart body;
	private final ModelPart tentacleLeftFront;
	private final ModelPart tentacleLeftMiddle;
	private final ModelPart tentacleLeftRear;
	private final ModelPart tentacleRightFront;
	private final ModelPart tentacleRightMiddle;
	private final ModelPart tentacleRightRear;

	public LoginarModel(ModelPart root) {
		this.body = root.getChild("body");
		this.tentacleLeftFront = root.getChild("tentacleLeftFront");
		this.tentacleLeftMiddle = root.getChild("tentacleLeftMiddle");
		this.tentacleLeftRear = root.getChild("tentacleLeftRear");
		this.tentacleRightFront = root.getChild("tentacleRightFront");
		this.tentacleRightMiddle = root.getChild("tentacleRightMiddle");
		this.tentacleRightRear = root.getChild("tentacleRightRear");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-6.0F, -18.0F, -6.0F, 12.0F, 10.0F, 12.0F, new CubeDeformation(0.0F))
				.texOffs(36, 0).addBox(-4.0F, -20.0F, -4.0F, 8.0F, 2.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(0, 22).addBox(-6.5F, -9.0F, -6.5F, 13.0F, 2.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition tentacleLeftFront = partdefinition.addOrReplaceChild("tentacleLeftFront", CubeListBuilder.create().texOffs(24, 37).addBox(3.0F, -8.0F, 2.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition tentacleLeftMiddle = partdefinition.addOrReplaceChild("tentacleLeftMiddle", CubeListBuilder.create().texOffs(16, 37).addBox(-1.0F, -8.0F, 3.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition tentacleLeftRear = partdefinition.addOrReplaceChild("tentacleLeftRear", CubeListBuilder.create().texOffs(8, 37).addBox(-5.0F, -8.0F, 2.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition tentacleRightFront = partdefinition.addOrReplaceChild("tentacleRightFront", CubeListBuilder.create().texOffs(0, 37).addBox(3.0F, -8.0F, -4.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition tentacleRightMiddle = partdefinition.addOrReplaceChild("tentacleRightMiddle", CubeListBuilder.create().texOffs(0, 22).addBox(-1.0F, -8.0F, -5.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition tentacleRightRear = partdefinition.addOrReplaceChild("tentacleRightRear", CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, -8.0F, -4.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(LoginarEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		tentacleLeftFront.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		tentacleLeftMiddle.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		tentacleLeftRear.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		tentacleRightFront.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		tentacleRightMiddle.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		tentacleRightRear.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}