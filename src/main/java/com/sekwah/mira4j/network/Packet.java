package com.sekwah.mira4j.network;

public interface Packet {

    void readData(PacketBuf reader);
    void writeData(PacketBuf writer);

}
