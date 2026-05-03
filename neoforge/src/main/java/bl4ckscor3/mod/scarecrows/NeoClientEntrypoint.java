package bl4ckscor3.mod.scarecrows;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(value = Scarecrows.MODID, dist = Dist.CLIENT)
@EventBusSubscriber
public class NeoClientEntrypoint {
	public NeoClientEntrypoint(ModContainer modContainer) {
		modContainer.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
	}

	@SubscribeEvent
	public static void onRegisterEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
		ScarecrowsClient.registerRenderer(event::registerEntityRenderer);
	}

	@SubscribeEvent
	public static void onRegisterLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
		ScarecrowsClient.registerLayerDefinitions(event::registerLayerDefinition);
	}

	@SubscribeEvent
	public static void onFMLClientSetup(FMLClientSetupEvent event) {
		ScarecrowsClient.registerTypes();
	}
}
