package bl4ckscor3.mod.scarecrows.handler;

import bl4ckscor3.mod.scarecrows.ScarecrowTracker;
import bl4ckscor3.mod.scarecrows.Scarecrows;
import bl4ckscor3.mod.scarecrows.ai.RunAwayGoal;
import bl4ckscor3.mod.scarecrows.entity.Scarecrow;
import bl4ckscor3.mod.scarecrows.util.EntityUtil;
import com.google.common.base.Predicate;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.MobSpawnEvent;

import java.util.List;

@EventBusSubscriber(modid = Scarecrows.MODID)
public class SpawnHandler {
	@SubscribeEvent
	public static void onCheckSpawn(MobSpawnEvent.FinalizeSpawn event) {
		if (!(event.getLevel() instanceof Level level))
			return;

		Entity entity = event.getEntity();
		boolean animal = EntityUtil.isAttackableAnimal(entity);

		if (EntityUtil.isAttackableMonster(entity) || animal) {
			List<Scarecrow> scarecrows = ScarecrowTracker.getScarecrowsInRange(level, entity.blockPosition());

			for (Scarecrow scarecrow : scarecrows) {
				Predicate<Entity> filter = e -> e.isAlive() && ((Mob) entity).getSensing().hasLineOfSight(e);

				if (filter.apply(scarecrow) && entity.distanceTo(scarecrow) <= scarecrow.getScarecrowType().getRange() && ((Mob) entity).hasLineOfSight(scarecrow)) {
					if (animal && scarecrow.getScarecrowType().shouldScareAnimals())
						event.setSpawnCancelled(true);
					else if (!animal)
						event.setSpawnCancelled(true);

					return;
				}
			}
		}
	}

	@SubscribeEvent
	public static void onEntityJoinWorld(EntityJoinLevelEvent event) {
		if (event.getEntity() instanceof Mob mob && (EntityUtil.isAttackableMonster(mob) || EntityUtil.isAttackableAnimal(mob)))
			mob.goalSelector.addGoal(0, new RunAwayGoal(mob));
	}
}
