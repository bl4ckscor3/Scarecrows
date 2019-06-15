package bl4ckscor3.mod.scarecrows.type;

import bl4ckscor3.mod.scarecrows.Configuration;
import bl4ckscor3.mod.scarecrows.entity.EntityScarecrow;
import bl4ckscor3.mod.scarecrows.model.ModelSpookyScarecrow;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;

public class SpookyScarecrow extends ScarecrowType
{
	/*
	 *   P				- P: Pumpkin
	 *  ANA				- A: Arm
	 *   F				- N: Netherrack
	 *   				- F: Netherbrick Fence
	 */

	public SpookyScarecrow()
	{
		super("spooky_scarecrow", 3, Configuration.CONFIG.spookyRange.get(), Configuration.CONFIG.spookyScareAnimals.get());
	}

	@Override
	public boolean checkStructure(IWorld world, BlockPos pos, Direction pumpkinFacing)
	{
		BlockState state = world.getBlockState(pos = pos.down());

		if(hasArms(world, pos, pumpkinFacing) && state.getBlock() == Blocks.NETHERRACK)
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
		return new ItemStack[] {new ItemStack(Items.STICK, 2), new ItemStack(Blocks.NETHERRACK), new ItemStack(Blocks.NETHER_BRICK_FENCE)};
	}

	@Override
	public EntityModel<EntityScarecrow> getModel(boolean isLit)
	{
		return new ModelSpookyScarecrow(isLit);
	}
}
