package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.storage.serialization.ObjectStreamSerializer;
import ru.javawebinar.basejava.storage.serialization.Serializer;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.NotDirectoryException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.stream.Stream;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

public class PathStorage extends AbstractStorage<Path> {

    protected final Path directory;
    protected final Serializer serializer;

    public PathStorage(String dir) {
        Objects.requireNonNull(dir, "directory must be non null");
        serializer = new ObjectStreamSerializer();
        directory = Path.of(dir);
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            LOG.log(Level.WARNING, "{0} is not directory or is not writable", directory);
            throw new IllegalArgumentException(directory + " is not directory or is not writable");
        }
    }

    @Override
    public void clear() {
        getStreamPaths(directory).forEach(this::doDelete);
    }

    @Override
    public int size() {
        return (int) getStreamPaths(directory).count();
    }

    @Override
    public List<Resume> doCopyAll() {
        List<Resume> result = new ArrayList<>();
        getStreamPaths(directory).forEach(e -> result.add(doGet(e)));
        return result;
    }

    @Override
    public Path getSearchKey(String uuid) {
        return directory.resolve(uuid);
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
        try {
            Files.createFile(path);
        } catch (IOException e) {
            LOG.log(Level.WARNING, "Path create error {0}", path);
            throw new StorageException("Path create error", path.toString(), e);
        }
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

    private Stream<Path> getStreamPaths(Path directory) {
        try {
            return Files.list(directory);
        } catch (NotDirectoryException e) {
            LOG.log(Level.WARNING, "Error obtaining list files: {0} is not directory", directory);
            throw new StorageException("Error obtaining list files: is not directory", directory.toString(), e);
        } catch (IOException e) {
            LOG.log(Level.WARNING, "Error obtaining list files {0}", directory);
            throw new StorageException("Error obtaining list files", null, e);
        }
    }

    private void doWrite(Resume r, OutputStream os) throws IOException {
        serializer.serialize(r, os);
    }

    private Resume doRead(InputStream is) throws IOException {
        try {
            return serializer.unserialize(is);
        } catch (ClassNotFoundException e) {
            LOG.log(Level.WARNING, "Error read resume");
            throw new StorageException("Error read resume", null, e);
        }
    }

}
