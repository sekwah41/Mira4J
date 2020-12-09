package com.sekwah.mira4j.network.inbound.packets;

import java.util.Arrays;

import com.sekwah.mira4j.Mira4J;
import com.sekwah.mira4j.network.ConnectionManager;
import com.sekwah.mira4j.network.PacketListener;
import com.sekwah.mira4j.network.Packets.MessageType;

public class ClientListener implements PacketListener {
    private final ConnectionManager manager;
    
    public ClientListener(ConnectionManager manager) {
        this.manager = manager;
    }
    
    public void onHelloPacket(HelloPacket packet) {
        Mira4J.LOGGER.info("A 'Hello' packet '{}' '{}'", packet.getAckId(), Arrays.toString(packet.getData()));
    }
    
    public void onDisconnectPacket(DisconnectPacket packet) {
        Mira4J.LOGGER.info("A 'Disconnect' packet");
    }
    
    public void onReliablePacket(ReliablePacket packet) {
        Mira4J.LOGGER.info("A 'Reliable' packet '{}' '{}'", packet.getAckId(), Arrays.toString(packet.getData()));
    }
    
    public void onUnreliablePacket(UnreliablePacket packet) {
        Mira4J.LOGGER.info("A 'Unreliable' packet '{}'", Arrays.toString(packet.getData()));
    }
    
    public void onAcknowledgePacket(AcknowledgePacket packet) {
        Mira4J.LOGGER.info("An 'Acknowledge' packet '{}' '{}'", packet.getAckId(), packet.getAckIdFlags());
    }
    
    public void onKeepAlivePacket(KeepAlivePacket packet) {
        Mira4J.LOGGER.info("A 'KeepAlive' packet '{}'", packet.getAckId());
    }
}
