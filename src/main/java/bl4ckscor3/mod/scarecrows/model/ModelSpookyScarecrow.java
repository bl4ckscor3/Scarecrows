package bl4ckscor3.mod.scarecrows.model;

import bl4ckscor3.mod.scarecrows.entity.EntityScarecrow;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;

public class ModelSpookyScarecrow extends EntityModel<EntityScarecrow>
{
	private RendererModel head;
	private RendererModel body;
	private RendererModel rightArm;
	private RendererModel leftArm;
	private RendererModel fence;

	public ModelSpookyScarecrow(boolean isLit)
	{
		textureWidth = 64;
		textureHeight = 96;

		head = new RendererModel(this, 0, isLit ? 50 : 26); //offset texture for scarecrow with jack o' lantern
		head.setRotationPoint(-6.0F, -14.0F, -6.0F);
		head.addBox(0.0F, 0.0F, 0.0F, 12, 12, 12, 0.0F);

		body = new RendererModel(this, 0, 0);
		body.setRotationPoint(-5.0F, -2.0F, -5.0F);
		body.addBox(0.0F, 0.0F, 0.0F, 10, 16, 10, 0.0F);

		rightArm = new RendererModel(this, 50, 0);
		rightArm.setRotationPoint(-12.0F, 0.0F, -0.5F);
		rightArm.addBox(0.0F, 0.0F, 0.0F, 1, 10, 1, 0.0F);
		setRotateAngles(rightArm, 0.0F, 0.0F, -0.7853981633974483F);

		leftArm = new RendererModel(this, 44, 0);
		leftArm.setRotationPoint(12.0F, 0.0F, -0.5F);
		leftArm.addBox(-1.0F, 0.0F, 0.0F, 1, 10, 1, 0.0F);
		setRotateAngles(leftArm, 0.0F, 0.0F, 0.7853981633974483F);

		fence = new RendererModel(this, 56, 0);
		fence.setRotationPoint(-1.0F, 14.0F, -1.1F);
		fence.addBox(0.0F, 0.0F, 0.0F, 2, 10, 2, 0.0F);
	}

	@Override
	public void render(EntityScarecrow entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale)
	{
		head.render(scale);
		body.render(scale);
		rightArm.render(scale);
		leftArm.render(scale);
		fence.render(scale);
	}

	public void setRotateAngles(RendererModel rendererModel, float x, float y, float z)
	{
		rendererModel.rotateAngleX = x;
		rendererModel.rotateAngleY = y;
		rendererModel.rotateAngleZ = z;
	}
}
