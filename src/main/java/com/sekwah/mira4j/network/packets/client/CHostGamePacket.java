package com.sekwah.mira4j.network.packets.client;

import com.sekwah.mira4j.network.PacketBuf;
import com.sekwah.mira4j.network.data.MessageType;
import com.sekwah.mira4j.network.packets.HazelMessage;

public class CHostGamePacket extends HazelMessage {
    private int gameId;

    public CHostGamePacket(int gameId) {
        super(MessageType.HostGame);
        this.gameId = gameId;
    }

    @Override
    public void readData(PacketBuf reader) {

    }

    @Override
    public void writeHazelData(PacketBuf writer) {
        writer.writeInt(gameId);
    }
}
