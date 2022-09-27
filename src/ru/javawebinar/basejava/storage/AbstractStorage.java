package ru.javawebinar.basejava.storage;

import java.util.Comparator;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractStorage implements Storage {

    protected Comparator<Resume> compareByNameAndUuid = Comparator.comparing(Resume::getFullName)
            .thenComparing(Resume::getUuid);

    public final void save(Resume r) {
        doSave(r, findNotExistingResume(r.getUuid()));
    }

    public final void delete(String uuid) {
        doDelete(findExistingResume(uuid));
    }

    public final Resume get(String uuid) {
        return doGet(findExistingResume(uuid));
    }

    public final void update(Resume r) {
        doUpdate(r, findExistingResume(r.getUuid()));
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

    public abstract void doSave(Resume resume, Object searchKey);

    public abstract void doDelete(Object searchKey);

    public abstract Resume doGet(Object searchKey);

    public abstract void doUpdate(Resume resume, Object searchKey);
}
