package bl4ckscor3.mod.scarecrows.type;

import bl4ckscor3.mod.scarecrows.Configuration;
import bl4ckscor3.mod.scarecrows.entity.ScarecrowEntity;
import bl4ckscor3.mod.scarecrows.model.SpookyScarecrowModel;
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

public class SuperSpookyScarecrow extends ScarecrowType
{
	/*
	 *   P				- P: Pumpkin
	 *  AQA				- A: Arm
	 *   F				- Q: Quartz Pillar
	 *   				- F: Netherbrick Fence
	 */

	public SuperSpookyScarecrow()
	{
		super("super_spooky_scarecrow", 3, Configuration.CONFIG.superSpookyRange.get(), Configuration.CONFIG.superSpookyScareAnimals.get());
	}

	@Override
	public boolean checkStructure(LevelAccessor world, BlockPos pos, Direction pumpkinFacing)
	{
		BlockState state = world.getBlockState(pos = pos.below());

		if(hasArms(world, pos, pumpkinFacing) && state.getBlock() == Blocks.QUARTZ_PILLAR)
		{
			if(world.getBlockState(pos.below()).getBlock() == Blocks.NETHER_BRICK_FENCE)
				return true;
		}

		return false;
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
		world.destroyBlock(pos.below(), false); //foot
	}

	@Override
	public ItemStack[] getDrops()
	{
		return new ItemStack[] {new ItemStack(Items.STICK, 2), new ItemStack(Blocks.QUARTZ_PILLAR), new ItemStack(Blocks.NETHER_BRICK_FENCE)};
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public EntityModel<ScarecrowEntity> getModel(boolean isLit)
	{
		return new SpookyScarecrowModel(isLit);
	}
}
