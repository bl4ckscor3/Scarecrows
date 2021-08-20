package bl4ckscor3.mod.scarecrows.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import bl4ckscor3.mod.scarecrows.entity.ScarecrowEntity;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;

public class SpoopyScarecrowModel extends EntityModel<ScarecrowEntity>
{
	private ModelPart head;
	private ModelPart body;
	private ModelPart rightArm;
	private ModelPart leftArm;

	public SpoopyScarecrowModel(boolean isLit)
	{
		texWidth = 64;
		texHeight = 64;

		head = new ModelPart(this, 0, isLit ? 42 : 22); //offset texture for scarecrow with jack o' lantern
		head.setPos(-5.0F, 0.0F, -5.0F);
		head.addBox(0.0F, 0.0F, 0.0F, 10, 10, 10);

		body = new ModelPart(this, 0, 0);
		body.setPos(-4.0F, 10.0F, -4.0F);
		body.addBox(0.0F, 0.0F, 0.0F, 8, 14, 8);

		rightArm = new ModelPart(this, 40, 0);
		rightArm.setPos(-9.5F, 12.0F, -0.5F);
		rightArm.addBox(0.0F, 0.0F, 0.0F, 1, 8, 1);
		setRotateAngles(rightArm, 0.0F, 0.0F, -0.7853981633974483F);

		leftArm = new ModelPart(this, 33, 0);
		leftArm.setPos(9.5F, 12.0F, -0.5F);
		leftArm.addBox(-1.0F, 0.0F, 0.0F, 1, 8, 1);
		setRotateAngles(leftArm, 0.0F, 0.0F, 0.7853981633974483F);
	}

	@Override
	public void renderToBuffer(PoseStack stack, VertexConsumer builder, int p_225598_3_, int p_225598_4_, float p_225598_5_, float p_225598_6_, float p_225598_7_, float p_225598_8_)
	{
		head.render(stack, builder, p_225598_3_, p_225598_4_, p_225598_5_, p_225598_6_, p_225598_7_, p_225598_8_);
		body.render(stack, builder, p_225598_3_, p_225598_4_, p_225598_5_, p_225598_6_, p_225598_7_, p_225598_8_);
		rightArm.render(stack, builder, p_225598_3_, p_225598_4_, p_225598_5_, p_225598_6_, p_225598_7_, p_225598_8_);
		leftArm.render(stack, builder, p_225598_3_, p_225598_4_, p_225598_5_, p_225598_6_, p_225598_7_, p_225598_8_);
	}

	public void setRotateAngles(ModelPart modelRenderer, float x, float y, float z)
	{
		modelRenderer.xRot = x;
		modelRenderer.yRot = y;
		modelRenderer.zRot = z;
	}

	@Override
	public void setupAnim(ScarecrowEntity entity, float p_225597_2_, float p_225597_3_, float p_225597_4_, float p_225597_5_, float p_225597_6_) {}
}
