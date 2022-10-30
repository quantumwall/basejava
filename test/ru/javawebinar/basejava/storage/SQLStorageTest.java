package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.Config;

public class SQLStorageTest extends AbstractStorageTest{
    
    private static final Config config = Config.get();
    
    public SQLStorageTest() {
        super(new SQLStorage(config.getDbURL(), config.getDbUser(), config.getDbPassword()));
    }

}
