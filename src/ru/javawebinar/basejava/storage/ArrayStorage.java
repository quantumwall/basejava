package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    public void save(final Resume r) {
        if(size >= STORAGE_LIMIT) {
            System.out.println("Storage is full");
            return;
        }
        if(getIndex(r.getUuid()) > -1) {
            System.out.printf("Resume %s is already present\n", r);
            return;
        }
        storage[size++] = r;
    }

    public void delete(final String uuid) {
        int resumeIndex = getIndex(uuid);
        if(resumeIndex < 0) {
            System.out.printf("Resume %s isn't present\n", uuid);
            return;
        }
        storage[resumeIndex] = storage[--size];
        storage[size] = null;
        
    }

    protected int getIndex(final String uuid) {
        for(int i = 0; i < size; i++) {
            if(storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
