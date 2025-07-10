package net.silentchaos512.loginar.client.model;

import net.minecraft.client.model.BabyModelTransform;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.silentchaos512.loginar.LoginarMod;
import net.silentchaos512.loginar.client.renderer.state.LoginarRenderState;

import java.util.Set;

public class LoginarModel<S extends LoginarRenderState> extends EntityModel<S> {
	public static final MeshTransformer BABY_TRANSFORMER = new BabyModelTransform(
			true,
			1.0f,
			0.0f,
			0.7f,
			0.5f,
			0.0f,
			Set.of("body", "antennaLeft", "antennaRight")
	);

	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(LoginarMod.getId("loginar"), "main");
	private final ModelPart body;
	private final ModelPart tentacleLeftFront;
	private final ModelPart tentacleRightFront;
	private final ModelPart tentacleLeftMiddle;
	private final ModelPart tentacleRightMiddle;
	private final ModelPart tentacleLeftRear;
	private final ModelPart tentacleRightRear;
	private final ModelPart antennaLeft;
	private final ModelPart antennaRight;

	public LoginarModel(ModelPart root) {
		super(root);
		this.body = root.getChild("body");
		this.tentacleLeftFront = body.getChild("tentacleLeftFront");
		this.tentacleRightFront = body.getChild("tentacleRightFront");
		this.tentacleLeftMiddle = body.getChild("tentacleLeftMiddle");
		this.tentacleRightMiddle = body.getChild("tentacleRightMiddle");
		this.tentacleLeftRear = body.getChild("tentacleLeftRear");
		this.tentacleRightRear = body.getChild("tentacleRightRear");
		this.antennaLeft = body.getChild("antennaLeft");
		this.antennaRight = body.getChild("antennaRight");
	}

	public static MeshDefinition createMeshDefinition() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-6.0F, -18.0F, -6.0F, 12.0F, 10.0F, 12.0F, new CubeDeformation(0.0F))
				.texOffs(36, 0).addBox(-4.0F, -20.0F, -4.0F, 8.0F, 2.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(0, 22).addBox(-6.5F, -9.0F, -6.5F, 13.0F, 2.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition tentacleRightFront = body.addOrReplaceChild("tentacleRightFront", CubeListBuilder.create().texOffs(24, 37).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, -7.0F, -4.0F));

		PartDefinition tentacleRightMiddle = body.addOrReplaceChild("tentacleRightMiddle", CubeListBuilder.create().texOffs(16, 37).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.0F, -7.0F, 0.0F));

		PartDefinition tentacleRightRear = body.addOrReplaceChild("tentacleRightRear", CubeListBuilder.create().texOffs(8, 37).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, -7.0F, 4.0F));

		PartDefinition tentacleLeftFront = body.addOrReplaceChild("tentacleLeftFront", CubeListBuilder.create().texOffs(0, 37).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, -7.0F, -4.0F));

		PartDefinition tentacleLeftMiddle = body.addOrReplaceChild("tentacleLeftMiddle", CubeListBuilder.create().texOffs(0, 22).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, -7.0F, 0.0F));

		PartDefinition tentacleLeftRear = body.addOrReplaceChild("tentacleLeftRear", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, -7.0F, 4.0F));

		PartDefinition antennaRight = body.addOrReplaceChild("antennaRight", CubeListBuilder.create().texOffs(68, -10).mirror().addBox(0.0F, -10.0F, -9.5F, 0.0F, 10.0F, 10.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(88, 0).addBox(-1.0F, -7.0F, -10.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, -20.0F, -3.5F, 0.0F, 0.5236F, 0.0F));

		PartDefinition antennaLeft = body.addOrReplaceChild("antennaLeft", CubeListBuilder.create().texOffs(68, -10).mirror().addBox(0.0F, -10.0F, -9.5F, 0.0F, 10.0F, 10.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(88, 0).addBox(-1.0F, -7.0F, -10.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, -20.0F, -3.5F, 0.0F, -0.5236F, 0.0F));

		return meshdefinition;
	}

	public static LayerDefinition createLayerDefinition() {
		return LayerDefinition.create(LoginarModel.createMeshDefinition(), 128, 128);
	}

	public static LayerDefinition createBabyLayerDefinition() {
		return createLayerDefinition().apply(BABY_TRANSFORMER);
	}

	@Override
	public void setupAnim(S state) {
		this.body.xRot = Mth.cos(state.walkAnimationPos * 0.662f) * 0.25f * state.walkAnimationSpeed;
		float pi = (float) Math.PI;
		this.antennaLeft.yRot = -30 * pi / 180 + Mth.cos(state.ageInTicks / 4 + pi) * 0.15f;
		this.antennaRight.yRot = 30 * pi / 180 + Mth.cos(state.ageInTicks / 4) * 0.15f;
		this.tentacleRightRear.xRot = getLegRotation(state, 0f);
		this.tentacleLeftRear.xRot = getLegRotation(state, pi);
		this.tentacleRightMiddle.xRot = getLegRotation(state, pi);
		this.tentacleLeftMiddle.xRot = getLegRotation(state, 0f);
		this.tentacleRightFront.xRot = getLegRotation(state, 0f);
		this.tentacleLeftFront.xRot = getLegRotation(state, pi);
	}

	private float getLegRotation(S state, float offset) {
		return Mth.cos(state.walkAnimationPos * 1.5f + offset) * 0.7f * state.walkAnimationSpeed;
	}
}