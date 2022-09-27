package ru.javawebinar.basejava.storage;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static ru.javawebinar.basejava.storage.AbstractArrayStorage.STORAGE_LIMIT;

import org.junit.jupiter.api.Test;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractArrayStorageTest extends AbstractStorageTest {

    public AbstractArrayStorageTest(Storage storage) {
        super(storage);
    }

    @Test
    public void saveOverflow() {
        storage.clear();
        try {
            for (int i = 0; i < STORAGE_LIMIT; i++) {
                storage.save(new Resume(String.format("uuid%d", i), String.format("name%d", i)));
            }
        } catch (StorageException e) {
            fail("Premature storage overflow");
        }
        assertThrows(StorageException.class, () -> storage.save(new Resume(UUID_NOT_EXIST, NAME_1)));
    }
}
