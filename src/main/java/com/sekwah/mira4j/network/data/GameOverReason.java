package com.sekwah.mira4j.network.data;

public enum GameOverReason {

    CREWMATES_BY_VOTE(0),
    CREWMATES_BY_TASK(1),
    IMPOSTORS_BY_VOTE(2),
    IMPOSTORS_BY_KILL(3),
    IMPOSTORS_BY_SABOTAGE(4),
    IMPOSTOR_DISCONNECT(5),
    CREWMATE_DISCONNECT(6)
    ;

    final int id;
    private GameOverReason(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static GameOverReason fromId(int id) {
        for (GameOverReason reason : values()) {
            if (reason.getId() == id) {
                return reason;
            }
        }

        return null;
    }
}
