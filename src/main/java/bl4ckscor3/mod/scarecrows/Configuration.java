package bl4ckscor3.mod.scarecrows;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Comment;

@Config(modid=Scarecrows.MODID)
public class Configuration
{
	public static Spoopy spoopy_scarecrow = new Spoopy();
	public static SuperSpoopy super_spoopy_scarecrow = new SuperSpoopy();
	public static Spooky spooky_scarecrow = new Spooky();
	public static SuperSpooky super_spooky_scarecrow = new SuperSpooky();
	public static Scary scary_scarecrow = new Scary();
	public static SuperScary super_scary_scarecrow = new SuperScary();

	public static class Spoopy
	{
		@Comment("The effect range of this scarecrow")
		public int RANGE = 10;

		@Comment("false if this scarecrow only scares monsters, true if animals should be affected additionally")
		public boolean SCARE_ANIMALS = false;
	}

	public static class SuperSpoopy
	{
		@Comment("The effect range of this scarecrow")
		public int RANGE = 10;

		@Comment("false if this scarecrow only scares monsters, true if animals should be affected additionally")
		public boolean SCARE_ANIMALS = true;
	}

	public static class Spooky
	{
		@Comment("The effect range of this scarecrow")
		public int RANGE = 25;

		@Comment("false if this scarecrow only scares monsters, true if animals should be affected additionally")
		public boolean SCARE_ANIMALS = false;
	}

	public static class SuperSpooky
	{
		@Comment("The effect range of this scarecrow")
		public int RANGE = 25;

		@Comment("false if this scarecrow only scares monsters, true if animals should be affected additionally")
		public boolean SCARE_ANIMALS = true;
	}

	public static class Scary
	{
		@Comment("The effect range of this scarecrow")
		public int RANGE = 50;

		@Comment("false if this scarecrow only scares monsters, true if animals should be affected additionally")
		public boolean SCARE_ANIMALS = false;
	}

	public static class SuperScary
	{
		@Comment("The effect range of this scarecrow")
		public int RANGE = 50;

		@Comment("false if this scarecrow only scares monsters, true if animals should be affected additionally")
		public boolean SCARE_ANIMALS = true;
	}
}
