package bl4ckscor3.mod.scarecrows.type;

import bl4ckscor3.mod.scarecrows.Configuration;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class SuperSpoopyScarecrow extends ScarecrowType {
	public static final ScarecrowType TYPE = new SuperSpoopyScarecrow();

	//@formatter:off
	/*
	 *   P				- P: Pumpkin
	 *  ABA				- A: Arm
	 *					- C: Chiseled Stone Bricks
	 */
	//@formatter:on
	private SuperSpoopyScarecrow() {
		super("super_spoopy_scarecrow", 2, Configuration.CONFIG.superSpoopyRange.get(), Configuration.CONFIG.superSpoopyScareAnimals.get());
	}

	@Override
	public boolean checkStructure(LevelAccessor level, BlockPos pos, Direction pumpkinFacing) {
		pos = pos.below();

		BlockState state = level.getBlockState(pos);

		return hasArms(level, pos, pumpkinFacing) && state.is(Blocks.CHISELED_STONE_BRICKS);
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
	}

	@Override
	public ItemStack[] getDrops() {
		return new ItemStack[] {
			new ItemStack(Items.STICK, 2), new ItemStack(Blocks.CHISELED_STONE_BRICKS)
		};
	}
}
