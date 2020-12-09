package com.sekwah.mira4j.network.inbound.packets;

import com.sekwah.mira4j.network.Packet;
import com.sekwah.mira4j.network.PacketBuf;

public class KeepAlivePacket implements Packet<ClientListener> {
    private int ack_id;
    
    @Override
    public void readData(PacketBuf reader) {
        ack_id = reader.readUnsignedShortBE();
    }

    @Override
    public void writeData(PacketBuf writer) {}

    @Override
    public void forwardPacket(ClientListener listener) {
        listener.onKeepAlivePacket(this);
    }
    
    public int getAckId() {
        return ack_id;
    }
}
