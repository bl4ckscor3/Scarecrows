package bl4ckscor3.mod.scarecrows.ai;

import java.util.EnumSet;
import java.util.List;
import java.util.Random;

import com.google.common.base.Predicate;

import bl4ckscor3.mod.scarecrows.ScarecrowTracker;
import bl4ckscor3.mod.scarecrows.entity.ScarecrowEntity;
import bl4ckscor3.mod.scarecrows.util.EntityUtil;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.particles.BlockParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;

import net.minecraft.entity.ai.goal.Goal.Flag;

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
		canBeSeenSelector = e -> e.isAlive() && entity.getSensing().canSee(e);
		this.entity = entity;
		navigation = entity.getNavigation();
		setFlags(EnumSet.of(Flag.MOVE));
	}

	@Override
	public boolean canUse()
	{
		List<ScarecrowEntity> list = ScarecrowTracker.getScarecrowsInRange(entity.level, entity.blockPosition());

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
		List<MobEntity> entities = scarecrow.level.<MobEntity>getEntitiesOfClass(entity.getClass(), scarecrow.getArea());

		for(MobEntity e : entities)
		{
			if(e == entity)
			{
				if(e.distanceTo(scarecrow) <= scarecrow.getScarecrowType().getRange())
				{
					Vector3d scarecrowPos = new Vector3d(scarecrow.getX(), scarecrow.getY(), scarecrow.getZ());
					Vector3d ownPos = new Vector3d(e.getX(), e.getY(), e.getZ());
					Vector3d newPosition = EntityUtil.generateRandomPos(e, 16, 7, ownPos.subtract(scarecrowPos), true);

					if(newPosition == null || scarecrow.distanceToSqr(newPosition.x, newPosition.y, newPosition.z) < scarecrow.distanceToSqr(e))
						return false;
					else
					{
						path = navigation.createPath(new BlockPos(newPosition), 0);
						return path != null;
					}
				}
				else return false;
			}
		}

		return false;
	}

	@Override
	public boolean canContinueToUse()
	{
		return !navigation.isDone();
	}

	@Override
	public void start()
	{
		navigation.moveTo(path, speed);
	}

	@Override
	public void tick()
	{
		if(ticksSinceSound == 0)
		{
			entity.playAmbientSound();
			createRunningParticles(entity);
			ticksSinceSound = 10;
		}
		else
			ticksSinceSound--;

		entity.getNavigation().setSpeedModifier(speed);
	}

	private void createRunningParticles(Entity entity)
	{
		int x = MathHelper.floor(entity.getX());
		int y = MathHelper.floor(entity.getY() - 0.2F);
		int z = MathHelper.floor(entity.getZ());
		BlockPos pos = new BlockPos(x, y, z);
		BlockState state = entity.level.getBlockState(pos);

		if(!state.addRunningEffects(entity.level, pos, entity) && state.getRenderShape() != BlockRenderType.INVISIBLE)
		{
			Vector3d motion = entity.getDeltaMovement();
			EntitySize size = entity.getDimensions(entity.getPose());
			Random rand = entity.level.random;

			entity.level.addParticle(new BlockParticleData(ParticleTypes.BLOCK, state).setPos(pos), entity.getX() + (rand.nextDouble() - 0.5D) * size.width, entity.getY() + 0.1D, entity.getZ() + (rand.nextDouble() - 0.5D) * size.width, motion.x * -4.0D, 1.5D, motion.z * -4.0D);
		}
	}
}