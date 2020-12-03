package com.sekwah.mira4j;

import com.sekwah.mira4j.config.ServerConfig;
import com.sekwah.mira4j.data.DataStorage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

public class Mira4J {

    private final DataStorage dataStorage;
    private final Server server;

    private final String SERVER_SETTINGS_LOC = "serversettings.json";

    public static final Logger LOGGER = LogManager.getLogger("Mira4J");

    public Mira4J() {
        dataStorage = new DataStorage(new File("./"));

        ServerConfig serverConfig = dataStorage.loadJson(ServerConfig.class, SERVER_SETTINGS_LOC);

        dataStorage.storeJson(serverConfig, SERVER_SETTINGS_LOC);

        LOGGER.info("Starting up server...");

        server = new Server();
    }

}
