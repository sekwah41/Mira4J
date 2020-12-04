package com.sekwah.mira4j.network;

public class Packets {

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

}
