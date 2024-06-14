package bl4ckscor3.mod.scarecrows.datagen;

import java.util.Set;

import bl4ckscor3.mod.scarecrows.Scarecrows;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredHolder;

public class BlockLootTableGenerator extends BlockLootSubProvider {
	protected BlockLootTableGenerator(HolderLookup.Provider lookupProvider) {
		super(Set.of(), FeatureFlags.REGISTRY.allFlags(), lookupProvider);
	}

	@Override
	public void generate() {
		dropOther(Scarecrows.ARM.get(), Items.STICK);
	}

	@Override
	protected Iterable<Block> getKnownBlocks() {
		return (Iterable<Block>) Scarecrows.BLOCKS.getEntries().stream().map(DeferredHolder::get).toList();
	}
}
