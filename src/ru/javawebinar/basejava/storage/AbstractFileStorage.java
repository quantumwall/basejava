package ru.javawebinar.basejava.storage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractFileStorage extends AbstractStorage<File> {

    private File directory;

    protected AbstractFileStorage(File directory) {
        Objects.requireNonNull(directory, "file must be non null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " must be directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable/writable");
        }
        this.directory = directory;
    }

    @Override
    public File getSearchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    public boolean isExist(File file) {
        return file.exists();
    }

    @Override
    public void doSave(Resume resume, File file) {
        try {
            file.createNewFile();
            doWrite(resume, file);
        } catch (IOException e) {
            LOG.warning("IOException saving " + resume + " to " + file.getName());
            throw new StorageException("IO Exception", file.getName(), e);
        }
    }

    @Override
    public void doDelete(File file) {
        file.delete();
    }

    @Override
    public Resume doGet(File file) {
        Resume result = null;
        try {
            result = doRead(file);
        } catch (IOException e) {
            LOG.warning("IOException reading  from " + file.getName());
            throw new StorageException("IO Exception", file.getName(), e);
        }
        return result;
    }

    @Override
    public void doUpdate(Resume resume, File file) {
        try {
            doWrite(resume, file);
        } catch (IOException e) {
            LOG.warning("IOException saving " + resume + " to " + file.getName());
            throw new StorageException("IO Exception", file.getName(), e);
        }
    }

    @Override
    public List<Resume> doCopyAll() {
        List<Resume> result = new ArrayList<>();
        for (File file : directory.listFiles()) {
            if (!file.isDirectory()) {
                try {
                    result.add(doRead(file));
                } catch (IOException e) {
                    LOG.warning("IOException reading  from " + file.getName());
                    throw new StorageException("IO Exception", file.getName(), e);
                }
            }
        }
        return result;
    }

    @Override
    public void clear() {
        for (File file : directory.listFiles()) {
            file.delete();
        }
    }

    @Override
    public int size() {
        return directory.list().length;
    }

    protected abstract void doWrite(Resume resume, File file) throws IOException;

    protected abstract Resume doRead(File file) throws IOException;

}
