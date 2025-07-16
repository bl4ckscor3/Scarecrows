package bl4ckscor3.mod.scarecrows;

import java.util.Arrays;
import java.util.List;

import bl4ckscor3.mod.scarecrows.block.ArmBlock;
import bl4ckscor3.mod.scarecrows.entity.Scarecrow;
import bl4ckscor3.mod.scarecrows.type.ScarecrowType;
import bl4ckscor3.mod.scarecrows.type.ScaryScarecrow;
import bl4ckscor3.mod.scarecrows.type.SpookyScarecrow;
import bl4ckscor3.mod.scarecrows.type.SpoopyScarecrow;
import bl4ckscor3.mod.scarecrows.type.SuperScaryScarecrow;
import bl4ckscor3.mod.scarecrows.type.SuperSpookyScarecrow;
import bl4ckscor3.mod.scarecrows.type.SuperSpoopyScarecrow;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.phys.AABB;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries.Keys;

@Mod(Scarecrows.MODID)
public class Scarecrows {
	public static final String MODID = "scarecrows";
	public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MODID);
	public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(Registries.ENTITY_TYPE, MODID);
	public static final DeferredRegister<EntityDataSerializer<?>> ENTITY_DATA_SERIALIZERS = DeferredRegister.create(Keys.ENTITY_DATA_SERIALIZERS, MODID);
	public static final DeferredBlock<ArmBlock> ARM = BLOCKS.registerBlock("arm", ArmBlock::new, BlockBehaviour.Properties.of()
		//@formatter:off
			.strength(0.25F, 1.0F)
			.sound(SoundType.WOOD)
			.isRedstoneConductor((state, world, pos) -> false));
	//@formatter:on
	public static final DeferredHolder<EntityType<?>, EntityType<Scarecrow>> SCARECROW_ENTITY_TYPE = ENTITY_TYPES.register("scarecrow", () -> EntityType.Builder.<Scarecrow>of(Scarecrow::new, MobCategory.MISC)
		//@formatter:off
			.sized(1.0F, 1.0F)
			.setTrackingRange(256)
			.setUpdateInterval(20)
			.setShouldReceiveVelocityUpdates(false)
			.build(ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(MODID, "scarecrow"))));
	public static final StreamCodec<ByteBuf, AABB> AABB_STREAM_CODEC = StreamCodec.composite(
			ByteBufCodecs.DOUBLE, aabb -> aabb.minX,
			ByteBufCodecs.DOUBLE, aabb -> aabb.minY,
			ByteBufCodecs.DOUBLE, aabb -> aabb.minZ,
			ByteBufCodecs.DOUBLE, aabb -> aabb.maxX,
			ByteBufCodecs.DOUBLE, aabb -> aabb.maxY,
			ByteBufCodecs.DOUBLE, aabb -> aabb.maxZ,
			AABB::new);
	//@formatter:on
	public static final StreamCodec<ByteBuf, ScarecrowType> SCARECROW_TYPE_STREAM_CODEC = StreamCodec.composite(ByteBufCodecs.STRING_UTF8, ScarecrowType::getName, name -> {
		for (ScarecrowType type : Scarecrows.TYPES) {
			if (type.getName().equals(name))
				return type;
		}

		throw new IllegalArgumentException("Non-existent scarecrow type: " + name);
	});
	public static final DeferredHolder<EntityDataSerializer<?>, EntityDataSerializer<ScarecrowType>> SCARECROW_ENTITY_DATA_SERIALIZER = ENTITY_DATA_SERIALIZERS.<EntityDataSerializer<ScarecrowType>>register("scarecrow_type", () -> EntityDataSerializer.forValueType(SCARECROW_TYPE_STREAM_CODEC));
	public static final DeferredHolder<EntityDataSerializer<?>, EntityDataSerializer<AABB>> AABB_ENTITY_DATA_SERIALIZER = ENTITY_DATA_SERIALIZERS.<EntityDataSerializer<AABB>>register("aabb", () -> EntityDataSerializer.forValueType(AABB_STREAM_CODEC));
	//@formatter:off
	public static final List<ScarecrowType> TYPES = Arrays.asList(
		SpoopyScarecrow.TYPE,
		SuperSpoopyScarecrow.TYPE,
		SpookyScarecrow.TYPE,
		SuperSpookyScarecrow.TYPE,
		ScaryScarecrow.TYPE,
		SuperScaryScarecrow.TYPE
	);
	//@formatter:on

	public Scarecrows(IEventBus modEventBus, ModContainer modContainer) {
		modContainer.registerConfig(ModConfig.Type.COMMON, Configuration.CONFIG_SPEC);
		BLOCKS.register(modEventBus);
		ENTITY_TYPES.register(modEventBus);
		ENTITY_DATA_SERIALIZERS.register(modEventBus);
	}
}
