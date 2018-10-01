package bl4ckscor3.mod.scarecrows.types;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SpookyScarecrow implements IScarecrowType
{
	/*
	 *   P				- P: Pumpkin //TODO: customizable scarecrows which give off light when a lit pumpkin is placed
	 *  ANA				- A: Arm
	 *   F				- N: Netherrack
	 *   				- F: Netherbrick Fence
	 */

	@Override
	public boolean checkStructure(World world, BlockPos pos)
	{
		IBlockState state = world.getBlockState(pos = pos.down());

		if(hasArms(world, pos) && state.getBlock() == Blocks.NETHERRACK)
		{
			if(world.getBlockState(pos.down()).getBlock() == Blocks.NETHER_BRICK_FENCE)
				return true;
		}

		return false;
	}

	@Override
	public void spawn(World world, BlockPos pos)
	{}
}
