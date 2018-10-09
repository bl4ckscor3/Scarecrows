package bl4ckscor3.mod.scarecrows.renderer;

import java.util.HashMap;

import org.apache.commons.lang3.tuple.Triple;

import bl4ckscor3.mod.scarecrows.entity.EntityScarecrow;
import bl4ckscor3.mod.scarecrows.type.ScarecrowType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.util.ResourceLocation;

public class RenderScarecrow extends Render<EntityScarecrow>
{
	//used so the memory doesn't build up with new instances of resource locations and models each render tick
	/**
	 * Used so the memory doesn't build up with new instances of resource locations and models each render tick
	 *
	 * Triple: texture, model with normal pumpkin, model with jack o' lantern
	 */
	public static final HashMap<String,Triple<ResourceLocation,ModelBase,ModelBase>> RENDER_INFO = new HashMap<String,Triple<ResourceLocation,ModelBase,ModelBase>>();

	public RenderScarecrow()
	{
		super(Minecraft.getMinecraft().getRenderManager());

		for(ScarecrowType type : ScarecrowType.TYPES)
		{
			RENDER_INFO.put(type.getName(), Triple.<ResourceLocation,ModelBase,ModelBase>of(new ResourceLocation("scarecrows:textures/entity/" + type.getName() + ".png"), type.getModel(false), type.getModel(true)));
		}
	}

	@Override
	public void doRender(EntityScarecrow entity, double x, double y, double z, float entityYaw, float partialTicks)
	{
		GlStateManager.pushMatrix();
		GlStateManager.translate(x, y + 1.5F, z);
		GlStateManager.scale(-1, -1, 1);
		GlStateManager.rotate(entity.getRotation(), 0, 1, 0);
		bindEntityTexture(entity);

		if(entity.isLit())
			RENDER_INFO.get(entity.getType().getName()).getRight().render(entity, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
		else
			RENDER_INFO.get(entity.getType().getName()).getMiddle().render(entity, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);

		GlStateManager.popMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityScarecrow entity)
	{
		return RENDER_INFO.get(entity.getType().getName()).getLeft();
	}
}
