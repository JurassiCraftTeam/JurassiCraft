package org.jurassicraft.server.block.entity;

import java.util.List;
import org.jurassicraft.client.model.animation.EntityAnimation;
import org.jurassicraft.server.dinosaur.Dinosaur;
import org.jurassicraft.server.entity.DinosaurEntity;
import org.jurassicraft.server.entity.EntityHandler;
import org.jurassicraft.server.plugin.waila.IWailaProvider;
import org.jurassicraft.server.util.LangUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class DisplayBlockEntity extends TileEntity implements IWailaProvider {
	
    private DinosaurEntity entity;
    private int rotation;
    private boolean isFossile;
    private boolean isMale;
    private byte variant;
    private boolean isSkeleton;

    private SerializedData serializedData = new InvalidData();

    public void setDinosaur(int dinosaurId, boolean isMale, boolean isSkeleton, boolean isFossile, byte variant) {
        this.isMale = isMale;
        this.isSkeleton = isSkeleton;
        this.variant = variant;
        this.isFossile = isFossile;
        try {
            Dinosaur dinosaur = EntityHandler.getDinosaurById(dinosaurId);
            this.entity = dinosaur.construct(this.world);
            this.initializeEntity(this.entity);
        } catch (Exception e) {
        }
        this.markDirty();
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);

        if (nbt.hasKey("DinosaurId")) {
            this.serializedData = new LegacyId();
        } else if (nbt.hasKey("DinosaurTag")) {
            this.serializedData = new TagData();
        }

        this.entity = null;

        this.serializedData.deserialize(nbt);

        this.rotation = nbt.getInteger("Rotation");
        this.isMale = !nbt.hasKey("IsMale") || nbt.getBoolean("IsMale");
        this.isSkeleton = nbt.getBoolean("IsSkeleton");
        this.variant = nbt.getByte("Variant");
        this.isFossile = nbt.getBoolean("IsFossile");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        nbt = super.writeToNBT(nbt);

        if (this.entity != null) {
            NBTTagCompound tag = this.entity.serializeNBT();
            nbt.setTag("DinosaurTag", tag);
        } else if (this.serializedData != null) {
            this.serializedData.serialize(nbt);
        }

        nbt.setInteger("Rotation", this.rotation);
        nbt.setBoolean("IsMale", this.isMale);
        nbt.setBoolean("IsSkeleton", this.isSkeleton);
        nbt.setByte("Variant", this.variant);
        nbt.setBoolean("IsFossile", this.isFossile);

        return nbt;
    }

    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        return new SPacketUpdateTileEntity(this.pos, 0, this.getUpdateTag());
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        return this.writeToNBT(new NBTTagCompound());
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onDataPacket(NetworkManager networkManager, SPacketUpdateTileEntity packet) {
        this.readFromNBT(packet.getNbtCompound());
    }

    @Override
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getRenderBoundingBox() {
        if (this.isSkeleton && this.entity != null) {
            return this.entity.getRenderBoundingBox().expand(3.0, 3.0, 3.0).offset(this.pos);
        }
        return super.getRenderBoundingBox();
    }

    public boolean isMale() {
        return this.isMale;
    }

    public boolean isSkeleton() {
        return this.isSkeleton;
    }
    
    public boolean isFossile() {
        return this.isFossile;
    }
    
    public byte getVariant() {
        return this.variant;
    }

    public int getRot() {
        return this.rotation;
    }

    public void setRot(int rotation) {
        this.markDirty();
        this.rotation = rotation;
    }

    public DinosaurEntity getEntity() {
        if (this.entity == null && this.serializedData != null) {
            return this.createEntity();
        }
        return this.entity;
    }

    private DinosaurEntity createEntity() {
        DinosaurEntity entity = this.serializedData.create(this.world);
        if (entity != null) {
            this.serializedData = null;
            this.initializeEntity(entity);
            this.entity = entity;
            return entity;
        } else {
            return new InvalidData().create(this.world);
        }
    }

    private void initializeEntity(DinosaurEntity entity) {
        entity.setupDisplay(this.isMale);
        entity.setSkeleton(this.isSkeleton);
        entity.setSkeletonVariant(this.variant);
        entity.setIsFossile(this.isFossile);
        entity.setAnimation(EntityAnimation.IDLE.get());
    }

    private interface SerializedData {
        void serialize(NBTTagCompound compound);

        void deserialize(NBTTagCompound compound);

        DinosaurEntity create(World world);
    }

    private class LegacyId implements SerializedData {
        protected int dinosaurId;

        @Override
        public void serialize(NBTTagCompound compound) {
            compound.setInteger("DinosaurId", this.dinosaurId);
        }

        @Override
        public void deserialize(NBTTagCompound compound) {
            this.dinosaurId = compound.getInteger("DinosaurId");
        }

        @Override
        public DinosaurEntity create(World world) {
            try {
                Dinosaur dinosaur = EntityHandler.getDinosaurById(this.dinosaurId);
                return dinosaur.construct(world);
            } catch (Exception e) {
                return null;
            }
        }
    }

    private class TagData implements SerializedData {
        protected NBTTagCompound data;

        @Override
        public void serialize(NBTTagCompound compound) {
            compound.setTag("DinosaurTag", this.data);
        }

        @Override
        public void deserialize(NBTTagCompound compound) {
            this.data = compound.getCompoundTag("DinosaurTag");
        }

        @Override
        public DinosaurEntity create(World world) {
            Entity entity = EntityList.createEntityFromNBT(this.data, world);
            if (entity instanceof DinosaurEntity) {
                return (DinosaurEntity) entity;
            }
            return null;
        }
    }

    private class InvalidData extends LegacyId {
        @Override
        public void deserialize(NBTTagCompound compound) {
            this.dinosaurId = EntityHandler.getDinosaurId(EntityHandler.VELOCIRAPTOR);
        }
    }

	@Override
	public List<String> getWailaData(List<String> list) {
		list.add(TextFormatting.GOLD + LangUtils.translate("gender.name") + ": " + TextFormatting.WHITE + LangUtils.getGenderMode(this.isMale() == true ? 1 : 2));
		if (this.isSkeleton() && this.entity.getMetadata().skeletonPoses().length > 1) {
			list.add(TextFormatting.YELLOW + LangUtils.translate("pose.name") + ": " + TextFormatting.WHITE + LangUtils.getSkeletonMode(this.entity.getDinosaur(), this.getVariant()));
		}
		
		return list;
	}
}
