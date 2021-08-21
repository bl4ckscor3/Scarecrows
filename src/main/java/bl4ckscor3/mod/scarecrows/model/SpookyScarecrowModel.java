package bl4ckscor3.mod.scarecrows.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import bl4ckscor3.mod.scarecrows.entity.Scarecrow;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class SpookyScarecrowModel extends EntityModel<Scarecrow>
{
	private ModelPart head;
	private ModelPart body;
	private ModelPart rightArm;
	private ModelPart leftArm;
	private ModelPart fence;

	public SpookyScarecrowModel(ModelPart modelPart)
	{
		head = modelPart.getChild("head");
		body = modelPart.getChild("body");
		rightArm = modelPart.getChild("right_arm");
		setRotateAngles(rightArm, 0.0F, 0.0F, -0.7853981633974483F);
		leftArm = modelPart.getChild("left_arm");
		setRotateAngles(leftArm, 0.0F, 0.0F, 0.7853981633974483F);
		leftArm = modelPart.getChild("fence");
	}

	public static LayerDefinition createLayer(boolean isLit)
	{
		MeshDefinition meshDefinition = new MeshDefinition();
		PartDefinition partDefinition = meshDefinition.getRoot();

		partDefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, isLit ? 50 : 26).addBox(0.0F, 0.0F, 0.0F, 12.0F, 12.0F, 12.0F), PartPose.offset(-6.0F, -14.0F, -6.0F));
		partDefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, 0.0F, 0.0F, 10.0F, 16.0F, 10.0F), PartPose.offset(-5.0F, -2.0F, -5.0F));
		partDefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(50, 0).addBox(0.0F, 0.0F, 0.0F, 1.0F, 10.0F, 1.0F), PartPose.offset(-12.0F, 0.0F, -0.5F));
		partDefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(44, 0).addBox(-1.0F, 0.0F, 0.0F, 1.0F, 10.0F, 1.0F), PartPose.offset(12.0F, 0.0F, -0.5F));
		partDefinition.addOrReplaceChild("fence", CubeListBuilder.create().texOffs(56, 0).addBox(0.0F, 0.0F, 0.0F, 2.0F, 10.0F, 2.0F), PartPose.offset(-1.0F, 14.0F, -1.1F));
		return LayerDefinition.create(meshDefinition, 64, 96);
	}

	@Override
	public void renderToBuffer(PoseStack stack, VertexConsumer builder, int packedLight, int packedOverlay, float red, float green, float blue, float alpha)
	{
		head.render(stack, builder, packedLight, packedOverlay, red, green, blue, alpha);
		body.render(stack, builder, packedLight, packedOverlay, red, green, blue, alpha);
		rightArm.render(stack, builder, packedLight, packedOverlay, red, green, blue, alpha);
		leftArm.render(stack, builder, packedLight, packedOverlay, red, green, blue, alpha);
		fence.render(stack, builder, packedLight, packedOverlay, red, green, blue, alpha);
	}

	public void setRotateAngles(ModelPart modelRenderer, float x, float y, float z)
	{
		modelRenderer.xRot = x;
		modelRenderer.yRot = y;
		modelRenderer.zRot = z;
	}

	@Override
	public void setupAnim(Scarecrow entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {}
}
