package bl4ckscor3.mod.scarecrows;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import bl4ckscor3.mod.scarecrows.model.ScaryScarecrowModel;
import bl4ckscor3.mod.scarecrows.model.SpookyScarecrowModel;
import bl4ckscor3.mod.scarecrows.model.SpoopyScarecrowModel;
import bl4ckscor3.mod.scarecrows.renderer.ScarecrowEntityRenderState;
import bl4ckscor3.mod.scarecrows.renderer.ScarecrowRenderer;
import bl4ckscor3.mod.scarecrows.type.ScarecrowType;
import bl4ckscor3.mod.scarecrows.type.ScaryScarecrow;
import bl4ckscor3.mod.scarecrows.type.SpookyScarecrow;
import bl4ckscor3.mod.scarecrows.type.SpoopyScarecrow;
import bl4ckscor3.mod.scarecrows.type.SuperScaryScarecrow;
import bl4ckscor3.mod.scarecrows.type.SuperSpookyScarecrow;
import bl4ckscor3.mod.scarecrows.type.SuperSpoopyScarecrow;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.resources.Identifier;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent.RegisterLayerDefinitions;
import net.neoforged.neoforge.client.event.EntityRenderersEvent.RegisterRenderers;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(value = Scarecrows.MODID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = Scarecrows.MODID, value = Dist.CLIENT)
public class ScarecrowsClient {
	public static final ModelLayerLocation SPOOPY_SCARECROW = new ModelLayerLocation(Identifier.fromNamespaceAndPath(Scarecrows.MODID, "spoopy_scarecrow"), "main");
	public static final ModelLayerLocation SPOOPY_SCARECROW_LIT = new ModelLayerLocation(Identifier.fromNamespaceAndPath(Scarecrows.MODID, "spoopy_scarecrow_lit"), "main");
	public static final ModelLayerLocation SPOOKY_SCARECROW = new ModelLayerLocation(Identifier.fromNamespaceAndPath(Scarecrows.MODID, "spooky_scarecrow"), "main");
	public static final ModelLayerLocation SPOOKY_SCARECROW_LIT = new ModelLayerLocation(Identifier.fromNamespaceAndPath(Scarecrows.MODID, "spooky_scarecrow_lit"), "main");
	public static final ModelLayerLocation SCARY_SCARECROW = new ModelLayerLocation(Identifier.fromNamespaceAndPath(Scarecrows.MODID, "scary_scarecrow"), "main");
	public static final ModelLayerLocation SCARY_SCARECROW_LIT = new ModelLayerLocation(Identifier.fromNamespaceAndPath(Scarecrows.MODID, "scary_scarecrow_lit"), "main");
	public static final Map<ScarecrowType, ClientType> CLIENT_TYPES = new HashMap<>();

	public ScarecrowsClient(ModContainer modContainer) {
		modContainer.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
	}

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

	@SubscribeEvent
	public static void onFMLClientSetup(FMLClientSetupEvent event) {
		CLIENT_TYPES.put(SpoopyScarecrow.TYPE, new ClientType(isLit -> isLit ? SPOOPY_SCARECROW_LIT : SPOOPY_SCARECROW, SpoopyScarecrowModel::new));
		CLIENT_TYPES.put(SuperSpoopyScarecrow.TYPE, new ClientType(isLit -> isLit ? SPOOPY_SCARECROW_LIT : SPOOPY_SCARECROW, SpoopyScarecrowModel::new));
		CLIENT_TYPES.put(SpookyScarecrow.TYPE, new ClientType(isLit -> isLit ? SPOOKY_SCARECROW_LIT : SPOOKY_SCARECROW, SpookyScarecrowModel::new));
		CLIENT_TYPES.put(SuperSpookyScarecrow.TYPE, new ClientType(isLit -> isLit ? SPOOKY_SCARECROW_LIT : SPOOKY_SCARECROW, SpookyScarecrowModel::new));
		CLIENT_TYPES.put(ScaryScarecrow.TYPE, new ClientType(isLit -> isLit ? SCARY_SCARECROW_LIT : SCARY_SCARECROW, ScaryScarecrowModel::new));
		CLIENT_TYPES.put(SuperScaryScarecrow.TYPE, new ClientType(isLit -> isLit ? SCARY_SCARECROW_LIT : SCARY_SCARECROW, ScaryScarecrowModel::new));
	}

	public record ClientType(Function<Boolean, ModelLayerLocation> modelLayerLocationGetter, Function<ModelPart, EntityModel<ScarecrowEntityRenderState>> modelGetter) {}
}
