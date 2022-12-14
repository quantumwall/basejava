package ru.javawebinar.basejava;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import ru.javawebinar.basejava.storage.SQLStorage;
import ru.javawebinar.basejava.storage.Storage;

public class Config {

//    private final File PROPERTY_FILE = new File("/data/IT/projects/java/basejava/config/resumes.properties");
    private final String PROPERTY_FILE = System.getenv("BASEJAVA_PROPS");
    private final File storageDir;
    private final Storage storage;

    private Config() {
        try (InputStream input = new FileInputStream(PROPERTY_FILE)) {
            Properties properties = new Properties();
            properties.load(input);
            storageDir = new File(properties.getProperty("storage.dir"));
            storage = new SQLStorage(properties.getProperty("db.url"), properties.getProperty("db.user"), properties.getProperty("db.password"));
        } catch (IOException e) {
            throw new IllegalStateException("Invalig config file " + PROPERTY_FILE);
        }
    }

    public static Config get() {
        return ConfigHolder.INSTANCE;
    }

    public Storage getStorage() {
        return storage;
    }

    public File getStorageDir() {
        return storageDir;
    }

    private static class ConfigHolder {

        private static final Config INSTANCE = new Config();
    }
}
