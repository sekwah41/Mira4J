package com.sekwah.mira4j.network.packets;

import com.sekwah.mira4j.network.Packet;
import com.sekwah.mira4j.network.PacketBuf;
import com.sekwah.mira4j.network.data.MessageType;

public abstract class HazelMessage implements Packet {
    private int type;
    private byte[] data;

    public HazelMessage() {

    }

    public HazelMessage(MessageType type) {
        this.type = type.getId();
    }

    @Override
    public void readData(PacketBuf reader) {

    }

    @Override
    public final void writeData(PacketBuf writer) {
        PacketBuf buf = PacketBuf.create(4096);
        writeHazelData(buf);
        byte[] data = buf.readBytes(buf.readableBytes());
        buf.release();
        writer.writeShort(data.length);
        writer.writeByte(type);
        writer.writeBytes(data);
    }

    public abstract void writeHazelData(PacketBuf writer);

    public byte[] getData() {
        return data;
    }
}
