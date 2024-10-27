package bl4ckscor3.mod.scarecrows.model;

import bl4ckscor3.mod.scarecrows.renderer.ScarecrowEntityRenderState;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class ScaryScarecrowModel extends EntityModel<ScarecrowEntityRenderState> {
	public ScaryScarecrowModel(ModelPart root) {
		super(root);
		setRotateAngles(root.getChild("right_arm"), 0.0F, 0.0F, -0.7853981633974483F);
		setRotateAngles(root.getChild("left_arm"), 0.0F, 0.0F, 0.7853981633974483F);
	}

	public static LayerDefinition createLayer(boolean isLit) {
		MeshDefinition meshDefinition = new MeshDefinition();
		PartDefinition partDefinition = meshDefinition.getRoot();

		partDefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, isLit ? 59 : 31).addBox(0.0F, 0.0F, 0.0F, 14.0F, 14.0F, 14.0F), PartPose.offset(-7.0F, -29.0F, -7.0F));
		partDefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, 0.0F, 0.0F, 12.0F, 19.0F, 12.0F), PartPose.offset(-6.0F, -15.0F, -6.0F));
		partDefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(54, 0).addBox(0.0F, 0.0F, 0.0F, 1.0F, 12.0F, 1.0F), PartPose.offset(-14.0F, -13.0F, -0.5F));
		partDefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(49, 0).addBox(-1.0F, 0.0F, 0.0F, 1.0F, 12.0F, 1.0F), PartPose.offset(14.0F, -13.0F, -0.5F));
		partDefinition.addOrReplaceChild("rod_top", CubeListBuilder.create().texOffs(0, 89).addBox(0.0F, 0.0F, 0.0F, 6.0F, 1.0F, 6.0F), PartPose.offset(-3.0F, 4.0F, -3.0F));
		partDefinition.addOrReplaceChild("rod", CubeListBuilder.create().texOffs(56, 55).addBox(0.0F, 0.0F, 0.0F, 2.0F, 18.0F, 2.0F), PartPose.offset(-1.0F, 5.0F, -1.0F));
		partDefinition.addOrReplaceChild("rod_bottom", CubeListBuilder.create().texOffs(40, 89).addBox(0.0F, 0.0F, 0.0F, 6.0F, 1.0F, 6.0F), PartPose.offset(-3.0F, 23.0F, -3.0F));
		return LayerDefinition.create(meshDefinition, 64, 96);
	}

	public void setRotateAngles(ModelPart modelRenderer, float x, float y, float z) {
		modelRenderer.xRot = x;
		modelRenderer.yRot = y;
		modelRenderer.zRot = z;
	}
}
