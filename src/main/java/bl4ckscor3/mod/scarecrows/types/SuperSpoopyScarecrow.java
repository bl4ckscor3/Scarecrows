package bl4ckscor3.mod.scarecrows.types;

import bl4ckscor3.mod.scarecrows.Configuration;
import net.minecraft.block.BlockStoneBrick;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SuperSpoopyScarecrow extends ScarecrowType
{
	/*
	 *   P				- P: Pumpkin
	 *  ABA				- A: Arm
	 *					- C: Chiseled Stone Bricks
	 */

	public SuperSpoopyScarecrow()
	{
		super(2, Configuration.super_spoopy_scarecrow.RANGE, Configuration.super_spoopy_scarecrow.SCARE_ANIMALS);
	}

	@Override
	public boolean checkStructure(World world, BlockPos pos)
	{
		IBlockState state = world.getBlockState(pos = pos.down());

		return hasArms(world, pos) && state.getBlock() == Blocks.STONEBRICK && state.getValue(BlockStoneBrick.VARIANT) == BlockStoneBrick.EnumType.CHISELED;
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
