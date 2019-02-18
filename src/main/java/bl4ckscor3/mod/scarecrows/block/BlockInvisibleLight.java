package bl4ckscor3.mod.scarecrows.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

public class BlockInvisibleLight extends Block
{
	public static final String NAME = "invisible_light";

	public BlockInvisibleLight()
	{
		super(Block.Properties.create(Material.ROCK).hardnessAndResistance(-1.0F, Float.MAX_VALUE).lightValue(15)); //light level of a jack o lantern

		setRegistryName(NAME);
	}

	@Override
	public VoxelShape getShape(IBlockState state, IBlockReader source, BlockPos pos)
	{
		return Block.makeCuboidShape(5.5F, 5.5F, 5.5F, 6.5F, 6.5F, 6.5F);
	}

	@Override
	public VoxelShape getCollisionShape(IBlockState state, IBlockReader world, BlockPos pos)
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
