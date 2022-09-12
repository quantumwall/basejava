package ru.javawebinar.basejava.storage;

import static ru.javawebinar.basejava.storage.AbstractArrayStorage.STORAGE_LIMIT;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractArrayStorageTest {

    protected final Storage storage;
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid34";
    private static final String UUID_3 = "uuid5";
    private static final String UUID_4 = "uuid29";
    private static final String UUID_NOT_EXIST = "dummy";
    private static final Resume RESUME_1 = new Resume(UUID_1);
    private static final Resume RESUME_2 = new Resume(UUID_2);
    private static final Resume RESUME_3 = new Resume(UUID_3);
    private static final Resume RESUME_4 = new Resume(UUID_4);

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @BeforeEach
    public void setUp() {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }

    @Test
    public void size() {
        assertSize(3);
    }

    private void assertSize(int expResult) {
        assertEquals(expResult, storage.size());
    }

    @Test
    public void clear() {
        storage.clear();
        assertSize(0);
        Resume[] emptyArr = new Resume[0];
        assertArrayEquals(emptyArr, storage.getAll());
    }

    @Test
    public void getAll() {
        assertSize(3);
        Resume[] expResult = {RESUME_1, RESUME_2, RESUME_3};
        assertArrayEquals(expResult, storage.getAll());
    }

    @Test
    public void get() {
        assertGet(RESUME_1);
        assertGet(RESUME_2);
        assertGet(RESUME_3);
    }

    private void assertGet(Resume expResult) {
        Resume result;
        try {
            result = storage.get(expResult.getUuid());
            assertEquals(expResult, result);
        } catch (NotExistStorageException e) {
            fail(String.format("Searched resume %s is not found\n", expResult));
        }
    }

    @Test
    public void getNotExistingResume() {
        assertThrows(NotExistStorageException.class, () -> storage.get(UUID_NOT_EXIST));
    }

    @Test
    public void save() {
        assertDoesNotThrow(() -> storage.save(RESUME_4));
        assertGet(RESUME_4);
        assertSize(4);
    }

    @Test
    public void saveExistingResume() {
        assertThrows(ExistStorageException.class, () -> storage.save(RESUME_1));
    }

    @Test
    public void delete() {
        assertDoesNotThrow(() -> storage.delete(UUID_1));
        assertSize(2);
        assertThrows(NotExistStorageException.class, () -> storage.get(UUID_1));
    }

    @Test
    public void deleteNotExistingResume() {
        assertThrows(NotExistStorageException.class, () -> storage.delete(UUID_NOT_EXIST));
    }

    @Test
    public void saveOverflow() {
        storage.clear();
        try {
            for (int i = 0; i < STORAGE_LIMIT; i++) {
                storage.save(new Resume(String.format("uuid%d", i)));
            }
        } catch (StorageException e) {
            fail("Premature storage overflow");
        }
        assertThrows(StorageException.class, () -> storage.save(new Resume(UUID_NOT_EXIST)));
    }

    @Test
    public void update() {
        Resume resumeToUpdate = new Resume(UUID_1);
        assertDoesNotThrow(() -> storage.update(resumeToUpdate));
        assertSame(resumeToUpdate, storage.get(UUID_1));
        assertSize(3);
    }

    @Test
    public void updateNotExistingResume() {
        Resume resumeToUpdate = new Resume(UUID_NOT_EXIST);
        assertThrows(NotExistStorageException.class, () -> storage.update(resumeToUpdate));
    }
}
