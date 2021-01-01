package com.sekwah.mira4j.network.packets.server;

import com.sekwah.mira4j.network.Packet;
import com.sekwah.mira4j.network.PacketBuf;
import com.sekwah.mira4j.network.data.MessageType;

public class SHazelMessage implements Packet<SClientListener> {
    private int type;
    private byte[] data;

    public SHazelMessage() {

    }

    public SHazelMessage(MessageType type) {
        this.type = type.getId();
    }

    @Override
    public void readData(PacketBuf reader) {

    }

    @Override
    public final void writeData(PacketBuf writer) {
        PacketBuf buf = PacketBuf.create(4096);
        writeData0(buf);
        byte[] data = buf.readBytes(buf.readableBytes());
        buf.release();
        writer.writeShort(data.length);
        writer.writeByte(type);
        writer.writeBytes(data);
    }

    protected void writeData0(PacketBuf writer) {}

    @Override
    public void forwardPacket(SClientListener listener) {}

    public byte[] getData() {
        return data;
    }
}
