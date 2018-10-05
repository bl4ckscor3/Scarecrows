package bl4ckscor3.mod.scarecrows.types;

import bl4ckscor3.mod.scarecrows.Configuration;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ScaryScarecrow extends ScarecrowType
{
	/*
	 *   P				- P: Pumpkin
	 *  AEA				- A: Arm
	 *   R				- E: Endstone
	 *   R				- R: End Rod
	 */

	public ScaryScarecrow()
	{
		super(4, Configuration.scary_scarecrow.RANGE, Configuration.scary_scarecrow.SCARE_ANIMALS);
	}

	@Override
	public boolean checkStructure(World world, BlockPos pos)
	{
		IBlockState state = world.getBlockState(pos = pos.down());

		if(hasArms(world, pos) && state.getBlock() == Blocks.END_STONE)
		{
			if(world.getBlockState(pos.down()).getBlock() == Blocks.END_ROD && world.getBlockState(pos.down().down()).getBlock() == Blocks.END_ROD)
				return true;
		}

		return false;
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
		pos = pos.down();
		world.destroyBlock(pos, false); //leg
		world.destroyBlock(pos.down(), false); //foot
	}
}
