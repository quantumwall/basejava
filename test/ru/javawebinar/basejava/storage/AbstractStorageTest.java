package ru.javawebinar.basejava.storage;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractStorageTest {

    protected final Storage storage;
    protected static final String UUID_1 = "uuid1";
    protected static final String UUID_2 = "uuid34";
    protected static final String UUID_3 = "uuid5";
    protected static final String UUID_4 = "uuid29";
    protected static final String UUID_NOT_EXIST = "dummy";
    protected static final Resume RESUME_1 = new Resume(UUID_1);
    protected static final Resume RESUME_2 = new Resume(UUID_2);
    protected static final Resume RESUME_3 = new Resume(UUID_3);
    protected static final Resume RESUME_4 = new Resume(UUID_4);

    public AbstractStorageTest(Storage storage) {
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
        result = storage.get(expResult.getUuid());
        assertEquals(expResult, result);
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
    public void update() {
        Resume resumeToUpdate = new Resume(UUID_1);
        assertDoesNotThrow(() -> storage.update(resumeToUpdate));
        assertTrue(resumeToUpdate == storage.get(UUID_1));
//        assertSame(resumeToUpdate, storage.get(UUID_1));
        assertSize(3);
    }

    @Test
    public void updateNotExistingResume() {
        Resume resumeToUpdate = new Resume(UUID_NOT_EXIST);
        assertThrows(NotExistStorageException.class, () -> storage.update(resumeToUpdate));
    }
}
