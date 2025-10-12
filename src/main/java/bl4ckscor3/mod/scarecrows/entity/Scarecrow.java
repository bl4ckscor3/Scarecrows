package bl4ckscor3.mod.scarecrows.entity;

import bl4ckscor3.mod.scarecrows.ScarecrowTracker;
import bl4ckscor3.mod.scarecrows.Scarecrows;
import bl4ckscor3.mod.scarecrows.type.ScarecrowType;
import bl4ckscor3.mod.scarecrows.type.SpoopyScarecrow;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.AABB;

public class Scarecrow extends Entity {
	private static final EntityDataAccessor<ScarecrowType> TYPE = SynchedEntityData.<ScarecrowType>defineId(Scarecrow.class, Scarecrows.SCARECROW_ENTITY_DATA_SERIALIZER.get());
	private static final EntityDataAccessor<Boolean> LIT = SynchedEntityData.<Boolean>defineId(Scarecrow.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Float> ROTATION = SynchedEntityData.<Float>defineId(Scarecrow.class, EntityDataSerializers.FLOAT);
	private static final EntityDataAccessor<AABB> AREA = SynchedEntityData.<AABB>defineId(Scarecrow.class, Scarecrows.AABB_ENTITY_DATA_SERIALIZER.get());

	public Scarecrow(EntityType<Scarecrow> type, Level world) {
		super(type, world);
	}

	public Scarecrow(Level world) {
		super(Scarecrows.SCARECROW_ENTITY_TYPE.get(), world);
	}

	public Scarecrow(ScarecrowType type, Level world, BlockPos pos, boolean isLit, Direction facing) {
		this(world);

		setPos(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D);
		entityData.set(TYPE, type);
		entityData.set(LIT, isLit);
		entityData.set(ROTATION, facing.toYRot() + 180); //+180 because the default rotation of the model is not at the 0th horizontal facing
		entityData.set(AREA, new AABB(getX(), getY(), getZ(), getX(), getY(), getZ()).inflate(type.getRange(), type.getHeight() * 3, type.getRange()));
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		builder.define(TYPE, SpoopyScarecrow.TYPE);
		builder.define(LIT, false);
		builder.define(ROTATION, 0F);
		builder.define(AREA, new AABB(0, 0, 0, 0, 0, 0));
	}

	@Override
	public void onAddedToLevel() {
		super.onAddedToLevel();
		ScarecrowTracker.track(this);
	}

	@Override
	public void onRemovedFromLevel() {
		super.onRemovedFromLevel();
		ScarecrowTracker.stopTracking(this);
	}

	@Override
	public void tick() {
		if (level().getBlockState(blockPosition().below()).isAir())
			remove(RemovalReason.KILLED);
	}

	@Override
	public void remove(RemovalReason reason) {
		super.remove(reason);

		if (!level().isClientSide()) {
			if (isLit())
				level().destroyBlock(blockPosition().above(getScarecrowType().getHeight() - 1), false);

			getScarecrowType().dropMaterials(level(), blockPosition(), isLit());
		}
	}

	@Override
	protected void readAdditionalSaveData(ValueInput tag) {
		String name = tag.getStringOr("type", SpoopyScarecrow.TYPE.getName());

		for (ScarecrowType st : Scarecrows.TYPES) {
			if (st.getName().equals(name)) {
				entityData.set(TYPE, st);
				break;
			}
		}

		entityData.set(LIT, tag.getBooleanOr("isLit", false));
		entityData.set(ROTATION, tag.getFloatOr("rotation", 0.0F));
		entityData.set(AREA, new AABB(tag.getDoubleOr("areaMinX", 0D), tag.getDoubleOr("areaMinY", 0D), tag.getDoubleOr("areaMinZ", 0D), tag.getDoubleOr("areaMaxX", 0D), tag.getDoubleOr("areaMaxY", 0D), tag.getDoubleOr("areaMaxZ", 0D)));
	}

	@Override
	protected void addAdditionalSaveData(ValueOutput tag) {
		AABB area = getArea();

		tag.putString("type", getScarecrowType().getName());
		tag.putBoolean("isLit", isLit());
		tag.putFloat("rotation", getRotation());
		tag.putDouble("areaMinX", area.minX);
		tag.putDouble("areaMinY", area.minY);
		tag.putDouble("areaMinZ", area.minZ);
		tag.putDouble("areaMaxX", area.maxX);
		tag.putDouble("areaMaxY", area.maxY);
		tag.putDouble("areaMaxZ", area.maxZ);
	}

	/**
	 * @return The type of this scarecrow
	 */
	public ScarecrowType getScarecrowType() {
		return entityData.get(TYPE);
	}

	/**
	 * @return true if this entity gives off light, false otherwhise
	 */
	public boolean isLit() {
		return entityData.get(LIT);
	}

	/**
	 * @return The rotation of the entity for rendering
	 */
	public Float getRotation() {
		return entityData.get(ROTATION);
	}

	/**
	 * @return The area this scarecrow is affecting
	 */
	public AABB getArea() {
		return entityData.get(AREA);
	}

	@Override
	public Packet<ClientGamePacketListener> getAddEntityPacket(ServerEntity serverEntity) {
		return new ClientboundAddEntityPacket(this, serverEntity);
	}

	@Override
	public boolean hurtServer(ServerLevel level, DamageSource damageSource, float amount) {
		return false;
	}
}
