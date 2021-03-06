package bl4ckscor3.mod.scarecrows.ai;

import java.util.EnumSet;
import java.util.List;

import com.google.common.base.Predicate;

import bl4ckscor3.mod.scarecrows.ScarecrowTracker;
import bl4ckscor3.mod.scarecrows.entity.ScarecrowEntity;
import bl4ckscor3.mod.scarecrows.util.EntityUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class RunAwayGoal extends Goal
{
	private final Predicate<Entity> canBeSeenSelector;
	private MobEntity entity;
	private final float speed = 1.5F;
	/** The PathEntity of our entity */
	private Path path;
	/** The PathNavigate of our entity */
	private final PathNavigator navigation;
	private long ticksSinceSound = 0;

	public RunAwayGoal(MobEntity entity)
	{
		canBeSeenSelector = e -> e.isAlive() && entity.getEntitySenses().canSee(e);
		this.entity = entity;
		navigation = entity.getNavigator();
		setMutexFlags(EnumSet.of(Flag.MOVE));
	}

	@Override
	public boolean shouldExecute()
	{
		List<ScarecrowEntity> list = ScarecrowTracker.getScarecrowsInRange(entity.world, entity.getPosition());

		if(list.isEmpty())
			return false;
		else
		{
			for(ScarecrowEntity scarecrow : list)
			{
				if(canBeSeenSelector.apply(scarecrow))
				{
					if(EntityUtil.isAttackableMonster(entity))
					{
						if(!shouldScare(scarecrow))
							continue;
						else return true;
					}
					else if(scarecrow.getScarecrowType().shouldScareAnimals() && EntityUtil.isAttackableAnimal(entity))
					{
						if(!shouldScare(scarecrow))
							continue;
						else return true;
					}
				}
			}

			return false;
		}
	}

	/**
	 * @param scarecrow The scarecrow that potentially scares this entity
	 * @return true if this ai task should execute, false otherwhise
	 */
	private boolean shouldScare(ScarecrowEntity scarecrow)
	{
		List<MobEntity> entities = scarecrow.world.<MobEntity>getEntitiesWithinAABB(entity.getClass(), scarecrow.getArea());

		for(MobEntity e : entities)
		{
			if(e == entity)
			{
				if(e.getDistance(scarecrow) <= scarecrow.getScarecrowType().getRange())
				{
					Vec3d scarecrowPos = new Vec3d(scarecrow.func_226277_ct_(), scarecrow.func_226278_cu_(), scarecrow.func_226281_cx_());
					Vec3d ownPos = new Vec3d(e.func_226277_ct_(), e.func_226278_cu_(), e.func_226281_cx_());
					Vec3d newPosition = EntityUtil.generateRandomPos(e, 16, 7, ownPos.subtract(scarecrowPos), true);

					if(newPosition == null || scarecrow.getDistanceSq(newPosition.x, newPosition.y, newPosition.z) < scarecrow.getDistanceSq(e))
						return false;
					else
					{
						path = navigation.getPathToPos(new BlockPos(newPosition), 0);
						return path != null;
					}
				}
				else return false;
			}
		}

		return false;
	}

	@Override
	public boolean shouldContinueExecuting()
	{
		return !navigation.noPath();
	}

	@Override
	public void startExecuting()
	{
		navigation.setPath(path, speed);
	}

	@Override
	public void tick()
	{
		if(ticksSinceSound == 0)
		{
			entity.playAmbientSound();
			entity.spawnRunningParticles();
			ticksSinceSound = 10;
		}
		else
			ticksSinceSound--;

		entity.getNavigator().setSpeed(speed);
	}
}