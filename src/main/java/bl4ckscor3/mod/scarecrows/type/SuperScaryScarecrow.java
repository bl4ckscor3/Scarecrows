package bl4ckscor3.mod.scarecrows.type;

import bl4ckscor3.mod.scarecrows.Configuration;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
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
		super("super_scary_scarecrow", 4, Configuration.super_scary_scarecrow.RANGE, Configuration.super_scary_scarecrow.SCARE_ANIMALS);
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

	@Override
	public ItemStack[] getDrops()
	{
		return new ItemStack[] {new ItemStack(Items.STICK, 2), new ItemStack(Blocks.PURPUR_PILLAR), new ItemStack(Blocks.END_ROD, 2)};
	}
}
