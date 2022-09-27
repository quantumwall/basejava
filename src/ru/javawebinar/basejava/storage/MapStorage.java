package ru.javawebinar.basejava.storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ru.javawebinar.basejava.model.Resume;

public abstract class MapStorage extends AbstractStorage {

    protected final Map<String, Resume> storage = new HashMap<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> result = getAll();
        result.sort(compareByNameAndUuid);
        return result;
    }

    @Override
    public int size() {
        return storage.size();
    }

    private List<Resume> getAll() {
        ArrayList<Resume> result = new ArrayList<>();
        for (Map.Entry<String, Resume> pair : storage.entrySet()) {
            result.add(pair.getValue());
        }
        return result;
    }
}
