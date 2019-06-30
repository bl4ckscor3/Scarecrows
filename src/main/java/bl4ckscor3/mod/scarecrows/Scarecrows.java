package bl4ckscor3.mod.scarecrows;

import bl4ckscor3.mod.scarecrows.block.BlockArm;
import bl4ckscor3.mod.scarecrows.block.BlockInvisibleLight;
import bl4ckscor3.mod.scarecrows.entity.EntityScarecrow;
import bl4ckscor3.mod.scarecrows.util.CustomDataSerializers;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.registries.ObjectHolder;

@Mod(Scarecrows.MODID)
@EventBusSubscriber(bus=Bus.MOD)
public class Scarecrows
{
	public static final String MODID = "scarecrows";
	public static final String PREFIX = MODID + ":";

	@ObjectHolder(Scarecrows.PREFIX + BlockArm.NAME)
	public static final Block ARM = null;
	@ObjectHolder(Scarecrows.PREFIX + BlockInvisibleLight.NAME)
	public static final Block INVISIBLE_LIGHT = null;

	public static final EntityType<EntityScarecrow> SCARECROW_ENTITY_TYPE = (EntityType<EntityScarecrow>)EntityType.Builder.<EntityScarecrow>create(EntityScarecrow::new, EntityClassification.MISC).size(1.0F, 1.0F).setCustomClientFactory((spawnEntity, world) -> new EntityScarecrow(world)).setTrackingRange(256).setUpdateInterval(20).setShouldReceiveVelocityUpdates(false).build(PREFIX + "scarecrow").setRegistryName(new ResourceLocation(MODID, "scarecrow"));

	public Scarecrows()
	{
		DataSerializers.registerSerializer(CustomDataSerializers.AXISALIGNEDBB);
		DataSerializers.registerSerializer(CustomDataSerializers.SCARECROWTYPE);
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Configuration.CONFIG_SPEC);
	}

	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event)
	{
		event.getRegistry().register(new BlockArm());
		event.getRegistry().register(new BlockInvisibleLight());
	}

	@SubscribeEvent
	public static void registerEntities(RegistryEvent.Register<EntityType<?>> event)
	{
		event.getRegistry().register(SCARECROW_ENTITY_TYPE);
	}
}
