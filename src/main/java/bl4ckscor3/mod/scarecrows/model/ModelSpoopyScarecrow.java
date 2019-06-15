package bl4ckscor3.mod.scarecrows.model;

import bl4ckscor3.mod.scarecrows.entity.EntityScarecrow;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;

public class ModelSpoopyScarecrow extends EntityModel<EntityScarecrow>
{
	private RendererModel head;
	private RendererModel body;
	private RendererModel rightArm;
	private RendererModel leftArm;

	public ModelSpoopyScarecrow(boolean isLit)
	{
		textureWidth = 64;
		textureHeight = 64;

		head = new RendererModel(this, 0, isLit ? 42 : 22); //offset texture for scarecrow with jack o' lantern
		head.setRotationPoint(-5.0F, 0.0F, -5.0F);
		head.addBox(0.0F, 0.0F, 0.0F, 10, 10, 10, 0.0F);

		body = new RendererModel(this, 0, 0);
		body.setRotationPoint(-4.0F, 10.0F, -4.0F);
		body.addBox(0.0F, 0.0F, 0.0F, 8, 14, 8, 0.0F);

		rightArm = new RendererModel(this, 40, 0);
		rightArm.setRotationPoint(-9.5F, 12.0F, -0.5F);
		rightArm.addBox(0.0F, 0.0F, 0.0F, 1, 8, 1, 0.0F);
		setRotateAngles(rightArm, 0.0F, 0.0F, -0.7853981633974483F);

		leftArm = new RendererModel(this, 33, 0);
		leftArm.setRotationPoint(9.5F, 12.0F, -0.5F);
		leftArm.addBox(-1.0F, 0.0F, 0.0F, 1, 8, 1, 0.0F);
		setRotateAngles(leftArm, 0.0F, 0.0F, 0.7853981633974483F);
	}

	@Override
	public void render(EntityScarecrow entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale)
	{
		head.render(scale);
		body.render(scale);
		rightArm.render(scale);
		leftArm.render(scale);
	}

	public void setRotateAngles(RendererModel rendererModel, float x, float y, float z)
	{
		rendererModel.rotateAngleX = x;
		rendererModel.rotateAngleY = y;
		rendererModel.rotateAngleZ = z;
	}
}
