package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.storage.serialization.JsonSerializer;

public class JsonPathStorageTest extends AbstractStorageTest {

    public JsonPathStorageTest() {
        super(new PathStorage(STORAGE_DIR, new JsonSerializer()));
    }
    
}
