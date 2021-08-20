package bl4ckscor3.mod.scarecrows.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class InvisibleLightBlock extends Block
{
	public static final String NAME = "invisible_light";

	public InvisibleLightBlock()
	{
		super(BlockBehaviour.Properties.of(Material.STONE).strength(-1.0F, Float.MAX_VALUE).lightLevel(state -> 15).isRedstoneConductor((state, world, pos) -> false)); //light level of a jack o lantern

		setRegistryName(NAME);
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter source, BlockPos pos, CollisionContext ctx)
	{
		return Block.box(5.5F, 5.5F, 5.5F, 6.5F, 6.5F, 6.5F);
	}

	@Override
	public VoxelShape getCollisionShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext ctx)
	{
		return Shapes.empty();
	}
}
