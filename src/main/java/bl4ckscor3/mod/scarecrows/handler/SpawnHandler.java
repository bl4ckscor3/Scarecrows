package bl4ckscor3.mod.scarecrows.handler;

import java.util.List;

import bl4ckscor3.mod.scarecrows.entity.EntityScarecrow;
import bl4ckscor3.mod.scarecrows.util.EntityUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraftforge.event.entity.living.LivingSpawnEvent.SpecialSpawn;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber
public class SpawnHandler
{
	@SubscribeEvent
	public static void onSpecialSpawn(SpecialSpawn event)
	{
		Entity entity = event.getEntity();
		boolean animal = EntityUtil.isAttackableAnimal(entity);

		if(EntityUtil.isAttackableMonster(entity) || animal)
		{
			List<EntityScarecrow> scarecrows = event.getWorld().<EntityScarecrow>getEntities(EntityScarecrow.class, (x) -> { return true; });

			for(EntityScarecrow scarecrow : scarecrows)
			{
				if(entity.getDistance(scarecrow) <= scarecrow.getType().getRange() && ((EntityLiving)entity).canEntityBeSeen(scarecrow))
				{
					if(animal && scarecrow.getType().shouldScareAnimals())
						event.setCanceled(true);
					else if(!animal)
						event.setCanceled(true);

					return;
				}
			}
		}
	}
}
