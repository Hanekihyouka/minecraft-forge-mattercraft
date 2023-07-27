package com.rosspaffett.mattercraft;

import net.minecraft.network.chat.TextComponent;

public class ChatMessage {
    private final String text;
    private final String username;

    ChatMessage(String username, String text) {
        this.text = text;
        this.username = username;
    }

    public String getText() {
        return this.text;
    }

    public String getUsername() {
        return this.username;
    }

    public String toString() {
        return "<" + getUsername() + "> " + getText();
    }

    public TextComponent toTextComponent() {
        return new TextComponent(toString());
    }
}
