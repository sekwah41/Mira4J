package com.sekwah.mira4j.network;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import com.sekwah.mira4j.Mira4J;
import com.sekwah.mira4j.network.data.MessageType;
import com.sekwah.mira4j.network.data.PacketType;
import com.sekwah.mira4j.network.packets.server.*;
import com.sekwah.mira4j.network.packets.server.hazel.HostGamePacket;

public class Packets {
    private static final Map<PacketType, Class<? extends Packet<?>>> packets;
    private static final Map<MessageType, Class<? extends SHazelMessage>> hazel_packets;

    static {
        packets = new HashMap<>();
        packets.put(PacketType.NORMAL, SNormalPacket.class);
        packets.put(PacketType.RELIABLE, SReliablePacket.class);
        packets.put(PacketType.HELLO, SHelloPacket.class);
        packets.put(PacketType.DISCONNECT, SDisconnectPacket.class);
        packets.put(PacketType.ACKNOWLEDGEMENT, SAcknowledgePacket.class);
        packets.put(PacketType.PING, SPingPacket.class);


        hazel_packets = new HashMap<>();
        hazel_packets.put(MessageType.HostGame, HostGamePacket.class);
    }

    public static Packet<?> getPacketFromType(PacketType type) {
        Class<? extends Packet<?>> clazz = packets.get(type);
        if(clazz == null) {
            Mira4J.LOGGER.error("Failed to create packet of type {}", type.toString());
            return null;
        }

        try {
            return clazz.getConstructor().newInstance();
        } catch(InstantiationException | IllegalAccessException | IllegalArgumentException
           | InvocationTargetException | NoSuchMethodException | SecurityException e) {
            Mira4J.LOGGER.error("Failed to create packet of type {}", type.toString());
            e.printStackTrace();
        }

        return null;
    }

    public static PacketType getPacketType(Class<?> clazz) {
        for(PacketType key : packets.keySet()) {
            Class<? extends Packet<?>> item = packets.get(key);
            if(item.equals(clazz)) return key;
        }
        return null;
    }

    public static SHazelMessage getHazelPacket(MessageType type) {
        Class<? extends SHazelMessage> clazz = hazel_packets.get(type);
        if(clazz == null) {
            Mira4J.LOGGER.error("Failed to create packet of type {}", type.toString());
            return null;
        }

        try {
            return clazz.getConstructor().newInstance();
        } catch(InstantiationException | IllegalAccessException | IllegalArgumentException
           | InvocationTargetException | NoSuchMethodException | SecurityException e) {
            Mira4J.LOGGER.error("Failed to create packet of type {}", type.toString());
            e.printStackTrace();
        }

        return null;
    }

}
