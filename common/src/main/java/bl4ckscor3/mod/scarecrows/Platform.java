package bl4ckscor3.mod.scarecrows;

import java.util.function.Supplier;

import net.minecraft.core.Registry;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.resources.ResourceKey;

public interface Platform {
	<R, T extends R> void register(ResourceKey<? extends Registry<R>> registry, Supplier<T> entry, String path);

	<T> void registerEntityDataSerializer(Supplier<EntityDataSerializer<T>> serializer, String path);

	default <R, T extends R> void register(ResourceKey<? extends Registry<R>> registry, RegistryObject<T> registryObject) {
		register(registry, registryObject.object(), registryObject.id().getPath());
	}
}
