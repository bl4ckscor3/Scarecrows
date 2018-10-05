package bl4ckscor3.mod.scarecrows.types;

import bl4ckscor3.mod.scarecrows.Configuration;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SuperScaryScarecrow extends ScarecrowType
{
	/*
	 *   P				- P: Pumpkin
	 *  ABA				- A: Arm
	 *   R				- B: Purpur Pillar
	 *   R				- R: End Rod
	 */

	public SuperScaryScarecrow()
	{
		super(4, Configuration.super_scary_scarecrow.RANGE, Configuration.super_scary_scarecrow.SCARE_ANIMALS);
	}

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
}
