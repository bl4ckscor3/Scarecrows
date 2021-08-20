package bl4ckscor3.mod.scarecrows.type;

import bl4ckscor3.mod.scarecrows.Configuration;
import bl4ckscor3.mod.scarecrows.entity.ScarecrowEntity;
import bl4ckscor3.mod.scarecrows.model.ScaryScarecrowModel;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.EndRodBlock;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

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
		super("super_scary_scarecrow", 4, Configuration.CONFIG.superScaryRange.get(), Configuration.CONFIG.superScaryScareAnimals.get());
	}

	@Override
	public boolean checkStructure(IWorld world, BlockPos pos, Direction pumpkinFacing)
	{
		BlockState state = world.getBlockState(pos = pos.below());

		if(hasArms(world, pos, pumpkinFacing) && state.getBlock() == Blocks.PURPUR_PILLAR)
		{
			BlockState topState = world.getBlockState(pos.below());
			BlockState bottomState = world.getBlockState(pos.below(2));

			if(topState.getBlock() == Blocks.END_ROD && topState.getValue(EndRodBlock.FACING) == Direction.DOWN &&
					bottomState.getBlock() == Blocks.END_ROD && bottomState.getValue(EndRodBlock.FACING) == Direction.UP)
				return true;
		}

		return false;
	}

	@Override
	public void destroy(IWorld world, BlockPos pos)
	{
		world.destroyBlock(pos, false); //pumpkin
		pos = pos.below();
		world.destroyBlock(pos.west(), false); //a potential arm
		world.destroyBlock(pos.north(), false); //a potential arm
		world.destroyBlock(pos.south(), false); //a potential arm
		world.destroyBlock(pos.east(), false); //a potential arm
		world.destroyBlock(pos, false); //arm attachement block
		pos = pos.below();
		world.destroyBlock(pos, false); //leg
		world.destroyBlock(pos.below(), false); //foot
	}

	@Override
	public ItemStack[] getDrops()
	{
		return new ItemStack[] {new ItemStack(Items.STICK, 2), new ItemStack(Blocks.PURPUR_PILLAR), new ItemStack(Blocks.END_ROD, 2)};
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public EntityModel<ScarecrowEntity> getModel(boolean isLit)
	{
		return new ScaryScarecrowModel(isLit);
	}
}
