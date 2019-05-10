package bl4ckscor3.mod.scarecrows.handler;

import bl4ckscor3.mod.scarecrows.Scarecrows;
import bl4ckscor3.mod.scarecrows.block.BlockArm;
import bl4ckscor3.mod.scarecrows.type.ScarecrowType;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCarvedPumpkin;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.event.world.BlockEvent.EntityPlaceEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid=Scarecrows.MODID)
public class PlaceHandler
{
	@SubscribeEvent
	public static void onRightClickBlock(RightClickBlock event) //stick placement logic
	{
		ItemStack held = event.getItemStack();

		if(held.getItem() == Items.STICK)
		{
			BlockPos pos = event.getPos();
			EnumFacing face = event.getFace();
			BlockPos placeAt = pos.offset(face);
			World world = event.getWorld();

			if(face != EnumFacing.UP && face != EnumFacing.DOWN && Scarecrows.ARM.canBeConnectedTo(world.getBlockState(placeAt), world, placeAt, face) && world.isAirBlock(placeAt))
			{
				world.setBlockState(placeAt, Scarecrows.ARM.getDefaultState().with(BlockArm.FACING, face));
				world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundType.WOOD.getPlaceSound(), SoundCategory.BLOCKS, 1.0F, 1.0F);
				event.getEntityPlayer().swingArm(event.getHand());

				if(!event.getEntityPlayer().isCreative())
					held.shrink(1);
			}
		}
	}

	@SubscribeEvent
	public static void onPlace(EntityPlaceEvent event)
	{
		tryBuildScarecrow(event.getWorld(), event.getPos(), event.getPlacedBlock());
	}

	@SubscribeEvent
	public static void onRightClick(RightClickBlock event) //scarecrow structure logic
	{
		tryBuildScarecrow(event.getWorld(), event.getPos(), event.getWorld().getBlockState(event.getPos()));
	}

	/**
	 * Tries to build a scarecrow
	 * @param world The world to build the scarecrow in
	 * @param pos The position of the block that was placed/rightclicked
	 * @param state The state of the block that was placed/rightclicked
	 */
	private static void tryBuildScarecrow(IWorld world, BlockPos pos, IBlockState state)
	{
		Block block = state.getBlock();

		if(block == Blocks.CARVED_PUMPKIN || block == Blocks.JACK_O_LANTERN) //structure only ever activates when placing a carved pumpkin or jack o lantern
		{
			for(ScarecrowType type : ScarecrowType.TYPES)
			{
				EnumFacing pumpkinFacing = state.get(BlockCarvedPumpkin.FACING);
				BlockPos groundPos = pos.down(type.getHeight());
				IBlockState groundState = world.getBlockState(groundPos);

				if(!groundState.getBlock().isAir(groundState, world, groundPos) && type.checkStructure(world, pos, pumpkinFacing))
				{
					type.destroy(world, pos);
					type.spawn(type, world, pos.down(type.getHeight() - 1), block == Blocks.JACK_O_LANTERN, pumpkinFacing); //-1 because of the feet
					return;
				}
			}
		}
	}
}
