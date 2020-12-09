package com.sekwah.mira4j.network;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import com.sekwah.mira4j.Mira4J;
import com.sekwah.mira4j.network.inbound.packets.*;

public class Packets {
    private static final Map<PacketType, Class<? extends Packet<?>>> packets;
    
    static {
        packets = new HashMap<>();
        packets.put(PacketType.NORMAL, NormalPacket.class);
        packets.put(PacketType.RELIABLE, ReliablePacket.class);
        packets.put(PacketType.HELLO, HelloPacket.class);
        packets.put(PacketType.DISCONNECT, DisconnectPacket.class);
        packets.put(PacketType.ACKNOWLEDGEMENT, AcknowledgePacket.class);
        packets.put(PacketType.PING, PingPacket.class);
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
    
    public enum PacketType {
        NORMAL(0x00),
        RELIABLE(0x01),
        HELLO(0x08),
        DISCONNECT(0x09),
        ACKNOWLEDGEMENT(0x0a),
        FRAGMENT(0x0b),
        PING(0x0c);

        private int id;

        PacketType(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

        public static PacketType fromId(int id) {
            for (PacketType type : values()) {
                if (type.getId() == id) {
                    return type;
                }
            }
            return null;
        }
    }

    public enum Maps {
        THE_SKELD(0x00);

        private int id;

        Maps(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

        public static Maps fromId(int id) {
            for (Maps type : values()) {
                if (type.getId() == id) {
                    return type;
                }
            }
            return null;
        }
    }
    
    public enum MessageType {
        HostingGame(0),
        GameJoinDisconnect(1),
        GameStarted(2),
        PlayerLeft(4),
        GameInfo(5),
        GameInfoTo(6),
        JoinedGame(7),
        AlterGameInfo(10),
        KickPlayer(11),
        ChangeServer(13),
        ServerList(14),
        GameList(16)
        ;
        
        final int id;
        MessageType(int id) {
            this.id = id;
        }
        
        public static MessageType fromId(int id) {
            for (MessageType type : values()) {
                if (type.id == id) {
                    return type;
                }
            }
            return null;
        }
    }
}
