package bl4ckscor3.mod.scarecrows.type;

import bl4ckscor3.mod.scarecrows.Scarecrows;
import bl4ckscor3.mod.scarecrows.block.ArmBlock;
import bl4ckscor3.mod.scarecrows.entity.Scarecrow;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public abstract class ScarecrowType {
	//@formatter:off
	public static final ScarecrowType[] TYPES = {
			new SpoopyScarecrow(),
			new SuperSpoopyScarecrow(),
			new SpookyScarecrow(),
			new SuperSpookyScarecrow(),
			new ScaryScarecrow(),
			new SuperScaryScarecrow()
	};
	//@formatter:on
	private String name;
	private int height;
	private int range;
	private boolean scareAnimals;

	/**
	 * @param name The name of this type, needs to be unique to all others in the TYPES array
	 * @param height The height of this scarecrow type in blocks
	 * @param range The range in blocks in which this scarecrow will be effective in
	 * @param scareAnimals true if this scarecrow should scare away animals, false otherwise
	 */
	public ScarecrowType(String name, int height, int range, boolean scareAnimals) {
		this.name = name;
		this.height = height;
		this.range = range;
		this.scareAnimals = scareAnimals;
	}

	/**
	 * Checks whether this scarecrow is built correctly
	 *
	 * @param level The world to check in
	 * @param pos The position to start checking from (the pumpkin)
	 * @param pumpkinFacing The facing of the pumpkin
	 * @return true if the scarecrow is correctly built, false otherwise
	 */
	public abstract boolean checkStructure(LevelAccessor level, BlockPos pos, Direction pumpkinFacing);

	/**
	 * Destroy the structure of this scarecrow
	 *
	 * @param level The world to destroy in
	 * @param pos The position to start destroying from (the pumpkin)
	 */
	public abstract void destroy(LevelAccessor level, BlockPos pos);

	/**
	 * @return The drops that this scarecrow will drop when its entity is removed, excluding the pumpkin
	 */
	public abstract ItemStack[] getDrops();

	/**
	 * @param isLit Whether this model should show up as lit or not
	 * @return The model layer location this scarecrow will use
	 */
	@OnlyIn(Dist.CLIENT)
	public abstract ModelLayerLocation getModelLayerLocation(boolean isLit);

	/**
	 * Creates the model to be used for rendering this scarecrow
	 *
	 * @param modelPart The baked model part
	 * @return The model to render
	 */
	@OnlyIn(Dist.CLIENT)
	public abstract EntityModel<Scarecrow> createModel(ModelPart modelPart);

	/**
	 * @return The name of the scarecrow
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return The height of the scarecrow, used for correctly defining the spawn position
	 */
	public final int getHeight() {
		return height;
	}

	/**
	 * @return The value that should be used to determine the effect range of this scarecrow
	 */
	public final int getRange() {
		return range;
	}

	/**
	 * Defines whether this scarecow also scares animals additional to monsters
	 */
	public final boolean shouldScareAnimals() {
		return scareAnimals;
	}

	/**
	 * Checks whether this scarecrow has arms
	 *
	 * @param level The level the scarecrow is in
	 * @param pos The block position to which the arms should be attached
	 * @param pumpkinFacing The facing of the pumpkin, used to determin if the arms are placed on the correct sides
	 */
	public final boolean hasArms(LevelAccessor level, BlockPos pos, Direction pumpkinFacing) {
		BlockPos posNorth = pos.north();
		BlockPos posEast = pos.east();
		BlockPos posSouth = pos.south();
		BlockPos posWest = pos.west();
		BlockState stateNorth = level.getBlockState(posNorth);
		BlockState stateEast = level.getBlockState(posEast);
		BlockState stateSouth = level.getBlockState(posSouth);
		BlockState stateWest = level.getBlockState(posWest);

		//@formatter:off
		if ((pumpkinFacing == Direction.EAST || pumpkinFacing == Direction.WEST) &&
				stateNorth.getBlock() == Scarecrows.ARM && stateNorth.getValue(ArmBlock.FACING) == Direction.NORTH &&
				stateSouth.getBlock() == Scarecrows.ARM && stateSouth.getValue(ArmBlock.FACING) == Direction.SOUTH &&
				stateWest.isAir() && stateEast.isAir())
			return true;
		else if ((pumpkinFacing == Direction.NORTH || pumpkinFacing == Direction.SOUTH) &&
				stateEast.getBlock() == Scarecrows.ARM && stateEast.getValue(ArmBlock.FACING) == Direction.EAST &&
				stateWest.getBlock() == Scarecrows.ARM && stateWest.getValue(ArmBlock.FACING) == Direction.WEST &&
				stateNorth.isAir() && stateSouth.isAir())
			return true;
		//@formatter:on
		else
			return false;
	}

	/**
	 * Spawns the scarecrow entity
	 *
	 * @param type The type of scarecrow to spawn
	 * @param level The level to spawn in
	 * @param pos The position to spawn at
	 * @param isLit Whether the scarecrow should emit light (from a Jack o' Lantern used as the head)
	 * @param facing The facing of the spawned scarecrow entity
	 */
	public final void spawn(ScarecrowType type, LevelAccessor level, BlockPos pos, boolean isLit, Direction facing) {
		if (isLit)
			((Level) level).setBlockAndUpdate(pos.above(height - 1), Scarecrows.INVISIBLE_LIGHT.defaultBlockState());

		level.addFreshEntity(new Scarecrow(type, (Level) level, pos, isLit, facing));
	}

	/**
	 * Drops the items that the structure was made of. Used when the entity dies
	 *
	 * @param level The level to spawn the drops in
	 * @param pos The position to spawn the drops at
	 * @param dropLight true if the entity was made with a Jack o' Lantern, false otherwise
	 */
	public final void dropMaterials(Level level, BlockPos pos, boolean dropLight) {
		Block.popResource(level, pos, new ItemStack(dropLight ? Blocks.JACK_O_LANTERN : Blocks.CARVED_PUMPKIN));

		for (ItemStack stack : getDrops()) {
			Block.popResource(level, pos, stack);
		}
	}
}
