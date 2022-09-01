package ru.javawebinar.basejava.storage;

import java.util.Arrays;
import ru.javawebinar.basejava.model.Resume;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage implements Storage {

    protected static final int STORAGE_LIMIT = 10000;
    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }
    
    @Override
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }
    
    @Override
    public final Resume get(final String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            System.out.println("Resume " + uuid + " is not found");
            return null;
        }
        return storage[index];
    }
    
    @Override
    public final void save(Resume r) {
        if(size >= STORAGE_LIMIT) {
            System.out.println("Storage is full");
            return;
        }
        int index = getIndex(r.getUuid());
        if(index > -1) {
            System.out.printf("Resume %s is already present\n", r);
            return;
        }
        index = Math.abs(index + 1);
        insertResume(r, index);
    }

    @Override
    public final void delete(String uuid) {
        int index = getIndex(uuid);
        if(index < 0) {
            System.out.printf("The resume %s is not found\n", uuid);
            return;
        }
        deleteResume(index);
    }
    
    @Override
    public final void update(final Resume r) {
        int index = getIndex(r.getUuid());
        if(index < 0) {
            System.out.printf("Resume %s is not found\n", r);
            return;
        } 
        storage[index] = r;
    }
    
    protected abstract int getIndex(final String uuid);
    
    protected abstract void insertResume(Resume r, int index);
    
    protected abstract void deleteResume(int index);
}
