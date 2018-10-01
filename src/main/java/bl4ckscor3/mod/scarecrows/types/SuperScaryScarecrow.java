package bl4ckscor3.mod.scarecrows.types;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SuperScaryScarecrow implements IScarecrowType
{
	@Override
	public boolean checkStructure(World world, BlockPos pos)
	{
		return false;
	}

	@Override
	public void spawn(World world, BlockPos pos)
	{}
}
