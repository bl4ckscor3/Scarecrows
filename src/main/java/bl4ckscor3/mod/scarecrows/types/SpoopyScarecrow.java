package bl4ckscor3.mod.scarecrows.types;

import bl4ckscor3.mod.scarecrows.Configuration;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SpoopyScarecrow extends ScarecrowType
{
	/*
	 *   P				- P: Pumpkin
	 *  ACA				- A: Arm
	 *					- C: Clay
	 */

	public SpoopyScarecrow()
	{
		super(2, Configuration.spoopy_scarecrow.RANGE, Configuration.spoopy_scarecrow.SCARE_ANIMALS);
	}

	@Override
	public boolean checkStructure(World world, BlockPos pos)
	{
		pos = pos.down();

		return hasArms(world, pos) && world.getBlockState(pos).getBlock() == Blocks.CLAY;
	}

	@Override
	public void destroy(World world, BlockPos pos)
	{
		world.destroyBlock(pos, false); //pumpkin
		pos = pos.down();
		world.destroyBlock(pos.west(), false); //a potential arm
		world.destroyBlock(pos.north(), false); //a potential arm
		world.destroyBlock(pos.south(), false); //a potential arm
		world.destroyBlock(pos.east(), false); //a potential arm
		world.destroyBlock(pos, false); //arm attachement block
	}
}
