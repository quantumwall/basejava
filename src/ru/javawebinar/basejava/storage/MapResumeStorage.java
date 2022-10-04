package ru.javawebinar.basejava.storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ru.javawebinar.basejava.model.Resume;

public class MapResumeStorage extends AbstractStorage<Resume> {

    private final Map<String, Resume> storage = new HashMap<>();

    @Override
    public Resume doGet(Resume searchKey) {
        return searchKey;
    }

    @Override
    public boolean isExist(Resume searchKey) {
        return searchKey == null ? false : storage.containsKey(searchKey.getUuid());
    }

    @Override
    public void doDelete(Resume searchKey) {
        storage.remove(searchKey.getUuid());
    }

    @Override
    public Resume getSearchKey(String uuid) {
        return storage.get(uuid);
    }

    @Override
    public void doSave(Resume resume, Resume searchKey) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    public void doUpdate(Resume resume, Resume searchKey) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    public List<Resume> doCopyAll() {
        ArrayList<Resume> result = new ArrayList<>();
        for (Map.Entry<String, Resume> pair : storage.entrySet()) {
            result.add(pair.getValue());
        }
        return result;
    }
}
