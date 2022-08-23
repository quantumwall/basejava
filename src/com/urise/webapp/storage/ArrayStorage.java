package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;
import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    private int size = 0;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void save(Resume r) {
        if(r != null) {
            if(size < storage.length) {
                if(searchResume(r.getUuid()) > -1) {
                    System.out.printf("Resume %s is already present\n", r);
                } else {
                    storage[size++] = r;
                }
            } else {
                System.out.println("Storage is full");
            }
        } else {
            System.out.println("null passed");
        }
    }

    public Resume get(String uuid) {
        if(uuid != null) {
            int resumeIndex = searchResume(uuid);
            if(resumeIndex > -1) {
                return storage[resumeIndex];
            }
            System.out.printf("Resume %s isn't present\n", uuid);
        } else {
            System.out.println("null passed");
        }
        return null;
    }

    public void delete(String uuid) {
        if(uuid != null) {
            int resumeIndex = searchResume(uuid);
            if(resumeIndex > -1) {
                storage[resumeIndex] = storage[--size];
                storage[size] = null;
            } else {
                System.out.printf("Resume %s isn't present\n", uuid);
            }
        } else {
            System.out.println("null passed");
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
        if(r != null) {
            int resumeIndex = searchResume(r.getUuid());
            if(resumeIndex > -1) {
                storage[resumeIndex] = r;
            } else {
                System.out.printf("Resume %s is not found\n", r);
            }
        } else {
            System.out.println("null passed");
        }
    }

    /**
     * @return int, index of the found resume or
     * -1 if isn't present
     */
    private int searchResume(String uuid) {
        for(int i = 0; i < size; i++) {
            if(storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
