package com.rosspaffett.mattercraft;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.IExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.network.NetworkConstants;


@Mod(MattercraftMod.MOD_ID)
@Mod.EventBusSubscriber(value = Dist.DEDICATED_SERVER, bus = Mod.EventBusSubscriber.Bus.MOD)
public class MattercraftMod {
    public static final String MOD_ID = "mattercraft";

    public MattercraftMod() {
        allowClientVersionMismatch();
        registerConfig();
        registerServerEventHandler();
    }

    /**
     * Tell Forge to ignore any Mattercraft version mismatch between the client and server, since the server
     * implementation is the only one that's used.
     *
     * see https://mcforge.readthedocs.io/en/latest/concepts/sides/#writing-one-sided-mods
     *
     */
    private void allowClientVersionMismatch() {
        ModLoadingContext.get().registerExtensionPoint(IExtensionPoint.DisplayTest.class, () -> new IExtensionPoint.DisplayTest(() -> NetworkConstants.IGNORESERVERONLY, (a, b) -> true));
    }

    @SubscribeEvent
    public static void onModConfigEvent(ModConfigEvent event) {
        if (event.getConfig().getSpec() == MattercraftConfig.SPEC) {
            MattercraftConfig.cacheValuesFromSpec();
        }
    }

    private void registerConfig() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, MattercraftConfig.SPEC);
    }

    private void registerServerEventHandler() {
        ServerEventHandler serverEventHandler = new ServerEventHandler();
        MinecraftForge.EVENT_BUS.register(serverEventHandler);
    }
}
