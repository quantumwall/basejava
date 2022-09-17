package ru.javawebinar.basejava.storage;

import java.util.Arrays;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage extends AbstractStorage {

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
    public final void save(Resume r) {
        int index = getIndex(r.getUuid());
        if (size >= STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", r.getUuid());
        } else if (index > -1) {
            throw new ExistStorageException(r.getUuid());
        } else {
            index = Math.abs(index + 1);
            insertResume(r, index);
            size++;
        }
    }

    protected abstract void insertResume(Resume r, int index);
    
    @Override
    public Resume getSearchKey(String uuid) {
        int index = getIndex(uuid);
        if(index < 0) {
            return null;
        }
        return storage[index];
    }
    @Override
    public boolean isExist(Object searchKey) {
        
    }

    public abstract void doSave(Object searchKey);

    public abstract void doDelete(Object searchKey);

    public abstract Resume doGet(Object searchKey);

    public abstract void doUpdate(Object searchKey);
    
    protected abstract int getIndex(String uuid);
}
