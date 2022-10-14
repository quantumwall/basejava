package ru.javawebinar.basejava.storage;

import java.io.*;
import java.util.*;
import java.util.logging.Level;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

public class FileStorage extends AbstractStorage<File> {

    private final File directory;
    private Serializer serializer;

    protected FileStorage(String directory) {
        Objects.requireNonNull(directory, "file must be non null");
        File dir = new File(directory);
        if (!dir.isDirectory()) {
            throw new IllegalArgumentException(dir.getAbsolutePath() + " must be directory");
        }
        if (!dir.canRead() || !dir.canWrite()) {
            throw new IllegalArgumentException(dir.getAbsolutePath() + " is not readable/writable");
        }
        this.directory = dir;
        serializer = new ObjectStreamSerializer();
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
//        try {
//            file.createNewFile();
//        } catch (IOException e) {
//            LOG.log(Level.WARNING, "IOException saving {0} to {1}", new Object[]{resume, file.getName()});
//            throw new StorageException("IO Exception", file.getName(), e);
//        }
        doUpdate(resume, file);
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
            return doRead(new BufferedInputStream(new FileInputStream(file)));
        } catch (IOException e) {
            LOG.log(Level.WARNING, "IOException reading  from {0}", file.getName());
            throw new StorageException("IO Exception", file.getName(), e);
        }
    }

    @Override
    public void doUpdate(Resume resume, File file) {
        try {
            doWrite(resume, new BufferedOutputStream(new FileOutputStream(file)));
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
                result.add(doGet(file));
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
