package ru.javawebinar.basejava.storage;

import java.util.Arrays;
import ru.javawebinar.basejava.model.Resume;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    public void save(final Resume r) {
        if(size >= STORAGE.length) {
            System.out.println("Storage is full");
            return;
        }
        int index = Arrays.binarySearch(STORAGE, 0, size, r);
        if(index >= 0) {
            System.out.printf("The resume %s is already exists\n", r);
            return;
        }
        index = Math.abs(index + 1);
        for(int i = size; i > index; i--) {
            STORAGE[i] = STORAGE[i - 1];
        }
        STORAGE[index] = r;
        size++;
    }

    @Override
    public void delete(final String uuid) {
        final Resume searchKey = new Resume(uuid);
        int index = Arrays.binarySearch(STORAGE, 0, size, searchKey);
        if(index < 0) {
            System.out.printf("The resume %s is not found\n", uuid);
            return;
        }
        for(int i = index; i < size; i++) {
            STORAGE[i] = STORAGE[i + 1];
        }
        STORAGE[size--] = null;
    }

    @Override
    public int getIndex(final String uuid) {
        final Resume searchKey = new Resume(uuid);
        return Arrays.binarySearch(STORAGE, 0, size, searchKey);
    }
}
