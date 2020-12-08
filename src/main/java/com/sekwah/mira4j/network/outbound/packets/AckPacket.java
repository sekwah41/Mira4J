package com.sekwah.mira4j.network.outbound.packets;

import com.sekwah.mira4j.network.Packet;

import io.netty.buffer.ByteBuf;

public class AckPacket implements Packet<Object> {
    @Override
    public void readData(ByteBuf reader) {}

    @Override
    public void writeData(ByteBuf writer) {
        writer.writeByte(0);
    }

    @Override
    public void forwardPacket(Object obj) {}
}
