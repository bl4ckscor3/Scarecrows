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

public class InvisibleLightBlock extends Block {
	public static final String NAME = "invisible_light";
	private static final VoxelShape SHAPE = Block.box(5.5F, 5.5F, 5.5F, 6.5F, 6.5F, 6.5F);

	public InvisibleLightBlock() {
		super(BlockBehaviour.Properties.of(Material.STONE).strength(-1.0F, Float.MAX_VALUE).lightLevel(state -> 15).isRedstoneConductor((state, world, pos) -> false)); //light level of a jack o lantern

		setRegistryName(NAME);
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext ctx) {
		return SHAPE;
	}

	@Override
	public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext ctx) {
		return Shapes.empty();
	}
}
