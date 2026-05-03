package bl4ckscor3.mod.scarecrows;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

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
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;

public class ScarecrowsClient {
	public static final ModelLayerLocation SPOOPY_SCARECROW = new ModelLayerLocation(Identifier.fromNamespaceAndPath(Scarecrows.MODID, "spoopy_scarecrow"), "main");
	public static final ModelLayerLocation SPOOPY_SCARECROW_LIT = new ModelLayerLocation(Identifier.fromNamespaceAndPath(Scarecrows.MODID, "spoopy_scarecrow_lit"), "main");
	public static final ModelLayerLocation SPOOKY_SCARECROW = new ModelLayerLocation(Identifier.fromNamespaceAndPath(Scarecrows.MODID, "spooky_scarecrow"), "main");
	public static final ModelLayerLocation SPOOKY_SCARECROW_LIT = new ModelLayerLocation(Identifier.fromNamespaceAndPath(Scarecrows.MODID, "spooky_scarecrow_lit"), "main");
	public static final ModelLayerLocation SCARY_SCARECROW = new ModelLayerLocation(Identifier.fromNamespaceAndPath(Scarecrows.MODID, "scary_scarecrow"), "main");
	public static final ModelLayerLocation SCARY_SCARECROW_LIT = new ModelLayerLocation(Identifier.fromNamespaceAndPath(Scarecrows.MODID, "scary_scarecrow_lit"), "main");
	public static final Map<ScarecrowType, ClientType> CLIENT_TYPES = new HashMap<>();

	public static void registerRenderer(EntityRendererRegistration registrar) {
		registrar.register(Scarecrows.SCARECROW_ENTITY_TYPE.get(), ScarecrowRenderer::new);
	}

	public static void registerLayerDefinitions(LayerDefinitionRegistration registrar) {
		registrar.register(SPOOPY_SCARECROW, () -> SpoopyScarecrowModel.createLayer(false));
		registrar.register(SPOOPY_SCARECROW_LIT, () -> SpoopyScarecrowModel.createLayer(true));
		registrar.register(SPOOKY_SCARECROW, () -> SpookyScarecrowModel.createLayer(false));
		registrar.register(SPOOKY_SCARECROW_LIT, () -> SpookyScarecrowModel.createLayer(true));
		registrar.register(SCARY_SCARECROW, () -> ScaryScarecrowModel.createLayer(false));
		registrar.register(SCARY_SCARECROW_LIT, () -> ScaryScarecrowModel.createLayer(true));
	}

	public static void registerTypes() {
		CLIENT_TYPES.put(SpoopyScarecrow.TYPE, new ClientType(isLit -> isLit ? SPOOPY_SCARECROW_LIT : SPOOPY_SCARECROW, SpoopyScarecrowModel::new));
		CLIENT_TYPES.put(SuperSpoopyScarecrow.TYPE, new ClientType(isLit -> isLit ? SPOOPY_SCARECROW_LIT : SPOOPY_SCARECROW, SpoopyScarecrowModel::new));
		CLIENT_TYPES.put(SpookyScarecrow.TYPE, new ClientType(isLit -> isLit ? SPOOKY_SCARECROW_LIT : SPOOKY_SCARECROW, SpookyScarecrowModel::new));
		CLIENT_TYPES.put(SuperSpookyScarecrow.TYPE, new ClientType(isLit -> isLit ? SPOOKY_SCARECROW_LIT : SPOOKY_SCARECROW, SpookyScarecrowModel::new));
		CLIENT_TYPES.put(ScaryScarecrow.TYPE, new ClientType(isLit -> isLit ? SCARY_SCARECROW_LIT : SCARY_SCARECROW, ScaryScarecrowModel::new));
		CLIENT_TYPES.put(SuperScaryScarecrow.TYPE, new ClientType(isLit -> isLit ? SCARY_SCARECROW_LIT : SCARY_SCARECROW, ScaryScarecrowModel::new));
	}

	public record ClientType(Function<Boolean, ModelLayerLocation> modelLayerLocationGetter, Function<ModelPart, EntityModel<ScarecrowEntityRenderState>> modelGetter) {}

	@FunctionalInterface
	public interface EntityRendererRegistration {
		<T extends Entity> void register(EntityType<? extends T> entityType, EntityRendererProvider<T> entityRendererProvider);
	}

	@FunctionalInterface
	public interface LayerDefinitionRegistration {
		void register(ModelLayerLocation layerLocation, Supplier<LayerDefinition> supplier);
	}
}
