package bl4ckscor3.mod.scarecrows;

import bl4ckscor3.mod.scarecrows.entity.EntityScarecrow;
import bl4ckscor3.mod.scarecrows.renderer.RenderScarecrow;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@EventBusSubscriber(modid=Scarecrows.MODID, value=Dist.CLIENT, bus=Bus.MOD)
public class ClientReg
{
	@SubscribeEvent
	public static void onFMLClientSetup(FMLClientSetupEvent event)
	{
		RenderingRegistry.registerEntityRenderingHandler(EntityScarecrow.class, manager -> new RenderScarecrow(manager));
	}
}
