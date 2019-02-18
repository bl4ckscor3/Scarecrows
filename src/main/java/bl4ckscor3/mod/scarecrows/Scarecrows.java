package bl4ckscor3.mod.scarecrows;

import bl4ckscor3.mod.scarecrows.block.BlockArm;
import bl4ckscor3.mod.scarecrows.block.BlockInvisibleLight;
import bl4ckscor3.mod.scarecrows.entity.EntityScarecrow;
import bl4ckscor3.mod.scarecrows.renderer.RenderScarecrow;
import bl4ckscor3.mod.scarecrows.util.CustomDataSerializers;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.registries.ObjectHolder;

@Mod(Scarecrows.MODID )
@EventBusSubscriber(bus=Bus.MOD)
public class Scarecrows
{
	public static final String MODID = "scarecrows";
	public static final String NAME = "Scarecrows";
	public static final String VERSION = "v1.0.3";
	public static final String MC_VERSION = "1.12";
	public static final String PREFIX = MODID + ":";

	@ObjectHolder(Scarecrows.PREFIX + BlockArm.NAME)
	public static final Block ARM = null;
	@ObjectHolder(Scarecrows.PREFIX + BlockInvisibleLight.NAME)
	public static final Block INVISIBLE_LIGHT = null;

	public static final EntityType<EntityScarecrow> SCARECROW_ENTITY_TYPE = EntityType.register(PREFIX + "scarecrow", EntityType.Builder.create(EntityScarecrow.class, EntityScarecrow::new).tracker(256, 20, false));

	public Scarecrows()
	{
		DataSerializers.registerSerializer(CustomDataSerializers.AXISALIGNEDBB);
		DataSerializers.registerSerializer(CustomDataSerializers.SCARECROWTYPE);
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Configuration.CONFIG_SPEC);
		MinecraftForge.EVENT_BUS.addListener(this::onModelRegistry);
	}

	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event)
	{
		event.getRegistry().register(new BlockArm());
		event.getRegistry().register(new BlockInvisibleLight());
	}

	public void onModelRegistry(ModelRegistryEvent event)
	{
		RenderingRegistry.registerEntityRenderingHandler(EntityScarecrow.class, manager -> new RenderScarecrow(manager));
	}
}
