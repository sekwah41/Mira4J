package com.sekwah.mira4j.network.packets.server;

import com.sekwah.mira4j.network.Packet;
import com.sekwah.mira4j.network.PacketBuf;

public class SNormalPacket implements Packet<SClientListener> {
    private byte[] data;

    @Override
    public void readData(PacketBuf reader) {
        data = reader.readBytes(reader.readableBytes());
    }

    @Override
    public void writeData(PacketBuf writer) {}

    @Override
    public void forwardPacket(SClientListener listener) {
        listener.onNormalPacket(this);
    }

    public byte[] getData() {
        return data;
    }
}
