package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

public class MapResumeStorage extends AbstractMapStorage {

    @Override
    public Resume doGet(Object searchKey) {
        return (Resume) searchKey;
    }

    @Override
    public boolean isExist(Object searchKey) {
        return searchKey == null ? false : storage.containsKey(((Resume) searchKey).getUuid());
    }

    @Override
    public void doDelete(Object searchKey) {
        storage.remove(((Resume) searchKey).getUuid());
    }

    @Override
    public Object getSearchKey(String uuid) {
        return storage.get(uuid);
    }

    @Override
    public void doSave(Resume resume, Object searchKey) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    public void doUpdate(Resume resume, Object searchKey) {
        storage.put(resume.getUuid(), resume);
    }
}
