package bl4ckscor3.mod.scarecrows.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import bl4ckscor3.mod.scarecrows.entity.ScarecrowEntity;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;

public class SpookyScarecrowModel extends EntityModel<ScarecrowEntity>
{
	private ModelRenderer head;
	private ModelRenderer body;
	private ModelRenderer rightArm;
	private ModelRenderer leftArm;
	private ModelRenderer fence;

	public SpookyScarecrowModel(boolean isLit)
	{
		texWidth = 64;
		texHeight = 96;

		head = new ModelRenderer(this, 0, isLit ? 50 : 26); //offset texture for scarecrow with jack o' lantern
		head.setPos(-6.0F, -14.0F, -6.0F);
		head.addBox(0.0F, 0.0F, 0.0F, 12, 12, 12);

		body = new ModelRenderer(this, 0, 0);
		body.setPos(-5.0F, -2.0F, -5.0F);
		body.addBox(0.0F, 0.0F, 0.0F, 10, 16, 10);

		rightArm = new ModelRenderer(this, 50, 0);
		rightArm.setPos(-12.0F, 0.0F, -0.5F);
		rightArm.addBox(0.0F, 0.0F, 0.0F, 1, 10, 1);
		setRotateAngles(rightArm, 0.0F, 0.0F, -0.7853981633974483F);

		leftArm = new ModelRenderer(this, 44, 0);
		leftArm.setPos(12.0F, 0.0F, -0.5F);
		leftArm.addBox(-1.0F, 0.0F, 0.0F, 1, 10, 1);
		setRotateAngles(leftArm, 0.0F, 0.0F, 0.7853981633974483F);

		fence = new ModelRenderer(this, 56, 0);
		fence.setPos(-1.0F, 14.0F, -1.1F);
		fence.addBox(0.0F, 0.0F, 0.0F, 2, 10, 2);
	}

	@Override
	public void renderToBuffer(MatrixStack stack, IVertexBuilder builder, int p_225598_3_, int p_225598_4_, float p_225598_5_, float p_225598_6_, float p_225598_7_, float p_225598_8_)
	{
		head.render(stack, builder, p_225598_3_, p_225598_4_, p_225598_5_, p_225598_6_, p_225598_7_, p_225598_8_);
		body.render(stack, builder, p_225598_3_, p_225598_4_, p_225598_5_, p_225598_6_, p_225598_7_, p_225598_8_);
		rightArm.render(stack, builder, p_225598_3_, p_225598_4_, p_225598_5_, p_225598_6_, p_225598_7_, p_225598_8_);
		leftArm.render(stack, builder, p_225598_3_, p_225598_4_, p_225598_5_, p_225598_6_, p_225598_7_, p_225598_8_);
		fence.render(stack, builder, p_225598_3_, p_225598_4_, p_225598_5_, p_225598_6_, p_225598_7_, p_225598_8_);
	}

	public void setRotateAngles(ModelRenderer modelRenderer, float x, float y, float z)
	{
		modelRenderer.xRot = x;
		modelRenderer.yRot = y;
		modelRenderer.zRot = z;
	}

	@Override
	public void setupAnim(ScarecrowEntity entity, float p_225597_2_, float p_225597_3_, float p_225597_4_, float p_225597_5_, float p_225597_6_) {}
}
