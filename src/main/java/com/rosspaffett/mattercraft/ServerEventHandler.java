package com.rosspaffett.mattercraft;


import com.rosspaffett.mattercraft.config.ConfigHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import static com.rosspaffett.mattercraft.MattercraftMod.SERVER;

public class ServerEventHandler {
    private static final String INCOMING_MESSAGE_THREAD_NAME = "Mattercraft/IncomingMessageThread";
    private static final String OUTGOING_MESSAGE_THREAD_NAME = "Mattercraft/OutgoingMessageThread";

    private ChatMessageBroadcaster incomingMessageBroadcaster;
    private ChatMessageReceiver incomingMessageReceiver;
    private ChatMessageSender outgoingMessageSender;

    @SubscribeEvent
    public void onServerChatEvent(ServerChatEvent event) {
        sendOutgoingChatMessage(event.getUsername(), event.getMessage());
    }

    @SubscribeEvent
    public void onServerTick(TickEvent.ServerTickEvent event) {
        sendIncomingChatMessage();
    }

    @SubscribeEvent
    public void onPlayerDeath(LivingDeathEvent event){
        if (event.getEntity() instanceof EntityPlayer){
            EntityPlayer player = (EntityPlayer) event.getEntityLiving();
            sendOutgoingChatMessage(
                    "\uD83D\uDCA1 " + player.getName(),
                    "**[ " + ((int) player.posX) + " " + ((int) player.posZ) + " y" + ((int) player.posY) + " ]** \n \uD83D\uDC80 "
                            + event.getEntityLiving().getCombatTracker().getDeathMessage().getUnformattedText());
        }
    }

    @SubscribeEvent
    public void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event){
        sendOutgoingChatMessage(" + ",event.player.getName());
    }

    @SubscribeEvent
    public void onPlayerLeave(PlayerEvent.PlayerLoggedOutEvent event){
        sendOutgoingChatMessage(" - ",event.player.getName());
    }

    private void sendIncomingChatMessage() {
        ChatMessage message = incomingMessageReceiver.poll();
        if (message == null) { return; }
        incomingMessageBroadcaster.broadcast(message);
    }

    void sendOutgoingChatMessage(String username, String body) {
        ChatMessage message = new ChatMessage(username, body);
        this.outgoingMessageSender.enqueue(message);
    }

    private void startIncomingMessageThread() {
        Thread incomingMessageThread = new Thread (this.incomingMessageReceiver, INCOMING_MESSAGE_THREAD_NAME);
        incomingMessageThread.start();
    }

    private void startOutgoingMessageThread() {
        Thread outgoingMessageThread = new Thread(this.outgoingMessageSender, OUTGOING_MESSAGE_THREAD_NAME);
        outgoingMessageThread.start();
    }

    void startReceivingMessages() {
        this.incomingMessageBroadcaster = new ChatMessageBroadcaster(SERVER);
        this.incomingMessageReceiver = new ChatMessageReceiver(ConfigHandler.mattercraftConfig.base_url, ConfigHandler.mattercraftConfig.gateway, ConfigHandler.mattercraftConfig.api_token);

        startIncomingMessageThread();
    }

    void startSendingMessages() {
        this.outgoingMessageSender = new ChatMessageSender(ConfigHandler.mattercraftConfig.base_url, ConfigHandler.mattercraftConfig.gateway, ConfigHandler.mattercraftConfig.api_token);

        startOutgoingMessageThread();
    }

    void stopReceivingMessages() {
        this.incomingMessageReceiver.stop();
    }

    void stopSendingMessages() {
        this.outgoingMessageSender.stop();
    }
}
