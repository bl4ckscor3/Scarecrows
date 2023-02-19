package bl4ckscor3.mod.scarecrows.util;

import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ambient.AmbientCreature;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Squid;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.monster.Ghast;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Shulker;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.Vec3;

public class EntityUtil {
	/**
	 * Checks whether a given entity is a monster (Zombie, Creeper, ...) that a scarecrow would attack
	 *
	 * @param entity The entity to check
	 * @return true if a scarecrow would attack the given entity, false otherwise
	 */
	public static boolean isAttackableMonster(Entity entity) {
		return entity instanceof Monster || entity instanceof EnderDragon || entity instanceof Ghast || entity instanceof Shulker || entity instanceof Slime;
	}

	/**
	 * Checks whether a given entity is an animal (Squid, Sheep, ...) that a scarecrow would attack
	 *
	 * @param entity The entity to check
	 * @return true if a scarecrow would attack the given entity, false otherwise
	 */
	public static boolean isAttackableAnimal(Entity entity) {
		return entity instanceof AmbientCreature || entity instanceof Animal || entity instanceof Squid;
	}

	/**
	 * @see {@link net.minecraft.world.entity.ai.util.RandomPos}
	 */
	public static Vec3 generateRandomPos(Mob entity, int xz, int y, @Nullable Vec3 target, boolean b) {
		PathNavigation pathnavigate = entity.getNavigation();
		Random random = entity.getRandom();
		boolean flag = false;
		boolean flag1 = false;
		int k1 = 0;
		int i = 0;
		int j = 0;

		for (int k = 0; k < 10; ++k) {
			int l = random.nextInt(2 * xz + 1) - xz;
			int i1 = random.nextInt(2 * y + 1) - y;
			int j1 = random.nextInt(2 * xz + 1) - xz;

			if (target == null || l * target.x + j1 * target.z >= 0.0D) {
				BlockPos blockpos1 = new BlockPos(l + entity.getX(), i1 + entity.getY(), j1 + entity.getZ());

				if (!flag && pathnavigate.isStableDestination(blockpos1)) {
					if (!b) {
						blockpos1 = moveAboveSolid(blockpos1, entity);

						if (isWaterDestination(blockpos1, entity))
							continue;
					}

					k1 = l;
					i = i1;
					j = j1;
					flag1 = true;
				}
			}
		}

		if (flag1)
			return new Vec3(k1 + entity.getX(), i + entity.getY(), j + entity.getZ());
		else
			return null;
	}

	/**
	 * @see {@link net.minecraft.world.entity.ai.util.RandomPos}
	 */
	private static BlockPos moveAboveSolid(BlockPos pos, Mob entity) {
		if (!entity.level.getBlockState(pos).getMaterial().isSolid())
			return pos;
		else {
			BlockPos blockpos;

			for (blockpos = pos.above(); blockpos.getY() < entity.level.getMaxBuildHeight() && entity.level.getBlockState(blockpos).getMaterial().isSolid(); blockpos = blockpos.above()) {
			}

			return blockpos;
		}
	}

	/**
	 * @see {@link net.minecraft.world.entity.ai.util.RandomPos}
	 */
	private static boolean isWaterDestination(BlockPos pos, Mob entity) {
		return entity.level.getBlockState(pos).getMaterial() == Material.WATER;
	}
}
