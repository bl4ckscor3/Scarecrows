package bl4ckscor3.mod.scarecrows.handler;

import java.util.List;

import com.google.common.base.Predicates;

import bl4ckscor3.mod.scarecrows.Scarecrows;
import bl4ckscor3.mod.scarecrows.ai.EntityAIRunAway;
import bl4ckscor3.mod.scarecrows.entity.EntityScarecrow;
import bl4ckscor3.mod.scarecrows.util.EntityUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent.SpecialSpawn;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid=Scarecrows.MODID)
public class SpawnHandler
{
	@SubscribeEvent
	public static void onSpecialSpawn(SpecialSpawn event)
	{
		Entity entity = event.getEntity();
		boolean animal = EntityUtil.isAttackableAnimal(entity);

		if(EntityUtil.isAttackableMonster(entity) || animal)
		{
			List<EntityScarecrow> scarecrows = EntityUtil.getLoadedScarecrows(event.getWorld(), Predicates.alwaysTrue());

			for(EntityScarecrow scarecrow : scarecrows)
			{
				if(entity.getDistance(scarecrow) <= scarecrow.getScarecrowType().getRange() && ((EntityLiving)entity).canEntityBeSeen(scarecrow))
				{
					if(animal && scarecrow.getScarecrowType().shouldScareAnimals())
						event.setCanceled(true);
					else if(!animal)
						event.setCanceled(true);

					return;
				}
			}
		}
	}

	@SubscribeEvent
	public static void onEntityJoinWorld(EntityJoinWorldEvent event)
	{
		if(EntityUtil.isAttackableMonster(event.getEntity()) || EntityUtil.isAttackableAnimal(event.getEntity()))
		{
			((EntityLiving)event.getEntity()).targetTasks.addTask(0, new EntityAIRunAway((EntityLiving)event.getEntity()));
		}
	}
}
