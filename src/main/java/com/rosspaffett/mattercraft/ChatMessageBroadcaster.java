package com.rosspaffett.mattercraft;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerList;

public class ChatMessageBroadcaster {

    private final PlayerList playerList;

    public ChatMessageBroadcaster(MinecraftServer server) {
        this.playerList = server.getPlayerList();
    }

    public void broadcast(ChatMessage message) {
        playerList.sendMessage(message.toTextComponent(), true);
    }

}
