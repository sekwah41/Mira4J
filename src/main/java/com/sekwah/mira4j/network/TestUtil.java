package com.sekwah.mira4j.network;

/**
 * Random tests and things that arnt needed for now
 */
public class TestUtil {

    public static int getVersion(int year, int month, int day, int revision) {
        return year * 25000 + month * 1800 + day * 50 + revision;
    }

    public static String getVersionString(int version) {
        int year = version / 25000;
        version %= 25000;
        int month = version / 1800;
        version %= 1800;
        int day = version / 50;
        int revision = version % 50;
        return String.format("v%s.%s.%s - %s", year, month, day, revision);
    }
}
