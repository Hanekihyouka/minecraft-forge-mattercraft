package com.rosspaffett.mattercraft;


import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.NeoForge;


@Mod(MattercraftMod.MOD_ID)
@EventBusSubscriber(value = Dist.DEDICATED_SERVER, bus = EventBusSubscriber.Bus.MOD)
public class MattercraftMod {
    public static final String MOD_ID = "mattercraft";

    public MattercraftMod() {
        registerConfig();
        registerServerEventHandler();
    }

    @SubscribeEvent
    public static void onModConfigEvent(ModConfigEvent event) {
        if (event.getConfig().getSpec() == MattercraftConfig.SPEC) {
            MattercraftConfig.cacheValuesFromSpec();
        }
    }

    private void registerConfig() {
        ModLoadingContext.get().getActiveContainer().registerConfig(ModConfig.Type.SERVER, MattercraftConfig.SPEC);
    }

    private void registerServerEventHandler() {
        ServerEventHandler serverEventHandler = new ServerEventHandler();
        NeoForge.EVENT_BUS.register(serverEventHandler);
    }
}
