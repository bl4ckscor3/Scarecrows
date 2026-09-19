package bl4ckscor3.mod.scarecrows.datagen;

import java.util.Set;

import bl4ckscor3.mod.scarecrows.Scarecrows;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;

public class BlockLootTableGenerator extends BlockLootSubProvider {
	protected BlockLootTableGenerator(LootTableSubProvider.Context output) {
		super(Set.of(), FeatureFlags.REGISTRY.allFlags(), output);
	}

	@Override
	public void generate() {
		dropOther(Scarecrows.ARM.get(), Items.STICK);
	}
}
