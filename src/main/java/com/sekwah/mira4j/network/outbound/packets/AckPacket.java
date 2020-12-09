package com.sekwah.mira4j.network.outbound.packets;

import com.sekwah.mira4j.network.Packet;
import com.sekwah.mira4j.network.PacketBuf;

public class AckPacket implements Packet<Object> {
    @Override
    public void readData(PacketBuf reader) {}

    @Override
    public void writeData(PacketBuf writer) {
        writer.writeByte(0);
    }

    @Override
    public void forwardPacket(Object obj) {}
}
