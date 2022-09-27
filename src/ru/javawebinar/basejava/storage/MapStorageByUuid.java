package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

public class MapStorageByUuid extends MapStorage {

    @Override
    public Resume doGet(Object uuid) {
        return storage.get((String) uuid);
    }

    @Override
    public boolean isExist(Object uuid) {
        return storage.containsKey((String) uuid);
    }

    @Override
    public void doDelete(Object searchKey) {
        storage.remove((String) searchKey);
    }

    @Override
    public Object getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    public void doSave(Resume resume, Object searchKey) {
        storage.put((String) searchKey, resume);
    }

    @Override
    public void doUpdate(Resume resume, Object searchKey) {
        storage.put((String) searchKey, resume);
    }
}
