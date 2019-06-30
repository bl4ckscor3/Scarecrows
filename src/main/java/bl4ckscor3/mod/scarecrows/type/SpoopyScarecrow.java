package bl4ckscor3.mod.scarecrows.type;

import bl4ckscor3.mod.scarecrows.Configuration;
import bl4ckscor3.mod.scarecrows.entity.EntityScarecrow;
import bl4ckscor3.mod.scarecrows.model.ModelSpoopyScarecrow;
import net.minecraft.block.Blocks;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

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
	public boolean checkStructure(IWorld world, BlockPos pos, Direction pumpkinFacing)
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
	@OnlyIn(Dist.CLIENT)
	public EntityModel<EntityScarecrow> getModel(boolean isLit)
	{
		return new ModelSpoopyScarecrow(isLit);
	}
}
