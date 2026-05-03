package bl4ckscor3.mod.scarecrows.model;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class SpoopyScarecrowModel extends ScarecrowModel {
	public SpoopyScarecrowModel(ModelPart root) {
		super(root);
	}

	public static LayerDefinition createLayer(boolean isLit) {
		MeshDefinition meshDefinition = new MeshDefinition();
		PartDefinition partDefinition = meshDefinition.getRoot();

		partDefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, isLit ? 42 : 22).addBox(0.0F, 0.0F, 0.0F, 10.0F, 10.0F, 10.0F), PartPose.offset(-5.0F, 0.0F, -5.0F));
		partDefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, 0.0F, 0.0F, 8.0F, 14.0F, 8.0F), PartPose.offset(-4.0F, 10.0F, -4.0F));
		partDefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(40, 0).addBox(0.0F, 0.0F, 0.0F, 1.0F, 8.0F, 1.0F), PartPose.offset(-9.5F, 12.0F, -0.5F));
		partDefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(33, 0).addBox(-1.0F, 0.0F, 0.0F, 1.0F, 8.0F, 1.0F), PartPose.offset(9.5F, 12.0F, -0.5F));
		return LayerDefinition.create(meshDefinition, 64, 64);
	}
}
