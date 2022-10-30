package ru.javawebinar.basejava.storage;

import java.util.Comparator;
import java.util.List;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractStorage<SK> implements Storage {

    protected Comparator<Resume> compareByNameAndUuid = Comparator.comparing(Resume::getFullName)
            .thenComparing(Resume::getUuid);


    @Override
    public final void save(Resume r) {
        LOG.info("Save " + r);
        doSave(r, findNotExistingResume(r.getUuid()));
    }

    @Override
    public final void delete(String uuid) {
        LOG.info("Delete " + uuid);
        doDelete(findExistingResume(uuid));
    }

    @Override
    public final Resume get(String uuid) {
        LOG.info("Get " + uuid);
        return doGet(findExistingResume(uuid));
    }

    @Override
    public final void update(Resume r) {
        LOG.info("Update " + r);
        doUpdate(r, findExistingResume(r.getUuid()));
    }

    @Override
    public final List<Resume> getAllSorted() {
        List<Resume> result = doCopyAll();
        result.sort(compareByNameAndUuid);
        return result;
    }

    private SK findExistingResume(String uuid) {
        SK result = getSearchKey(uuid);
        if (!isExist(result)) {
            LOG.warning("Resume " + uuid + " is not exist");
            throw new NotExistStorageException(uuid);
        }
        return result;
    }

    private SK findNotExistingResume(String uuid) {
        SK result = getSearchKey(uuid);
        if (isExist(result)) {
            LOG.warning("Resume " + uuid + " already exist");
            throw new ExistStorageException(uuid);
        }
        return result;
    }

    public abstract SK getSearchKey(String uuid);

    public abstract boolean isExist(SK searchKey);

    public abstract void doSave(Resume resume, SK searchKey);

    public abstract void doDelete(SK searchKey);

    public abstract Resume doGet(SK searchKey);

    public abstract void doUpdate(Resume resume, SK searchKey);

    public abstract List<Resume> doCopyAll();
}
