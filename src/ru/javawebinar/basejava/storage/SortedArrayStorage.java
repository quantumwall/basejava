package ru.javawebinar.basejava.storage;

import java.util.Arrays;
import ru.javawebinar.basejava.model.Resume;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    public void save(final Resume r) {
        if(size >= storage.length) {
            System.out.println("Storage is full");
            return;
        }
        int index = Arrays.binarySearch(storage, 0, size, r);
        if(index >= 0) {
            System.out.printf("The resume %s is already exists\n", r);
            return;
        }
        index = Math.abs(index + 1);
        for(int i = size; i > index; i--) {
            storage[i] = storage[i - 1];
        }
        storage[index] = r;
        size++;
    }

    @Override
    public void delete(final String uuid) {
        final Resume searchKey = new Resume(uuid);
        int index = Arrays.binarySearch(storage, 0, size, searchKey);
        if(index < 0) {
            System.out.printf("The resume %s is not found\n", uuid);
            return;
        }
        for(int i = index; i < size; i++) {
            storage[i] = storage[i + 1];
        }
        storage[size--] = null;
    }

    @Override
    public int getIndex(final String uuid) {
        final Resume searchKey = new Resume(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }
}
