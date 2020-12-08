package com.sekwah.mira4j.network.inbound.packets;

import com.sekwah.mira4j.Mira4J;
import com.sekwah.mira4j.network.ConnectionManager;
import com.sekwah.mira4j.network.MessageType;
import com.sekwah.mira4j.network.PacketListener;

public class ClientListener implements PacketListener {
    private final ConnectionManager manager;
    
    public ClientListener(ConnectionManager manager) {
        this.manager = manager;
    }
    
    public void onHelloPacket(HelloPacket packet) {
        String username = packet.getUsername();
        MessageType type = packet.getMessageType();
        
        Mira4J.LOGGER.info("A user '{}' is trying to '{}'", username, type);
        
        // manager.sendPacket(new AckPacket());
    }
}
