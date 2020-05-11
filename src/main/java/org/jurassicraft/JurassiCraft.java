package org.jurassicraft;

import net.ilexiconn.llibrary.server.network.NetworkWrapper;
import net.ilexiconn.llibrary.server.update.UpdateHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import org.apache.logging.log4j.Logger;
import org.jurassicraft.server.command.ForceAnimationCommand;
import org.jurassicraft.server.command.SpawnStructureCommand;
import org.jurassicraft.server.message.*;
import org.jurassicraft.server.proxy.ServerProxy;

@Mod(modid = JurassiCraft.MODID, name = JurassiCraft.NAME, version = JurassiCraft.VERSION, dependencies = "required-after:llibrary@[" + JurassiCraft.LLIBRARY_VERSION + ",);required-after:forge@[14.23.4.2705,)")
public class JurassiCraft {
    public static final String MODID = "jurassicraft";
    public static final String NAME = "JurassiCraft";
    public static final String VERSION = "2.1.23 ";

    public static final String LLIBRARY_VERSION = "1.7.15";
    @SidedProxy(serverSide = "org.jurassicraft.server.proxy.ServerProxy", clientSide = "org.jurassicraft.client.proxy.ClientProxy")
    public static ServerProxy PROXY;

    @Instance(JurassiCraft.MODID)
    public static JurassiCraft INSTANCE;

    public static long timerTicks;

    @NetworkWrapper({ PlacePaddockSignMessage.class, ChangeTemperatureMessage.class, SwitchHybridizerCombinatorMode.class, SetOrderMessage.class, OpenFieldGuideGuiMessage.class, UpdateVehicleControlMessage.class, BiPacketOrder.class, MicroraptorDismountMessage.class, FordExplorerChangeStateMessage.class, FordExplorerUpdatePositionStateMessage.class, DNASequenceTransferClicked.class, CarEntityPlayRecord.class, AttemptMoveToSeatMessage.class, TileEntityFieldsMessage.class, SpecialAnimationMessage.class})
    public static SimpleNetworkWrapper NETWORK_WRAPPER;

    private static Logger logger;
    @Mod.EventHandler
    public void onPreInit(FMLPreInitializationEvent event) {
        this.logger = event.getModLog();
        UpdateHandler.INSTANCE.registerUpdateChecker(this, "http://pastebin.com/raw/Rb96SNWb");
        PROXY.onPreInit(event);
    }

    @Mod.EventHandler
    public void onInit(FMLInitializationEvent event) {
        PROXY.onInit(event);
    }

    @Mod.EventHandler
    public void onPostInit(FMLPostInitializationEvent event) {
        PROXY.onPostInit(event);
    }

    @Mod.EventHandler
    public void onServerStarting(FMLServerStartingEvent event) {
        event.registerServerCommand(new ForceAnimationCommand());
        event.registerServerCommand(new SpawnStructureCommand());
    }

    public static Logger getLogger() {
        return logger;
    }
}