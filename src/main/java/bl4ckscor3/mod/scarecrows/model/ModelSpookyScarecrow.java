package bl4ckscor3.mod.scarecrows.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelSpookyScarecrow extends ModelBase
{
	private ModelRenderer head;
	private ModelRenderer body;
	private ModelRenderer rightArm;
	private ModelRenderer leftArm;
	private ModelRenderer fence;

	public ModelSpookyScarecrow(boolean isLit)
	{
		textureWidth = 64;
		textureHeight = 96;

		head = new ModelRenderer(this, 0, isLit ? 50 : 26); //offset texture for scarecrow with jack o' lantern
		head.setRotationPoint(-6.0F, -14.0F, -6.0F);
		head.addBox(0.0F, 0.0F, 0.0F, 12, 12, 12, 0.0F);

		body = new ModelRenderer(this, 0, 0);
		body.setRotationPoint(-5.0F, -2.0F, -5.0F);
		body.addBox(0.0F, 0.0F, 0.0F, 10, 16, 10, 0.0F);

		rightArm = new ModelRenderer(this, 50, 0);
		rightArm.setRotationPoint(-12.0F, 0.0F, -0.5F);
		rightArm.addBox(0.0F, 0.0F, 0.0F, 1, 10, 1, 0.0F);
		setRotateAngles(rightArm, 0.0F, 0.0F, -0.7853981633974483F);

		leftArm = new ModelRenderer(this, 44, 0);
		leftArm.setRotationPoint(12.0F, 0.0F, -0.5F);
		leftArm.addBox(-1.0F, 0.0F, 0.0F, 1, 10, 1, 0.0F);
		setRotateAngles(leftArm, 0.0F, 0.0F, 0.7853981633974483F);

		fence = new ModelRenderer(this, 56, 0);
		fence.setRotationPoint(-1.0F, 14.0F, -1.1F);
		fence.addBox(0.0F, 0.0F, 0.0F, 2, 10, 2, 0.0F);
	}

	@Override
	public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale)
	{
		head.render(scale);
		body.render(scale);
		rightArm.render(scale);
		leftArm.render(scale);
		fence.render(scale);
	}

	public void setRotateAngles(ModelRenderer modelRenderer, float x, float y, float z)
	{
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}
