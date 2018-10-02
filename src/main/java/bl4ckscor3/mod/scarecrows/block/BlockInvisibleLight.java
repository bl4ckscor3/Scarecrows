package bl4ckscor3.mod.scarecrows.block;

import bl4ckscor3.mod.scarecrows.Scarecrows;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockInvisibleLight extends Block
{
	public static final String NAME = "invisible_light";

	public BlockInvisibleLight()
	{
		super(Material.ROCK);

		setRegistryName(NAME);
		setHardness(-1.0F);
		setResistance(Float.MAX_VALUE);
		setLightLevel(1.0F); //light level of a jack o lantern
		setTranslationKey(Scarecrows.PREFIX + NAME);
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		float px = 1.0F / 16.0F;
		float start = 7.5F * px;
		float end = 8.5F * px;

		return new AxisAlignedBB(start, start, start, end, end, end);
	}

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
