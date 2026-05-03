package bl4ckscor3.mod.scarecrows.handler;

import bl4ckscor3.mod.scarecrows.Scarecrows;
import bl4ckscor3.mod.scarecrows.block.ArmBlock;
import bl4ckscor3.mod.scarecrows.type.ScarecrowType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CarvedPumpkinBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;

public class PlaceHandler {
	private PlaceHandler() {}

	public static InteractionResult tryPlaceStick(ItemStack held, BlockPos pos, Direction face, Level world, Player player, InteractionHand hand) {
		if (held.getItem() == Items.STICK) {
			BlockPos placeAt = pos.relative(face);

			if (face != Direction.UP && face != Direction.DOWN && ArmBlock.canBeConnectedTo(world, placeAt, face) && world.isEmptyBlock(placeAt)) {
				world.setBlockAndUpdate(placeAt, Scarecrows.ARM.get().defaultBlockState().setValue(ArmBlock.FACING, face));
				world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundType.WOOD.getPlaceSound(), SoundSource.BLOCKS, 1.0F, 1.0F);
				player.swing(hand);

				if (!player.isCreative())
					held.shrink(1);

				return InteractionResult.SUCCESS;
			}
		}

		return InteractionResult.PASS;
	}

	/**
	 * Tries to build a scarecrow
	 *
	 * @param level The level to build the scarecrow in
	 * @param pos The position of the block that was placed/rightclicked
	 * @param state The state of the block that was placed/rightclicked
	 */
	public static InteractionResult tryBuildScarecrow(LevelAccessor level, BlockPos pos, BlockState state) {
		Block block = state.getBlock();

		if (block == Blocks.CARVED_PUMPKIN || block == Blocks.JACK_O_LANTERN) { //structure only ever activates when placing a carved pumpkin or jack o lantern
			for (ScarecrowType type : Scarecrows.TYPES) {
				Direction pumpkinFacing = state.getValue(CarvedPumpkinBlock.FACING);
				BlockPos groundPos = pos.below(type.getHeight());
				BlockState groundState = level.getBlockState(groundPos);

				if (!groundState.isAir() && type.checkStructure(level, pos, pumpkinFacing)) {
					type.destroy(level, pos);
					type.spawn(type, level, pos.below(type.getHeight() - 1), block == Blocks.JACK_O_LANTERN, pumpkinFacing); //-1 because of the feet
					return InteractionResult.SUCCESS;
				}
			}
		}

		return InteractionResult.PASS;
	}
}
