package com.rosspaffett.mattercraft;

import com.mojang.authlib.GameProfile;
import net.minecraft.network.chat.ChatType;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.players.PlayerList;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.FakePlayerFactory;

public class ChatMessageBroadcaster {
    private static final ChatType CHAT_TYPE = ChatType.CHAT;
    private static final String FAKE_PLAYER_NAME = "Mattercraft";

    private final FakePlayer fakePlayer;
    private final PlayerList playerList;
    private final MinecraftServer server;

    public ChatMessageBroadcaster(MinecraftServer server) {
        this.server = server;
        this.playerList = server.getPlayerList();
        this.fakePlayer = buildFakePlayer();
    }

    public void broadcast(ChatMessage message) {
        playerList.broadcastMessage(message.toTextComponent(), CHAT_TYPE, fakePlayer.getUUID());
    }

    private FakePlayer buildFakePlayer() {
        GameProfile profile = new GameProfile(null, FAKE_PLAYER_NAME);
        return FakePlayerFactory.get(getOverworld(), profile);
    }

    private ServerLevel getOverworld() {
        return server.getLevel(Level.OVERWORLD);
    }
}
