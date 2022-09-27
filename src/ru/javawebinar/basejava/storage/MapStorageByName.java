package ru.javawebinar.basejava.storage;

import java.util.Map;
import ru.javawebinar.basejava.model.Resume;

public class MapStorageByName extends MapStorage {

    @Override
    public Resume doGet(Object name) {
        Resume result = null;
        for (Map.Entry<String, Resume> pair : storage.entrySet()) {
            Resume resume = pair.getValue();
            if (resume.getFullName().equalsIgnoreCase((String) name)) {
                return resume;
            }
        }
        return result;
    }

    @Override
    public boolean isExist(Object name) {
        boolean isExist = false;
        for (Map.Entry<String, Resume> pair : storage.entrySet()) {
            if (pair.getValue().getFullName().equalsIgnoreCase((String) name)) {
                return true;
            }
        }
        return isExist;
    }

    @Override
    public void doDelete(Object name) {
        for (Map.Entry<String, Resume> pair : storage.entrySet()) {
            if (pair.getValue().getFullName().equalsIgnoreCase((String) name)) {
                storage.remove(pair.getKey());
                return;
            }
        }
    }

    @Override
    public Object getSearchKey(String uuid) {
        for (Map.Entry<String, Resume> pair : storage.entrySet()) {
            if (pair.getKey().equalsIgnoreCase(uuid)) {
                return pair.getValue().getFullName();
            }
        }
        return null;
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
