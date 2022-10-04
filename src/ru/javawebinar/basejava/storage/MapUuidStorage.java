package ru.javawebinar.basejava.storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ru.javawebinar.basejava.model.Resume;

public class MapUuidStorage extends AbstractStorage<String> {

    private final Map<String, Resume> storage = new HashMap<>();

    @Override
    public Resume doGet(String uuid) {
        return storage.get(uuid);
    }

    @Override
    public boolean isExist(String uuid) {
        return storage.containsKey(uuid);
    }

    @Override
    public void doDelete(String searchKey) {
        storage.remove(searchKey);
    }

    @Override
    public String getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    public void doSave(Resume resume, String searchKey) {
        storage.put(searchKey, resume);
    }

    @Override
    public void doUpdate(Resume resume, String searchKey) {
        storage.put(searchKey, resume);
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
