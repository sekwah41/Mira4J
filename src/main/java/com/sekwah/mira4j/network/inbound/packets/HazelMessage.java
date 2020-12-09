package com.sekwah.mira4j.network.inbound.packets;

import com.sekwah.mira4j.network.Packet;
import com.sekwah.mira4j.network.PacketBuf;

public class HazelMessage implements Packet<ClientListener> {
    protected int type;
    protected byte[] data;
    
    public HazelMessage() {
        
    }
    
    public HazelMessage(int type, Packet<?> pck) {
        this.type = type;
        this.data = pck.toBytes();
    }
    
    @Override
    public void readData(PacketBuf reader) {
        int length = reader.readShort();
        type = reader.readUnsignedByte();
        data = reader.readBytes(length);
    }
    
    @Override
    public final void writeData(PacketBuf writer) {
        if(!isSubclass()) {
            writer.writeShort(data.length);
            writer.writeByte(type);
            writer.writeBytes(data);
        } else {
            PacketBuf buf = PacketBuf.create(4096);
            writeData0(buf);
            byte[] data = buf.readBytes(buf.readableBytes());
            buf.release();
            writer.writeShort(data.length);
            writer.writeByte(type);
            writer.writeBytes(data);
        }
        
    }
    
    protected void writeData0(PacketBuf writer) {}
    
    protected boolean isSubclass() {
        return getClass() != HazelMessage.class;
    }
    
    @Override
    public void forwardPacket(ClientListener listener) {}
    
    public int getType() {
        return type;
    }
    
    public byte[] getData() {
        return data;
    }
}