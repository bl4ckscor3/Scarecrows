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
	public static void onPlace(RightClickBlock event) //scarecrow structure logic
	{
		IWorld world = event.getWorld();
		BlockPos pos = event.getPos();
		Block block = world.getBlockState(pos).getBlock();

		if(block == Blocks.CARVED_PUMPKIN || block == Blocks.JACK_O_LANTERN) //structure only ever activates when placing a carved pumpkin or jack o lantern
		{
			for(ScarecrowType type : ScarecrowType.TYPES)
			{
				EnumFacing pumpkinFacing = world.getBlockState(pos).get(BlockCarvedPumpkin.FACING);
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
