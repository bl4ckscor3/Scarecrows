package bl4ckscor3.mod.scarecrows;

import bl4ckscor3.mod.scarecrows.model.ScaryScarecrowModel;
import bl4ckscor3.mod.scarecrows.model.SpookyScarecrowModel;
import bl4ckscor3.mod.scarecrows.model.SpoopyScarecrowModel;
import bl4ckscor3.mod.scarecrows.renderer.ScarecrowRenderer;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod.EventBusSubscriber;
import net.neoforged.fml.common.Mod.EventBusSubscriber.Bus;
import net.neoforged.neoforge.client.event.EntityRenderersEvent.RegisterLayerDefinitions;
import net.neoforged.neoforge.client.event.EntityRenderersEvent.RegisterRenderers;

@EventBusSubscriber(modid = Scarecrows.MODID, value = Dist.CLIENT, bus = Bus.MOD)
public class ClientReg {
	public static final ModelLayerLocation SPOOPY_SCARECROW = new ModelLayerLocation(new ResourceLocation(Scarecrows.MODID, "spoopy_scarecrow"), "main");
	public static final ModelLayerLocation SPOOPY_SCARECROW_LIT = new ModelLayerLocation(new ResourceLocation(Scarecrows.MODID, "spoopy_scarecrow_lit"), "main");
	public static final ModelLayerLocation SPOOKY_SCARECROW = new ModelLayerLocation(new ResourceLocation(Scarecrows.MODID, "spooky_scarecrow"), "main");
	public static final ModelLayerLocation SPOOKY_SCARECROW_LIT = new ModelLayerLocation(new ResourceLocation(Scarecrows.MODID, "spooky_scarecrow_lit"), "main");
	public static final ModelLayerLocation SCARY_SCARECROW = new ModelLayerLocation(new ResourceLocation(Scarecrows.MODID, "scary_scarecrow"), "main");
	public static final ModelLayerLocation SCARY_SCARECROW_LIT = new ModelLayerLocation(new ResourceLocation(Scarecrows.MODID, "scary_scarecrow_lit"), "main");

	@SubscribeEvent
	public static void onRegisterEntityRenderers(RegisterRenderers event) {
		event.registerEntityRenderer(Scarecrows.SCARECROW_ENTITY_TYPE.get(), ScarecrowRenderer::new);
	}

	@SubscribeEvent
	public static void onRegisterLayerDefinitions(RegisterLayerDefinitions event) {
		event.registerLayerDefinition(SPOOPY_SCARECROW, () -> SpoopyScarecrowModel.createLayer(false));
		event.registerLayerDefinition(SPOOPY_SCARECROW_LIT, () -> SpoopyScarecrowModel.createLayer(true));
		event.registerLayerDefinition(SPOOKY_SCARECROW, () -> SpookyScarecrowModel.createLayer(false));
		event.registerLayerDefinition(SPOOKY_SCARECROW_LIT, () -> SpookyScarecrowModel.createLayer(true));
		event.registerLayerDefinition(SCARY_SCARECROW, () -> ScaryScarecrowModel.createLayer(false));
		event.registerLayerDefinition(SCARY_SCARECROW_LIT, () -> ScaryScarecrowModel.createLayer(true));
	}
}
