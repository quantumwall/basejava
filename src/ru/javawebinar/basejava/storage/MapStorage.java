package ru.javawebinar.basejava.storage;

import java.util.HashMap;
import java.util.Map;
import ru.javawebinar.basejava.model.Resume;

public class MapStorage extends AbstractStorage {

    private final Map<String, Resume> storage = new HashMap<>();

    @Override
    public Object getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    public boolean isExist(Object searchKey) {
        return storage.containsKey((String) searchKey);
    }

    @Override
    public void doSave(Resume resume, Object searchKey) {
        storage.put((String) searchKey, resume);
    }

    @Override
    public void doDelete(Object searchKey) {
        storage.remove((String) searchKey);
    }

    @Override
    public Resume doGet(Object searchKey) {
        return storage.get((String) searchKey);
    }

    @Override
    public void doUpdate(Resume resume, Object searchKey) {
        storage.put((String) searchKey, resume);
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public Resume[] getAll() {
        return storage.values().toArray(Resume[]::new);
    }

    @Override
    public int size() {
        return storage.size();
    }

}
