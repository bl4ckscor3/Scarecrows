package bl4ckscor3.mod.scarecrows;

import bl4ckscor3.mod.scarecrows.block.ArmBlock;
import bl4ckscor3.mod.scarecrows.entity.Scarecrow;
import bl4ckscor3.mod.scarecrows.type.ScarecrowType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistries.Keys;
import net.minecraftforge.registries.RegistryObject;

@Mod(Scarecrows.MODID)
@EventBusSubscriber(bus=Bus.MOD)
public class Scarecrows
{
	public static final String MODID = "scarecrows";
	public static final String PREFIX = MODID + ":";
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
	public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, MODID);
	public static final DeferredRegister<EntityDataSerializer<?>> ENTITY_DATA_SERIALIZERS = DeferredRegister.create(Keys.ENTITY_DATA_SERIALIZERS, MODID);
	public static final RegistryObject<ArmBlock> ARM = BLOCKS.register("arm", () -> new ArmBlock(Properties.of(Material.WOOD)
			.strength(0.25F, 1.0F)
			.sound(SoundType.WOOD)
			.isRedstoneConductor((state, world, pos) -> false)));
	public static final RegistryObject<EntityType<Scarecrow>> SCARECROW_ENTITY_TYPE = ENTITY_TYPES.register("scarecrow", () -> EntityType.Builder.<Scarecrow>of(Scarecrow::new, MobCategory.MISC)
			.sized(1.0F, 1.0F)
			.setTrackingRange(256)
			.setUpdateInterval(20)
			.setShouldReceiveVelocityUpdates(false)
			.build(PREFIX + "scarecrow"));
	public static final RegistryObject<EntityDataSerializer<ScarecrowType>> SCARECROW_ENTITY_DATA_SERIALIZER = ENTITY_DATA_SERIALIZERS.<EntityDataSerializer<ScarecrowType>>register("scarecrow_type", () -> new EntityDataSerializer<>()
	{
		@Override
		public void write(FriendlyByteBuf buf, ScarecrowType value)
		{
			buf.writeUtf(value.getName());
		}

		@Override
		public ScarecrowType read(FriendlyByteBuf buf)
		{
			String bufferedName = buf.readUtf(Integer.MAX_VALUE / 4);

			for(ScarecrowType type : ScarecrowType.TYPES)
			{
				if(type.getName().equals(bufferedName))
					return type;
			}

			return null;
		}

		@Override
		public EntityDataAccessor<ScarecrowType> createAccessor(int id)
		{
			return new EntityDataAccessor<>(id, this);
		}

		@Override
		public ScarecrowType copy(ScarecrowType value)
		{
			return value;
		}
	});
	public static final RegistryObject<EntityDataSerializer<AABB>> AABB_ENTITY_DATA_SERIALIZER = ENTITY_DATA_SERIALIZERS.<EntityDataSerializer<AABB>>register("aabb", () -> new EntityDataSerializer<>()
	{
		@Override
		public void write(FriendlyByteBuf buf, AABB value)
		{
			buf.writeDouble(value.minX);
			buf.writeDouble(value.minY);
			buf.writeDouble(value.minZ);
			buf.writeDouble(value.maxX);
			buf.writeDouble(value.maxY);
			buf.writeDouble(value.maxZ);
		}

		@Override
		public AABB read(FriendlyByteBuf buf)
		{
			return new AABB(buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readDouble());
		}

		@Override
		public EntityDataAccessor<AABB> createAccessor(int id)
		{
			return new EntityDataAccessor<>(id, this);
		}

		@Override
		public AABB copy(AABB value)
		{
			return value.inflate(0);
		}
	});

	public Scarecrows()
	{
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Configuration.CONFIG_SPEC);
		BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
		ENTITY_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());
		ENTITY_DATA_SERIALIZERS.register(FMLJavaModLoadingContext.get().getModEventBus());
	}
}
