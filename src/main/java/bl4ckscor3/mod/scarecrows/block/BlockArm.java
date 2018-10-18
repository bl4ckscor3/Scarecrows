package bl4ckscor3.mod.scarecrows.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockArm extends Block
{
	public static final String NAME = "arm";
	public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);

	public BlockArm()
	{
		super(Material.WOOD);

		setRegistryName(NAME);
		setHardness(0.25F);
		setResistance(1.0F);
		setSoundType(SoundType.WOOD);
	}

	@Override
	public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block, BlockPos fromPos)
	{
		if(pos.offset(state.getValue(FACING).getOpposite()).equals(fromPos) && world.getBlockState(fromPos).getBlock() == Blocks.AIR)
			world.destroyBlock(pos, true);
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		EnumFacing facing = state.getValue(FACING);
		float px = 1.0F / 16.0F;
		float yStart = 7 * px;
		float yEnd = 1.0F;

		if(facing == EnumFacing.SOUTH)
			return new AxisAlignedBB(7.5F * px, yStart, 0, 8.5F * px, yEnd, 0.5F);
		else if(facing == EnumFacing.WEST)
			return new AxisAlignedBB(0.5F, yStart, 7.5F * px, 1.0F, yEnd, 8.5F * px);
		else if(facing == EnumFacing.NORTH)
			return new AxisAlignedBB(7.5F * px, yStart, 0.5F, 8.5F * px, yEnd, 1.0F);
		else if(facing == EnumFacing.EAST)
			return new AxisAlignedBB(0, yStart, 7.5F * px, 0.5F, yEnd, 8.5F * px);
		else
			return new AxisAlignedBB(0, 0, 0, 1, 1, 1);
	}

	//borrowed code from BlockTorck
	@Override
	public boolean canPlaceBlockAt(World world, BlockPos pos)
	{
		for(EnumFacing facing : FACING.getAllowedValues())
		{
			if(canPlaceAt(world, pos, facing))
				return true;
		}

		return false;
	}

	private boolean canPlaceAt(World world, BlockPos pos, EnumFacing facing)
	{
		BlockPos oppositePos = pos.offset(facing.getOpposite());
		IBlockState oppositeState = world.getBlockState(oppositePos);

		if(facing != EnumFacing.UP && facing != EnumFacing.DOWN)
			return !isExceptBlockForAttachWithPiston(oppositeState.getBlock()) && oppositeState.getBlockFaceShape(world, oppositePos, facing) == BlockFaceShape.SOLID;
		else
			return false;
	}
	//end of borrowed code

	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos)
	{
		return null;
	}

	@Override
	public float getAmbientOcclusionLightValue(IBlockState state)
	{
		return 1.0F;
	}

	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
	{
		return BlockFaceShape.UNDEFINED;
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return state.getValue(FACING).getHorizontalIndex();
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return getDefaultState().withProperty(FACING, EnumFacing.byHorizontalIndex(meta));
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, new IProperty[] {FACING});
	}

	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player)
	{
		return new ItemStack(Items.STICK);
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return Items.STICK;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}

	@Override
	public boolean isNormalCube(IBlockState state, IBlockAccess world, BlockPos pos)
	{
		return false;
	}

	@Override
	public boolean isFullBlock(IBlockState state)
	{
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state)
	{
		return false;
	}
}
