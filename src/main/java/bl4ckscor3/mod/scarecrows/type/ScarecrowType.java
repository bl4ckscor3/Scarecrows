package bl4ckscor3.mod.scarecrows.type;

import bl4ckscor3.mod.scarecrows.Scarecrows;
import bl4ckscor3.mod.scarecrows.block.BlockArm;
import bl4ckscor3.mod.scarecrows.entity.EntityScarecrow;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public abstract class ScarecrowType
{
	public static final ScarecrowType[] TYPES = new ScarecrowType[] {
			new SpoopyScarecrow(),
			new SuperSpoopyScarecrow(),
			new SpookyScarecrow(),
			new SuperSpookyScarecrow(),
			new ScaryScarecrow(),
			new SuperScaryScarecrow()
	};
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
	public ScarecrowType(String name, int height, int range, boolean scareAnimals)
	{
		this.name = name;
		this.height = height;
		this.range = range;
		this.scareAnimals = scareAnimals;
	}

	/**
	 * Checks whether this scarecrow is built correctly
	 * @param world The world to check in
	 * @param pos The position to start checking from (the pumpkin)
	 * @param pumpkinFacing The facing of the pumpkin
	 * @return true if the scarecrow is correctly built, false otherwise
	 */
	public abstract boolean checkStructure(IWorld world, BlockPos pos, Direction pumpkinFacing);

	/**
	 * Destroy the structure of this scarecrow
	 * @param world The world to destroy in
	 * @param pos The position to start destroying from (the pumpkin)
	 */
	public abstract void destroy(IWorld world, BlockPos pos);

	/**
	 * @return The drops that this scarecrow will drop when its entity is removed, excluding the pumpkin
	 */
	public abstract ItemStack[] getDrops();

	/**
	 * @param isLit Whether this model should show up as lit or not
	 * @return The model this scarecrow will use
	 */
	@OnlyIn(Dist.CLIENT)
	public abstract EntityModel<EntityScarecrow> getModel(boolean isLit);

	/**
	 * @return The name of the scarecrow
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @return The height of the scarecrow, used for correctly defining the spawn position
	 */
	public final int getHeight()
	{
		return height;
	}

	/**
	 * @return The value that should be used to determine the effect range of this scarecrow
	 */
	public final int getRange()
	{
		return range;
	}

	/**
	 * Defines whether this scarecow also scares animals additional to monsters
	 */
	public final boolean shouldScareAnimals()
	{
		return scareAnimals;
	}

	/**
	 * Checks whether this scarecrow has arms
	 * @param world The world the scarecrow is in
	 * @param pos The block to which the arms should be attached
	 * @param pumpkinFacing The facing of the pumpkin, used to determin if the arms are placed on the correct sides
	 */
	public final boolean hasArms(IWorld world, BlockPos pos, Direction pumpkinFacing)
	{
		BlockPos posNorth = pos.north();
		BlockPos posEast = pos.east();
		BlockPos posSouth = pos.south();
		BlockPos posWest = pos.west();
		BlockState stateNorth = world.getBlockState(posNorth);
		BlockState stateEast = world.getBlockState(posEast);
		BlockState stateSouth = world.getBlockState(posSouth);
		BlockState stateWest = world.getBlockState(posWest);

		if((pumpkinFacing == Direction.EAST || pumpkinFacing == Direction.WEST) &&
				stateNorth.getBlock() == Scarecrows.ARM && stateNorth.get(BlockArm.FACING) == Direction.NORTH &&
				stateSouth.getBlock() == Scarecrows.ARM && stateSouth.get(BlockArm.FACING) == Direction.SOUTH &&
				stateWest.isAir(world, pos) && stateEast.isAir(world, pos))
			return true;
		else if((pumpkinFacing == Direction.NORTH || pumpkinFacing == Direction.SOUTH) &&
				stateEast.getBlock() == Scarecrows.ARM && stateEast.get(BlockArm.FACING) == Direction.EAST &&
				stateWest.getBlock() == Scarecrows.ARM && stateWest.get(BlockArm.FACING) == Direction.WEST &&
				stateNorth.isAir(world, pos) && stateSouth.isAir(world, pos))
			return true;
		else return false;
	}

	/**
	 * Spawns the scarecrow entity
	 * @param type The type of scarecrow to spawn
	 * @param world The world to spawn in
	 * @param pos The position to spawn at
	 * @param isLit Whether the scarecrow should emit light (from a Jack o' Lantern used as the head)
	 * @param facing The facing of the spawned scarecrow entity
	 */
	public final void spawn(ScarecrowType type, IWorld world, BlockPos pos, boolean isLit, Direction facing)
	{
		if(isLit)
			((World)world).setBlockState(pos.up(height - 1), Scarecrows.INVISIBLE_LIGHT.getDefaultState());

		world.addEntity(new EntityScarecrow(type, (World)world, pos, isLit, facing));
	}

	/**
	 * Drops the items that the structure was made of. Used when the entity dies
	 * @param world The world to spawn the drops in
	 * @param pos The position to spawn the drops at
	 * @param dropLight true if the entity was made with a Jack o' Lantern, false otherwise
	 */
	public final void dropMaterials(World world, BlockPos pos, boolean dropLight)
	{
		Block.spawnAsEntity(world, pos, new ItemStack(dropLight ? Blocks.JACK_O_LANTERN : Blocks.CARVED_PUMPKIN));

		for(ItemStack stack : getDrops())
		{
			Block.spawnAsEntity(world, pos, stack);
		}
	}
}
