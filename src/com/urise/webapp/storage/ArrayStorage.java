package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;
import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    protected static final int STORAGE_LIMIT = 10000;
    private final Resume[] storage = new Resume[STORAGE_LIMIT];
    private int size = 0;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void save(Resume r) {
        if(size >= STORAGE_LIMIT) {
            System.out.println("Storage is full");
        } else if(findResumeIndex(r.getUuid()) > -1) {
            System.out.printf("Resume %s is already present\n", r);
        } else {
            storage[size++] = r;
        }
    }

    public Resume get(String uuid) {
        int resumeIndex = findResumeIndex(uuid);
        if(resumeIndex > -1) {
            return storage[resumeIndex];
        }
        System.out.printf("Resume %s isn't present\n", uuid);
        return null;
    }

    public void delete(String uuid) {
        int resumeIndex = findResumeIndex(uuid);
        if(resumeIndex > -1) {
            storage[resumeIndex] = storage[--size];
            storage[size] = null;
        } else {
            System.out.printf("Resume %s isn't present\n", uuid);
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }

    public void update(Resume r) {
        int resumeIndex = findResumeIndex(r.getUuid());
        if(resumeIndex > -1) {
            storage[resumeIndex] = r;
        } else {
            System.out.printf("Resume %s is not found\n", r);
        }
    }

    /**
     * @return int, index of the found resume or
     * -1 if isn't present
     */
    private int findResumeIndex(String uuid) {
        for(int i = 0; i < size; i++) {
            if(storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
