package com.sekwah.mira4j;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.config.Configurator;

public class Launch {

    public static void main(String[] args) {

        Configurator.setRootLevel(Level.INFO);

        // TODO Pass in command line arguments or other info at some point
        Mira4J mira4J = new Mira4J();
    }
}
