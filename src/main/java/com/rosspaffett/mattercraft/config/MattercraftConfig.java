package com.rosspaffett.mattercraft.config;

import com.rosspaffett.mattercraft.MattercraftMod;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
@Config(modid = MattercraftMod.MODID, name = MattercraftMod.NAME, category = "general")
public class MattercraftConfig {
    @Config.Comment({"Your Matterbridge API token"})
    public String api_token = "s3cr3t";
    @Config.Comment({"Matterbridge API base URL, including protocol"})
    public String base_url = "https://matterbridge.example.com";
    @Config.Comment({"Matterbridge gateway name"})
    public String gateway = "example";
}
