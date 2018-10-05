package bl4ckscor3.mod.scarecrows.handler;

import bl4ckscor3.mod.scarecrows.Scarecrows;
import bl4ckscor3.mod.scarecrows.block.BlockArm;
import bl4ckscor3.mod.scarecrows.types.ScarecrowType;
import bl4ckscor3.mod.scarecrows.types.ScaryScarecrow;
import bl4ckscor3.mod.scarecrows.types.SpookyScarecrow;
import bl4ckscor3.mod.scarecrows.types.SpoopyScarecrow;
import bl4ckscor3.mod.scarecrows.types.SuperScaryScarecrow;
import bl4ckscor3.mod.scarecrows.types.SuperSpookyScarecrow;
import bl4ckscor3.mod.scarecrows.types.SuperSpoopyScarecrow;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.event.world.BlockEvent.PlaceEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber
public class PlaceHandler
{
	private static final ScarecrowType[] TYPES = new ScarecrowType[] {
			new SpoopyScarecrow(),
			new SuperSpoopyScarecrow(),
			new SpookyScarecrow(),
			new SuperSpookyScarecrow(),
			new ScaryScarecrow(),
			new SuperScaryScarecrow()
	};

	@SubscribeEvent
	public static void onRightClickBlock(RightClickBlock event) //stick placement logic
	{
		ItemStack held = event.getItemStack();

		if(held.getItem() == Items.STICK)
		{
			BlockPos pos = event.getPos();
			EnumFacing face = event.getFace();

			if(face != EnumFacing.UP && face != EnumFacing.DOWN && event.getWorld().getBlockState(pos.offset(face)).getBlock() == Blocks.AIR)
			{
				event.getWorld().setBlockState(pos.offset(face), Scarecrows.ARM.getDefaultState().withProperty(BlockArm.FACING, face));
				event.getWorld().playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundType.WOOD.getPlaceSound(), SoundCategory.BLOCKS, 1.0F, 1.0F);
				event.getEntityPlayer().swingArm(event.getHand());

				if(!event.getEntityPlayer().isCreative())
					held.shrink(1);
			}
		}
	}

	@SubscribeEvent
	public static void onPlace(PlaceEvent event) //scarecrow structure logic
	{
		World world = event.getWorld();
		BlockPos pos = event.getPos();

		for(ScarecrowType type : TYPES)
		{
			Block block = event.getPlacedBlock().getBlock();

			if(block == Blocks.PUMPKIN || block == Blocks.LIT_PUMPKIN) //structure only ever activates when placing a pumpkin or jack o lantern
			{
				boolean isLit = block == Blocks.LIT_PUMPKIN;

				if((isLit ? world.getBlockState(pos.up()).getBlock() == Blocks.AIR : true) && type.checkStructure(world, pos)) //check for empty space to place the light TODO: place where jackolantern was?
					type.spawn(world, pos.down(type.getHeight() - 1), isLit); //-1 because of the feet
			}
		}
	}
}
