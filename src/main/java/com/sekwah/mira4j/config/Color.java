package com.sekwah.mira4j.config;

public enum Color {
    RED(0),
    BLUE(1),
    GREEN(2),
    PINK(3),
    ORANGE(4),
    YELLOW(5),
    GREY(6),
    WHITE(7),
    PURPLE(8),
    BROWN(9),
    CYAN(10),
    LIGHT_GREEN(11);
    
    final int id;
    private Color(int id) {
        this.id = id;
    }
    
    public int getId() {
        return id;
    }

    public static Color fromId(int id) {
        for (Color color : values()) {
            if (color.getId() == id) {
                return color;
            }
        }
        
        return null;
    }
}
