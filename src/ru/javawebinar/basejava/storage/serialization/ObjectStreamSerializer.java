package ru.javawebinar.basejava.storage.serialization;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import ru.javawebinar.basejava.model.Resume;

public class ObjectStreamSerializer implements Serializer {

    @Override
    public void serialize(Serializable obj, OutputStream out) throws IOException {
        try ( ObjectOutputStream oos = new ObjectOutputStream(out)) {
            oos.writeObject(obj);
        }
    }

    @Override
    public Resume unserialize(InputStream in) throws IOException, ClassNotFoundException {
        try ( ObjectInputStream ois = new ObjectInputStream(in)) {
            return (Resume) ois.readObject();
        }
    }

}
