package com.sekwah.mira4j.network.inbound.packets;

import com.sekwah.mira4j.network.Packet;
import com.sekwah.mira4j.network.PacketBuf;

public class AcknowledgePacket implements Packet<ClientListener> {
    private int ack_id;
    private int ack_id_flags;
    
    @Override
    public void readData(PacketBuf reader) {
        ack_id = reader.readUnsignedShortBE();
        ack_id_flags = reader.readUnsignedByte();
    }

    @Override
    public void writeData(PacketBuf writer) {}

    @Override
    public void forwardPacket(ClientListener listener) {
        listener.onAcknowledgePacket(this);
    }
    
    public int getAckId() {
        return ack_id;
    }
    
    public int getAckIdFlags() {
        return ack_id_flags;
    }
}
