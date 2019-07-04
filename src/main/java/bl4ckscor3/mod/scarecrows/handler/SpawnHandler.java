package bl4ckscor3.mod.scarecrows.handler;

import java.util.List;

import com.google.common.base.Predicate;

import bl4ckscor3.mod.scarecrows.ScarecrowTracker;
import bl4ckscor3.mod.scarecrows.ai.EntityAIRunAway;
import bl4ckscor3.mod.scarecrows.entity.EntityScarecrow;
import bl4ckscor3.mod.scarecrows.util.EntityUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent.CheckSpawn;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber
public class SpawnHandler
{
	@SubscribeEvent
	public static void onCheckSpawn(CheckSpawn event)
	{
		Entity entity = event.getEntity();
		boolean animal = EntityUtil.isAttackableAnimal(entity);

		if(EntityUtil.isAttackableMonster(entity) || animal)
		{
			List<EntityScarecrow> scarecrows = ScarecrowTracker.getScarecrowsInRange(event.getWorld(), entity.getPosition());

			for(EntityScarecrow scarecrow : scarecrows)
			{
				Predicate<Entity> filter = e -> e.isEntityAlive() && ((EntityLiving)entity).getEntitySenses().canSee(e);

				if(filter.apply(scarecrow) && entity.getDistance(scarecrow) <= scarecrow.getType().getRange() && ((EntityLiving)entity).canEntityBeSeen(scarecrow))
				{
					if(animal && scarecrow.getType().shouldScareAnimals())
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
		if(EntityUtil.isAttackableMonster(event.getEntity()) || EntityUtil.isAttackableAnimal(event.getEntity()))
			((EntityLiving)event.getEntity()).tasks.addTask(0, new EntityAIRunAway((EntityLiving)event.getEntity()));
	}
}
