package bl4ckscor3.mod.scarecrows.type;

import bl4ckscor3.mod.scarecrows.Configuration;
import bl4ckscor3.mod.scarecrows.model.ModelSpoopyScarecrow;
import net.minecraft.client.renderer.entity.model.ModelBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;

public class SpoopyScarecrow extends ScarecrowType
{
	/*
	 *   P				- P: Pumpkin
	 *  ACA				- A: Arm
	 *					- C: Clay
	 */

	public SpoopyScarecrow()
	{
		super("spoopy_scarecrow", 2, Configuration.CONFIG.spoopyRange.get(), Configuration.CONFIG.spoopyScareAnimals.get());
	}

	@Override
	public boolean checkStructure(IWorld world, BlockPos pos, EnumFacing pumpkinFacing)
	{
		pos = pos.down();

		return hasArms(world, pos, pumpkinFacing) && world.getBlockState(pos).getBlock() == Blocks.CLAY;
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
	}

	@Override
	public ItemStack[] getDrops()
	{
		return new ItemStack[] {new ItemStack(Items.STICK, 2), new ItemStack(Blocks.CLAY)};
	}

	@Override
	public ModelBase getModel(boolean isLit)
	{
		return new ModelSpoopyScarecrow(isLit);
	}
}
