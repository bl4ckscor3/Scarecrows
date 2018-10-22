package bl4ckscor3.mod.scarecrows.util;

import java.util.ArrayList;
import java.util.Random;

import javax.annotation.Nullable;

import com.google.common.base.Predicate;
import com.google.common.collect.Lists;

import bl4ckscor3.mod.scarecrows.entity.EntityScarecrow;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntityShulker;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.passive.EntityAmbientCreature;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

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

	/**
	 * @see {@link net.minecraft.entity.ai.RandomPositionGenerator}
	 */
	public static Vec3d generateRandomPos(EntityLiving entity, int xz, int y, @Nullable Vec3d target, boolean b)
	{
		PathNavigate pathnavigate = entity.getNavigator();
		Random random = entity.getRNG();
		boolean flag = false;
		boolean flag1 = false;
		int k1 = 0;
		int i = 0;
		int j = 0;

		for(int k = 0; k < 10; ++k)
		{
			int l = random.nextInt(2 * xz + 1) - xz;
			int i1 = random.nextInt(2 * y + 1) - y;
			int j1 = random.nextInt(2 * xz + 1) - xz;

			if(target == null || l * target.x + j1 * target.z >= 0.0D)
			{
				BlockPos blockpos1 = new BlockPos(l + entity.posX, i1 + entity.posY, j1 + entity.posZ);

				if(!flag && pathnavigate.canEntityStandOnPos(blockpos1))
				{
					if(!b)
					{
						blockpos1 = moveAboveSolid(blockpos1, entity);

						if(isWaterDestination(blockpos1, entity))
							continue;
					}

					k1 = l;
					i = i1;
					j = j1;
					flag1 = true;
				}
			}
		}

		if(flag1)
			return new Vec3d(k1 + entity.posX, i + entity.posY, j + entity.posZ);
		else
			return null;
	}

	/**
	 * This may fix a ConcurrentModificationException that occurs from time to time - Note that this is a fori loop, not a for each
	 *
	 * @param world The world to get the entities from
	 * @see {@link net.minecraft.world.World#getEntities(Class, com.google.common.base.Predicate)}
	 */
	public static ArrayList<EntityScarecrow> getLoadedScarecrows(World world, Predicate <? super EntityScarecrow> filter)
	{
		ArrayList<EntityScarecrow> list = Lists.<EntityScarecrow>newArrayList();

		for(int i = 0; i < world.loadedEntityList.size(); i++)
		{
			if(EntityScarecrow.class.isAssignableFrom(world.loadedEntityList.get(i).getClass()) && filter.apply((EntityScarecrow)world.loadedEntityList.get(i)))
				list.add((EntityScarecrow)world.loadedEntityList.get(i));
		}

		return list;
	}

	/**
	 * @see {@link net.minecraft.entity.ai.RandomPositionGenerator}
	 */
	private static BlockPos moveAboveSolid(BlockPos pos, EntityLiving entity)
	{
		if(!entity.world.getBlockState(pos).getMaterial().isSolid())
			return pos;
		else
		{
			BlockPos blockpos;

			for(blockpos = pos.up(); blockpos.getY() < entity.world.getHeight() && entity.world.getBlockState(blockpos).getMaterial().isSolid(); blockpos = blockpos.up())
			{
				;
			}

			return blockpos;
		}
	}

	/**
	 * @see {@link net.minecraft.entity.ai.RandomPositionGenerator}
	 */
	private static boolean isWaterDestination(BlockPos pos, EntityLiving entity)
	{
		return entity.world.getBlockState(pos).getMaterial() == Material.WATER;
	}
}
