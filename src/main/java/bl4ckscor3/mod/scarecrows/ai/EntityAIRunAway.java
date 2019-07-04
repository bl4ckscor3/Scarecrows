package bl4ckscor3.mod.scarecrows.ai;

import java.util.List;

import com.google.common.base.Predicate;

import bl4ckscor3.mod.scarecrows.ScarecrowTracker;
import bl4ckscor3.mod.scarecrows.entity.EntityScarecrow;
import bl4ckscor3.mod.scarecrows.util.EntityUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.util.math.Vec3d;

public class EntityAIRunAway extends EntityAIBase
{
	private final Predicate<Entity> canBeSeenSelector;
	private EntityLiving entity;
	private final float speed = 1.5F;
	/** The PathEntity of our entity */
	private Path path;
	/** The PathNavigate of our entity */
	private final PathNavigate navigation;
	private long ticksSinceSound = 0;

	public EntityAIRunAway(EntityLiving entity)
	{
		canBeSeenSelector = e -> e.isEntityAlive() && entity.getEntitySenses().canSee(e);
		this.entity = entity;
		navigation = entity.getNavigator();
		setMutexBits(1);
	}

	@Override
	public boolean shouldExecute()
	{
		List<EntityScarecrow> list = ScarecrowTracker.getScarecrowsInRange(entity.world, entity.getPosition());

		if(list.isEmpty())
			return false;
		else
		{
			for(EntityScarecrow scarecrow : list)
			{
				if(canBeSeenSelector.apply(scarecrow))
				{
					if(EntityUtil.isAttackableMonster(entity))
					{
						if(!shouldScare(scarecrow))
							continue;
						else return true;
					}
					else if(scarecrow.getType().shouldScareAnimals() && EntityUtil.isAttackableAnimal(entity))
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
	private boolean shouldScare(EntityScarecrow scarecrow)
	{
		List<EntityLiving> entities = scarecrow.world.<EntityLiving>getEntitiesWithinAABB(entity.getClass(), scarecrow.getArea());

		for(EntityLiving e : entities)
		{
			if(e == entity)
			{
				if(e.getDistance(scarecrow) <= scarecrow.getType().getRange())
				{
					Vec3d scarecrowPos = new Vec3d(scarecrow.posX, scarecrow.posY, scarecrow.posZ);
					Vec3d ownPos = new Vec3d(e.posX, e.posY, e.posZ);
					Vec3d newPosition = EntityUtil.generateRandomPos(e, 16, 7, ownPos.subtract(scarecrowPos), true);

					if(newPosition == null || scarecrow.getDistanceSq(newPosition.x, newPosition.y, newPosition.z) < scarecrow.getDistanceSq(e))
						return false;
					else
					{
						path = navigation.getPathToXYZ(newPosition.x, newPosition.y, newPosition.z);
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
	public void updateTask()
	{
		if(ticksSinceSound == 0)
		{
			entity.playLivingSound();
			entity.spawnRunningParticles();
			ticksSinceSound = 10;
		}
		else
			ticksSinceSound--;

		entity.getNavigator().setSpeed(speed);
	}
}