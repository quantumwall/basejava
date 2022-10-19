package ru.javawebinar.basejava.storage.serialization;

import ru.javawebinar.basejava.model.*;
import ru.javawebinar.basejava.util.XmlParser;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class XmlStreamSerializer implements Serializer {

    private final XmlParser xmlParser;

    public XmlStreamSerializer() {
        xmlParser = new XmlParser(
                Resume.class, Company.class, Link.class,
                CompanySection.class, TextSection.class, ListSection.class, Period.class);
    }

    @Override
    public void serialize(Resume resume, OutputStream to) throws IOException {
        try ( Writer w = new OutputStreamWriter(to, StandardCharsets.UTF_8)) {
            xmlParser.marshall(resume, w);
        }
    }

    @Override
    public Resume unserialize(InputStream in) throws IOException, ClassNotFoundException {
        try ( Reader r = new InputStreamReader(in, StandardCharsets.UTF_8)) {
            return xmlParser.unmarshall(r);
        }
    }

}
