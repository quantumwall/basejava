package ru.javawebinar.basejava.storage;

import java.util.Arrays;
import ru.javawebinar.basejava.model.Resume;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage implements Storage {

    protected static final int STORAGE_LIMIT = 10000;
    protected final Resume[] STORAGE = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    @Override
    public int size() {
        return size;
    }

    @Override
    public final Resume get(final String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            System.out.println("Resume " + uuid + " is not found");
            return null;
        }
        return STORAGE[index];
    }
    
    @Override
    public void clear() {
        Arrays.fill(STORAGE, 0, size, null);
        size = 0;
    }
    
    @Override
    public Resume[] getAll() {
        return Arrays.copyOf(STORAGE, size);
    }
    
    @Override
    public final void update(final Resume r) {
        int index = getIndex(r.getUuid());
        if(index < 0) {
            System.out.printf("Resume %s is not found\n", r);
            return;
        } 
        STORAGE[index] = r;
    }

    protected abstract int getIndex(final String uuid);
}
