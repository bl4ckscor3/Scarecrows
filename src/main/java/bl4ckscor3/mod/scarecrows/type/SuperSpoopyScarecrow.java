package bl4ckscor3.mod.scarecrows.type;

import bl4ckscor3.mod.scarecrows.Configuration;
import bl4ckscor3.mod.scarecrows.entity.ScarecrowEntity;
import bl4ckscor3.mod.scarecrows.model.SpoopyScarecrowModel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.client.model.EntityModel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class SuperSpoopyScarecrow extends ScarecrowType
{
	/*
	 *   P				- P: Pumpkin
	 *  ABA				- A: Arm
	 *					- C: Chiseled Stone Bricks
	 */

	public SuperSpoopyScarecrow()
	{
		super("super_spoopy_scarecrow", 2, Configuration.CONFIG.superSpoopyRange.get(), Configuration.CONFIG.superSpoopyScareAnimals.get());
	}

	@Override
	public boolean checkStructure(LevelAccessor world, BlockPos pos, Direction pumpkinFacing)
	{
		BlockState state = world.getBlockState(pos = pos.below());

		return hasArms(world, pos, pumpkinFacing) && state.getBlock() == Blocks.CHISELED_STONE_BRICKS;
	}

	@Override
	public void destroy(LevelAccessor world, BlockPos pos)
	{
		world.destroyBlock(pos, false); //pumpkin
		pos = pos.below();
		world.destroyBlock(pos.west(), false); //a potential arm
		world.destroyBlock(pos.north(), false); //a potential arm
		world.destroyBlock(pos.south(), false); //a potential arm
		world.destroyBlock(pos.east(), false); //a potential arm
		world.destroyBlock(pos, false); //arm attachement block
	}

	@Override
	public ItemStack[] getDrops()
	{
		return new ItemStack[] {new ItemStack(Items.STICK, 2), new ItemStack(Blocks.CHISELED_STONE_BRICKS)};
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public EntityModel<ScarecrowEntity> getModel(boolean isLit)
	{
		return new SpoopyScarecrowModel(isLit);
	}
}
