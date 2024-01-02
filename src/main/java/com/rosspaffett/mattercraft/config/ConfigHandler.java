package com.rosspaffett.mattercraft.config;

import com.rosspaffett.mattercraft.MattercraftMod;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
@Config(modid = MattercraftMod.MODID, name = MattercraftMod.NAME, category = "general")
public class ConfigHandler {
    public static MattercraftConfig mattercraftConfig = new MattercraftConfig();
    @SubscribeEvent
    public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.getModID().equals(MattercraftMod.MODID)) {
            ConfigManager.load(MattercraftMod.MODID, Config.Type.INSTANCE);
        }
    }
}
