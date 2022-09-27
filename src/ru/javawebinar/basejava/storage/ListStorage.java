package ru.javawebinar.basejava.storage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
    public List<Resume> getAllSorted() {
        Collections.sort(storage, compareByNameAndUuid);
        return storage.subList(0, storage.size());
    }

    @Override
    public void doSave(Resume r, Object index) {
        storage.add(r);
    }

    @Override
    public void doDelete(Object index) {
        storage.remove(storage.get((Integer) index));
    }

    @Override
    public Resume doGet(Object index) {
        return storage.get((Integer) index);
    }

    @Override
    public void doUpdate(Resume r, Object index) {
        storage.set((Integer) index, r);
    }

    @Override
    public Integer getSearchKey(String uuid) {
        for (int i = 0; i < storage.size(); i++) {
            if (storage.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public boolean isExist(Object index) {
        return (Integer) index >= 0;
    }
}
