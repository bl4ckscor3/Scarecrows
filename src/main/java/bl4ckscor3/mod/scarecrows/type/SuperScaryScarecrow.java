package bl4ckscor3.mod.scarecrows.type;

import bl4ckscor3.mod.scarecrows.Configuration;
import bl4ckscor3.mod.scarecrows.model.ModelScaryScarecrow;
import net.minecraft.block.BlockEndRod;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.model.ModelBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
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
	public boolean checkStructure(World world, BlockPos pos, EnumFacing pumpkinFacing)
	{
		IBlockState state = world.getBlockState(pos = pos.down());

		if(hasArms(world, pos, pumpkinFacing) && state.getBlock() == Blocks.PURPUR_PILLAR)
		{
			IBlockState topState = world.getBlockState(pos.down());
			IBlockState bottomState = world.getBlockState(pos.down(2));

			if(topState.getBlock() == Blocks.END_ROD && topState.getValue(BlockEndRod.FACING) == EnumFacing.DOWN &&
					bottomState.getBlock() == Blocks.END_ROD && bottomState.getValue(BlockEndRod.FACING) == EnumFacing.UP)
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

	@Override
	public ModelBase getModel(boolean isLit)
	{
		return new ModelScaryScarecrow(isLit);
	}
}
