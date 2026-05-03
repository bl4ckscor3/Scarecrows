package bl4ckscor3.mod.scarecrows;

import fuzs.forgeconfigapiport.fabric.api.v5.client.ConfigScreenFactoryRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.ModelLayerRegistry;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;

public class FabricClientEntrypoint implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		ConfigScreenFactoryRegistry.INSTANCE.register(Scarecrows.MODID, ConfigurationScreen::new);
		ScarecrowsClient.registerTypes();
		ScarecrowsClient.registerRenderer(EntityRenderers::register);
		ScarecrowsClient.registerLayerDefinitions((layerLocation, supplier) -> ModelLayerRegistry.registerModelLayer(layerLocation, supplier::get));
	}
}
