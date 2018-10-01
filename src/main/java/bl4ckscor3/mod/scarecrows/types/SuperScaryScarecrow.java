package bl4ckscor3.mod.scarecrows.types;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SuperScaryScarecrow implements IScarecrowType
{
	/*
	 *   P				- P: Pumpkin //TODO: customizable scarecrows which give off light when a lit pumpkin is placed
	 *  ABA				- A: Arm
	 *   R				- B: Purpur Pillar
	 *   R				- R: End Rod
	 */

	@Override
	public boolean checkStructure(World world, BlockPos pos)
	{
		IBlockState state = world.getBlockState(pos = pos.down());

		if(hasArms(world, pos) && state.getBlock() == Blocks.PURPUR_PILLAR)
		{
			if(world.getBlockState(pos.down()).getBlock() == Blocks.END_ROD && world.getBlockState(pos.down().down()).getBlock() == Blocks.END_ROD)
				return true;
		}

		return false;
	}

	@Override
	public void spawn(World world, BlockPos pos, boolean isLit)
	{}
}
