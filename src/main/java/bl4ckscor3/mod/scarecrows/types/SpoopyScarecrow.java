package bl4ckscor3.mod.scarecrows.types;

import bl4ckscor3.mod.scarecrows.Scarecrows;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SpoopyScarecrow implements IScarecrowType
{
	/*
	 *   P				- P: Pumpkin
	 *  ACA				- A: Arm
	 *					- C: Clay
	 */

	@Override
	public boolean checkStructure(World world, BlockPos pos)
	{
		pos = pos.down();

		return hasArms(world, pos) && world.getBlockState(pos).getBlock() == Blocks.CLAY;
	}

	@Override
	public void spawn(World world, BlockPos pos, boolean isLit)
	{}

	@Override
	public int getHeight()
	{
		return 2;
	}
}
