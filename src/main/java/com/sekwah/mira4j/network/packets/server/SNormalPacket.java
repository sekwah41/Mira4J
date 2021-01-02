package com.sekwah.mira4j.network.packets.server;

import com.sekwah.mira4j.network.Packet;
import com.sekwah.mira4j.network.PacketBuf;

public class SNormalPacket implements Packet {
    private byte[] data;

    @Override
    public void readData(PacketBuf reader) {
        data = reader.readBytes(reader.readableBytes());
    }

    @Override
    public void writeData(PacketBuf writer) {}

    public byte[] getData() {
        return data;
    }
}
