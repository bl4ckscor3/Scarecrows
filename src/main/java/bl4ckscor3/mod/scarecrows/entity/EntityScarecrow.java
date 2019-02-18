package bl4ckscor3.mod.scarecrows.entity;

import bl4ckscor3.mod.scarecrows.Scarecrows;
import bl4ckscor3.mod.scarecrows.type.ScarecrowType;
import bl4ckscor3.mod.scarecrows.util.CustomDataSerializers;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntityScarecrow extends Entity
{
	private static final DataParameter<ScarecrowType> TYPE = EntityDataManager.<ScarecrowType>createKey(EntityScarecrow.class, CustomDataSerializers.SCARECROWTYPE);
	private static final DataParameter<Boolean> LIT = EntityDataManager.<Boolean>createKey(EntityScarecrow.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Float> ROTATION = EntityDataManager.<Float>createKey(EntityScarecrow.class, DataSerializers.FLOAT);
	private static final DataParameter<AxisAlignedBB> AREA = EntityDataManager.<AxisAlignedBB>createKey(EntityScarecrow.class, CustomDataSerializers.AXISALIGNEDBB);

	public EntityScarecrow(World world)
	{
		super(Scarecrows.SCARECROW_ENTITY_TYPE, world);
	}

	public EntityScarecrow(ScarecrowType type, World world, BlockPos pos, boolean isLit, EnumFacing facing)
	{
		this(world);

		setSize(1.0F, height);
		setPosition(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D);
		dataManager.set(TYPE, type);
		dataManager.set(LIT, isLit);
		dataManager.set(ROTATION, facing.getHorizontalAngle() + 180); //+180 because the default rotation of the model is not at the 0th horizontal facing
		dataManager.set(AREA, new AxisAlignedBB(posX, posY, posZ, posX, posY, posZ).grow(type.getRange(), type.getHeight() * 3, type.getRange()));
	}

	@Override
	protected void registerData()
	{
		dataManager.register(TYPE, null);
		dataManager.register(LIT, false);
		dataManager.register(ROTATION, 0F);
		dataManager.register(AREA, new AxisAlignedBB(0, 0, 0, 0, 0, 0));
	}

	@Override
	public void tick()
	{
		if(world.getBlockState(getPosition().down()).getBlock().isAir(world.getBlockState(getPosition().down()), world, getPosition()))
			remove();
	}

	@Override
	public void remove()
	{
		super.remove();

		if(!world.isRemote)
		{
			if(isLit())
				world.destroyBlock(getPosition().up(getScarecrowType().getHeight() - 1), false);

			getScarecrowType().dropMaterials(world, getPosition(), isLit());
		}
	}

	@Override
	protected void readAdditional(NBTTagCompound tag)
	{
		String name = tag.getString("type");

		for(ScarecrowType st : ScarecrowType.TYPES)
		{
			if(st.getName().equals(name))
			{
				dataManager.set(TYPE, st);
				break;
			}
		}

		dataManager.set(LIT, tag.getBoolean("isLit"));
		dataManager.set(ROTATION, tag.getFloat("rotation"));
		dataManager.set(AREA, new AxisAlignedBB(
				tag.getDouble("areaMinX"),
				tag.getDouble("areaMinY"),
				tag.getDouble("areaMinZ"),
				tag.getDouble("areaMaxX"),
				tag.getDouble("areaMaxY"),
				tag.getDouble("areaMaxZ")));
	}

	@Override
	protected void writeAdditional(NBTTagCompound tag)
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
		return dataManager.get(TYPE);
	}

	/**
	 * @return true if this entity gives off light, false otherwhise
	 */
	public boolean isLit()
	{
		return dataManager.get(LIT);
	}

	/**
	 * @return The rotation of the entity for rendering
	 */
	public Float getRotation()
	{
		return dataManager.get(ROTATION);
	}

	/**
	 * @return The area this scarecrow is affecting
	 */
	public AxisAlignedBB getArea()
	{
		return dataManager.get(AREA);
	}
}
