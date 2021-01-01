package com.sekwah.mira4j.network.data;
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
