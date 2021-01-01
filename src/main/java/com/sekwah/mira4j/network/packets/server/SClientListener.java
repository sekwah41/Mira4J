package com.sekwah.mira4j.network.packets.server;

import java.util.Arrays;

import com.sekwah.mira4j.Mira4J;
import com.sekwah.mira4j.network.*;
import com.sekwah.mira4j.network.packets.server.hazel.HostGamePacket;
import com.sekwah.mira4j.network.packets.client.HostGame;
import com.sekwah.mira4j.utils.GameUtils;

public class SClientListener implements PacketListener {
    private final ConnectionManager manager;

    public SClientListener(ConnectionManager manager) {
        this.manager = manager;
    }

    public void onHelloPacket(SHelloPacket packet) {
        Mira4J.LOGGER.info("A 'Hello' packet '{}' '{}'", packet.getNonce(), Arrays.toString(packet.getData()));
        // This will probably be the Connecting packet
        PacketBuf buffer = PacketBuf.wrap(packet.getData());

        /* int reserved =  buffer.readUnsignedByte(); */ buffer.skipBytes(1);
        long version = buffer.readUnsignedInt();
        String username = buffer.readString();
        Mira4J.LOGGER.info("Hello: version='{}' username='{}'", String.format("%08x", version), username);
        buffer.release();


        SAcknowledgePacket ack_packet = new SAcknowledgePacket(packet.getNonce(), packet.getNonce() - 1);
        manager.sendPacket(ack_packet);
    }

    public void onDisconnectPacket(SDisconnectPacket packet) {
        Mira4J.LOGGER.info("A 'Disconnect' packet");

        manager.disconnect();
    }

    public void onReliablePacket(SReliablePacket packet) {
        Mira4J.LOGGER.info("A 'Reliable' packet '{}' '{}'", packet.getNonce(), packet.getMessages());

        for(SHazelMessage msg : packet.getMessages()) {
            msg.forwardPacket(this);
        }

        SAcknowledgePacket ack_packet = new SAcknowledgePacket(packet.getNonce(), -1);
        manager.sendPacket(ack_packet);
    }

    public void onNormalPacket(SNormalPacket packet) {
        Mira4J.LOGGER.info("A 'Normal' packet '{}'", Arrays.toString(packet.getData()));
    }

    public void onAcknowledgePacket(SAcknowledgePacket packet) {
        // Mira4J.LOGGER.info("A 'Acknowledge' packet '{}' '{}'", packet.getNonce(), packet.getMissingPackets());
    }

    public void onKeepAlivePacket(SPingPacket packet) {
        // Mira4J.LOGGER.info("A 'KeepAlive' packet '{}'", packet.getNonce());

        SAcknowledgePacket ack_packet = new SAcknowledgePacket(packet.getNonce(), -1);
        manager.sendPacket(ack_packet);
    }



    public void onHostGamePacket(HostGamePacket packet) {
        Mira4J.LOGGER.info("A 'HostGamePacket' packet data='{}'", packet.getGameOptionsData());

        SReliablePacket send = new SReliablePacket(1, new HostGame(GameUtils.codeToInt("SEKWAH")));
        manager.sendPacket(send);
    }
}
