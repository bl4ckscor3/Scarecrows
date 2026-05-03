package bl4ckscor3.mod.scarecrows;

import bl4ckscor3.mod.scarecrows.handler.PlaceHandler;
import bl4ckscor3.mod.scarecrows.handler.SpawnHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.EntityLeaveLevelEvent;
import net.neoforged.neoforge.event.entity.living.FinalizeSpawnEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.neoforged.neoforge.event.level.BlockEvent.EntityPlaceEvent;

@EventBusSubscriber
public class NeoPlaceAndSpawnHandler {
	private NeoPlaceAndSpawnHandler() {}

	@SubscribeEvent
	public static void onRightClickBlock(RightClickBlock event) {
		Level level = event.getLevel();
		BlockPos pos = event.getPos();
		InteractionResult result = PlaceHandler.tryBuildScarecrow(level, pos, level.getBlockState(pos));

		if (result == InteractionResult.PASS) {
			result = PlaceHandler.tryPlaceStick(event.getItemStack(), pos, event.getFace(), level, event.getEntity(), event.getHand());
		}

		if (result != InteractionResult.PASS) {
			event.setCancellationResult(result);
			event.setCanceled(true);
		}
	}

	@SubscribeEvent
	public static void onPlace(EntityPlaceEvent event) {
		PlaceHandler.tryBuildScarecrow(event.getLevel(), event.getPos(), event.getPlacedBlock());
	}

	@SubscribeEvent
	public static void onCheckSpawn(FinalizeSpawnEvent event) {
		if (event.getLevel() instanceof Level level && SpawnHandler.shouldDisallowSpawn(level, event.getEntity(), event.getSpawnType()))
			event.setSpawnCancelled(true);
	}

	@SubscribeEvent
	public static void onEntityJoinLevel(EntityJoinLevelEvent event) {
		Entity entity = event.getEntity();

		if (entity instanceof Mob mob)
			SpawnHandler.addRunAwayGoal(mob, mob.goalSelector);

		ScarecrowTracker.track(entity);
	}

	@SubscribeEvent
	public static void onEntityLeaveLevel(EntityLeaveLevelEvent event) {
		ScarecrowTracker.stopTracking(event.getEntity());
	}
}
