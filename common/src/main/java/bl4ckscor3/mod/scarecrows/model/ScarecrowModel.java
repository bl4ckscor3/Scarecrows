package bl4ckscor3.mod.scarecrows.model;

import bl4ckscor3.mod.scarecrows.renderer.ScarecrowEntityRenderState;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;

public class ScarecrowModel extends EntityModel<ScarecrowEntityRenderState> {
	private final ModelPart rightArm;
	private final ModelPart leftArm;

	public ScarecrowModel(ModelPart root) {
		super(root);
		rightArm = root.getChild("right_arm");
		leftArm = root.getChild("left_arm");
	}

	@Override
	public void setupAnim(ScarecrowEntityRenderState renderState) {
		setRotateAngles(rightArm, 0.0F, 0.0F, -0.7853981633974483F);
		setRotateAngles(leftArm, 0.0F, 0.0F, 0.7853981633974483F);
	}

	public void setRotateAngles(ModelPart modelRenderer, float x, float y, float z) {
		modelRenderer.xRot = x;
		modelRenderer.yRot = y;
		modelRenderer.zRot = z;
	}
}
