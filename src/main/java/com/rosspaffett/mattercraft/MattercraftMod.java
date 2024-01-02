package com.rosspaffett.mattercraft;

import com.rosspaffett.mattercraft.config.ConfigHandler;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLServerAboutToStartEvent;
import net.minecraftforge.fml.common.event.FMLServerStartedEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@Mod(modid = MattercraftMod.MODID, name = MattercraftMod.NAME, version = MattercraftMod.VERSION,
        serverSideOnly = true, acceptableRemoteVersions = "*", acceptedMinecraftVersions = "[1.12.2]")
public class MattercraftMod {
    public static final String MODID = "mattercraft";
    public static final String NAME = "MatterCraft";
    public static final String VERSION = "1.0";
    private static final Logger LOGGER = LogManager.getLogger();
    public static MinecraftServer SERVER = null;

    private ServerEventHandler serverEventHandler;

    public MattercraftMod() {
        registerServerEventHandler();
    }

    private void registerServerEventHandler() {
        serverEventHandler = new ServerEventHandler();
        MinecraftForge.EVENT_BUS.register(serverEventHandler);
    }

    @Mod.EventHandler
    public void onServerAboutToStart(FMLServerAboutToStartEvent event) {
        SERVER = event.getServer();
        serverEventHandler.startReceivingMessages();
        serverEventHandler.startSendingMessages();
        LOGGER.info("Mattercraft is relaying chat to Matterbridge gateway \"{}\" at {}",
                ConfigHandler.mattercraftConfig.gateway, ConfigHandler.mattercraftConfig.base_url);
    }

    @Mod.EventHandler
    public void onServerStarted(FMLServerStartedEvent event){
        serverEventHandler.sendOutgoingChatMessage("\uD83D\uDCA1\uD83D\uDCA1","Server started.");
    }

    @Mod.EventHandler
    public void onServerStopping(FMLServerStoppingEvent event) {
        serverEventHandler.sendOutgoingChatMessage("\uD83D\uDCA1\uD83D\uDCA1","Server shutting down.");
        serverEventHandler.stopReceivingMessages();
        serverEventHandler.stopSendingMessages();

        SERVER = null;
    }

}
