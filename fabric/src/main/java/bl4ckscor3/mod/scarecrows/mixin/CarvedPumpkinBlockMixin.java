package bl4ckscor3.mod.scarecrows.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;

import bl4ckscor3.mod.scarecrows.handler.PlaceHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CarvedPumpkinBlock;
import net.minecraft.world.level.block.state.BlockState;

@Mixin(CarvedPumpkinBlock.class)
public class CarvedPumpkinBlockMixin {
	@WrapOperation(method = "onPlace", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/CarvedPumpkinBlock;trySpawnGolem(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;)V"))
	public void tryPlaceScarecrow(CarvedPumpkinBlock instance, Level level, BlockPos topPos, Operation<Void> original, BlockState state) {
		InteractionResult result = PlaceHandler.tryBuildScarecrow(level, topPos, state);

		if (result == InteractionResult.PASS) {
			original.call(instance, level, topPos);
		}
	}
}
