package bl4ckscor3.mod.scarecrows.type;

import bl4ckscor3.mod.scarecrows.Configuration;
import bl4ckscor3.mod.scarecrows.entity.ScarecrowEntity;
import bl4ckscor3.mod.scarecrows.model.SpookyScarecrowModel;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
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
	public boolean checkStructure(IWorld world, BlockPos pos, Direction pumpkinFacing)
	{
		BlockState state = world.getBlockState(pos = pos.down());

		if(hasArms(world, pos, pumpkinFacing) && state.getBlock() == Blocks.QUARTZ_PILLAR)
		{
			if(world.getBlockState(pos.down()).getBlock() == Blocks.NETHER_BRICK_FENCE)
				return true;
		}

		return false;
	}

	@Override
	public void destroy(IWorld world, BlockPos pos)
	{
		world.destroyBlock(pos, false); //pumpkin
		pos = pos.down();
		world.destroyBlock(pos.west(), false); //a potential arm
		world.destroyBlock(pos.north(), false); //a potential arm
		world.destroyBlock(pos.south(), false); //a potential arm
		world.destroyBlock(pos.east(), false); //a potential arm
		world.destroyBlock(pos, false); //arm attachement block
		world.destroyBlock(pos.down(), false); //foot
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
