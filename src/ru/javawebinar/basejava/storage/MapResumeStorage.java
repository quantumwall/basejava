package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

public class MapResumeStorage extends AbstractMapStorage {

    @Override
    public Resume doGet(Object searchKey) {
        return storage.get(((Resume) searchKey).getUuid());
    }

    @Override
    public boolean isExist(Object searchKey) {
        return storage.containsValue((Resume) searchKey);
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
