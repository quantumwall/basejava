package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

/**
 * Array based STORAGE for Resumes
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
        STORAGE[size++] = r;
    }

    public void delete(final String uuid) {
        int resumeIndex = getIndex(uuid);
        if(resumeIndex < 0) {
            System.out.printf("Resume %s isn't present\n", uuid);
            return;
        }
        STORAGE[resumeIndex] = STORAGE[--size];
        STORAGE[size] = null;
        
    }

    protected int getIndex(final String uuid) {
        for(int i = 0; i < size; i++) {
            if(STORAGE[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
