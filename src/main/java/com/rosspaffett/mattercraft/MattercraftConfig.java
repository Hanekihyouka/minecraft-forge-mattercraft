package com.rosspaffett.mattercraft;

import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class MattercraftConfig {
    public static final MatterbridgeConfig MATTERBRIDGE;
    public static final ModConfigSpec SPEC;

    static {
        final Pair<MatterbridgeConfig, ModConfigSpec> configSpecPair = new ModConfigSpec.Builder().
            configure(MatterbridgeConfig::new);
        MATTERBRIDGE = configSpecPair.getLeft();
        SPEC = configSpecPair.getRight();
    }

    public static String apiToken;
    public static String baseUrl;
    public static String gateway;

    protected static void cacheValuesFromSpec() {
        apiToken = MATTERBRIDGE.apiToken.get();
        baseUrl = MATTERBRIDGE.baseUrl.get();
        gateway = MATTERBRIDGE.gateway.get();
    }

    public static class MatterbridgeConfig {
        public final ModConfigSpec.ConfigValue<String> apiToken;
        public final ModConfigSpec.ConfigValue<String> baseUrl;
        public final ModConfigSpec.ConfigValue<String> gateway;

        public MatterbridgeConfig(ModConfigSpec.Builder builder) {
            builder.push("matterbridge");

            apiToken = builder
                .comment("Your Matterbridge API token")
                .define("api_token", "example-api-token");

            baseUrl = builder
                .comment("Matterbridge API base URL, including protocol")
                .define("base_url", "https://matterbridge.example.com");

            gateway = builder
                .comment("Matterbridge gateway name")
                .define("gateway", "example-gateway");

            builder.pop();
        }
    }
}
