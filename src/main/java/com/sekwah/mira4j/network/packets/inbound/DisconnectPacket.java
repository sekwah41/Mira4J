package com.sekwah.mira4j.network.packets.inbound;

import com.sekwah.mira4j.network.Packet;
import com.sekwah.mira4j.network.PacketBuf;

public class DisconnectPacket implements Packet<ClientListener> {
    @Override
    public void readData(PacketBuf reader) {}

    @Override
    public void writeData(PacketBuf writer) {}

    @Override
    public void forwardPacket(ClientListener listener) {
        listener.onDisconnectPacket(this);
    }
}
