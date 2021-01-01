package com.sekwah.mira4j.api;

public abstract class Mira4JPlugin {
    /**
     * Called when the server loads this plugin
     */
    public void onEnable() {}

    /**
     * Called when the server disables this plugin
     */
    public void onDisable() {}
}
