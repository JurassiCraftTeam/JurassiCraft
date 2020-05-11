package org.jurassicraft.server.message;

import org.jurassicraft.client.model.animation.EntityAnimation;
import org.jurassicraft.server.api.Animatable;

import io.netty.buffer.ByteBuf;
import net.ilexiconn.llibrary.server.animation.IAnimatedEntity;
import net.ilexiconn.llibrary.server.network.AbstractMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class SpecialAnimationMessage extends AbstractMessage<SpecialAnimationMessage> {
    private int entityID;
    private int index;
    private byte variant;

    public SpecialAnimationMessage() {}

    public SpecialAnimationMessage(int entityID, int index, byte variant) {
        this.entityID = entityID;
        this.index = index;
        this.variant = variant;
       
    }

	@Override
	@SideOnly(Side.CLIENT)
	public void onClientReceived(Minecraft client, SpecialAnimationMessage message, EntityPlayer player, MessageContext messageContext) {
		Animatable entity = (Animatable) player.world.getEntityByID(message.entityID);
		if (entity != null) {
			entity.setAnimationTick(0);
			if (!(message.variant == 0 && !entity.getVariants().containsKey(entity.getAnimations()[message.index]))) {
				if (EntityAnimation.getAnimation(entity.getAnimations()[message.index]).getVariants(entity.getEntityClass()) > 0 && message.variant <= EntityAnimation.getAnimation(entity.getAnimations()[message.index]).getVariants(entity.getEntityClass())) {

					entity.addVariant(entity.getAnimations()[message.index], (byte) message.variant);
				}
			}

			if (message.index == -1) {
				entity.setAnimation(IAnimatedEntity.NO_ANIMATION);
			} else {
				entity.setAnimation(entity.getAnimations()[message.index]);
			}

		}
	}

    @Override
    public void onServerReceived(MinecraftServer server, SpecialAnimationMessage message, EntityPlayer player, MessageContext messageContext) {}

    @Override
    public void fromBytes(ByteBuf byteBuf) {
        this.entityID = byteBuf.readInt();
        this.index = byteBuf.readInt();
        this.variant = byteBuf.readByte();
    }

    @Override
    public void toBytes(ByteBuf byteBuf) {
        byteBuf.writeInt(this.entityID);
        byteBuf.writeInt(this.index);
        byteBuf.writeByte(this.variant);
    }
}