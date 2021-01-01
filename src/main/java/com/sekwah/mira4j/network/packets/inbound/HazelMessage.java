package com.sekwah.mira4j.network.packets.inbound;

import com.sekwah.mira4j.network.Packet;
import com.sekwah.mira4j.network.PacketBuf;
import com.sekwah.mira4j.network.Packets.MessageType;

public class HazelMessage implements Packet<ClientListener> {
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
        writeData0(buf);
        byte[] data = buf.readBytes(buf.readableBytes());
        buf.release();
        writer.writeShort(data.length);
        writer.writeByte(type);
        writer.writeBytes(data);
    }

    protected void writeData0(PacketBuf writer) {}

    @Override
    public void forwardPacket(ClientListener listener) {}

    public byte[] getData() {
        return data;
    }
}
