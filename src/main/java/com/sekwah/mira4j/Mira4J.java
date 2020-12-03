package com.sekwah.mira4j;

import com.sekwah.mira4j.data.DataStorage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

public class Mira4J {

    private final DataStorage dataStorage;
    private final Server server;

    public static final Logger LOGGER = LogManager.getLogger("Mira4J");

    public Mira4J() {
        dataStorage = new DataStorage(new File("./"));

        LOGGER.info("Starting up server...");

        server = new Server(dataStorage);
    }

}
