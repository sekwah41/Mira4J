package com.sekwah.mira4j.network.outbound.packets;

import com.sekwah.mira4j.network.PacketBuf;
import com.sekwah.mira4j.network.Packets.MessageType;
import com.sekwah.mira4j.network.inbound.packets.HazelMessage;

public class HostGame extends HazelMessage {
    private int gameId;
    
    public HostGame(int gameId) {
        this.type = MessageType.HostingGame.getId();
        this.gameId = gameId;
    }
    
    @Override
    public void writeData(PacketBuf writer) {
        PacketBuf buf = PacketBuf.create(4096);
        buf.writeInt(gameId);
        
        this.data = buf.readBytes(buf.readableBytes());
        buf.release();
        super.writeData(writer);
    }
    
}
