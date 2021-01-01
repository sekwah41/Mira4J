package com.sekwah.mira4j.network.packets.client;

import com.sekwah.mira4j.network.PacketBuf;
import com.sekwah.mira4j.network.data.MessageType;
import com.sekwah.mira4j.network.packets.server.SHazelMessage;

public class CHostGamePacket extends SHazelMessage {
    private int gameId;

    public CHostGamePacket(int gameId) {
        super(MessageType.HostGame);
        this.gameId = gameId;
    }

    @Override
    public void readData(PacketBuf reader) {

    }

    @Override
    public void writeData0(PacketBuf writer) {
        writer.writeInt(gameId);
    }
}
