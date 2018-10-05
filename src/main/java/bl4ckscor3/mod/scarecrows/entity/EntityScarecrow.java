package bl4ckscor3.mod.scarecrows.entity;

import bl4ckscor3.mod.scarecrows.types.ScarecrowType;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntityScarecrow extends Entity
{
	private ScarecrowType type;
	private boolean isLit;

	public EntityScarecrow(World world)
	{
		super(world);
	}

	public EntityScarecrow(ScarecrowType type, World world, BlockPos pos, boolean isLit)
	{
		super(world);

		setSize(1.0F, height);
		setPosition(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D);
		this.type = type;
		this.isLit = isLit;
	}

	@Override
	public void onUpdate()
	{
		if(world.getBlockState(getPosition().down()).getBlock().isAir(world.getBlockState(getPosition().down()), world, getPosition()))
			setDead();
	}

	@Override
	public void setDead()
	{
		super.setDead();

		if(!world.isRemote)
		{
			if(isLit)
				world.destroyBlock(getPosition().up(type.getHeight() - 1), false);

			type.dropMaterials(world, getPosition(), isLit);
		}
	}

	@Override
	protected void entityInit() {}

	@Override
	protected void readEntityFromNBT(NBTTagCompound tag)
	{
		String name = tag.getString("scarecrow_type");

		for(ScarecrowType st : ScarecrowType.TYPES)
		{
			if(st.getName().equals(name))
			{
				type = st;
				return;
			}
		}
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound tag)
	{
		tag.setString("scarecrow_type", type.getName());
	}
}
