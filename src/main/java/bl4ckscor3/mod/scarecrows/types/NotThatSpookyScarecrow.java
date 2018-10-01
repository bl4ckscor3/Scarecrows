package bl4ckscor3.mod.scarecrows.types;

import net.minecraft.block.BlockColored;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class NotThatSpookyScarecrow implements IScarecrowType
{
	/*
	 *   P				- P: Pumpkin //TODO: customizable scarecrows which give off light when a lit pumpkin is placed
	 *  AWA				- A: Arm
	 *   F				- W: Wool //TODO: customizable scarecrows with different colors
	 * 					- F: Fence //TODO: customizable scarecrows with any type of fence
	 */

	@Override
	public boolean checkStructure(World world, BlockPos pos)
	{
		IBlockState state = world.getBlockState(pos = pos.down());

		if(hasArms(world, pos) && state.getBlock() == Blocks.WOOL && state.getValue(BlockColored.COLOR) == EnumDyeColor.BROWN)
		{
			pos = pos.down();
			state = world.getBlockState(pos);

			if(state.getBlock() == Blocks.OAK_FENCE)
				return true;
		}

		return false;
	}

	@Override
	public void spawn(World world, BlockPos pos)
	{}
}
