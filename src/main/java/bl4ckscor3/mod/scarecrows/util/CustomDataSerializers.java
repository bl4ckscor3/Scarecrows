package bl4ckscor3.mod.scarecrows.util;

import bl4ckscor3.mod.scarecrows.type.ScarecrowType;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializer;
import net.minecraft.util.math.AxisAlignedBB;

public class CustomDataSerializers
{
	public static final DataSerializer<ScarecrowType> SCARECROWTYPE = new DataSerializer<ScarecrowType>()
	{
		@Override
		public void write(PacketBuffer buf, ScarecrowType value)
		{
			buf.writeString(value.getName());
		}

		@Override
		public ScarecrowType read(PacketBuffer buf)
		{
			String bufferedName = buf.readString(Integer.MAX_VALUE);

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
		public ScarecrowType copyValue(ScarecrowType value)
		{
			return value;
		}
	};

	public static final DataSerializer<AxisAlignedBB> AXISALIGNEDBB = new DataSerializer<AxisAlignedBB>()
	{
		@Override
		public void write(PacketBuffer buf, AxisAlignedBB value)
		{
			buf.writeDouble(value.minX);
			buf.writeDouble(value.minY);
			buf.writeDouble(value.minZ);
			buf.writeDouble(value.maxX);
			buf.writeDouble(value.maxY);
			buf.writeDouble(value.maxZ);
		}

		@Override
		public AxisAlignedBB read(PacketBuffer buf)
		{
			return new AxisAlignedBB(buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readDouble());
		}

		@Override
		public DataParameter<AxisAlignedBB> createKey(int id)
		{
			return new DataParameter<AxisAlignedBB>(id, this);
		}

		@Override
		public AxisAlignedBB copyValue(AxisAlignedBB value)
		{
			return value.grow(0);
		}
	};
}
