package com.sekwah.mira4j.network.packets.outbound;

import com.sekwah.mira4j.network.PacketBuf;
import com.sekwah.mira4j.network.Packets.MessageType;
import com.sekwah.mira4j.network.packets.inbound.HazelMessage;

public class HostGame extends HazelMessage {
    private int gameId;

    public HostGame(int gameId) {
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
