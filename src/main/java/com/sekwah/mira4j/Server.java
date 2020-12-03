package com.sekwah.mira4j;

import com.sekwah.mira4j.data.DataStorage;

public class Server {

    private static Server instance;

    public static Server getInstance() {
        return instance;
    }

    public Server(DataStorage dataStorage) {
        instance = this;
    }
}
