package ru.javawebinar.basejava.storage;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.model.ResumeTestData;

public abstract class AbstractStorageTest {

    protected static final String STORAGE_DIR = "storage";
    protected final Storage storage;
    protected static final String UUID_1 = "uuid1";
    protected static final String UUID_2 = "uuid34";
    protected static final String UUID_3 = "uuid5";
    protected static final String UUID_4 = "uuid29";
    protected static final String NAME_1 = "Annabel";
    protected static final String NAME_2 = "Bettany";
    protected static final String NAME_3 = "Catherine";
    protected static final String NAME_4 = "Dana";
    protected static final String UUID_NOT_EXIST = "dummy";
    protected static final Resume RESUME_1 = ResumeTestData.getResume(UUID_1, NAME_1);
    protected static final Resume RESUME_2 = ResumeTestData.getResume(UUID_2, NAME_2);
    protected static final Resume RESUME_3 = ResumeTestData.getResume(UUID_3, NAME_3);
    protected static final Resume RESUME_4 = ResumeTestData.getResume(UUID_4, NAME_4);

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
        List<Resume> emptyList = new ArrayList<>();
        assertEquals(emptyList, storage.getAllSorted());
    }

    @Test
    public void getAllSorted() {
        assertSize(3);
        List<Resume> expResult = new ArrayList<>();
        expResult.add(RESUME_1);
        expResult.add(RESUME_2);
        expResult.add(RESUME_3);
        assertEquals(expResult, storage.getAllSorted());
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
        Resume resumeToUpdate = ResumeTestData.getResume(UUID_1, NAME_4);
        assertDoesNotThrow(() -> storage.update(resumeToUpdate));
        assertTrue(resumeToUpdate.equals(storage.get(UUID_1)));
        assertSize(3);
    }

    @Test
    public void updateNotExistingResume() {
        Resume resumeToUpdate = new Resume(UUID_NOT_EXIST, NAME_4);
        assertThrows(NotExistStorageException.class, () -> storage.update(resumeToUpdate));
    }
}
