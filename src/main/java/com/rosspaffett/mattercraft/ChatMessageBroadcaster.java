package com.rosspaffett.mattercraft;

import com.mojang.authlib.GameProfile;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.PlayerChatMessage;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.players.PlayerList;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.FakePlayerFactory;
import java.util.UUID;

public class ChatMessageBroadcaster {
    private static final String FAKE_PLAYER_NAME = "Mattercraft";
    private static final UUID FAKE_PLAYER_UUID = UUIDUtil.createOfflinePlayerUUID(FAKE_PLAYER_NAME);

    private final FakePlayer fakePlayer;
    private final PlayerList playerList;
    private final MinecraftServer server;

    public ChatMessageBroadcaster(MinecraftServer server) {
        this.server = server;
        this.playerList = server.getPlayerList();
        this.fakePlayer = buildFakePlayer();
    }

    public void broadcast(ChatMessage message) {
        playerList.broadcastChatMessage(PlayerChatMessage.system(
                message.getText()),
                fakePlayer.createCommandSourceStack(),
                ChatType.bind(ChatType.CHAT,fakePlayer));
    }

    private FakePlayer buildFakePlayer() {
        GameProfile profile = new GameProfile(FAKE_PLAYER_UUID, FAKE_PLAYER_NAME);
        return FakePlayerFactory.get(getOverworld(), profile);
    }

    private ServerLevel getOverworld() {
        return server.getLevel(Level.OVERWORLD);
    }
}
