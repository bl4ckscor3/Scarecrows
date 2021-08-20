package bl4ckscor3.mod.scarecrows.entity;

import bl4ckscor3.mod.scarecrows.ScarecrowTracker;
import bl4ckscor3.mod.scarecrows.Scarecrows;
import bl4ckscor3.mod.scarecrows.type.ScarecrowType;
import bl4ckscor3.mod.scarecrows.util.CustomDataSerializers;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class ScarecrowEntity extends Entity
{
	private static final DataParameter<ScarecrowType> TYPE = EntityDataManager.<ScarecrowType>defineId(ScarecrowEntity.class, CustomDataSerializers.SCARECROWTYPE);
	private static final DataParameter<Boolean> LIT = EntityDataManager.<Boolean>defineId(ScarecrowEntity.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Float> ROTATION = EntityDataManager.<Float>defineId(ScarecrowEntity.class, DataSerializers.FLOAT);
	private static final DataParameter<AxisAlignedBB> AREA = EntityDataManager.<AxisAlignedBB>defineId(ScarecrowEntity.class, CustomDataSerializers.AXISALIGNEDBB);

	public ScarecrowEntity(EntityType<ScarecrowEntity> type, World world)
	{
		super(type, world);
	}

	public ScarecrowEntity(World world)
	{
		super(Scarecrows.SCARECROW_ENTITY_TYPE, world);
	}

	public ScarecrowEntity(ScarecrowType type, World world, BlockPos pos, boolean isLit, Direction facing)
	{
		this(world);

		setPos(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D);
		entityData.set(TYPE, type);
		entityData.set(LIT, isLit);
		entityData.set(ROTATION, facing.toYRot() + 180); //+180 because the default rotation of the model is not at the 0th horizontal facing
		entityData.set(AREA, new AxisAlignedBB(getX(), getY(), getZ(), getX(), getY(), getZ()).inflate(type.getRange(), type.getHeight() * 3, type.getRange()));
	}

	@Override
	protected void defineSynchedData()
	{
		entityData.define(TYPE, null);
		entityData.define(LIT, false);
		entityData.define(ROTATION, 0F);
		entityData.define(AREA, new AxisAlignedBB(0, 0, 0, 0, 0, 0));
	}

	@Override
	public void onAddedToWorld()
	{
		super.onAddedToWorld();
		ScarecrowTracker.track(this);
	}

	@Override
	public void onRemovedFromWorld()
	{
		super.onRemovedFromWorld();
		ScarecrowTracker.stopTracking(this);
	}

	@Override
	public void tick()
	{
		if(level.getBlockState(blockPosition().below()).getBlock().isAir(level.getBlockState(blockPosition().below()), level, blockPosition()))
			remove();
	}

	@Override
	public void remove()
	{
		super.remove();

		if(!level.isClientSide)
		{
			if(isLit())
				level.destroyBlock(blockPosition().above(getScarecrowType().getHeight() - 1), false);

			getScarecrowType().dropMaterials(level, blockPosition(), isLit());
		}
	}

	@Override
	protected void readAdditionalSaveData(CompoundNBT tag)
	{
		String name = tag.getString("type");

		for(ScarecrowType st : ScarecrowType.TYPES)
		{
			if(st.getName().equals(name))
			{
				entityData.set(TYPE, st);
				break;
			}
		}

		entityData.set(LIT, tag.getBoolean("isLit"));
		entityData.set(ROTATION, tag.getFloat("rotation"));
		entityData.set(AREA, new AxisAlignedBB(
				tag.getDouble("areaMinX"),
				tag.getDouble("areaMinY"),
				tag.getDouble("areaMinZ"),
				tag.getDouble("areaMaxX"),
				tag.getDouble("areaMaxY"),
				tag.getDouble("areaMaxZ")));
	}

	@Override
	protected void addAdditionalSaveData(CompoundNBT tag)
	{
		AxisAlignedBB area = getArea();

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
	public ScarecrowType getScarecrowType()
	{
		return entityData.get(TYPE);
	}

	/**
	 * @return true if this entity gives off light, false otherwhise
	 */
	public boolean isLit()
	{
		return entityData.get(LIT);
	}

	/**
	 * @return The rotation of the entity for rendering
	 */
	public Float getRotation()
	{
		return entityData.get(ROTATION);
	}

	/**
	 * @return The area this scarecrow is affecting
	 */
	public AxisAlignedBB getArea()
	{
		return entityData.get(AREA);
	}

	@Override
	public IPacket<?> getAddEntityPacket()
	{
		return NetworkHooks.getEntitySpawningPacket(this);
	}
}
