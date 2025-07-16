package bl4ckscor3.mod.scarecrows.type;

import bl4ckscor3.mod.scarecrows.Configuration;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class SuperSpookyScarecrow extends ScarecrowType {
	public static final ScarecrowType TYPE = new SuperSpookyScarecrow();

	//@formatter:off
	/*
	 *   P				- P: Pumpkin
	 *  AQA				- A: Arm
	 *   F				- Q: Quartz Pillar
	 *   				- F: Netherbrick Fence
	 */
	//@formatter:on
	private SuperSpookyScarecrow() {
		super("super_spooky_scarecrow", 3, Configuration.CONFIG.superSpookyRange.get(), Configuration.CONFIG.superSpookyScareAnimals.get());
	}

	@Override
	public boolean checkStructure(LevelAccessor level, BlockPos pos, Direction pumpkinFacing) {
		pos = pos.below();

		BlockState state = level.getBlockState(pos);

		return hasArms(level, pos, pumpkinFacing) && state.is(Blocks.QUARTZ_PILLAR) && level.getBlockState(pos.below()).is(Blocks.NETHER_BRICK_FENCE);
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
		level.destroyBlock(pos.below(), false); //foot
	}

	@Override
	public ItemStack[] getDrops() {
		return new ItemStack[] {
			new ItemStack(Items.STICK, 2), new ItemStack(Blocks.QUARTZ_PILLAR), new ItemStack(Blocks.NETHER_BRICK_FENCE)
		};
	}
}
