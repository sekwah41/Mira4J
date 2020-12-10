package com.sekwah.mira4j.network.inbound.packets;

import com.sekwah.mira4j.network.Packet;
import com.sekwah.mira4j.network.PacketBuf;

public class NormalPacket implements Packet<ClientListener> {
    private byte[] data;
    
    @Override
    public void readData(PacketBuf reader) {
        data = reader.readBytes(reader.readableBytes());
    }

    @Override
    public void writeData(PacketBuf writer) {}

    @Override
    public void forwardPacket(ClientListener listener) {
        listener.onNormalPacket(this);
    }
    
    public byte[] getData() {
        return data;
    }
}
