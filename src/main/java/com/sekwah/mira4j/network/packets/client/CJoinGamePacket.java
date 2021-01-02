package com.sekwah.mira4j.network.packets.client;

import com.sekwah.mira4j.network.PacketBuf;
import com.sekwah.mira4j.network.data.MessageType;
import com.sekwah.mira4j.network.packets.HazelMessage;

public class CJoinGamePacket extends HazelMessage {
    public CJoinGamePacket(int gameId) {
        super(MessageType.JoinGame);
    }

    @Override
    public void readData(PacketBuf reader) {

    }

    @Override
    public void writeHazelData(PacketBuf writer) {

    }
}
