package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    protected int getIndex(final String uuid) {
        for(int i = 0; i < size; i++) {
            if(storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -(size + 1);
    }

    @Override
    protected void insertResume(Resume r, int index) {
        storage[index] = r;
        size++;
    }

    @Override
    protected void deleteResume(int index) {
        storage[index] = storage[--size];
        storage[size] = null;
    }
}
