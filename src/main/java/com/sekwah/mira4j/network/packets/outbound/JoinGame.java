package com.sekwah.mira4j.network.packets.outbound;

import com.sekwah.mira4j.network.PacketBuf;
import com.sekwah.mira4j.network.Packets.MessageType;
import com.sekwah.mira4j.network.packets.inbound.HazelMessage;

public class JoinGame extends HazelMessage {
    public JoinGame(int gameId) {
        super(MessageType.JoinGame);
    }

    @Override
    public void readData(PacketBuf reader) {

    }

    @Override
    public void writeData0(PacketBuf writer) {

    }
}
