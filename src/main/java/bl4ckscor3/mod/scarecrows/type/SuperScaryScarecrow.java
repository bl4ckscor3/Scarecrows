package bl4ckscor3.mod.scarecrows.type;

import bl4ckscor3.mod.scarecrows.ClientReg;
import bl4ckscor3.mod.scarecrows.Configuration;
import bl4ckscor3.mod.scarecrows.entity.Scarecrow;
import bl4ckscor3.mod.scarecrows.model.ScaryScarecrowModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EndRodBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class SuperScaryScarecrow extends ScarecrowType {
	//@formatter:off
	/*
	 *   P				- P: Pumpkin
	 *  ABA				- A: Arm
	 *   R				- B: Purpur Pillar
	 *   R				- R: End Rod
	 */
	//@formatter:on
	public SuperScaryScarecrow() {
		super("super_scary_scarecrow", 4, Configuration.CONFIG.superScaryRange.get(), Configuration.CONFIG.superScaryScareAnimals.get());
	}

	@Override
	public boolean checkStructure(LevelAccessor level, BlockPos pos, Direction pumpkinFacing) {
		BlockState state = level.getBlockState(pos = pos.below());

		if (hasArms(level, pos, pumpkinFacing) && state.getBlock() == Blocks.PURPUR_PILLAR) {
			BlockState topState = level.getBlockState(pos.below());
			BlockState bottomState = level.getBlockState(pos.below(2));

			//@formatter:off
			if(topState.getBlock() == Blocks.END_ROD && topState.getValue(EndRodBlock.FACING) == Direction.DOWN &&
					bottomState.getBlock() == Blocks.END_ROD && bottomState.getValue(EndRodBlock.FACING) == Direction.UP) {
				//@formatter:on
				return true;
			}
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
		pos = pos.below();
		level.destroyBlock(pos, false); //leg
		level.destroyBlock(pos.below(), false); //foot
	}

	@Override
	public ItemStack[] getDrops() {
		return new ItemStack[] {
				new ItemStack(Items.STICK, 2), new ItemStack(Blocks.PURPUR_PILLAR), new ItemStack(Blocks.END_ROD, 2)
		};
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public ModelLayerLocation getModelLayerLocation(boolean isLit) {
		return isLit ? ClientReg.SCARY_SCARECROW_LIT : ClientReg.SCARY_SCARECROW;
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public EntityModel<Scarecrow> createModel(ModelPart modelPart) {
		return new ScaryScarecrowModel(modelPart);
	}
}
