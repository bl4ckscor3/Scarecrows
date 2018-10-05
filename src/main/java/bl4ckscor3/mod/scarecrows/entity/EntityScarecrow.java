package bl4ckscor3.mod.scarecrows.entity;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntityScarecrow extends Entity
{
	public int range;
	public boolean scareAnimals;

	public EntityScarecrow(World world)
	{
		super(world);
	}

	public EntityScarecrow(World world, BlockPos pos, int height, int range, boolean scareAnimals)
	{
		super(world);

		setSize(1.0F, height);
		setPosition(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D);
		this.range = range;
		this.scareAnimals = scareAnimals;
	}

	@Override
	protected void entityInit() {}

	@Override
	protected void readEntityFromNBT(NBTTagCompound tag)
	{
		range = tag.getInteger("range");
		scareAnimals = tag.getBoolean("scare_animals");
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound tag)
	{
		tag.setInteger("range", range);
		tag.setBoolean("scare_animals", scareAnimals);
	}
}
