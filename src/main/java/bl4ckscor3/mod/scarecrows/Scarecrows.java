package bl4ckscor3.mod.scarecrows;

import bl4ckscor3.mod.scarecrows.block.ArmBlock;
import bl4ckscor3.mod.scarecrows.block.InvisibleLightBlock;
import bl4ckscor3.mod.scarecrows.entity.ScarecrowEntity;
import bl4ckscor3.mod.scarecrows.util.CustomDataSerializers;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.EntityType;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.resources.ResourceLocation;
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

	@ObjectHolder(Scarecrows.PREFIX + ArmBlock.NAME)
	public static final Block ARM = null;
	@ObjectHolder(Scarecrows.PREFIX + InvisibleLightBlock.NAME)
	public static final Block INVISIBLE_LIGHT = null;

	public static final EntityType<ScarecrowEntity> SCARECROW_ENTITY_TYPE = (EntityType<ScarecrowEntity>)EntityType.Builder.<ScarecrowEntity>of(ScarecrowEntity::new, MobCategory.MISC).sized(1.0F, 1.0F).setCustomClientFactory((spawnEntity, world) -> new ScarecrowEntity(world)).setTrackingRange(256).setUpdateInterval(20).setShouldReceiveVelocityUpdates(false).build(PREFIX + "scarecrow").setRegistryName(new ResourceLocation(MODID, "scarecrow"));

	public Scarecrows()
	{
		EntityDataSerializers.registerSerializer(CustomDataSerializers.AXISALIGNEDBB);
		EntityDataSerializers.registerSerializer(CustomDataSerializers.SCARECROWTYPE);
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Configuration.CONFIG_SPEC);
	}

	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event)
	{
		event.getRegistry().register(new ArmBlock());
		event.getRegistry().register(new InvisibleLightBlock());
	}

	@SubscribeEvent
	public static void registerEntities(RegistryEvent.Register<EntityType<?>> event)
	{
		event.getRegistry().register(SCARECROW_ENTITY_TYPE);
	}
}
