package com.sekwah.mira4j.network.inbound.packets.hazel;

import com.sekwah.mira4j.game.GameOptionsData;
import com.sekwah.mira4j.network.Packet;
import com.sekwah.mira4j.network.PacketBuf;
import com.sekwah.mira4j.network.inbound.packets.ClientListener;

public class HostGamePacket implements Packet<ClientListener> {
    private GameOptionsData data;
    
    public HostGamePacket() {
        
    }
    
    public HostGamePacket(GameOptionsData data) {
        this.data = data;
    }
    
    @Override
    public void readData(PacketBuf reader) {
        this.data = reader.readGameOptionsData();
    }

    @Override
    public void writeData(PacketBuf writer) {
        writer.writeGameOptionsData(data);
    }

    @Override
    public void forwardPacket(ClientListener listener) {
        listener.onHostGamePacket(this);
    }
    
    public GameOptionsData getGameOptionsData() {
        return data;
    }
}
