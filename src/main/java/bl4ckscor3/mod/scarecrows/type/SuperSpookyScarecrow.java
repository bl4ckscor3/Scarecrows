package bl4ckscor3.mod.scarecrows.type;

import bl4ckscor3.mod.scarecrows.ClientReg;
import bl4ckscor3.mod.scarecrows.Configuration;
import bl4ckscor3.mod.scarecrows.entity.Scarecrow;
import bl4ckscor3.mod.scarecrows.model.SpookyScarecrowModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class SuperSpookyScarecrow extends ScarecrowType {
	//@formatter:off
	/*
	 *   P				- P: Pumpkin
	 *  AQA				- A: Arm
	 *   F				- Q: Quartz Pillar
	 *   				- F: Netherbrick Fence
	 */
	//@formatter:on
	public SuperSpookyScarecrow() {
		super("super_spooky_scarecrow", 3, Configuration.CONFIG.superSpookyRange.get(), Configuration.CONFIG.superSpookyScareAnimals.get());
	}

	@Override
	public boolean checkStructure(LevelAccessor level, BlockPos pos, Direction pumpkinFacing) {
		BlockState state = level.getBlockState(pos = pos.below());

		if (hasArms(level, pos, pumpkinFacing) && state.getBlock() == Blocks.QUARTZ_PILLAR) {
			if (level.getBlockState(pos.below()).getBlock() == Blocks.NETHER_BRICK_FENCE)
				return true;
		}

		return false;
	}

	@Override
	public void destroy(LevelAccessor level, BlockPos pos) {
		level.destroyBlock(pos, false); //pumpkin
		pos = pos.below();
		level.destroyBlock(pos.west(), false); //a potential arm
		level.destroyBlock(pos.north(), false); //a potential arm
		level.destroyBlock(pos.south(), false); //a potential arm
		level.destroyBlock(pos.east(), false); //a potential arm
		level.destroyBlock(pos, false); //arm attachement block
		level.destroyBlock(pos.below(), false); //foot
	}

	@Override
	public ItemStack[] getDrops() {
		return new ItemStack[] {
				new ItemStack(Items.STICK, 2), new ItemStack(Blocks.QUARTZ_PILLAR), new ItemStack(Blocks.NETHER_BRICK_FENCE)
		};
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public ModelLayerLocation getModelLayerLocation(boolean isLit) {
		return isLit ? ClientReg.SPOOKY_SCARECROW_LIT : ClientReg.SPOOKY_SCARECROW;
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public EntityModel<Scarecrow> createModel(ModelPart modelPart) {
		return new SpookyScarecrowModel(modelPart);
	}
}
