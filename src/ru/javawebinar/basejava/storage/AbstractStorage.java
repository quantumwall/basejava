package ru.javawebinar.basejava.storage;

import java.util.Comparator;
import java.util.List;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractStorage implements Storage {

    protected Comparator<Resume> compareByNameAndUuid = Comparator.comparing(Resume::getFullName)
            .thenComparing(Resume::getUuid);

    @Override
    public final void save(Resume r) {
        doSave(r, findNotExistingResume(r.getUuid()));
    }

    @Override
    public final void delete(String uuid) {
        doDelete(findExistingResume(uuid));
    }

    @Override
    public final Resume get(String uuid) {
        return doGet(findExistingResume(uuid));
    }

    @Override
    public final void update(Resume r) {
        doUpdate(r, findExistingResume(r.getUuid()));
    }

    @Override
    public final List<Resume> getAllSorted() {
        List<Resume> result = doCopyAll();
        result.sort(compareByNameAndUuid);
        return result;
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
    
    public abstract List<Resume> doCopyAll();
}
