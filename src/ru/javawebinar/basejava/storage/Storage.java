package ru.javawebinar.basejava.storage;

import java.util.List;
import ru.javawebinar.basejava.model.Resume;

public interface Storage {

    void clear();

    void update(Resume r);

    void save(Resume r);

    Resume get(String uuid);

    void delete(String uuid);

    List<Resume> getAllSorted();

    int size();
}
