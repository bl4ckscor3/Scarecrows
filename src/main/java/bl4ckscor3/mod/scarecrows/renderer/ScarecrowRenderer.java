package bl4ckscor3.mod.scarecrows.renderer;

import java.util.HashMap;
import java.util.Map;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import bl4ckscor3.mod.scarecrows.Scarecrows;
import bl4ckscor3.mod.scarecrows.entity.Scarecrow;
import bl4ckscor3.mod.scarecrows.type.ScarecrowType;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class ScarecrowRenderer extends EntityRenderer<Scarecrow, ScarecrowEntityRenderState> {
	private record RenderInfo(ResourceLocation textureLocation, EntityModel<ScarecrowEntityRenderState> unLitModel, EntityModel<ScarecrowEntityRenderState> litModel) {}

	/**
	 * Used so the memory doesn't build up with new instances of resource locations and models each render tick
	 */
	protected static final Map<String, RenderInfo> RENDER_INFO = new HashMap<>();

	public ScarecrowRenderer(EntityRendererProvider.Context ctx) {
		super(ctx);

		RENDER_INFO.clear();

		for (ScarecrowType type : Scarecrows.TYPES) {
			//@formatter:off
			RENDER_INFO.put(type.getName(), new RenderInfo(
					ResourceLocation.fromNamespaceAndPath(Scarecrows.MODID, "textures/entity/" + type.getName() + ".png"),
					type.createModel(ctx.bakeLayer(type.getModelLayerLocation(false))),
					type.createModel(ctx.bakeLayer(type.getModelLayerLocation(true)))));
			//@formatter:on
		}
	}

	@Override
	public void render(ScarecrowEntityRenderState renderState, PoseStack stack, MultiBufferSource buffer, int packedLight) {
		stack.translate(0.0D, 1.5D, 0.0D);
		stack.scale(-1, -1, 1);
		stack.mulPose(Axis.YP.rotationDegrees(renderState.rotation));
		renderState.model.renderToBuffer(stack, buffer.getBuffer(RenderType.entitySolid(renderState.texture)), packedLight, OverlayTexture.NO_OVERLAY, 0xFFFFFFFF);
	}

	@Override
	public ScarecrowEntityRenderState createRenderState() {
		return new ScarecrowEntityRenderState();
	}

	@Override
	public void extractRenderState(Scarecrow entity, ScarecrowEntityRenderState renderState, float partialTicks) {
		RenderInfo info = RENDER_INFO.get(entity.getScarecrowType().getName());

		renderState.rotation = entity.getRotation();
		renderState.model = entity.isLit() ? info.litModel() : info.unLitModel();
		renderState.texture = info.textureLocation();
		super.extractRenderState(entity, renderState, partialTicks);
	}
}
