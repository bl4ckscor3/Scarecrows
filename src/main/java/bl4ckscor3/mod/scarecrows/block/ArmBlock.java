package bl4ckscor3.mod.scarecrows.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ArmBlock extends Block
{
	public static final String NAME = "arm";
	public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

	public ArmBlock()
	{
		super(BlockBehaviour.Properties.of(Material.WOOD).strength(0.25F, 1.0F).sound(SoundType.WOOD).isRedstoneConductor((state, world, pos) -> false));

		setRegistryName(NAME);
		registerDefaultState(stateDefinition.any().setValue(FACING, Direction.NORTH));
	}

	@Override
	public void neighborChanged(BlockState state, Level world, BlockPos pos, Block block, BlockPos fromPos, boolean flag)
	{
		if(pos.relative(state.getValue(FACING).getOpposite()).equals(fromPos) && world.isEmptyBlock(fromPos))
			world.destroyBlock(pos, true);
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter source, BlockPos pos, CollisionContext ctx)
	{
		Direction facing = state.getValue(FACING);

		if(facing == Direction.SOUTH)
			return Block.box(7.5D, 7, 0, 8.5D, 16, 8.5D);
		else if(facing == Direction.WEST)
			return Block.box(8, 7, 7.5D, 16, 16, 8.5D);
		else if(facing == Direction.NORTH)
			return Block.box(7.5D, 7, 8, 8.5D, 16, 16);
		else if(facing == Direction.EAST)
			return Block.box(0, 7, 7.5D, 8.5D, 16, 8.5D);
		else
			return Block.box(0, 0, 0, 16, 16, 16);
	}

	public static boolean canBeConnectedTo(BlockState state, BlockGetter world, BlockPos pos, Direction facing)
	{
		BlockPos oppositePos = pos.relative(facing.getOpposite());
		BlockState oppositeState = world.getBlockState(oppositePos);

		if(facing != Direction.UP && facing != Direction.DOWN)
			return oppositeState.isFaceSturdy(world, oppositePos, facing);
		else
			return false;
	}

	@Override
	public VoxelShape getCollisionShape(BlockState blockState, BlockGetter world, BlockPos pos, CollisionContext ctx)
	{
		return Shapes.empty();
	}

	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder)
	{
		builder.add(FACING);
	}

	@Override
	public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter world, BlockPos pos, Player player)
	{
		return new ItemStack(Items.STICK);
	}
}
