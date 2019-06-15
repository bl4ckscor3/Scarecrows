package bl4ckscor3.mod.scarecrows.type;

import bl4ckscor3.mod.scarecrows.Configuration;
import bl4ckscor3.mod.scarecrows.entity.EntityScarecrow;
import bl4ckscor3.mod.scarecrows.model.ModelSpoopyScarecrow;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;

public class SuperSpoopyScarecrow extends ScarecrowType
{
	/*
	 *   P				- P: Pumpkin
	 *  ABA				- A: Arm
	 *					- C: Chiseled Stone Bricks
	 */

	public SuperSpoopyScarecrow()
	{
		super("super_spoopy_scarecrow", 2, Configuration.CONFIG.superSpoopyRange.get(), Configuration.CONFIG.superSpoopyScareAnimals.get());
	}

	@Override
	public boolean checkStructure(IWorld world, BlockPos pos, Direction pumpkinFacing)
	{
		BlockState state = world.getBlockState(pos = pos.down());

		return hasArms(world, pos, pumpkinFacing) && state.getBlock() == Blocks.CHISELED_STONE_BRICKS;
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
	}

	@Override
	public ItemStack[] getDrops()
	{
		return new ItemStack[] {new ItemStack(Items.STICK, 2), new ItemStack(Blocks.CHISELED_STONE_BRICKS)};
	}

	@Override
	public EntityModel<EntityScarecrow> getModel(boolean isLit)
	{
		return new ModelSpoopyScarecrow(isLit);
	}
}
