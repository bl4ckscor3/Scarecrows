package bl4ckscor3.mod.scarecrows.handler;

import bl4ckscor3.mod.scarecrows.Scarecrows;
import bl4ckscor3.mod.scarecrows.block.BlockArm;
import net.minecraft.block.SoundType;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber
public class PlaceHandler
{
	@SubscribeEvent
	public static void onRightClickBlock(RightClickBlock event)
	{
		ItemStack held = event.getEntityPlayer().getHeldItem(event.getHand());

		if(held.getItem() == Items.STICK)
		{
			EnumFacing face = event.getFace();
			BlockPos pos = event.getPos();

			if(face != EnumFacing.UP && face != EnumFacing.DOWN && event.getWorld().getBlockState(pos.offset(face)).getBlock() == Blocks.AIR)
			{
				event.getWorld().setBlockState(pos.offset(face), Scarecrows.ARM.getDefaultState().withProperty(BlockArm.FACING, face.getOpposite()));
				event.getWorld().playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundType.WOOD.getPlaceSound(), SoundCategory.BLOCKS, 1.0F, 1.0F);
				event.getEntityPlayer().swingArm(event.getHand());

				if(!event.getEntityPlayer().isCreative())
					held.shrink(1);
			}
		}
	}
}
