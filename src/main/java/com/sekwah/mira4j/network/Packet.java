package com.sekwah.mira4j.network;

public interface Packet<T> {
    void readData(PacketBuf reader);
    void writeData(PacketBuf writer);
    void forwardPacket(T listener);
}
