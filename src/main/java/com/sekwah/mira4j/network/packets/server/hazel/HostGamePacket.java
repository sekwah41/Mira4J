package com.sekwah.mira4j.network.packets.server.hazel;

import com.sekwah.mira4j.game.GameOptionsData;
import com.sekwah.mira4j.network.PacketBuf;
import com.sekwah.mira4j.network.packets.HazelMessage;

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
    public void writeHazelData(PacketBuf writer) {
        writer.writeGameOptionsData(data);
    }

    public GameOptionsData getGameOptionsData() {
        return data;
    }
}
