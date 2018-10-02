package bl4ckscor3.mod.scarecrows.types;

import net.minecraft.block.BlockQuartz;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SuperSpookyScarecrow implements IScarecrowType
{
	/*
	 *   P				- P: Pumpkin
	 *  AQA				- A: Arm
	 *   F				- Q: Quartz Pillar
	 *   				- F: Netherbrick Fence
	 */

	@Override
	public boolean checkStructure(World world, BlockPos pos)
	{
		IBlockState state = world.getBlockState(pos = pos.down());

		if(hasArms(world, pos) && state.getBlock() == Blocks.QUARTZ_BLOCK && state.getValue(BlockQuartz.VARIANT) == BlockQuartz.EnumType.LINES_Y)
		{
			if(world.getBlockState(pos.down()).getBlock() == Blocks.NETHER_BRICK_FENCE)
				return true;
		}

		return false;
	}

	@Override
	public void spawn(World world, BlockPos pos, boolean isLit)
	{}

	@Override
	public int getHeight()
	{
		return 3;
	}
}
