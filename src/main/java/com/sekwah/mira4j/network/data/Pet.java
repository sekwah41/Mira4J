package com.sekwah.mira4j.network.data;

public enum Pet {

    NONE(0),
    ALIEN(1),
    MINI_CREWMATE(2),
    DOG(3),
    HENRY_STICKMIN(4),
    HAMSTER(5),
    ROBOT(6),
    UFO(7),
    ELLIE_ROSE(8),
    SQUIG(9),
    BEDCRAB(10),
    GLITCH(11)
    ;

    final int id;
    private Pet(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static Pet fromId(int id) {
        for (Pet color : values()) {
            if (color.getId() == id) {
                return color;
            }
        }

        return null;
    }
}
