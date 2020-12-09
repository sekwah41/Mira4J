package com.sekwah.mira4j.network.inbound.packets;

import com.sekwah.mira4j.network.Packet;
import com.sekwah.mira4j.network.PacketBuf;

public class ReliablePacket implements Packet<ClientListener> {
    private int ack_id;
    private byte[] data;
    
    @Override
    public void readData(PacketBuf reader) {
        ack_id = reader.readUnsignedShortBE();
        data = reader.readBytes(reader.readableBytes());
    }

    @Override
    public void writeData(PacketBuf writer) {}

    @Override
    public void forwardPacket(ClientListener listener) {
        listener.onReliablePacket(this);
    }
    
    public int getAckId() {
        return ack_id;
    }
    
    public byte[] getData() {
        return data;
    }
}
