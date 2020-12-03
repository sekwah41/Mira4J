package com.sekwah.mira4j.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class DataStorage {

    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private File dataFolder;

    public DataStorage(File dataFolder) {
        this.dataFolder = dataFolder;
    }

    public void storeJson(Object dataHolder, String location) {
        String json = gson.toJson(dataHolder);
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(new File(this.dataFolder, location));
            fileWriter.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                assert fileWriter != null;
                fileWriter.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
