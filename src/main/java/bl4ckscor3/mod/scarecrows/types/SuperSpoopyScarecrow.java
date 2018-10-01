package bl4ckscor3.mod.scarecrows.types;

import net.minecraft.block.BlockStoneBrick;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SuperSpoopyScarecrow implements IScarecrowType
{
	/*
	 *   P				- P: Pumpkin //TODO: customizable scarecrows which give off light when a lit pumpkin is placed
	 *  ABA				- A: Arm
	 *					- C: Chiseled Stone Bricks
	 */

	@Override
	public boolean checkStructure(World world, BlockPos pos)
	{
		IBlockState state = world.getBlockState(pos = pos.down());

		return hasArms(world, pos) && state.getBlock() == Blocks.STONEBRICK && state.getValue(BlockStoneBrick.VARIANT) == BlockStoneBrick.EnumType.CHISELED;
	}

	@Override
	public void spawn(World world, BlockPos pos, boolean isLit)
	{}
}
