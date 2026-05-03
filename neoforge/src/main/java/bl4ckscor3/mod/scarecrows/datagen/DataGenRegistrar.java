package bl4ckscor3.mod.scarecrows.datagen;

import java.util.List;
import java.util.Set;

import bl4ckscor3.mod.scarecrows.Scarecrows;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.loot.LootTableProvider.SubProviderEntry;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

@EventBusSubscriber(modid = Scarecrows.MODID)
public class DataGenRegistrar {
	private DataGenRegistrar() {}

	@SubscribeEvent
	public static void onGatherData(GatherDataEvent.Client event) {
		event.createProvider((output, _) -> new LootTableProvider(output, Set.of(), List.of(new SubProviderEntry(lp -> new BlockLootTableGenerator(lp) {
			@Override
			protected Iterable<Block> getKnownBlocks() {
				return List.of(Scarecrows.ARM.get());
			}
		}, LootContextParamSets.BLOCK)), event.getLookupProvider()));
	}
}
