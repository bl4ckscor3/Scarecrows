package bl4ckscor3.mod.scarecrows.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ArmBlock extends Block {
	public static final EnumProperty<Direction> FACING = HorizontalDirectionalBlock.FACING;
	private static final VoxelShape SOUTH_SHAPE = Block.box(7.5D, 7, 0, 8.5D, 16, 8.5D);
	private static final VoxelShape WEST_SHAPE = Block.box(8, 7, 7.5D, 16, 16, 8.5D);
	private static final VoxelShape NORTH_SHAPE = Block.box(7.5D, 7, 8, 8.5D, 16, 16);
	private static final VoxelShape EAST_SHAPE = Block.box(0, 7, 7.5D, 8.5D, 16, 8.5D);

	public ArmBlock(Properties properties) {
		super(properties);

		registerDefaultState(stateDefinition.any().setValue(FACING, Direction.NORTH));
	}

	@Override
	public BlockState updateShape(BlockState state, LevelReader level, ScheduledTickAccess tickAccess, BlockPos pos, Direction facing, BlockPos facingPos, BlockState facingState, RandomSource random) {
		return facing == state.getValue(FACING).getOpposite() && !canSurvive(state, level, pos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, level, tickAccess, pos, facing, facingPos, facingState, random);
	}

	@Override
	protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
		return canBeConnectedTo(level, pos, state.getValue(FACING).getOpposite());
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext ctx) {
		return switch (state.getValue(FACING)) {
			case SOUTH -> SOUTH_SHAPE;
			case WEST -> WEST_SHAPE;
			case NORTH -> NORTH_SHAPE;
			case EAST -> EAST_SHAPE;
			default -> Shapes.block();
		};
	}

	public static boolean canBeConnectedTo(BlockGetter level, BlockPos pos, Direction facing) {
		BlockPos oppositePos = pos.relative(facing.getOpposite());
		BlockState oppositeState = level.getBlockState(oppositePos);

		return facing != Direction.UP && facing != Direction.DOWN && oppositeState.isFaceSturdy(level, oppositePos, facing);
	}

	@Override
	public VoxelShape getCollisionShape(BlockState blockState, BlockGetter level, BlockPos pos, CollisionContext ctx) {
		return Shapes.empty();
	}

	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
		builder.add(FACING);
	}

	@Override
	public ItemStack getCloneItemStack(BlockState state, HitResult target, LevelReader level, BlockPos pos, Player player) {
		return new ItemStack(Items.STICK);
	}
}
