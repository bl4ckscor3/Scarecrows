package bl4ckscor3.mod.scarecrows.ai;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

import bl4ckscor3.mod.scarecrows.entity.EntityScarecrow;
import bl4ckscor3.mod.scarecrows.util.EntityUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.math.Vec3d;

public class EntityAIRunAway extends EntityAIBase
{
	private final Predicate<Entity> canBeSeenSelector;
	private EntityLiving entity;
	private final float speed = 1.5F;
	private List<EntityScarecrow> scarecrows = new ArrayList<EntityScarecrow>();
	/** The PathEntity of our entity */
	private Path path;
	/** The PathNavigate of our entity */
	private final PathNavigate navigation;

	public EntityAIRunAway(EntityLiving entity)
	{
		canBeSeenSelector = new Predicate<Entity>()
		{
			@Override
			public boolean apply(@Nullable Entity e)
			{
				return e.isEntityAlive() && entity.getEntitySenses().canSee(e);
			}
		};
		this.entity = entity;
		navigation = entity.getNavigator();
		setMutexBits(1);
	}

	/**
	 * Returns whether the EntityAIBase should begin execution.
	 */
	public boolean shouldExecute()
	{
		List<EntityScarecrow> list = entity.world.getEntities(EntityScarecrow.class, Predicates.and(EntitySelectors.CAN_AI_TARGET, canBeSeenSelector));

		if(list.isEmpty())
			return false;
		else
		{
			scarecrows = list;

			for(EntityScarecrow scarecrow : scarecrows)
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

	/**
	 * Returns whether an in-progress EntityAIBase should continue executing
	 */
	public boolean shouldContinueExecuting()
	{
		return !navigation.noPath();
	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	public void startExecuting()
	{
		navigation.setPath(path, speed);
	}

	/**
	 * Reset the task's internal state. Called when this task is interrupted by another one
	 */
	public void resetTask()
	{
		scarecrows.clear();
	}

	/**
	 * Keep ticking a continuous task that has already been started
	 */
	public void updateTask()
	{
		//TODO: entity sounds and dust particles from running
		entity.getNavigator().setSpeed(speed);
	}
}