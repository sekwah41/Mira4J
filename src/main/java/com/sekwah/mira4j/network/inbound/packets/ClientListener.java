package com.sekwah.mira4j.network.inbound.packets;

import java.util.Arrays;

import com.sekwah.mira4j.Mira4J;
import com.sekwah.mira4j.network.*;
import com.sekwah.mira4j.network.decoder.HazelDecoder;
import com.sekwah.mira4j.network.inbound.packets.hazel.HostGamePacket;
import com.sekwah.mira4j.network.outbound.packets.HostGame;
import com.sekwah.mira4j.utils.GameCodes;

public class ClientListener implements PacketListener {
    private final ConnectionManager manager;
    
    public ClientListener(ConnectionManager manager) {
        this.manager = manager;
    }
    
    public void onHelloPacket(HelloPacket packet) {
        Mira4J.LOGGER.info("A 'Hello' packet '{}' '{}'", packet.getNonce(), Arrays.toString(packet.getData()));
        // This will probably be the Connecting packet
        PacketBuf buffer = PacketBuf.wrap(packet.getData());
        
        /* int reserved =  buffer.readUnsignedByte(); */ buffer.skipBytes(1);
        long version = buffer.readUnsignedInt();
        String username = buffer.readString();
        Mira4J.LOGGER.info("Hello: version='{}' username='{}'", String.format("%08x", version), username);
        buffer.release();
        
        
        AcknowledgePacket ack_packet = new AcknowledgePacket(packet.getNonce(), packet.getNonce() - 1);
        manager.sendPacket(ack_packet);
    }
    
    public void onDisconnectPacket(DisconnectPacket packet) {
        Mira4J.LOGGER.info("A 'Disconnect' packet");
        
        manager.disconnect();
    }
    
    public void onReliablePacket(ReliablePacket packet) {
        Mira4J.LOGGER.info("A 'Reliable' packet '{}' '{}'", packet.getNonce(), packet.getMessages());
        
        for(HazelMessage msg : packet.getMessages()) {
            msg.forwardPacket(this);
        }
        
        AcknowledgePacket ack_packet = new AcknowledgePacket(packet.getNonce(), -1);
        manager.sendPacket(ack_packet);
    }
    
    public void onNormalPacket(NormalPacket packet) {
        Mira4J.LOGGER.info("A 'Normal' packet '{}'", Arrays.toString(packet.getData()));
    }
    
    public void onAcknowledgePacket(AcknowledgePacket packet) {
        // Mira4J.LOGGER.info("A 'Acknowledge' packet '{}' '{}'", packet.getNonce(), packet.getMissingPackets());
    }
    
    public void onKeepAlivePacket(PingPacket packet) {
        // Mira4J.LOGGER.info("A 'KeepAlive' packet '{}'", packet.getNonce());
        
        AcknowledgePacket ack_packet = new AcknowledgePacket(packet.getNonce(), -1);
        manager.sendPacket(ack_packet);
    }
    
    
    
    public void onHostGamePacket(HostGamePacket packet) {
        Mira4J.LOGGER.info("A 'HostGamePacket' packet data='{}'", packet.getGameOptionsData());
        
        ReliablePacket send = new ReliablePacket(1, new HostGame(GameCodes.codeToInt("SEKWAH")));
        manager.sendPacket(send);
    }
}
