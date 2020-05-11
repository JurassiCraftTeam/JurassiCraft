package org.jurassicraft.server.message;

import io.netty.buffer.ByteBuf;
import net.ilexiconn.llibrary.server.network.AbstractMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import org.jurassicraft.JurassiCraft;
import org.jurassicraft.client.event.ClientEventHandler;
import org.jurassicraft.client.gui.DNACombinatorHybridizerGui;
import org.jurassicraft.client.proxy.ClientProxy;
import org.jurassicraft.server.block.entity.DNACombinatorHybridizerBlockEntity;
import org.jurassicraft.server.container.DNACombinatorHybridizerContainer;

public class SwitchHybridizerCombinatorMode extends AbstractMessage<SwitchHybridizerCombinatorMode> {
    private BlockPos pos;
    private boolean hybridizer;
    private int dimension;

    public SwitchHybridizerCombinatorMode() {
    }

    public SwitchHybridizerCombinatorMode(BlockPos pos, boolean hybridizer, int dimension) {
        this.hybridizer = hybridizer;
        this.pos = pos;
        this.dimension = dimension;
    }

    @Override
    public void onClientReceived(Minecraft minecraft, SwitchHybridizerCombinatorMode message, EntityPlayer player, MessageContext messageContext) {
        DNACombinatorHybridizerBlockEntity tile = (DNACombinatorHybridizerBlockEntity) player.world.getTileEntity(message.pos);
        tile.setMode(message.hybridizer);

        GuiScreen screen = ClientProxy.MC.currentScreen;

        if (screen instanceof DNACombinatorHybridizerGui) {
            ((DNACombinatorHybridizerContainer) ((DNACombinatorHybridizerGui) screen).inventorySlots).updateSlots(message.hybridizer);
        }
    }

    @Override
    public void onServerReceived(MinecraftServer minecraftServer, SwitchHybridizerCombinatorMode message, EntityPlayer player, MessageContext messageContext) {
        boolean mode = message.hybridizer;
        BlockPos pos = message.pos;

        DNACombinatorHybridizerBlockEntity tile = (DNACombinatorHybridizerBlockEntity) player.world.getTileEntity(pos);

        tile.setMode(mode);
        JurassiCraft.NETWORK_WRAPPER.sendToAllTracking(new SwitchHybridizerCombinatorMode(pos, mode, message.dimension), new TargetPoint(message.dimension, pos.getX(), pos.getY(), pos.getZ(), 5));

        Container openContainer = player.openContainer;

        if (openContainer instanceof DNACombinatorHybridizerContainer) {
            ((DNACombinatorHybridizerContainer) openContainer).updateSlots(mode);
        }
    }

    @Override
    public void toBytes(ByteBuf buffer) {
        buffer.writeBoolean(this.hybridizer);
        buffer.writeLong(this.pos.toLong());
        buffer.writeInt(this.dimension);
    }

    @Override
    public void fromBytes(ByteBuf buffer) {
        this.hybridizer = buffer.readBoolean();
        this.pos = BlockPos.fromLong(buffer.readLong());
        this.dimension = buffer.readInt();
    }
}
