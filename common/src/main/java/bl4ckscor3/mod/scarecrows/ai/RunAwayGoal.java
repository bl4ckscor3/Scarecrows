package bl4ckscor3.mod.scarecrows.ai;

import java.util.List;

import com.google.common.base.Predicate;

import bl4ckscor3.mod.scarecrows.ScarecrowTracker;
import bl4ckscor3.mod.scarecrows.entity.Scarecrow;
import bl4ckscor3.mod.scarecrows.mixin.EntityAccessor;
import bl4ckscor3.mod.scarecrows.util.EntityUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;

public class RunAwayGoal extends Goal {
	private static final float SPEED = 1.5F;
	private final Predicate<Entity> canBeSeenSelector;
	private Mob entity;
	/** The PathEntity of our entity */
	private Path path;
	/** The PathNavigate of our entity */
	private final PathNavigation navigation;
	private long ticksSinceSound = 0;

	public RunAwayGoal(Mob entity) {
		canBeSeenSelector = e -> e.isAlive() && entity.getSensing().hasLineOfSight(e);
		this.entity = entity;
		navigation = entity.getNavigation();
	}

	@Override
	public boolean canUse() {
		List<Scarecrow> list = ScarecrowTracker.getScarecrowsInRange(entity.level(), entity.blockPosition());

		for (Scarecrow scarecrow : list) {
			//@formatter:off
			if (canBeSeenSelector.apply(scarecrow)
					&& (EntityUtil.isAttackableMonster(entity) || scarecrow.getScarecrowType().shouldScareAnimals() && EntityUtil.isAttackableAnimal(entity))
					&& shouldScare(scarecrow)) {
						return true;
			}
			//@formatter:on
		}

		return false;
	}

	/**
	 * @param scarecrow The scarecrow that potentially scares this entity
	 * @return true if this ai task should execute, false otherwhise
	 */
	private boolean shouldScare(Scarecrow scarecrow) {
		List<? extends Mob> entities = scarecrow.level().getEntitiesOfClass(entity.getClass(), scarecrow.getArea());

		for (Mob e : entities) {
			if (e == entity) {
				if (e.distanceTo(scarecrow) <= scarecrow.getScarecrowType().getRange()) {
					Vec3 scarecrowPos = new Vec3(scarecrow.getX(), scarecrow.getY(), scarecrow.getZ());
					Vec3 currentPos = new Vec3(e.getX(), e.getY(), e.getZ());
					Vec3 newPosition = EntityUtil.generateRandomPos(e, 16, 7, currentPos.subtract(scarecrowPos), true);

					if (newPosition == null || scarecrow.distanceToSqr(newPosition.x, newPosition.y, newPosition.z) < scarecrow.distanceToSqr(e))
						return false;
					else {
						path = navigation.createPath(BlockPos.containing(newPosition), 0);
						return path != null;
					}
				}
				else
					return false;
			}
		}

		return false;
	}

	@Override
	public boolean canContinueToUse() {
		return !navigation.isDone();
	}

	@Override
	public void start() {
		navigation.moveTo(path, SPEED);
	}

	@Override
	public void tick() {
		if (ticksSinceSound == 0) {
			entity.playAmbientSound();
			((EntityAccessor) entity).callSpawnSprintParticle();
			ticksSinceSound = 10;
		}
		else
			ticksSinceSound--;

		entity.setTarget(null);
		entity.getNavigation().setSpeedModifier(SPEED);
	}
}