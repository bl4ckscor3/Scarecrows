package bl4ckscor3.mod.scarecrows.entity;

import java.util.List;

import bl4ckscor3.mod.scarecrows.types.ScarecrowType;
import bl4ckscor3.mod.scarecrows.util.CustomDataSerializers;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntityShulker;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.passive.EntityAmbientCreature;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntityScarecrow extends Entity
{
	private static final DataParameter<ScarecrowType> TYPE = EntityDataManager.<ScarecrowType>createKey(EntityScarecrow.class, CustomDataSerializers.SCARECROWTYPE);
	private static final DataParameter<AxisAlignedBB> AREA = EntityDataManager.<AxisAlignedBB>createKey(EntityScarecrow.class, CustomDataSerializers.AXISALIGNEDBB);
	private boolean isLit;

	public EntityScarecrow(World world)
	{
		super(world);
	}

	public EntityScarecrow(ScarecrowType type, World world, BlockPos pos, boolean isLit)
	{
		super(world);

		setSize(1.0F, height);
		setPosition(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D);
		dataManager.set(TYPE, type);
		this.isLit = isLit;
		dataManager.set(AREA, new AxisAlignedBB(posX, posY, posZ, posX, posY, posZ).grow(type.getRange(), type.getHeight() * 3, type.getRange()));
	}

	@Override
	protected void entityInit()
	{
		dataManager.register(TYPE, null);
		dataManager.register(AREA, new AxisAlignedBB(0, 0, 0, 0, 0, 0));
	}

	@Override
	public void onUpdate()
	{
		if(world.getBlockState(getPosition().down()).getBlock().isAir(world.getBlockState(getPosition().down()), world, getPosition()))
			setDead();

		scare(EntityMob.class, EntityDragon.class, EntityGhast.class, EntityShulker.class, EntitySlime.class);

		if(getType().shouldScareAnimals())
			scare(EntityAmbientCreature.class, EntityAnimal.class, EntitySquid.class);
	}

	private void scare(Class<? extends EntityLiving>... entityTypes)
	{
		for(Class<? extends EntityLiving> clazz : entityTypes)
		{
			List<EntityLiving> entities = world.<EntityLiving>getEntitiesWithinAABB(clazz, getArea());

			for(EntityLiving entity : entities)
			{
				if(entity.canEntityBeSeen(this))
				{
					//TODO: add ai to run away
				}
			}
		}
	}

	@Override
	public void setDead()
	{
		super.setDead();

		if(!world.isRemote)
		{
			if(isLit)
				world.destroyBlock(getPosition().up(getType().getHeight() - 1), false);

			getType().dropMaterials(world, getPosition(), isLit);
		}
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound tag)
	{
		String name = tag.getString("scarecrow_type");

		for(ScarecrowType st : ScarecrowType.TYPES)
		{
			if(st.getName().equals(name))
			{
				dataManager.set(TYPE, st);
				return;
			}
		}
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound tag)
	{
		tag.setString("scarecrow_type", getType().getName());
	}

	public ScarecrowType getType()
	{
		return dataManager.get(TYPE);
	}

	public AxisAlignedBB getArea()
	{
		return dataManager.get(AREA);
	}
}
