package com.sekwah.mira4j.network.inbound.packets;

import com.sekwah.mira4j.network.Packet;
import com.sekwah.mira4j.network.PacketBuf;

public class ReliablePacket implements Packet<ClientListener> {
    private int nonce;
    private byte[] data;
    
    public ReliablePacket() {
        
    }
    
    public ReliablePacket(int nonce, byte[] data) {
        this.nonce = nonce;
        this.data = data;
    }
    
    public ReliablePacket(int nonce, HazelMessage msg) {
        this.nonce = nonce;
        this.data = msg.toBytes();
    }
    
    @Override
    public void readData(PacketBuf reader) {
        nonce = reader.readUnsignedShortBE();
        data = reader.readBytes(reader.readableBytes());
    }

    @Override
    public void writeData(PacketBuf writer) {
        writer.writeUnsignedShortBE(nonce);
        writer.writeBytes(data);
    }

    @Override
    public void forwardPacket(ClientListener listener) {
        listener.onReliablePacket(this);
    }
    
    public int getNonce() {
        return nonce;
    }
    
    public byte[] getData() {
        return data;
    }
}
