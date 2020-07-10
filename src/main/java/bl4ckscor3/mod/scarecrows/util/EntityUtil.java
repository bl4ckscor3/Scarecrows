package bl4ckscor3.mod.scarecrows.util;

import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.monster.GhastEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.monster.ShulkerEntity;
import net.minecraft.entity.monster.SlimeEntity;
import net.minecraft.entity.passive.AmbientEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.SquidEntity;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;

public class EntityUtil
{
	/**
	 * Checks whether a given entity is a monster (Zombie, Creeper, ...) that a scarecrow would attack
	 * @param entity The entity to check
	 * @return true if a scarecrow would attack the given entity, false otherwise
	 */
	public static boolean isAttackableMonster(Entity entity)
	{
		return entity instanceof MonsterEntity || entity instanceof EnderDragonEntity || entity instanceof GhastEntity || entity instanceof ShulkerEntity || entity instanceof SlimeEntity;
	}

	/**
	 * Checks whether a given entity is an animal (Squid, Sheep, ...) that a scarecrow would attack
	 * @param entity The entity to check
	 * @return true if a scarecrow would attack the given entity, false otherwise
	 */
	public static boolean isAttackableAnimal(Entity entity)
	{
		return entity instanceof AmbientEntity || entity instanceof AnimalEntity || entity instanceof SquidEntity;
	}

	/**
	 * @see {@link net.minecraft.entity.ai.RandomPositionGenerator}
	 */
	public static Vector3d generateRandomPos(MobEntity entity, int xz, int y, @Nullable Vector3d target, boolean b)
	{
		PathNavigator pathnavigate = entity.getNavigator();
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
				BlockPos blockpos1 = new BlockPos(l + entity.getPosX(), i1 + entity.getPosY(), j1 + entity.getPosZ());

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
			return new Vector3d(k1 + entity.getPosX(), i + entity.getPosY(), j + entity.getPosZ());
		else
			return null;
	}

	/**
	 * @see {@link net.minecraft.entity.ai.RandomPositionGenerator}
	 */
	private static BlockPos moveAboveSolid(BlockPos pos, MobEntity entity)
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
	private static boolean isWaterDestination(BlockPos pos, MobEntity entity)
	{
		return entity.world.getBlockState(pos).getMaterial() == Material.WATER;
	}
}
