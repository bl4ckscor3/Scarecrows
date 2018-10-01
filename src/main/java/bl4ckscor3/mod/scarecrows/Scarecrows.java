package bl4ckscor3.mod.scarecrows;

import bl4ckscor3.mod.scarecrows.block.BlockArm;
import net.minecraft.block.Block;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;

@Mod(modid=Scarecrows.MODID, name=Scarecrows.NAME, version=Scarecrows.VERSION, acceptedMinecraftVersions=Scarecrows.MC_VERSION)
@EventBusSubscriber
public class Scarecrows
{
	public static final String MODID = "scarecrows";
	public static final String NAME = "Scarecrows";
	public static final String VERSION = "v1.0";
	public static final String MC_VERSION = "1.12";
	public static final String PREFIX = MODID + ":";

	@ObjectHolder(Scarecrows.PREFIX + BlockArm.NAME)
	public static final Block ARM = null;

	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event)
	{
		event.getRegistry().register(new BlockArm());
	}
}
