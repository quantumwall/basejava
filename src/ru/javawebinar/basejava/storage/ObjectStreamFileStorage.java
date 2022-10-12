package ru.javawebinar.basejava.storage;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

public class ObjectStreamFileStorage extends AbstractFileStorage {

    public ObjectStreamFileStorage(String directory) {
        super(directory, new ObjectStreamSerializer());
    }

    @Override
    protected void doWrite(Resume resume, OutputStream out) throws IOException {
        getSerializer().serialize(resume, out);
    }

    @Override
    protected Resume doRead(InputStream input) throws IOException {
        try {
            return getSerializer().unserialize(input);
        } catch (ClassNotFoundException e) {
            LOG.log(Level.WARNING, "Error read resume");
            throw new StorageException("Error read resume", null, e);
        }
    }
}
