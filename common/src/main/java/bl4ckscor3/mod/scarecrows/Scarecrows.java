package bl4ckscor3.mod.scarecrows;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import com.google.common.base.Suppliers;

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
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.phys.AABB;

public class Scarecrows {
	public static final String MODID = "scarecrows";
	private static Platform platform;
	public static final RegistryObject<ArmBlock> ARM = RegistryObject.block("arm", ArmBlock::new, () -> BlockBehaviour.Properties.of()
		.strength(0.25F, 1.0F)
		.sound(SoundType.WOOD)
		.isRedstoneConductor((_, _, _) -> false));
	public static final Supplier<EntityType<Scarecrow>> SCARECROW_ENTITY_TYPE = Suppliers.memoize(() -> EntityType.Builder.<Scarecrow>of(Scarecrow::new, MobCategory.MISC)
		.sized(1.0F, 1.0F)
		.clientTrackingRange(256)
		.updateInterval(20)
		.build(ResourceKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(MODID, "scarecrow"))));
	public static final StreamCodec<ByteBuf, AABB> AABB_STREAM_CODEC = StreamCodec.composite(
		ByteBufCodecs.DOUBLE, aabb -> aabb.minX,
		ByteBufCodecs.DOUBLE, aabb -> aabb.minY,
		ByteBufCodecs.DOUBLE, aabb -> aabb.minZ,
		ByteBufCodecs.DOUBLE, aabb -> aabb.maxX,
		ByteBufCodecs.DOUBLE, aabb -> aabb.maxY,
		ByteBufCodecs.DOUBLE, aabb -> aabb.maxZ,
		AABB::new);
	public static final StreamCodec<ByteBuf, ScarecrowType> SCARECROW_TYPE_STREAM_CODEC = StreamCodec.composite(ByteBufCodecs.STRING_UTF8, ScarecrowType::getName, name -> {
		for (ScarecrowType type : Scarecrows.TYPES) {
			if (type.getName().equals(name))
				return type;
		}

		throw new IllegalArgumentException("Non-existent scarecrow type: " + name);
	});
	public static final Supplier<EntityDataSerializer<ScarecrowType>> SCARECROW_ENTITY_DATA_SERIALIZER = Suppliers.memoize(() -> EntityDataSerializer.forValueType(SCARECROW_TYPE_STREAM_CODEC));
	public static final Supplier<EntityDataSerializer<AABB>> AABB_ENTITY_DATA_SERIALIZER = Suppliers.memoize(() -> EntityDataSerializer.forValueType(AABB_STREAM_CODEC));
	public static final List<ScarecrowType> TYPES = new ArrayList<>();

	public synchronized static void initialize(Platform platform) {
		if (Scarecrows.platform != null) {
			throw new IllegalArgumentException(MODID + " platform has already been initialized");
		}

		Scarecrows.platform = platform;
		platform.register(Registries.BLOCK, ARM);
		platform.register(Registries.ENTITY_TYPE, SCARECROW_ENTITY_TYPE, "scarecrow");
		platform.registerEntityDataSerializer(SCARECROW_ENTITY_DATA_SERIALIZER, "scarecrow_type");
		platform.registerEntityDataSerializer(AABB_ENTITY_DATA_SERIALIZER, "aabb");
	}

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MODID, path);
	}

	public static Platform platform() {
		return platform;
	}

	public static void registerTypes() {
		TYPES.add(SpoopyScarecrow.TYPE);
		TYPES.add(SuperSpoopyScarecrow.TYPE);
		TYPES.add(SpookyScarecrow.TYPE);
		TYPES.add(SuperSpookyScarecrow.TYPE);
		TYPES.add(ScaryScarecrow.TYPE);
		TYPES.add(SuperScaryScarecrow.TYPE);
	}
}
