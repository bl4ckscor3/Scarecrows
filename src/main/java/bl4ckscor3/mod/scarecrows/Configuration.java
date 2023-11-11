package bl4ckscor3.mod.scarecrows;

import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.ModConfigSpec.BooleanValue;
import net.neoforged.neoforge.common.ModConfigSpec.IntValue;
import org.apache.commons.lang3.tuple.Pair;

public class Configuration {
	public static final ModConfigSpec CONFIG_SPEC;
	public static final Configuration CONFIG;
	public final IntValue spoopyRange;
	public final BooleanValue spoopyScareAnimals;
	public final IntValue superSpoopyRange;
	public final BooleanValue superSpoopyScareAnimals;
	public final IntValue spookyRange;
	public final BooleanValue spookyScareAnimals;
	public final IntValue superSpookyRange;
	public final BooleanValue superSpookyScareAnimals;
	public final IntValue scaryRange;
	public final BooleanValue scaryScareAnimals;
	public final IntValue superScaryRange;
	public final BooleanValue superScaryScareAnimals;

	static {
		Pair<Configuration, ModConfigSpec> specPair = new ModConfigSpec.Builder().configure(Configuration::new);

		CONFIG_SPEC = specPair.getRight();
		CONFIG = specPair.getLeft();
	}

	Configuration(ModConfigSpec.Builder builder) {
		//@formatter:off
		builder.comment("Spoopy Scarecrow Configuration")
		.push("spoopy");
		spoopyRange = builder
				.comment("The effect range of this scarecrow")
				.defineInRange("range", 10, 0, Integer.MAX_VALUE);
		spoopyScareAnimals = builder
				.comment("false if this scarecrow only scares monsters, true if animals should be affected additionally")
				.define("scareAnimals", false);
		builder.pop();

		builder.comment("Super Spoopy Scarecrow Configuration")
		.push("super_spoopy");
		superSpoopyRange = builder
				.comment("The effect range of this scarecrow")
				.defineInRange("range", 10, 0, Integer.MAX_VALUE);
		superSpoopyScareAnimals = builder
				.comment("false if this scarecrow only scares monsters, true if animals should be affected additionally")
				.define("scareAnimals", true);
		builder.pop();

		builder.comment("Spooky Scarecrow Configuration")
		.push("spooky");
		spookyRange = builder
				.comment("The effect range of this scarecrow")
				.defineInRange("range", 25, 0, Integer.MAX_VALUE);
		spookyScareAnimals = builder
				.comment("false if this scarecrow only scares monsters, true if animals should be affected additionally")
				.define("scareAnimals", false);
		builder.pop();

		builder.comment("Super Spooky Scarecrow Configuration")
		.push("super_spooky");
		superSpookyRange = builder
				.comment("The effect range of this scarecrow")
				.defineInRange("range", 25, 0, Integer.MAX_VALUE);
		superSpookyScareAnimals = builder
				.comment("false if this scarecrow only scares monsters, true if animals should be affected additionally")
				.define("scareAnimals", true);
		builder.pop();

		builder.comment("Scary Scarecrow Configuration")
		.push("scary");
		scaryRange = builder
				.comment("The effect range of this scarecrow")
				.defineInRange("range", 50, 0, Integer.MAX_VALUE);
		scaryScareAnimals = builder
				.comment("false if this scarecrow only scares monsters, true if animals should be affected additionally")
				.define("scareAnimals", false);
		builder.pop();

		builder.comment("Super Scary Scarecrow Configuration")
		.push("super_scary");
		superScaryRange = builder
				.comment("The effect range of this scarecrow")
				.defineInRange("range", 50, 0, Integer.MAX_VALUE);
		superScaryScareAnimals = builder
				.comment("false if this scarecrow only scares monsters, true if animals should be affected additionally")
				.define("scareAnimals", true);
		builder.pop();
		//@formatter:on
	}
}
