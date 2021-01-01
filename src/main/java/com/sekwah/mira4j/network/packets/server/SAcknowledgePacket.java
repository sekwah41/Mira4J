package com.sekwah.mira4j.network.packets.server;

import com.sekwah.mira4j.network.Packet;
import com.sekwah.mira4j.network.PacketBuf;

public class SAcknowledgePacket implements Packet<SClientListener> {
    private int nonce;
    private int missing_packets;

    public SAcknowledgePacket() {

    }

    public SAcknowledgePacket(int nonce, int missing_packets) {
        this.nonce = nonce;
        this.missing_packets = missing_packets;
    }

    @Override
    public void readData(PacketBuf reader) {
        nonce = reader.readUnsignedShortBE();
        missing_packets = reader.readUnsignedByte();
    }

    @Override
    public void writeData(PacketBuf writer) {
        writer.writeShortBE(nonce);
        writer.writeByte(missing_packets);
    }

    @Override
    public void forwardPacket(SClientListener listener) {
        listener.onAcknowledgePacket(this);
    }

    public int getNonce() {
        return nonce;
    }

    public int getMissingPackets() {
        return missing_packets;
    }
}
