package com.sekwah.mira4j.network;

import com.sekwah.mira4j.network.Packets.MessageType;
import com.sekwah.mira4j.network.inbound.packets.ClientListener;
import com.sekwah.mira4j.network.inbound.packets.HazelMessage;
import com.sekwah.mira4j.network.inbound.packets.ReliablePacket;

public class ReliablePacketDecoder {
    public static void decode(ReliablePacket packet, ClientListener listener) {
        HazelMessage message = new HazelMessage();
        message.readData(PacketBuf.wrap(packet.getData()));
        
        Packet<?> pck = Packets.getHazelPacket(MessageType.fromId(message.getType()));
        if(pck == null) return;
        
        pck.readData(PacketBuf.wrap(packet.getData()));
        forward(pck, listener);
    }
    
    @SuppressWarnings("unchecked")
    private static <T> void forward(Packet<T> packet, ClientListener listener) {
        try {
            packet.forwardPacket((T)listener);
        } catch(ClassCastException e) {
            e.printStackTrace();
        }
    }
}
