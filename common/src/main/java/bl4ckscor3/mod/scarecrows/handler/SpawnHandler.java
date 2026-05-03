package bl4ckscor3.mod.scarecrows.handler;

import java.util.List;

import com.google.common.base.Predicate;

import bl4ckscor3.mod.scarecrows.ScarecrowTracker;
import bl4ckscor3.mod.scarecrows.ai.RunAwayGoal;
import bl4ckscor3.mod.scarecrows.entity.Scarecrow;
import bl4ckscor3.mod.scarecrows.util.EntityUtil;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.GoalSelector;
import net.minecraft.world.level.Level;

public class SpawnHandler {
	private SpawnHandler() {}

	public static boolean shouldDisallowSpawn(Level level, Entity entity, EntitySpawnReason reason) {
		if (reason == EntitySpawnReason.COMMAND || reason == EntitySpawnReason.SPAWN_ITEM_USE)
			return false;

		boolean animal = EntityUtil.isAttackableAnimal(entity);

		if (EntityUtil.isAttackableMonster(entity) || animal) {
			List<Scarecrow> scarecrows = ScarecrowTracker.getScarecrowsInRange(level, entity.blockPosition());

			for (Scarecrow scarecrow : scarecrows) {
				Predicate<Entity> filter = e -> e.isAlive() && ((Mob) entity).getSensing().hasLineOfSight(e);

				if (filter.apply(scarecrow) && entity.distanceTo(scarecrow) <= scarecrow.getScarecrowType().getRange() && ((Mob) entity).hasLineOfSight(scarecrow)) {
					if (!animal || scarecrow.getScarecrowType().shouldScareAnimals())
						return true;
				}
			}
		}

		return false;
	}

	public static void addRunAwayGoal(Mob mob, GoalSelector goalSelector) {
		if (EntityUtil.isAttackableMonster(mob) || EntityUtil.isAttackableAnimal(mob))
			goalSelector.addGoal(0, new RunAwayGoal(mob));
	}
}
