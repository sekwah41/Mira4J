package com.sekwah.mira4j.network;

public enum MessageType {
    HOST_GAME(0),
    JOIN_GAME(1),
    START_GAME(2),
    REMOVE_GAME(3),
    REMOVE_PLAYER(4),
    JOIND_GAME(7),
    END_GAME(8),
    ALTER_GAME(10),
    KICK_PLAYER(11);
    
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
