package bl4ckscor3.mod.scarecrows.handler;

import bl4ckscor3.mod.scarecrows.Scarecrows;
import bl4ckscor3.mod.scarecrows.block.BlockArm;
import bl4ckscor3.mod.scarecrows.type.ScarecrowType;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPumpkin;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
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

		for(ScarecrowType type : ScarecrowType.TYPES)
		{
			Block block = event.getPlacedBlock().getBlock();

			if(block == Blocks.PUMPKIN || block == Blocks.LIT_PUMPKIN) //structure only ever activates when placing a pumpkin or jack o lantern
			{
				EnumFacing pumpkinFacing = event.getState().getValue(BlockPumpkin.FACING);
				BlockPos groundPos = pos.down(type.getHeight());
				IBlockState groundState = world.getBlockState(groundPos);

				if(!groundState.getBlock().isAir(groundState, world, groundPos) && type.checkStructure(world, pos, pumpkinFacing))
				{
					type.destroy(world, pos);
					type.spawn(type, world, pos.down(type.getHeight() - 1), block == Blocks.LIT_PUMPKIN, pumpkinFacing); //-1 because of the feet
				}
			}
		}
	}
}
