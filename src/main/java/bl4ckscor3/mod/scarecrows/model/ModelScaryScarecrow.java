package bl4ckscor3.mod.scarecrows.model;

import bl4ckscor3.mod.scarecrows.entity.EntityScarecrow;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;

public class ModelScaryScarecrow extends EntityModel<EntityScarecrow>
{
	private RendererModel head;
	private RendererModel body;
	private RendererModel rightArm;
	private RendererModel leftArm;
	private RendererModel rodTop;
	private RendererModel rod;
	private RendererModel rodBottom;

	public ModelScaryScarecrow(boolean isLit)
	{
		textureWidth = 64;
		textureHeight = 96;

		head = new RendererModel(this, 0, isLit ? 59 : 31); //offset texture for scarecrow with jack o' lantern
		head.setRotationPoint(-7.0F, -29.0F, -7.0F);
		head.addBox(0.0F, 0.0F, 0.0F, 14, 14, 14, 0.0F);

		body = new RendererModel(this, 0, 0);
		body.setRotationPoint(-6.0F, -15.0F, -6.0F);
		body.addBox(0.0F, 0.0F, 0.0F, 12, 19, 12, 0.0F);

		rightArm = new RendererModel(this, 54, 0);
		rightArm.setRotationPoint(-14.0F, -13.0F, -0.5F);
		rightArm.addBox(0.0F, 0.0F, 0.0F, 1, 12, 1, 0.0F);
		setRotateAngles(rightArm, 0.0F, 0.0F, -0.7853981633974483F);

		leftArm = new RendererModel(this, 49, 0);
		leftArm.setRotationPoint(14.0F, -13.0F, -0.5F);
		leftArm.addBox(-1.0F, 0.0F, 0.0F, 1, 12, 1, 0.0F);
		setRotateAngles(leftArm, 0.0F, 0.0F, 0.7853981633974483F);

		rodTop = new RendererModel(this, 0, 89);
		rodTop.setRotationPoint(-3.0F, 4.0F, -3.0F);
		rodTop.addBox(0.0F, 0.0F, 0.0F, 6, 1, 6, 0.0F);

		rod = new RendererModel(this, 56, 55);
		rod.setRotationPoint(-1.0F, 5.0F, -1.0F);
		rod.addBox(0.0F, 0.0F, 0.0F, 2, 18, 2, 0.0F);

		rodBottom = new RendererModel(this, 40, 89);
		rodBottom.setRotationPoint(-3.0F, 23.0F, -3.0F);
		rodBottom.addBox(0.0F, 0.0F, 0.0F, 6, 1, 6, 0.0F);
	}

	@Override
	public void render(EntityScarecrow entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale)
	{
		head.render(scale);
		body.render(scale);
		rightArm.render(scale);
		leftArm.render(scale);
		rodTop.render(scale);
		rod.render(scale);
		rodBottom.render(scale);
	}

	public void setRotateAngles(RendererModel rendererModel, float x, float y, float z)
	{
		rendererModel.rotateAngleX = x;
		rendererModel.rotateAngleY = y;
		rendererModel.rotateAngleZ = z;
	}
}
