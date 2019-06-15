package bl4ckscor3.mod.scarecrows;

import bl4ckscor3.mod.scarecrows.entity.EntityScarecrow;
import bl4ckscor3.mod.scarecrows.renderer.RenderScarecrow;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid=Scarecrows.MODID, value=Dist.CLIENT)
public class ClientReg
{
	@SubscribeEvent
	public static void onModelRegistry(ModelRegistryEvent event)
	{
		RenderingRegistry.registerEntityRenderingHandler(EntityScarecrow.class, manager -> new RenderScarecrow(manager));
	}
}
