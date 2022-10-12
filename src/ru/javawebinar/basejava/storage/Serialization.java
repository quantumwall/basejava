package ru.javawebinar.basejava.storage;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import ru.javawebinar.basejava.model.Resume;

public interface Serialization {

    void serialize(Serializable obj, OutputStream to) throws IOException;

    Resume unserialize(InputStream in) throws IOException, ClassNotFoundException;
}
