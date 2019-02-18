package bl4ckscor3.mod.scarecrows.type;

import bl4ckscor3.mod.scarecrows.Configuration;
import bl4ckscor3.mod.scarecrows.model.ModelSpookyScarecrow;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.entity.model.ModelBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;

public class SuperSpookyScarecrow extends ScarecrowType
{
	/*
	 *   P				- P: Pumpkin
	 *  AQA				- A: Arm
	 *   F				- Q: Quartz Pillar
	 *   				- F: Netherbrick Fence
	 */

	public SuperSpookyScarecrow()
	{
		super("super_spooky_scarecrow", 3, Configuration.CONFIG.superSpookyRange.get(), Configuration.CONFIG.superSpookyScareAnimals.get());
	}

	@Override
	public boolean checkStructure(IWorld world, BlockPos pos, EnumFacing pumpkinFacing)
	{
		IBlockState state = world.getBlockState(pos = pos.down());

		if(hasArms(world, pos, pumpkinFacing) && state.getBlock() == Blocks.QUARTZ_PILLAR)
		{
			if(world.getBlockState(pos.down()).getBlock() == Blocks.NETHER_BRICK_FENCE)
				return true;
		}

		return false;
	}

	@Override
	public void destroy(IWorld world, BlockPos pos)
	{
		world.destroyBlock(pos, false); //pumpkin
		pos = pos.down();
		world.destroyBlock(pos.west(), false); //a potential arm
		world.destroyBlock(pos.north(), false); //a potential arm
		world.destroyBlock(pos.south(), false); //a potential arm
		world.destroyBlock(pos.east(), false); //a potential arm
		world.destroyBlock(pos, false); //arm attachement block
		world.destroyBlock(pos.down(), false); //foot
	}

	@Override
	public ItemStack[] getDrops()
	{
		return new ItemStack[] {new ItemStack(Items.STICK, 2), new ItemStack(Blocks.QUARTZ_PILLAR), new ItemStack(Blocks.NETHER_BRICK_FENCE)};
	}

	@Override
	public ModelBase getModel(boolean isLit)
	{
		return new ModelSpookyScarecrow(isLit);
	}
}
