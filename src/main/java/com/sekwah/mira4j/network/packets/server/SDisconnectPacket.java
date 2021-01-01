package com.sekwah.mira4j.network.packets.server;

import com.sekwah.mira4j.network.Packet;
import com.sekwah.mira4j.network.PacketBuf;

public class SDisconnectPacket implements Packet<SClientListener> {
    @Override
    public void readData(PacketBuf reader) {}

    @Override
    public void writeData(PacketBuf writer) {}

    @Override
    public void forwardPacket(SClientListener listener) {
        listener.onDisconnectPacket(this);
    }
}
