package bl4ckscor3.mod.scarecrows.type;

import bl4ckscor3.mod.scarecrows.Configuration;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;

public class ScaryScarecrow extends ScarecrowType {
	public static final ScarecrowType TYPE = new ScaryScarecrow();

	//@formatter:off
	/*
	 *   P				- P: Pumpkin
	 *  AEA				- A: Arm
	 *   R				- E: Endstone
	 *   R				- R: End Rod
	 */
	//@formatter:on
	private ScaryScarecrow() {
		super("scary_scarecrow", 4, Configuration.CONFIG.scaryRange.get(), Configuration.CONFIG.scaryScareAnimals.get());
	}

	@Override
	public boolean checkStructure(LevelAccessor level, BlockPos pos, Direction pumpkinFacing) {
		pos = pos.below();

		BlockState state = level.getBlockState(pos);

		if (hasArms(level, pos, pumpkinFacing) && state.is(Blocks.END_STONE)) {
			BlockState topState = level.getBlockState(pos.below());
			BlockState bottomState = level.getBlockState(pos.below(2));

			return topState.is(Blocks.END_ROD) && topState.getValue(DirectionalBlock.FACING) == Direction.DOWN && bottomState.is(Blocks.END_ROD) && bottomState.getValue(DirectionalBlock.FACING) == Direction.UP;
		}

		return false;
	}

	@Override
	public void destroy(LevelAccessor level, BlockPos pos) {
		level.destroyBlock(pos, false); //pumpkin
		pos = pos.below();
		level.destroyBlock(pos.west(), false); //a potential arm
		level.destroyBlock(pos.north(), false); //a potential arm
		level.destroyBlock(pos.south(), false); //a potential arm
		level.destroyBlock(pos.east(), false); //a potential arm
		level.destroyBlock(pos, false); //arm attachement block
		pos = pos.below();
		level.destroyBlock(pos, false); //leg
		level.destroyBlock(pos.below(), false); //foot
	}

	@Override
	public ItemStack[] getDrops() {
		return new ItemStack[] {
			new ItemStack(Items.STICK, 2), new ItemStack(Blocks.END_STONE), new ItemStack(Blocks.END_ROD, 2)
		};
	}
}
