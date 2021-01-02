package com.sekwah.mira4j.network.packets.server;

import com.sekwah.mira4j.network.Packet;
import com.sekwah.mira4j.network.PacketBuf;

public class SPingPacket implements Packet {
    private int nonce;

    public SPingPacket() {

    }

    public SPingPacket(int nonce) {
        this.nonce = nonce;
    }

    @Override
    public void readData(PacketBuf reader) {
        nonce = reader.readUnsignedShortBE();
    }

    @Override
    public void writeData(PacketBuf writer) {
        writer.writeShortBE(nonce);
    }

    public int getNonce() {
        return nonce;
    }
}
