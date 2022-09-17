package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractStorage implements Storage {

    public void save(Resume r) {
        doSave(findNotExistingResume(r.getUuid()));
    }

    public void delete(String uuid) {
        doDelete(findExistingResume(uuid));
    }

    public Resume get(String uuid) {
        return doGet(findExistingResume(uuid));
    }

    public void update(Resume r) {
        doUpdate(findExistingResume(r.getUuid()));
    }

    private Object findExistingResume(String uuid) {
        Object result = getSearchKey(uuid);
        if (!isExist(result)) {
            throw new NotExistStorageException(uuid);
        }
        return result;
    }

    private Object findNotExistingResume(String uuid) {
        Object result = getSearchKey(uuid);
        if (isExist(result)) {
            throw new ExistStorageException(uuid);
        }
        return result;
    }

    public abstract Object getSearchKey(String uuid);

    public abstract boolean isExist(Object searchKey);

    public abstract void doSave(Object searchKey);

    public abstract void doDelete(Object searchKey);

    public abstract Resume doGet(Object searchKey);

    public abstract void doUpdate(Object searchKey);
}
