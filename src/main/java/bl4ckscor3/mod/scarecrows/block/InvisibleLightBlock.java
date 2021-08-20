package bl4ckscor3.mod.scarecrows.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

import net.minecraft.block.AbstractBlock;

public class InvisibleLightBlock extends Block
{
	public static final String NAME = "invisible_light";

	public InvisibleLightBlock()
	{
		super(AbstractBlock.Properties.of(Material.STONE).strength(-1.0F, Float.MAX_VALUE).lightLevel(state -> 15).isRedstoneConductor((state, world, pos) -> false)); //light level of a jack o lantern

		setRegistryName(NAME);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader source, BlockPos pos, ISelectionContext ctx)
	{
		return Block.box(5.5F, 5.5F, 5.5F, 6.5F, 6.5F, 6.5F);
	}

	@Override
	public VoxelShape getCollisionShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext ctx)
	{
		return VoxelShapes.empty();
	}
}
