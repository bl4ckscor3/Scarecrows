package bl4ckscor3.mod.scarecrows.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import bl4ckscor3.mod.scarecrows.entity.ScarecrowEntity;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;

public class SpoopyScarecrowModel extends EntityModel<ScarecrowEntity>
{
	private ModelRenderer head;
	private ModelRenderer body;
	private ModelRenderer rightArm;
	private ModelRenderer leftArm;

	public SpoopyScarecrowModel(boolean isLit)
	{
		textureWidth = 64;
		textureHeight = 64;

		head = new ModelRenderer(this, 0, isLit ? 42 : 22); //offset texture for scarecrow with jack o' lantern
		head.setRotationPoint(-5.0F, 0.0F, -5.0F);
		head.addBox(0.0F, 0.0F, 0.0F, 10, 10, 10);

		body = new ModelRenderer(this, 0, 0);
		body.setRotationPoint(-4.0F, 10.0F, -4.0F);
		body.addBox(0.0F, 0.0F, 0.0F, 8, 14, 8);

		rightArm = new ModelRenderer(this, 40, 0);
		rightArm.setRotationPoint(-9.5F, 12.0F, -0.5F);
		rightArm.addBox(0.0F, 0.0F, 0.0F, 1, 8, 1);
		setRotateAngles(rightArm, 0.0F, 0.0F, -0.7853981633974483F);

		leftArm = new ModelRenderer(this, 33, 0);
		leftArm.setRotationPoint(9.5F, 12.0F, -0.5F);
		leftArm.addBox(-1.0F, 0.0F, 0.0F, 1, 8, 1);
		setRotateAngles(leftArm, 0.0F, 0.0F, 0.7853981633974483F);
	}

	@Override
	public void render(MatrixStack stack, IVertexBuilder builder, int p_225598_3_, int p_225598_4_, float p_225598_5_, float p_225598_6_, float p_225598_7_, float p_225598_8_)
	{
		head.render(stack, builder, p_225598_3_, p_225598_4_, p_225598_5_, p_225598_6_, p_225598_7_, p_225598_8_);
		body.render(stack, builder, p_225598_3_, p_225598_4_, p_225598_5_, p_225598_6_, p_225598_7_, p_225598_8_);
		rightArm.render(stack, builder, p_225598_3_, p_225598_4_, p_225598_5_, p_225598_6_, p_225598_7_, p_225598_8_);
		leftArm.render(stack, builder, p_225598_3_, p_225598_4_, p_225598_5_, p_225598_6_, p_225598_7_, p_225598_8_);
	}

	public void setRotateAngles(ModelRenderer modelRenderer, float x, float y, float z)
	{
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}

	@Override
	public void setRotationAngles(ScarecrowEntity entity, float p_225597_2_, float p_225597_3_, float p_225597_4_, float p_225597_5_, float p_225597_6_) {}
}
