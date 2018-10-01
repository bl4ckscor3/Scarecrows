package bl4ckscor3.mod.scarecrows.types;

import bl4ckscor3.mod.scarecrows.Scarecrows;
import bl4ckscor3.mod.scarecrows.block.BlockArm;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IScarecrowType
{
	/**
	 * Checks whether this scarecrow is built correctly
	 * @param world The world to check in
	 * @param pos The position to start checking from
	 * @return true if the scarecrow is correctly built, false otherwhise
	 */
	public boolean checkStructure(World world, BlockPos pos);

	/**
	 * Spawns the scarecrow entity
	 * @param world The world to spawn in
	 * @param pos The position to spawn at
	 * @param isLit Whether the scarecrow should emit light (from a Jack o' Lantern used as the head)
	 */
	public void spawn(World world, BlockPos pos, boolean isLit);

	/**
	 * Checks whether this scarecrow has arms
	 * @param world The world the scarecrow is in
	 * @param pos The block to which the arms should be attached
	 */
	public default boolean hasArms(World world, BlockPos pos)
	{
		BlockPos posNorth = pos.north();
		BlockPos posEast = pos.east();
		BlockPos posSouth = pos.south();
		BlockPos posWest = pos.west();
		IBlockState stateNorth = world.getBlockState(posNorth);
		IBlockState stateEast = world.getBlockState(posEast);
		IBlockState stateSouth = world.getBlockState(posSouth);
		IBlockState stateWest = world.getBlockState(posWest);

		if(stateNorth.getBlock() == Scarecrows.ARM && stateNorth.getValue(BlockArm.FACING) == EnumFacing.NORTH &&
				stateSouth.getBlock() == Scarecrows.ARM && stateSouth.getValue(BlockArm.FACING) == EnumFacing.SOUTH &&
				stateWest.getBlock() == Blocks.AIR && stateEast.getBlock() == Blocks.AIR)
			return true;
		else if(stateEast.getBlock() == Scarecrows.ARM && stateEast.getValue(BlockArm.FACING) == EnumFacing.EAST &&
				stateWest.getBlock() == Scarecrows.ARM && stateWest.getValue(BlockArm.FACING) == EnumFacing.WEST &&
				stateNorth.getBlock() == Blocks.AIR && stateSouth.getBlock() == Blocks.AIR)
			return true;
		else return false;
	}
}
