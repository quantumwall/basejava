package ru.javawebinar.basejava.storage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;
import static ru.javawebinar.basejava.storage.AbstractArrayStorage.STORAGE_LIMIT;

public abstract class AbstractArrayStorageTest {

    protected final Storage storage;

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @BeforeEach
    public void setUp() {
        storage.clear();
        storage.save(new Resume("uuid1"));
        storage.save(new Resume("uuid34"));
        storage.save(new Resume("uuid5"));
        storage.save(new Resume("uuid29"));

    }

    @Test
    public void size() {
        int expResult = 4;
        assertEquals(expResult, storage.size());
    }

    @Test
    public void clear() {
        storage.clear();
        Resume[] emptyArr = new Resume[0];
        assertArrayEquals(emptyArr, storage.getAll());
    }

    @Test
    public void getAll(Resume... expResult) {
        if(expResult.length == 0) {
            expResult = new Resume[]{
                new Resume("uuid1"),
                new Resume("uuid34"),
                new Resume("uuid5"),
                new Resume("uuid29")
            };
        }
        Resume[] result = storage.getAll();
        assertArrayEquals(expResult, result);
    }

    @Test
    public void getExistingResume() {
        String uuid = "uuid5";
        assertDoesNotThrow(() -> storage.get(uuid));
    }

    @Test
    public void getNotExistingResume() {
        String uuid = "uuid6";
        assertThrows(NotExistStorageException.class, () -> storage.get(uuid));
    }

    @Test
    public void saveNotExistingResume() {
        Resume r = new Resume("uuid777");
        assertDoesNotThrow(() -> storage.save(r));
    }

    @Test
    public void saveExistingResume() {
        Resume r = new Resume("uuid34");
        assertThrows(ExistStorageException.class, () -> storage.save(r));
    }

    @Test
    public void deleteExistingResume() {
        String uuid = "uuid34";
        assertDoesNotThrow(() -> storage.delete(uuid));
    }

    @Test
    public void deleteNotExistingResume() {
        String uuid = "uuid777";
        assertThrows(NotExistStorageException.class, () -> storage.delete(uuid));
    }
    
    @Test
    public void overflowStorage() {
        try {
            for(int i = storage.size(); i < STORAGE_LIMIT; i++) {
                storage.save(new Resume(String.format("uuid%d", i + 100)));
            }
        } catch(StorageException e) {
            fail("Premature storage overflow");
        }
        assertThrows(StorageException.class, () -> storage.save(new Resume("uuid-1")));
    }
}
