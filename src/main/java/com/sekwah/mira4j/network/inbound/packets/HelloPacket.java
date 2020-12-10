package com.sekwah.mira4j.network.inbound.packets;

import com.sekwah.mira4j.network.Packet;
import com.sekwah.mira4j.network.PacketBuf;

public class HelloPacket implements Packet<ClientListener> {
    private int nonce;
    private byte[] data;
    
    @Override
    public void readData(PacketBuf reader) {
        nonce = reader.readUnsignedShortBE();
        data = reader.readBytes(reader.readableBytes());
    }

    @Override
    public void writeData(PacketBuf writer) {}

    @Override
    public void forwardPacket(ClientListener listener) {
        listener.onHelloPacket(this);
    }

    public int getNonce() {
        return nonce;
    }
    
    public byte[] getData() {
        return data;
    }
}
