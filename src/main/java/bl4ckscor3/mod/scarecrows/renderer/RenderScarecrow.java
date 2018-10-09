package bl4ckscor3.mod.scarecrows.renderer;

import java.util.HashMap;

import bl4ckscor3.mod.scarecrows.entity.EntityScarecrow;
import bl4ckscor3.mod.scarecrows.type.ScarecrowType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Tuple;

public class RenderScarecrow extends Render<EntityScarecrow>
{
	//used so the memory doesn't build up with new instances of resource locations and models each render tick
	public static final HashMap<String,Tuple<ResourceLocation,ModelBase>> RENDER_INFO = new HashMap<String,Tuple<ResourceLocation,ModelBase>>();

	public RenderScarecrow()
	{
		super(Minecraft.getMinecraft().getRenderManager());

		for(ScarecrowType type : ScarecrowType.TYPES)
		{
			RENDER_INFO.put(type.getName(), new Tuple<ResourceLocation,ModelBase>(new ResourceLocation("scarecrows:textures/entity/" + type.getName() + ".png"), type.getModel()));
		}
	}

	@Override
	public void doRender(EntityScarecrow entity, double x, double y, double z, float entityYaw, float partialTicks)
	{
		GlStateManager.pushMatrix();
		GlStateManager.translate(x, y + 1.5F, z);
		GlStateManager.scale(-1, -1, 1);
		bindEntityTexture(entity);
		RENDER_INFO.get(entity.getType().getName()).getSecond().render(entity, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
		GlStateManager.popMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityScarecrow entity)
	{
		return RENDER_INFO.get(entity.getType().getName()).getFirst();
	}
}
