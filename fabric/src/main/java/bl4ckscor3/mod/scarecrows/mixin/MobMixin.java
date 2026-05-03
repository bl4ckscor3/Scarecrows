package bl4ckscor3.mod.scarecrows.mixin;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import bl4ckscor3.mod.scarecrows.handler.SpawnHandler;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.GoalSelector;
import net.minecraft.world.level.Level;

@Mixin(Mob.class)
public class MobMixin {
	@Shadow
	@Final
	protected GoalSelector goalSelector;

	@Inject(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Mob;registerGoals()V"))
	private void addRunAwayGoal(EntityType<?> type, Level level, CallbackInfo ci) {
		SpawnHandler.addRunAwayGoal((Mob) (Object) this, goalSelector);
	}
}
