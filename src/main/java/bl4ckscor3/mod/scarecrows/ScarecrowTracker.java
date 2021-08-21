package bl4ckscor3.mod.scarecrows;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import bl4ckscor3.mod.scarecrows.entity.Scarecrow;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

/**
 * Tracks all exisiting scarecrow so searching for them each tick is obsolete.
 * Also manages range checks
 */
public class ScarecrowTracker
{
	private static final Map<ResourceKey<Level>,Collection<Integer>> trackedScarecrows = new HashMap<>();

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
	 * @param world The world
	 * @param pos The block position
	 * @return A list of all scarecrows that have the given block position in their range
	 */
	public static List<Scarecrow> getScarecrowsInRange(Level world, BlockPos pos)
	{
		final Collection<Integer> scarecrows = getTrackedScarecrows(world);
		List<Scarecrow> returnValue = new ArrayList<>();

		for(Iterator<Integer> it = scarecrows.iterator(); it.hasNext(); )
		{
			int scarecrowId = it.next();
			Entity scarecrow = world.getEntity(scarecrowId);

			if(scarecrow instanceof Scarecrow)
			{
				if(canScarecrowReach((Scarecrow)scarecrow, pos))
					returnValue.add((Scarecrow)scarecrow);

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
	private static Collection<Integer> getTrackedScarecrows(Level world)
	{
		Collection<Integer> scarecrows = trackedScarecrows.get(world.dimension());

		if(scarecrows == null)
		{
			scarecrows = new HashSet<>();
			trackedScarecrows.put(world.dimension(), scarecrows);
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
