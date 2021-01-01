package com.sekwah.mira4j.api;

public interface GameLobby {
    /**
     * Returns the game id of this lobby.
     * Has to be exactly 6 characters long (due to packets)
     * @return the game id of this lobby
     */
    int getGameId();

    int getNumPlayers();

}
