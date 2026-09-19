package bl4ckscor3.mod.scarecrows.datagen;

import java.util.concurrent.CompletableFuture;

import bl4ckscor3.mod.scarecrows.Scarecrows;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.VanillaBlockTagsProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;

public class BlockTagGenerator extends VanillaBlockTagsProvider {
	public BlockTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
		super(output, lookupProvider);
	}

	@Override
	protected void addTags(HolderLookup.Provider provider) {
		tag(BlockTags.WASHED_AWAY_BY_FLUIDS).add(ResourceKey.create(Registries.BLOCK, Scarecrows.ARM.id()));
	}
}