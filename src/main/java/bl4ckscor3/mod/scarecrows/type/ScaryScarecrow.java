package bl4ckscor3.mod.scarecrows.type;

import bl4ckscor3.mod.scarecrows.Configuration;
import bl4ckscor3.mod.scarecrows.entity.ScarecrowEntity;
import bl4ckscor3.mod.scarecrows.model.ScaryScarecrowModel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EndRodBlock;
import net.minecraft.client.model.EntityModel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

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
		super("scary_scarecrow", 4, Configuration.CONFIG.scaryRange.get(), Configuration.CONFIG.scaryScareAnimals.get());
	}

	@Override
	public boolean checkStructure(LevelAccessor world, BlockPos pos, Direction pumpkinFacing)
	{
		BlockState state = world.getBlockState(pos = pos.below());

		if(hasArms(world, pos, pumpkinFacing) && state.getBlock() == Blocks.END_STONE)
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
	public void destroy(LevelAccessor world, BlockPos pos)
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
		return new ItemStack[] {new ItemStack(Items.STICK, 2), new ItemStack(Blocks.END_STONE), new ItemStack(Blocks.END_ROD, 2)};
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public EntityModel<ScarecrowEntity> getModel(boolean isLit)
	{
		return new ScaryScarecrowModel(isLit);
	}
}
