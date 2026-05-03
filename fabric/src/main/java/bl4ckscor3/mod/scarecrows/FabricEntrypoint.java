package bl4ckscor3.mod.scarecrows;

import java.util.Optional;
import java.util.function.Supplier;

import bl4ckscor3.mod.scarecrows.handler.PlaceHandler;
import bl4ckscor3.mod.scarecrows.handler.SpawnHandler;
import fuzs.forgeconfigapiport.fabric.api.v5.ConfigRegistry;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityDataRegistry;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.resources.ResourceKey;
import net.neoforged.fml.config.ModConfig;

public class FabricEntrypoint implements ModInitializer, Platform {
	@Override
	public void onInitialize() {
		Scarecrows.initialize(this);
		ConfigRegistry.INSTANCE.register(Scarecrows.MODID, ModConfig.Type.COMMON, Configuration.CONFIG_SPEC);
		Scarecrows.registerTypes();
		UseBlockCallback.EVENT.register((player, level, hand, hitResult) -> PlaceHandler.tryPlaceStick(player.getItemInHand(hand), hitResult.getBlockPos(), hitResult.getDirection(), level, player, hand));
		ServerEntityEvents.ENTITY_LOAD.register((entity, _) -> ScarecrowTracker.track(entity));
		ServerEntityEvents.ENTITY_UNLOAD.register((entity, _) -> ScarecrowTracker.stopTracking(entity));
		ServerEntityEvents.ALLOW_LOAD.register((entity, level, spawnReason, _) -> !SpawnHandler.shouldDisallowSpawn(level, entity, spawnReason));
	}

	@Override
	@SuppressWarnings({"rawtypes", "unchecked"})
	public <R, T extends R> void register(ResourceKey<? extends Registry<R>> registryKey, Supplier<T> entry, String path) {
		Optional<Holder.Reference<R>> registry = BuiltInRegistries.REGISTRY.get((ResourceKey) registryKey);

		if (registry.isEmpty()) {
			throw new IllegalArgumentException("Couldn't find registry " + registryKey);
		}

		Registry.register((Registry<R>) registry.get().value(), Scarecrows.id(path), entry.get());
	}

	@Override
	public <T> void registerEntityDataSerializer(Supplier<EntityDataSerializer<T>> serializer, String path) {
		FabricEntityDataRegistry.register(Scarecrows.id(path), serializer.get());
	}
}
