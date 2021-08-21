package bl4ckscor3.mod.scarecrows.renderer;

import java.util.HashMap;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;

import bl4ckscor3.mod.scarecrows.entity.Scarecrow;
import bl4ckscor3.mod.scarecrows.type.ScarecrowType;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class ScarecrowRenderer extends EntityRenderer<Scarecrow>
{
	private record RenderInfo(ResourceLocation textureLocation, EntityModel<Scarecrow> unLitModel, EntityModel<Scarecrow> litModel) {}

	/**
	 * Used so the memory doesn't build up with new instances of resource locations and models each render tick
	 */
	public static final HashMap<String,RenderInfo> RENDER_INFO = new HashMap<>();

	public ScarecrowRenderer(EntityRendererProvider.Context ctx)
	{
		super(ctx);

		for(ScarecrowType type : ScarecrowType.TYPES)
		{
			RENDER_INFO.put(type.getName(), new RenderInfo(
					new ResourceLocation("scarecrows", "textures/entity/" + type.getName() + ".png"),
					type.createModel(ctx.bakeLayer(type.getModelLayerLocation(false))),
					type.createModel(ctx.bakeLayer(type.getModelLayerLocation(true)))));
		}
	}

	@Override
	public void render(Scarecrow entity, float entityYaw, float partialTicks, PoseStack stack, MultiBufferSource buffer, int packedLight)
	{
		Model modelToRender;

		stack.translate(0.0D, 1.5D, 0.0D);
		stack.scale(-1, -1, 1);
		stack.mulPose(Vector3f.YP.rotationDegrees(entity.getRotation()));

		if(entity.isLit())
			modelToRender = RENDER_INFO.get(entity.getScarecrowType().getName()).litModel();
		else
			modelToRender = RENDER_INFO.get(entity.getScarecrowType().getName()).unLitModel();

		modelToRender.renderToBuffer(stack, buffer.getBuffer(RenderType.entitySolid(getTextureLocation(entity))), packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
	}

	@Override
	public ResourceLocation getTextureLocation(Scarecrow entity)
	{
		return RENDER_INFO.get(entity.getScarecrowType().getName()).textureLocation();
	}
}
