package bl4ckscor3.mod.scarecrows.util;

import bl4ckscor3.mod.scarecrows.type.ScarecrowType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.world.phys.AABB;

public class CustomDataSerializers
{
	public static final EntityDataSerializer<ScarecrowType> SCARECROWTYPE = new EntityDataSerializer<ScarecrowType>()
	{
		@Override
		public void write(FriendlyByteBuf buf, ScarecrowType value)
		{
			buf.writeUtf(value.getName());
		}

		@Override
		public ScarecrowType read(FriendlyByteBuf buf)
		{
			String bufferedName = buf.readUtf(Integer.MAX_VALUE / 4);

			for(ScarecrowType type : ScarecrowType.TYPES)
			{
				if(type.getName().equals(bufferedName))
					return type;
			}

			return null;
		}

		@Override
		public EntityDataAccessor<ScarecrowType> createAccessor(int id)
		{
			return new EntityDataAccessor<ScarecrowType>(id, this);
		}

		@Override
		public ScarecrowType copy(ScarecrowType value)
		{
			return value;
		}
	};

	public static final EntityDataSerializer<AABB> AXISALIGNEDBB = new EntityDataSerializer<AABB>()
	{
		@Override
		public void write(FriendlyByteBuf buf, AABB value)
		{
			buf.writeDouble(value.minX);
			buf.writeDouble(value.minY);
			buf.writeDouble(value.minZ);
			buf.writeDouble(value.maxX);
			buf.writeDouble(value.maxY);
			buf.writeDouble(value.maxZ);
		}

		@Override
		public AABB read(FriendlyByteBuf buf)
		{
			return new AABB(buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readDouble());
		}

		@Override
		public EntityDataAccessor<AABB> createAccessor(int id)
		{
			return new EntityDataAccessor<AABB>(id, this);
		}

		@Override
		public AABB copy(AABB value)
		{
			return value.inflate(0);
		}
	};
}
