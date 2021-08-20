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
		PathNavigator pathnavigate = entity.getNavigation();
		Random random = entity.getRandom();
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
				BlockPos blockpos1 = new BlockPos(l + entity.getX(), i1 + entity.getY(), j1 + entity.getZ());

				if(!flag && pathnavigate.isStableDestination(blockpos1))
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
			return new Vector3d(k1 + entity.getX(), i + entity.getY(), j + entity.getZ());
		else
			return null;
	}

	/**
	 * @see {@link net.minecraft.entity.ai.RandomPositionGenerator}
	 */
	private static BlockPos moveAboveSolid(BlockPos pos, MobEntity entity)
	{
		if(!entity.level.getBlockState(pos).getMaterial().isSolid())
			return pos;
		else
		{
			BlockPos blockpos;

			for(blockpos = pos.above(); blockpos.getY() < entity.level.getMaxBuildHeight() && entity.level.getBlockState(blockpos).getMaterial().isSolid(); blockpos = blockpos.above())
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
		return entity.level.getBlockState(pos).getMaterial() == Material.WATER;
	}
}
