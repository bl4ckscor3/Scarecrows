package bl4ckscor3.mod.scarecrows.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import bl4ckscor3.mod.scarecrows.entity.ScarecrowEntity;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;

public class ScaryScarecrowModel extends EntityModel<ScarecrowEntity>
{
	private ModelRenderer head;
	private ModelRenderer body;
	private ModelRenderer rightArm;
	private ModelRenderer leftArm;
	private ModelRenderer rodTop;
	private ModelRenderer rod;
	private ModelRenderer rodBottom;

	public ScaryScarecrowModel(boolean isLit)
	{
		textureWidth = 64;
		textureHeight = 96;

		head = new ModelRenderer(this, 0, isLit ? 59 : 31); //offset texture for scarecrow with jack o' lantern
		head.setRotationPoint(-7.0F, -29.0F, -7.0F);
		head.func_228300_a_(0.0F, 0.0F, 0.0F, 14, 14, 14);

		body = new ModelRenderer(this, 0, 0);
		body.setRotationPoint(-6.0F, -15.0F, -6.0F);
		body.func_228300_a_(0.0F, 0.0F, 0.0F, 12, 19, 12);

		rightArm = new ModelRenderer(this, 54, 0);
		rightArm.setRotationPoint(-14.0F, -13.0F, -0.5F);
		rightArm.func_228300_a_(0.0F, 0.0F, 0.0F, 1, 12, 1);
		setRotateAngles(rightArm, 0.0F, 0.0F, -0.7853981633974483F);

		leftArm = new ModelRenderer(this, 49, 0);
		leftArm.setRotationPoint(14.0F, -13.0F, -0.5F);
		leftArm.func_228300_a_(-1.0F, 0.0F, 0.0F, 1, 12, 1);
		setRotateAngles(leftArm, 0.0F, 0.0F, 0.7853981633974483F);

		rodTop = new ModelRenderer(this, 0, 89);
		rodTop.setRotationPoint(-3.0F, 4.0F, -3.0F);
		rodTop.func_228300_a_(0.0F, 0.0F, 0.0F, 6, 1, 6);

		rod = new ModelRenderer(this, 56, 55);
		rod.setRotationPoint(-1.0F, 5.0F, -1.0F);
		rod.func_228300_a_(0.0F, 0.0F, 0.0F, 2, 18, 2);

		rodBottom = new ModelRenderer(this, 40, 89);
		rodBottom.setRotationPoint(-3.0F, 23.0F, -3.0F);
		rodBottom.func_228300_a_(0.0F, 0.0F, 0.0F, 6, 1, 6);
	}

	@Override
	public void func_225598_a_(MatrixStack stack, IVertexBuilder builder, int p_225598_3_, int p_225598_4_, float p_225598_5_, float p_225598_6_, float p_225598_7_, float p_225598_8_)
	{
		head.func_228309_a_(stack, builder, p_225598_3_, p_225598_4_, p_225598_5_, p_225598_6_, p_225598_7_, p_225598_8_);
		body.func_228309_a_(stack, builder, p_225598_3_, p_225598_4_, p_225598_5_, p_225598_6_, p_225598_7_, p_225598_8_);
		rightArm.func_228309_a_(stack, builder, p_225598_3_, p_225598_4_, p_225598_5_, p_225598_6_, p_225598_7_, p_225598_8_);
		leftArm.func_228309_a_(stack, builder, p_225598_3_, p_225598_4_, p_225598_5_, p_225598_6_, p_225598_7_, p_225598_8_);
		rod.func_228309_a_(stack, builder, p_225598_3_, p_225598_4_, p_225598_5_, p_225598_6_, p_225598_7_, p_225598_8_);
		rodBottom.func_228309_a_(stack, builder, p_225598_3_, p_225598_4_, p_225598_5_, p_225598_6_, p_225598_7_, p_225598_8_);
	}

	public void setRotateAngles(ModelRenderer modelRenderer, float x, float y, float z)
	{
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}

	@Override
	public void func_225597_a_(ScarecrowEntity entity, float p_225597_2_, float p_225597_3_, float p_225597_4_, float p_225597_5_, float p_225597_6_) {}
}
