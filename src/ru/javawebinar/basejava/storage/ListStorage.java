package ru.javawebinar.basejava.storage;

import java.util.ArrayList;
import java.util.List;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.model.Resume;

public class ListStorage extends AbstractStorage {

    private final List<Resume> storage = new ArrayList<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    public Resume[] getAll() {
        Resume[] result = new Resume[storage.size()];
        storage.toArray(result);
        return result;
    }

    @Override
    public void save(Resume r) {
        if (getIndex(r.getUuid()) > -1) {
            throw new ExistStorageException(String.format("Resume %s is already exist\n", r));
        }
        storage.add(r);
    }

    @Override
    protected void deleteResume(int index) {
        storage.remove(index);
    }

    @Override
    protected int getIndex(String uuid) {
        Resume searchResume = new Resume(uuid);
        return storage.indexOf(searchResume);
    }

    @Override
    protected void updateResume(Resume r, int index) {
        storage.set(index, r);
    }

    @Override
    protected Resume getResume(int index) {
        return storage.get(index);
    }
}
