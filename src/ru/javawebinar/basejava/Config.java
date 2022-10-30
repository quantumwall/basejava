package ru.javawebinar.basejava;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {

    private final File PROPERTY_FILE = new File("config/resumes.properties");
    private final Properties properties = new Properties();
    private final File storageDir;
    private final String dbURL;
    private final String dbUser;
    private final String dbPassword;

    private Config() {
        try ( InputStream input = new FileInputStream(PROPERTY_FILE)) {
            properties.load(input);
            storageDir = new File(properties.getProperty("storage.dir"));
            dbURL = properties.getProperty("db.url");
            dbUser = properties.getProperty("db.user");
            dbPassword = properties.getProperty("db.password");
        } catch (IOException e) {
            throw new IllegalStateException("Invalig config file " + PROPERTY_FILE.getAbsolutePath());
        }
    }

    public static Config get() {
        return ConfigHolder.INSTANCE;
    }

    public File getStorageDir() {
        return storageDir;
    }

    public String getDbURL() {
        return dbURL;
    }

    public String getDbUser() {
        return dbUser;
    }

    public String getDbPassword() {
        return dbPassword;
    }

    private static class ConfigHolder {

        private static final Config INSTANCE = new Config();
    }
}
