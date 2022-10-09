package ru.javawebinar.basejava.storage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractFileStorage extends AbstractStorage<File> {

    private final File directory;

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
            LOG.log(Level.WARNING, "IOException saving {0} to {1}", new Object[]{resume, file.getName()});
            throw new StorageException("IO Exception", file.getName(), e);
        }
    }

    @Override
    public void doDelete(File file) {
        if (!file.delete()) {
            LOG.log(Level.WARNING, "IO Exception deleting file {0}", file.getName());
            throw new StorageException("IO Exception", file.getName());
        }
    }

    @Override
    public Resume doGet(File file) {
        try {
            return doRead(file);
        } catch (IOException e) {
            LOG.log(Level.WARNING, "IOException reading  from {0}", file.getName());
            throw new StorageException("IO Exception", file.getName(), e);
        }
    }

    @Override
    public void doUpdate(Resume resume, File file) {
        try {
            doWrite(resume, file);
        } catch (IOException e) {
            LOG.log(Level.WARNING, "IOException saving {0} to {1}", new Object[]{resume, file.getName()});
            throw new StorageException("IO Exception", file.getName(), e);
        }
    }

    @Override
    public List<Resume> doCopyAll() {
        List<Resume> result = new ArrayList<>();
        for (File file : getListFiles()) {
            if (!file.isDirectory()) {
                try {
                    result.add(doRead(file));
                } catch (IOException e) {
                    LOG.log(Level.WARNING, "IOException reading  from {0}", file.getName());
                    throw new StorageException("IO Exception", file.getName(), e);
                }
            }
        }
        return result;
    }

    @Override
    public void clear() {
        for (File file : getListFiles()) {
            file.delete();
        }
    }

    @Override
    public int size() {
        return getListFiles().length;
    }

    private File[] getListFiles() {
        File[] files = directory.listFiles();
        if (files == null) {
            LOG.log(Level.WARNING, "IO Exception reading directory {0}", directory.getAbsolutePath());
            throw new StorageException("Error reading directory", directory.getAbsolutePath());
        }
        return files;
    }

    protected abstract void doWrite(Resume resume, File file) throws IOException;

    protected abstract Resume doRead(File file) throws IOException;

}
