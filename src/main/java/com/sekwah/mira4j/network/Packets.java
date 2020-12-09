package com.sekwah.mira4j.network;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import com.sekwah.mira4j.Mira4J;
import com.sekwah.mira4j.network.inbound.packets.*;
import com.sekwah.mira4j.network.inbound.packets.hazel.HostGamePacket;

public class Packets {
    private static final Map<PacketType, Class<? extends Packet<?>>> packets;
    private static final Map<MessageType, Class<? extends Packet<?>>> hazel_packets;
    
    static {
        packets = new HashMap<>();
        packets.put(PacketType.NORMAL, NormalPacket.class);
        packets.put(PacketType.RELIABLE, ReliablePacket.class);
        packets.put(PacketType.HELLO, HelloPacket.class);
        packets.put(PacketType.DISCONNECT, DisconnectPacket.class);
        packets.put(PacketType.ACKNOWLEDGEMENT, AcknowledgePacket.class);
        packets.put(PacketType.PING, PingPacket.class);
        

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
    
    public static Packet<?> getHazelPacket(MessageType type) {
        Class<? extends Packet<?>> clazz = hazel_packets.get(type);
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
        THE_SKELD(0),
        MIRA_HQ(1),
        POLUS(2);

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
        HostGame(0),
        JoinGame(1),
        StartGame(2),
        RemoveGame(3),
        RemovePlayer(4),
        GameData(5),
        GameDataTo(6),
        JoinedGame(7),
        EndGame(8),
        GetGameList(9),
        AlterGame(10),
        KickPlayer(11),
        WaitForHost(12),
        Redirect(13),
        ReselectServer(14),
        GetGameListV2(16)
        ;
        
        final int id;
        MessageType(int id) {
            this.id = id;
        }
        
        public int getId() {
            return id;
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
