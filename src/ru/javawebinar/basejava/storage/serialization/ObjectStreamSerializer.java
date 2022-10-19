package ru.javawebinar.basejava.storage.serialization;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import ru.javawebinar.basejava.model.Resume;

public class ObjectStreamSerializer implements Serializer {

    @Override
    public void serialize(Resume resume, OutputStream out) throws IOException {
        try ( ObjectOutputStream oos = new ObjectOutputStream(out)) {
            oos.writeObject(resume);
        }
    }

    @Override
    public Resume unserialize(InputStream in) throws IOException, ClassNotFoundException {
        try ( ObjectInputStream ois = new ObjectInputStream(in)) {
            return (Resume) ois.readObject();
        }
    }

}
