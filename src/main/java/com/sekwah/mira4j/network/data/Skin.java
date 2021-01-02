package com.sekwah.mira4j.network.data;

public enum Skin {

    NONE(0),
    ASTRONAUT(1),
    CAPTAIN(2),
    MECHANIC(3),
    MILITARY(4),
    POLICE(5),
    SCIENTIST(6),
    SUIT_BLACK(7),
    SUIT_WHITE(8),
    WALL_GUARD(9),
    HAZMAT(10),
    SECURITY_GUARD(11),
    TARMAC(12),
    MINER(13),
    WINTER(14),
    ARCHAEOLOGIST(15)
    ;

    final int id;
    private Skin(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static Skin fromId(int id) {
        for (Skin color : values()) {
            if (color.getId() == id) {
                return color;
            }
        }

        return null;
    }
}
