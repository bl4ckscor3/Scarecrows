package bl4ckscor3.mod.scarecrows;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import bl4ckscor3.mod.scarecrows.entity.ScarecrowEntity;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Tracks all exisiting scarecrow so searching for them each tick is obsolete.
 * Also manages range checks
 */
public class ScarecrowTracker
{
	private static final Map<Integer,Collection<Integer>> trackedScarecrows = new HashMap<>();

	/**
	 * Starts tracking a scarecrow
	 * @param entity The scarecrow to track
	 */
	public static void track(ScarecrowEntity entity)
	{
		getTrackedScarecrows(entity.getEntityWorld()).add(entity.getEntityId());
	}

	/**
	 * Stops tracking the given scarecrow. Use when e.g. removing the entity from the world
	 * @param entity The scarecrow to stop tracking
	 */
	public static void stopTracking(ScarecrowEntity entity)
	{
		getTrackedScarecrows(entity.getEntityWorld()).remove(entity.getEntityId());
	}

	/**
	 * Gets all scarecrows that have the given block position in their range in the given world
	 * @param world The world
	 * @param pos The block position
	 * @return A list of all scarecrows that have the given block position in their range
	 */
	public static List<ScarecrowEntity> getScarecrowsInRange(World world, BlockPos pos)
	{
		final Collection<Integer> scarecrows = getTrackedScarecrows(world);
		List<ScarecrowEntity> returnValue = new ArrayList<>();

		for(Iterator<Integer> it = scarecrows.iterator(); it.hasNext(); )
		{
			int scarecrowId = it.next();
			Entity scarecrow = world.getEntityByID(scarecrowId);

			if(scarecrow instanceof ScarecrowEntity)
			{
				if(canScarecrowReach((ScarecrowEntity)scarecrow, pos))
					returnValue.add((ScarecrowEntity)scarecrow);

				continue;
			}

			it.remove();
		}

		return returnValue;
	}

	/**
	 * Gets all block positions at which a scarecrow is being tracked for the given world
	 * @param world The world to get the tracked scarecrows of
	 */
	private static Collection<Integer> getTrackedScarecrows(World world)
	{
		Collection<Integer> scarecrows = trackedScarecrows.get(world.getDimension().getType().getId());

		if(scarecrows == null)
		{
			scarecrows = new HashSet<Integer>();
			trackedScarecrows.put(world.getDimension().getType().getId(), scarecrows);
		}

		return scarecrows;
	}

	/**
	 * Checks whether the given block position is contained in the given scarecrow's range
	 * @param entity The scarecrow
	 * @param pos The block position
	 */
	private static boolean canScarecrowReach(ScarecrowEntity entity, BlockPos pos)
	{
		AxisAlignedBB scarecrowRange = entity.getArea();

		return scarecrowRange.minX <= pos.getX() && scarecrowRange.minY <= pos.getY() && scarecrowRange.minZ <= pos.getZ() && scarecrowRange.maxX >= pos.getX() && scarecrowRange.maxY >= pos.getY() && scarecrowRange.maxZ >= pos.getZ();
	}
}
