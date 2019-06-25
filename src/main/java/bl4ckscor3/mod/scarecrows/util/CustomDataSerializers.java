package bl4ckscor3.mod.scarecrows.util;

import bl4ckscor3.mod.scarecrows.type.ScarecrowType;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.IDataSerializer;
import net.minecraft.util.math.AxisAlignedBB;

public class CustomDataSerializers
{
	public static final IDataSerializer<ScarecrowType> SCARECROWTYPE = new IDataSerializer<ScarecrowType>()
	{
		@Override
		public void func_187160_a(PacketBuffer buf, ScarecrowType value)
		{
			buf.writeString(value.getName());
		}

		@Override
		public ScarecrowType func_187159_a(PacketBuffer buf)
		{
			String bufferedName = buf.readString(Integer.MAX_VALUE / 4);

			for(ScarecrowType type : ScarecrowType.TYPES)
			{
				if(type.getName().equals(bufferedName))
					return type;
			}

			return null;
		}

		@Override
		public DataParameter<ScarecrowType> createKey(int id)
		{
			return new DataParameter<ScarecrowType>(id, this);
		}

		@Override
		public ScarecrowType func_192717_a(ScarecrowType value)
		{
			return value;
		}
	};

	public static final IDataSerializer<AxisAlignedBB> AXISALIGNEDBB = new IDataSerializer<AxisAlignedBB>()
	{
		@Override
		public void func_187160_a(PacketBuffer buf, AxisAlignedBB value)
		{
			buf.writeDouble(value.minX);
			buf.writeDouble(value.minY);
			buf.writeDouble(value.minZ);
			buf.writeDouble(value.maxX);
			buf.writeDouble(value.maxY);
			buf.writeDouble(value.maxZ);
		}

		@Override
		public AxisAlignedBB func_187159_a(PacketBuffer buf)
		{
			return new AxisAlignedBB(buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readDouble());
		}

		@Override
		public DataParameter<AxisAlignedBB> createKey(int id)
		{
			return new DataParameter<AxisAlignedBB>(id, this);
		}

		@Override
		public AxisAlignedBB func_192717_a(AxisAlignedBB value)
		{
			return value.grow(0);
		}
	};
}
