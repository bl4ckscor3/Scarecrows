package bl4ckscor3.mod.scarecrows.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntityShulker;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.passive.EntityAmbientCreature;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntitySquid;

public class EntityUtil
{
	/**
	 * Checks whether a given entity is a monster (Zombie, Creeper, ...) that a scarecrow would attack
	 * @param entity The entity to check
	 * @return true if a scarecrow would attack the given entity, false otherwise
	 */
	public static boolean isAttackableMonster(Entity entity)
	{
		return entity instanceof EntityMob || entity instanceof EntityDragon || entity instanceof EntityGhast || entity instanceof EntityShulker || entity instanceof EntitySlime;
	}

	/**
	 * Checks whether a given entity is an animal (Squid, Sheep, ...) that a scarecrow would attack
	 * @param entity The entity to check
	 * @return true if a scarecrow would attack the given entity, false otherwise
	 */
	public static boolean isAttackableAnimal(Entity entity)
	{
		return entity instanceof EntityAmbientCreature || entity instanceof EntityAnimal || entity instanceof EntitySquid;
	}
}
