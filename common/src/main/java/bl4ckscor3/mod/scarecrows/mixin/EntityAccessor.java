package bl4ckscor3.mod.scarecrows.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.world.entity.Entity;

@Mixin(Entity.class)
public interface EntityAccessor {
	@Invoker
	void callSpawnSprintParticle();
}
