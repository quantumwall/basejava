package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.storage.serialization.DataSerializer;

public class DataPathStorageTest extends AbstractStorageTest {

    public DataPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getAbsolutePath(), new DataSerializer()));
    }
    
}
