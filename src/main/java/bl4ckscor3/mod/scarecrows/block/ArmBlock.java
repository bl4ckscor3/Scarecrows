package bl4ckscor3.mod.scarecrows.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import net.minecraft.block.AbstractBlock;

public class ArmBlock extends Block
{
	public static final String NAME = "arm";
	public static final DirectionProperty FACING = HorizontalBlock.FACING;

	public ArmBlock()
	{
		super(AbstractBlock.Properties.of(Material.WOOD).strength(0.25F, 1.0F).sound(SoundType.WOOD).isRedstoneConductor((state, world, pos) -> false));

		setRegistryName(NAME);
		registerDefaultState(stateDefinition.any().setValue(FACING, Direction.NORTH));
	}

	@Override
	public void neighborChanged(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean flag)
	{
		if(pos.relative(state.getValue(FACING).getOpposite()).equals(fromPos) && world.isEmptyBlock(fromPos))
			world.destroyBlock(pos, true);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader source, BlockPos pos, ISelectionContext ctx)
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

	public static boolean canBeConnectedTo(BlockState state, IBlockReader world, BlockPos pos, Direction facing)
	{
		BlockPos oppositePos = pos.relative(facing.getOpposite());
		BlockState oppositeState = world.getBlockState(oppositePos);

		if(facing != Direction.UP && facing != Direction.DOWN)
			return oppositeState.isFaceSturdy(world, oppositePos, facing);
		else
			return false;
	}

	@Override
	public VoxelShape getCollisionShape(BlockState blockState, IBlockReader world, BlockPos pos, ISelectionContext ctx)
	{
		return VoxelShapes.empty();
	}

	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder)
	{
		builder.add(FACING);
	}

	@Override
	public ItemStack getPickBlock(BlockState state, RayTraceResult target, IBlockReader world, BlockPos pos, PlayerEntity player)
	{
		return new ItemStack(Items.STICK);
	}
}
