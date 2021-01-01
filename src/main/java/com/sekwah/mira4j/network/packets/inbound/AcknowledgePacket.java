package com.sekwah.mira4j.network.packets.inbound;

import com.sekwah.mira4j.network.Packet;
import com.sekwah.mira4j.network.PacketBuf;

public class AcknowledgePacket implements Packet<ClientListener> {
    private int nonce;
    private int missing_packets;

    public AcknowledgePacket() {

    }

    public AcknowledgePacket(int nonce, int missing_packets) {
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
    public void forwardPacket(ClientListener listener) {
        listener.onAcknowledgePacket(this);
    }

    public int getNonce() {
        return nonce;
    }

    public int getMissingPackets() {
        return missing_packets;
    }
}
