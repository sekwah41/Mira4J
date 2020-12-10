package com.sekwah.mira4j.network.inbound.packets;

import com.sekwah.mira4j.network.Packet;
import com.sekwah.mira4j.network.PacketBuf;

public class PingPacket implements Packet<ClientListener> {
    private int nonce;
    
    public PingPacket() {
        
    }
    
    public PingPacket(int nonce) {
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

    @Override
    public void forwardPacket(ClientListener listener) {
        listener.onKeepAlivePacket(this);
    }
    
    public int getNonce() {
        return nonce;
    }
}
