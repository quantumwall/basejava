package ru.javawebinar.basejava.storage;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import static ru.javawebinar.basejava.storage.AbstractStorage.LOG;

public class ObjectStreamPathStorage extends AbstractPathStorage {

    public ObjectStreamPathStorage(String dir) {
        super(dir, new ObjectStreamSerializer());
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
