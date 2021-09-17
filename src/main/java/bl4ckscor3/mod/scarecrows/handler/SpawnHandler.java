package bl4ckscor3.mod.scarecrows.handler;

import java.util.List;

import com.google.common.base.Predicate;

import bl4ckscor3.mod.scarecrows.ScarecrowTracker;
import bl4ckscor3.mod.scarecrows.Scarecrows;
import bl4ckscor3.mod.scarecrows.ai.RunAwayGoal;
import bl4ckscor3.mod.scarecrows.entity.Scarecrow;
import bl4ckscor3.mod.scarecrows.util.EntityUtil;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent.CheckSpawn;
import net.minecraftforge.eventbus.api.Event.Result;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid=Scarecrows.MODID)
public class SpawnHandler
{
	@SubscribeEvent
	public static void onCheckSpawn(CheckSpawn event)
	{
		if(!(event.getWorld() instanceof Level level))
			return;

		Entity entity = event.getEntity();
		boolean animal = EntityUtil.isAttackableAnimal(entity);

		if(EntityUtil.isAttackableMonster(entity) || animal)
		{
			List<Scarecrow> scarecrows = ScarecrowTracker.getScarecrowsInRange(level, entity.blockPosition());

			for(Scarecrow scarecrow : scarecrows)
			{
				Predicate<Entity> filter = e -> e.isAlive() && ((Mob)entity).getSensing().hasLineOfSight(e);

				if(filter.apply(scarecrow) && entity.distanceTo(scarecrow) <= scarecrow.getScarecrowType().getRange() && ((Mob)entity).hasLineOfSight(scarecrow))
				{
					if(animal && scarecrow.getScarecrowType().shouldScareAnimals())
						event.setResult(Result.DENY);
					else if(!animal)
						event.setResult(Result.DENY);

					return; //needed so the loop stops
				}
			}
		}
	}

	@SubscribeEvent
	public static void onEntityJoinWorld(EntityJoinWorldEvent event)
	{
		if(event.getEntity() instanceof Mob mob && (EntityUtil.isAttackableMonster(mob) || EntityUtil.isAttackableAnimal(mob)))
			mob.goalSelector.addGoal(0, new RunAwayGoal(mob));
	}
}
