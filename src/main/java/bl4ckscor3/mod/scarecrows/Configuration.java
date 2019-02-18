package bl4ckscor3.mod.scarecrows;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;

public class Configuration
{
	public static final ForgeConfigSpec CONFIG_SPEC;
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

	static
	{
		Pair<Configuration,ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Configuration::new);

		CONFIG_SPEC = specPair.getRight();
		CONFIG = specPair.getLeft();
	}

	Configuration(ForgeConfigSpec.Builder builder)
	{
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
		.push("spoopy");
		superSpoopyRange = builder
				.comment("The effect range of this scarecrow")
				.defineInRange("range", 10, 0, Integer.MAX_VALUE);
		superSpoopyScareAnimals = builder
				.comment("false if this scarecrow only scares monsters, true if animals should be affected additionally")
				.define("scareAnimals", true);
		builder.pop();

		builder.comment("Spooky Scarecrow Configuration")
		.push("spoopy");
		spookyRange = builder
				.comment("The effect range of this scarecrow")
				.defineInRange("range", 25, 0, Integer.MAX_VALUE);
		spookyScareAnimals = builder
				.comment("false if this scarecrow only scares monsters, true if animals should be affected additionally")
				.define("scareAnimals", false);
		builder.pop();

		builder.comment("Super Spooky Scarecrow Configuration")
		.push("spoopy");
		superSpookyRange = builder
				.comment("The effect range of this scarecrow")
				.defineInRange("range", 25, 0, Integer.MAX_VALUE);
		superSpookyScareAnimals = builder
				.comment("false if this scarecrow only scares monsters, true if animals should be affected additionally")
				.define("scareAnimals", true);
		builder.pop();

		builder.comment("Scary Scarecrow Configuration")
		.push("spoopy");
		scaryRange = builder
				.comment("The effect range of this scarecrow")
				.defineInRange("range", 50, 0, Integer.MAX_VALUE);
		scaryScareAnimals = builder
				.comment("false if this scarecrow only scares monsters, true if animals should be affected additionally")
				.define("scareAnimals", false);
		builder.pop();

		builder.comment("Super Scary Scarecrow Configuration")
		.push("spoopy");
		superScaryRange = builder
				.comment("The effect range of this scarecrow")
				.defineInRange("range", 50, 0, Integer.MAX_VALUE);
		superScaryScareAnimals = builder
				.comment("false if this scarecrow only scares monsters, true if animals should be affected additionally")
				.define("scareAnimals", true);
		builder.pop();
	}
}
