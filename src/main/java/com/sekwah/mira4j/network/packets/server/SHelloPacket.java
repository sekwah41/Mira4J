package com.sekwah.mira4j.network.packets.server;

import com.sekwah.mira4j.network.Packet;
import com.sekwah.mira4j.network.PacketBuf;

public class SHelloPacket implements Packet {
    private int nonce;
    private byte[] data;

    @Override
    public void readData(PacketBuf reader) {
        nonce = reader.readUnsignedShortBE();
        data = reader.readBytes(reader.readableBytes());
    }

    @Override
    public void writeData(PacketBuf writer) {}

    public int getNonce() {
        return nonce;
    }

    public byte[] getData() {
        return data;
    }
}
