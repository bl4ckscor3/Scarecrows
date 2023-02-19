package bl4ckscor3.mod.scarecrows.ai;

import java.util.List;
import java.util.Random;

import com.google.common.base.Predicate;

import bl4ckscor3.mod.scarecrows.ScarecrowTracker;
import bl4ckscor3.mod.scarecrows.entity.Scarecrow;
import bl4ckscor3.mod.scarecrows.util.EntityUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;

public class RunAwayGoal extends Goal {
	private final Predicate<Entity> canBeSeenSelector;
	private Mob entity;
	private final float speed = 1.5F;
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
		List<Scarecrow> list = ScarecrowTracker.getScarecrowsInRange(entity.level, entity.blockPosition());

		for (Scarecrow scarecrow : list) {
			if (canBeSeenSelector.apply(scarecrow)) {
				if (EntityUtil.isAttackableMonster(entity)) {
					if (shouldScare(scarecrow))
						return true;
				}
				else if (scarecrow.getScarecrowType().shouldScareAnimals() && EntityUtil.isAttackableAnimal(entity)) {
					if (shouldScare(scarecrow))
						return true;
				}
			}
		}

		return false;
	}

	/**
	 * @param scarecrow The scarecrow that potentially scares this entity
	 * @return true if this ai task should execute, false otherwhise
	 */
	private boolean shouldScare(Scarecrow scarecrow) {
		List<? extends Mob> entities = scarecrow.level.getEntitiesOfClass(entity.getClass(), scarecrow.getArea());

		for (Mob e : entities) {
			if (e == entity) {
				if (e.distanceTo(scarecrow) <= scarecrow.getScarecrowType().getRange()) {
					Vec3 scarecrowPos = new Vec3(scarecrow.getX(), scarecrow.getY(), scarecrow.getZ());
					Vec3 currentPos = new Vec3(e.getX(), e.getY(), e.getZ());
					Vec3 newPosition = EntityUtil.generateRandomPos(e, 16, 7, currentPos.subtract(scarecrowPos), true);

					if (newPosition == null || scarecrow.distanceToSqr(newPosition.x, newPosition.y, newPosition.z) < scarecrow.distanceToSqr(e))
						return false;
					else {
						path = navigation.createPath(new BlockPos(newPosition), 0);
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
		navigation.moveTo(path, speed);
	}

	@Override
	public void tick() {
		if (ticksSinceSound == 0) {
			entity.playAmbientSound();
			createRunningParticles(entity);
			ticksSinceSound = 10;
		}
		else
			ticksSinceSound--;

		entity.getNavigation().setSpeedModifier(speed);
	}

	private void createRunningParticles(Entity entity) {
		int x = Mth.floor(entity.getX());
		int y = Mth.floor(entity.getY() - 0.2F);
		int z = Mth.floor(entity.getZ());
		BlockPos pos = new BlockPos(x, y, z);
		BlockState state = entity.level.getBlockState(pos);

		if (!state.addRunningEffects(entity.level, pos, entity) && state.getRenderShape() != RenderShape.INVISIBLE) {
			Vec3 motion = entity.getDeltaMovement();
			EntityDimensions size = entity.getDimensions(entity.getPose());
			Random rand = entity.level.random;

			entity.level.addParticle(new BlockParticleOption(ParticleTypes.BLOCK, state).setPos(pos), entity.getX() + (rand.nextDouble() - 0.5D) * size.width, entity.getY() + 0.1D, entity.getZ() + (rand.nextDouble() - 0.5D) * size.width, motion.x * -4.0D, 1.5D, motion.z * -4.0D);
		}
	}
}