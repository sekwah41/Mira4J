package com.sekwah.mira4j.network.outbound.packets;

import com.sekwah.mira4j.network.PacketBuf;
import com.sekwah.mira4j.network.Packets.MessageType;
import com.sekwah.mira4j.network.inbound.packets.HazelMessage;

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
