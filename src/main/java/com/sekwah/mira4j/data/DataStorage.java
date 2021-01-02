package com.sekwah.mira4j.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;

public class DataStorage {

    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private File dataFolder;

    public DataStorage(File dataFolder) {
        this.dataFolder = dataFolder;
    }

    public <T> T loadJson(Class<T> dataHolder, String location) {
        InputStream jsonResource = this.loadResource(location);
        if(jsonResource == null) {
            try {
                return dataHolder.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            return null;
        }
        BufferedReader bufReader = new BufferedReader(new InputStreamReader(jsonResource));
        return gson.fromJson(bufReader, dataHolder);
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

    public InputStream loadResource(String location) {
        File inFile = new File(dataFolder, location);
        if (inFile.exists() && !inFile.isDirectory()) {
            try {
                return new FileInputStream(inFile);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
