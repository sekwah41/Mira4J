package com.sekwah.mira4j.network.data;

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
