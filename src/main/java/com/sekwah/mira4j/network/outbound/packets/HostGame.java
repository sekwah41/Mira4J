package com.sekwah.mira4j.network.outbound.packets;

import com.sekwah.mira4j.network.PacketBuf;
import com.sekwah.mira4j.network.Packets.MessageType;
import com.sekwah.mira4j.network.inbound.packets.HazelMessage;

public class HostGame extends HazelMessage {
    private int gameId;
    
    public HostGame(int gameId) {
        this.type = MessageType.HostGame.getId();
        this.gameId = gameId;
    }
    
    @Override
    public void writeData0(PacketBuf writer) {
        writer.writeInt(gameId);
    }
}
