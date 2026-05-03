package bl4ckscor3.mod.scarecrows;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import net.minecraft.core.Registry;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.resources.ResourceKey;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

@Mod(Scarecrows.MODID)
@EventBusSubscriber
public class NeoEntrypoint implements Platform {
	private final Map<ResourceKey<? extends Registry<?>>, DeferredRegister<?>> registers = new HashMap<>();
	private final IEventBus modBus;

	public NeoEntrypoint(ModContainer modContainer, IEventBus modBus) {
		this.modBus = modBus;
		Scarecrows.initialize(this);
		modContainer.registerConfig(ModConfig.Type.COMMON, Configuration.CONFIG_SPEC);
	}

	@SubscribeEvent
	public static void onFMLCommonSetup(FMLCommonSetupEvent event) {
		Scarecrows.registerTypes();
	}

	@Override
	public <R, T extends R> void register(ResourceKey<? extends Registry<R>> registry, Supplier<T> entry, String path) {
		@SuppressWarnings("unchecked")
		DeferredRegister<R> register = (DeferredRegister<R>) registers.computeIfAbsent(
			registry,
			_ -> {
				DeferredRegister<R> r = DeferredRegister.create(registry, Scarecrows.MODID);

				r.register(modBus);
				return r;
			}
		);
		register.register(path, entry);
	}

	@Override
	public <T> void registerEntityDataSerializer(Supplier<EntityDataSerializer<T>> serializer, String path) {
		register(NeoForgeRegistries.Keys.ENTITY_DATA_SERIALIZERS, serializer, path);
	}
}
