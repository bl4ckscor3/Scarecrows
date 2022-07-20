package bl4ckscor3.mod.scarecrows;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import bl4ckscor3.mod.scarecrows.entity.Scarecrow;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

/**
 * Tracks all exisiting scarecrow so searching for them each tick is obsolete.
 * Also manages range checks
 */
public class ScarecrowTracker
{
	private static final Map<ResourceKey<Level>,Collection<Integer>> TRACKED_SCARECROWS = new ConcurrentHashMap<>();

	/**
	 * Starts tracking a scarecrow
	 * @param entity The scarecrow to track
	 */
	public static void track(Scarecrow entity)
	{
		getTrackedScarecrows(entity.getCommandSenderWorld()).add(entity.getId());
	}

	/**
	 * Stops tracking the given scarecrow. Use when e.g. removing the entity from the world
	 * @param entity The scarecrow to stop tracking
	 */
	public static void stopTracking(Scarecrow entity)
	{
		getTrackedScarecrows(entity.getCommandSenderWorld()).remove(entity.getId());
	}

	/**
	 * Gets all scarecrows that have the given block position in their range in the given world
	 * @param level The level
	 * @param pos The block position
	 * @return A list of all scarecrows that have the given block position in their range
	 */
	public static List<Scarecrow> getScarecrowsInRange(Level level, BlockPos pos)
	{
		final Collection<Integer> scarecrows = getTrackedScarecrows(level);
		List<Scarecrow> returnValue = new ArrayList<>();

		for(Iterator<Integer> it = scarecrows.iterator(); it.hasNext(); )
		{
			if(level.getEntity(it.next()) instanceof Scarecrow scarecrow)
			{
				if(canScarecrowReach(scarecrow, pos))
					returnValue.add(scarecrow);

				continue;
			}

			it.remove();
		}

		return returnValue;
	}

	/**
	 * Gets all block positions at which a scarecrow is being tracked for the given world
	 * @param level The world to get the tracked scarecrows of
	 */
	private static Collection<Integer> getTrackedScarecrows(Level level)
	{
		Collection<Integer> scarecrows = TRACKED_SCARECROWS.get(level.dimension());

		if(scarecrows == null)
		{
			scarecrows = ConcurrentHashMap.newKeySet();
			TRACKED_SCARECROWS.put(level.dimension(), scarecrows);
		}

		return scarecrows;
	}

	/**
	 * Checks whether the given block position is contained in the given scarecrow's range
	 * @param entity The scarecrow
	 * @param pos The block position
	 */
	private static boolean canScarecrowReach(Scarecrow entity, BlockPos pos)
	{
		AABB scarecrowRange = entity.getArea();

		return scarecrowRange.minX <= pos.getX() && scarecrowRange.minY <= pos.getY() && scarecrowRange.minZ <= pos.getZ() && scarecrowRange.maxX >= pos.getX() && scarecrowRange.maxY >= pos.getY() && scarecrowRange.maxZ >= pos.getZ();
	}
}
