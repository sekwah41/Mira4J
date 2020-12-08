package com.sekwah.mira4j.network;

import io.netty.buffer.ByteBuf;

public interface Packet<T> {
    void readData(ByteBuf reader);
    void writeData(ByteBuf writer);
    void forwardPacket(T listener);
}
