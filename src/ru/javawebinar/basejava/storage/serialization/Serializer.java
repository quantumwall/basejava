package ru.javawebinar.basejava.storage.serialization;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import ru.javawebinar.basejava.model.Resume;

public interface Serializer {

    void serialize(Resume obj, OutputStream to) throws IOException;

    Resume unserialize(InputStream in) throws IOException, ClassNotFoundException;
}
