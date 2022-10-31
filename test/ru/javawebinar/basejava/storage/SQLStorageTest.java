package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.Config;

public class SQLStorageTest extends AbstractStorageTest{
    
    private static final Config config = Config.get();
    
    public SQLStorageTest() {
        super(config.getStorage());
    }

}
