package bl4ckscor3.mod.scarecrows.handler;

import java.util.List;

import com.google.common.base.Predicate;

import bl4ckscor3.mod.scarecrows.ScarecrowTracker;
import bl4ckscor3.mod.scarecrows.Scarecrows;
import bl4ckscor3.mod.scarecrows.ai.EntityAIRunAway;
import bl4ckscor3.mod.scarecrows.entity.EntityScarecrow;
import bl4ckscor3.mod.scarecrows.util.EntityUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MobEntity;
import net.minecraft.world.World;
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
		if(!(event.getWorld() instanceof World))
			return;

		Entity entity = event.getEntity();
		boolean animal = EntityUtil.isAttackableAnimal(entity);

		if(EntityUtil.isAttackableMonster(entity) || animal)
		{
			List<EntityScarecrow> scarecrows = ScarecrowTracker.getScarecrowsInRange((World)event.getWorld(), entity.getPosition());

			for(EntityScarecrow scarecrow : scarecrows)
			{
				Predicate<Entity> filter = e -> e.isAlive() && ((MobEntity)entity).getEntitySenses().canSee(e);

				if(filter.apply(scarecrow) && entity.getDistance(scarecrow) <= scarecrow.getScarecrowType().getRange() && ((MobEntity)entity).canEntityBeSeen(scarecrow))
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
		if(EntityUtil.isAttackableMonster(event.getEntity()) || EntityUtil.isAttackableAnimal(event.getEntity()))
			((MobEntity)event.getEntity()).goalSelector.addGoal(0, new EntityAIRunAway((MobEntity)event.getEntity()));
	}
}
