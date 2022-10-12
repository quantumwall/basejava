package ru.javawebinar.basejava.storage;

import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import static ru.javawebinar.basejava.storage.AbstractStorage.LOG;

public abstract class AbstractPathStorage extends AbstractStorage<Path> {

    protected final Path directory;
    private Serialization serializer;

    public AbstractPathStorage(String dir, Serialization s) {
        Objects.requireNonNull(dir, "directory must be non null");
        Objects.requireNonNull(s, "serialization type must be non null");
        serializer = s;
        directory = Path.of(dir);
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            LOG.log(Level.WARNING, "{0} is not directory or is not writable", directory);
            throw new IllegalArgumentException(directory + " is not directory or is not writable");
        }
    }

    public void setSerializer(Serialization serializator) {
        this.serializer = serializator;
    }

    public Serialization getSerializer() {
        return serializer;
    }

    @Override
    public void clear() {
        try {
            Files.list(directory).forEach(this::doDelete);
        } catch (IOException e) {
            LOG.log(Level.WARNING, "Path delete error");
            throw new StorageException("Path delete error", null, e);
        }
    }

    @Override
    public int size() {
        try {
            return (int) Files.list(directory).count();
        } catch (IOException e) {
            LOG.log(Level.WARNING, "Read directory error {0}", directory);
            throw new StorageException("Read directory error", directory.toString(), e);
        }
    }

    @Override
    public Path getSearchKey(String uuid) {
        try {
            return FileSystems.getDefault().getPath(directory.toString(), uuid);
        } catch (InvalidPathException e) {
            LOG.log(Level.WARNING, "Get path error {0}", uuid);
            throw new StorageException("Get file error", uuid, e);
        }

    }

    @Override
    public void doUpdate(Resume r, Path path) {
        try {
            doWrite(r, new BufferedOutputStream(new FileOutputStream(path.toString())));
        } catch (IOException e) {
            LOG.log(Level.WARNING, "Path write error {0}", path);
            throw new StorageException("File write error", path.toString(), e);
        }
    }

    @Override
    public boolean isExist(Path path) {
        return Files.exists(path);
    }

    @Override
    public void doSave(Resume r, Path path) {
        doUpdate(r, path);
    }

    @Override
    public Resume doGet(Path path) {
        try {
            return doRead(new BufferedInputStream(new FileInputStream(path.toString())));
        } catch (IOException e) {
            LOG.log(Level.WARNING, "Path read error {0}", path);
            throw new StorageException("Path read error", path.toString(), e);
        }
    }

    @Override
    public void doDelete(Path path) {
        try {
            Files.delete(path);
        } catch (IOException e) {
            LOG.log(Level.WARNING, "Path delete error {0}", path);
            throw new StorageException("File delete error", path.toString(), e);
        }
    }

    @Override
    public List<Resume> doCopyAll() {
        List<Resume> result = new ArrayList<>();
        try {
            Files.list(directory).forEach(e -> result.add(doGet(e)));
        } catch (IOException e) {
            LOG.log(Level.WARNING, "Error obtaining list files {0}", directory);
            throw new StorageException("Error obtaining list files", null, e);
        }
        return result;
    }

    protected abstract void doWrite(Resume r, OutputStream os) throws IOException;

    protected abstract Resume doRead(InputStream is) throws IOException;

}
