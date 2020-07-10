package bl4ckscor3.mod.scarecrows.renderer;

import java.util.HashMap;

import org.apache.commons.lang3.tuple.Triple;

import com.mojang.blaze3d.matrix.MatrixStack;

import bl4ckscor3.mod.scarecrows.entity.ScarecrowEntity;
import bl4ckscor3.mod.scarecrows.type.ScarecrowType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;

public class ScarecrowRenderer extends EntityRenderer<ScarecrowEntity>
{
	/**
	 * Used so the memory doesn't build up with new instances of resource locations and models each render tick
	 *
	 * Triple: texture, model with normal pumpkin, model with jack o' lantern
	 */
	public static final HashMap<String,Triple<ResourceLocation,EntityModel<ScarecrowEntity>,EntityModel<ScarecrowEntity>>> RENDER_INFO = new HashMap<>();

	public ScarecrowRenderer(EntityRendererManager manager)
	{
		super(manager);

		for(ScarecrowType type : ScarecrowType.TYPES)
		{
			RENDER_INFO.put(type.getName(), Triple.<ResourceLocation,EntityModel<ScarecrowEntity>,EntityModel<ScarecrowEntity>>of(new ResourceLocation("scarecrows:textures/entity/" + type.getName() + ".png"), type.getModel(false), type.getModel(true)));
		}
	}

	@Override
	public void render(ScarecrowEntity entity, float partialTicks, float p_225623_3_, MatrixStack stack, IRenderTypeBuffer buffer, int p_225623_6_)
	{
		stack.translate(0.0D, 1.5D, 0.0D);
		stack.scale(-1, -1, 1);
		stack.rotate(Vector3f.YP.rotationDegrees(entity.getRotation()));
		Minecraft.getInstance().textureManager.bindTexture(getEntityTexture(entity));

		if(entity.isLit())
			RENDER_INFO.get(entity.getScarecrowType().getName()).getRight().render(stack, buffer.getBuffer(RenderType.getEntitySolid(getEntityTexture(entity))), p_225623_6_, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
		else
			RENDER_INFO.get(entity.getScarecrowType().getName()).getMiddle().render(stack, buffer.getBuffer(RenderType.getEntitySolid(getEntityTexture(entity))), p_225623_6_, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
	}

	@Override
	public ResourceLocation getEntityTexture(ScarecrowEntity entity)
	{
		return RENDER_INFO.get(entity.getScarecrowType().getName()).getLeft();
	}
}
