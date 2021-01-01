package com.sekwah.mira4j.network.decoder;

import com.sekwah.mira4j.network.PacketBuf;
import com.sekwah.mira4j.network.Packets;
import com.sekwah.mira4j.network.Packets.MessageType;
import com.sekwah.mira4j.network.packets.inbound.HazelMessage;

public class HazelDecoder {
    public static HazelMessage decode(PacketBuf reader) {
        int length = reader.readUnsignedShort();
        int type = reader.readUnsignedByte();

        HazelMessage packet = Packets.getHazelPacket(MessageType.fromId(type));
        if(packet == null) {
            reader.skipBytes(length);
            return null;
        }

        reader.markReaderIndex();
        packet.readData(reader);
        reader.resetReaderIndex();
        reader.skipBytes(length); // Make sure we read exactly this many bytes

        return packet;
    }
}

