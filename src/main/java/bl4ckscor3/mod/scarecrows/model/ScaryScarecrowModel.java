package bl4ckscor3.mod.scarecrows.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import bl4ckscor3.mod.scarecrows.entity.ScarecrowEntity;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;

public class ScaryScarecrowModel extends EntityModel<ScarecrowEntity>
{
	private ModelPart head;
	private ModelPart body;
	private ModelPart rightArm;
	private ModelPart leftArm;
	private ModelPart rodTop;
	private ModelPart rod;
	private ModelPart rodBottom;

	public ScaryScarecrowModel(boolean isLit)
	{
		texWidth = 64;
		texHeight = 96;

		head = new ModelPart(this, 0, isLit ? 59 : 31); //offset texture for scarecrow with jack o' lantern
		head.setPos(-7.0F, -29.0F, -7.0F);
		head.addBox(0.0F, 0.0F, 0.0F, 14, 14, 14);

		body = new ModelPart(this, 0, 0);
		body.setPos(-6.0F, -15.0F, -6.0F);
		body.addBox(0.0F, 0.0F, 0.0F, 12, 19, 12);

		rightArm = new ModelPart(this, 54, 0);
		rightArm.setPos(-14.0F, -13.0F, -0.5F);
		rightArm.addBox(0.0F, 0.0F, 0.0F, 1, 12, 1);
		setRotateAngles(rightArm, 0.0F, 0.0F, -0.7853981633974483F);

		leftArm = new ModelPart(this, 49, 0);
		leftArm.setPos(14.0F, -13.0F, -0.5F);
		leftArm.addBox(-1.0F, 0.0F, 0.0F, 1, 12, 1);
		setRotateAngles(leftArm, 0.0F, 0.0F, 0.7853981633974483F);

		rodTop = new ModelPart(this, 0, 89);
		rodTop.setPos(-3.0F, 4.0F, -3.0F);
		rodTop.addBox(0.0F, 0.0F, 0.0F, 6, 1, 6);

		rod = new ModelPart(this, 56, 55);
		rod.setPos(-1.0F, 5.0F, -1.0F);
		rod.addBox(0.0F, 0.0F, 0.0F, 2, 18, 2);

		rodBottom = new ModelPart(this, 40, 89);
		rodBottom.setPos(-3.0F, 23.0F, -3.0F);
		rodBottom.addBox(0.0F, 0.0F, 0.0F, 6, 1, 6);
	}

	@Override
	public void renderToBuffer(PoseStack stack, VertexConsumer builder, int p_225598_3_, int p_225598_4_, float p_225598_5_, float p_225598_6_, float p_225598_7_, float p_225598_8_)
	{
		head.render(stack, builder, p_225598_3_, p_225598_4_, p_225598_5_, p_225598_6_, p_225598_7_, p_225598_8_);
		body.render(stack, builder, p_225598_3_, p_225598_4_, p_225598_5_, p_225598_6_, p_225598_7_, p_225598_8_);
		rightArm.render(stack, builder, p_225598_3_, p_225598_4_, p_225598_5_, p_225598_6_, p_225598_7_, p_225598_8_);
		leftArm.render(stack, builder, p_225598_3_, p_225598_4_, p_225598_5_, p_225598_6_, p_225598_7_, p_225598_8_);
		rod.render(stack, builder, p_225598_3_, p_225598_4_, p_225598_5_, p_225598_6_, p_225598_7_, p_225598_8_);
		rodBottom.render(stack, builder, p_225598_3_, p_225598_4_, p_225598_5_, p_225598_6_, p_225598_7_, p_225598_8_);
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
