package bl4ckscor3.mod.scarecrows.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelSpoopyScarecrow extends ModelBase
{
	private ModelRenderer head;
	private ModelRenderer body;
	private ModelRenderer rightArm;
	private ModelRenderer leftArm;

	public ModelSpoopyScarecrow(boolean isLit)
	{
		textureWidth = 64;
		textureHeight = 64;

		head = new ModelRenderer(this, 0, isLit ? 42 : 22);
		head.setRotationPoint(-5.0F, 0.0F, -5.0F);
		head.addBox(0.0F, 0.0F, 0.0F, 10, 10, 10, 0.0F);

		body = new ModelRenderer(this, 0, 0);
		body.setRotationPoint(-4.0F, 10.0F, -4.0F);
		body.addBox(0.0F, 0.0F, 0.0F, 8, 14, 8, 0.0F);

		rightArm = new ModelRenderer(this, 40, 0);
		rightArm.setRotationPoint(-9.5F, 12.0F, -0.5F);
		rightArm.addBox(0.0F, 0.0F, 0.0F, 1, 8, 1, 0.0F);
		setRotateAngles(rightArm, 0.0F, 0.0F, -0.7853981633974483F);

		leftArm = new ModelRenderer(this, 33, 0);
		leftArm.setRotationPoint(9.5F, 12.0F, -0.5F);
		leftArm.addBox(-1.0F, 0.0F, 0.0F, 1, 8, 1, 0.0F);
		setRotateAngles(leftArm, 0.0F, 0.0F, 0.7853981633974483F);
	}

	@Override
	public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale)
	{
		head.render(scale);
		body.render(scale);
		rightArm.render(scale);
		leftArm.render(scale);
	}

	public void setRotateAngles(ModelRenderer modelRenderer, float x, float y, float z)
	{
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}
