package bl4ckscor3.mod.scarecrows.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class BlockArm extends Block
{
	public static final String NAME = "arm";
	public static final DirectionProperty FACING = BlockHorizontal.HORIZONTAL_FACING;

	public BlockArm()
	{
		super(Block.Properties.create(Material.WOOD).hardnessAndResistance(0.25F, 1.0F).sound(SoundType.WOOD));

		setRegistryName(NAME);
		setDefaultState(stateContainer.getBaseState().with(FACING, EnumFacing.NORTH));
	}

	@Override
	public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block, BlockPos fromPos)
	{
		if(pos.offset(state.get(FACING).getOpposite()).equals(fromPos) && world.isAirBlock(fromPos))
			world.destroyBlock(pos, true);
	}

	@Override
	public VoxelShape getShape(IBlockState state, IBlockReader source, BlockPos pos)
	{
		EnumFacing facing = state.get(FACING);

		if(facing == EnumFacing.SOUTH)
			return Block.makeCuboidShape(7.5D, 7, 0, 8.5D, 16, 8.5D);
		else if(facing == EnumFacing.WEST)
			return Block.makeCuboidShape(8, 7, 7.5D, 16, 16, 8.5D);
		else if(facing == EnumFacing.NORTH)
			return Block.makeCuboidShape(7.5D, 7, 8, 8.5D, 16, 16);
		else if(facing == EnumFacing.EAST)
			return Block.makeCuboidShape(0, 7, 7.5D, 8.5D, 16, 8.5D);
		else
			return Block.makeCuboidShape(0, 0, 0, 16, 16, 16);
	}

	@Override
	public boolean canBeConnectedTo(IBlockState state, IBlockReader world, BlockPos pos, EnumFacing facing)
	{
		BlockPos oppositePos = pos.offset(facing.getOpposite());
		IBlockState oppositeState = world.getBlockState(oppositePos);

		if(facing != EnumFacing.UP && facing != EnumFacing.DOWN)
			return !isExceptBlockForAttachWithPiston(oppositeState.getBlock()) && oppositeState.getBlockFaceShape(world, oppositePos, facing) == BlockFaceShape.SOLID;
		else
			return false;
	}

	@Override
	public VoxelShape getCollisionShape(IBlockState blockState, IBlockReader world, BlockPos pos)
	{
		return VoxelShapes.empty();
	}

	@Override
	public float getAmbientOcclusionLightValue(IBlockState state)
	{
		return 1.0F;
	}

	@Override
	public BlockFaceShape getBlockFaceShape(IBlockReader world, IBlockState state, BlockPos pos, EnumFacing face)
	{
		return BlockFaceShape.UNDEFINED;
	}

	@Override
	protected void fillStateContainer(Builder<Block, IBlockState> builder)
	{
		builder.add(FACING);
	}

	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, IBlockReader world, BlockPos pos, EntityPlayer player)
	{
		return new ItemStack(Items.STICK);
	}

	@Override
	public IItemProvider getItemDropped(IBlockState state, World world, BlockPos pos, int fortune)
	{
		return Items.STICK;
	}

	@Override
	public boolean isNormalCube(IBlockState state, IBlockReader world, BlockPos pos)
	{
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state)
	{
		return false;
	}
}
