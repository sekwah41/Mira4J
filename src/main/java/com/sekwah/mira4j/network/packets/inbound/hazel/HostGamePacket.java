package com.sekwah.mira4j.network.packets.inbound.hazel;

import com.sekwah.mira4j.game.GameOptionsData;
import com.sekwah.mira4j.network.PacketBuf;
import com.sekwah.mira4j.network.packets.inbound.ClientListener;
import com.sekwah.mira4j.network.packets.inbound.HazelMessage;

public class HostGamePacket extends HazelMessage {
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
    public void writeData0(PacketBuf writer) {
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
