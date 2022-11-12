package ru.javawebinar.basejava.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Resume implements Comparable<Resume>, Serializable {

    private static final long serialVersionUID = 1L;
    private String uuid;
    private String fullName;
    private final Map<ContactType, String> contacts = new EnumMap<>(ContactType.class);
    private final Map<SectionType, AbstractSection> sections = new EnumMap<>(SectionType.class);

    public Resume() {

    }

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid, String fullName) {
        this.uuid = uuid;
        this.fullName = fullName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullname) {
        this.fullName = fullname;
    }

    public String getUuid() {
        return uuid;
    }

    public void addContact(ContactType type, String text) {
        contacts.put(type, text);
    }

    public String getContact(ContactType type) {
        return contacts.get(type);
    }
    
    public Map<ContactType, String> getContacts() {
        return contacts;
    }

    public void addSection(SectionType type, AbstractSection section) {
        sections.put(type, section);
    }

    public AbstractSection getSection(SectionType type) {
        return sections.get(type);
    }
    
    public Map<SectionType, AbstractSection> getSections() {
        return sections;
    }

    @Override
    public String toString() {
        return "Resume{" + "uuid=" + uuid + ", fullName=" + fullName + '}';
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + Objects.hashCode(this.uuid);
        hash = 79 * hash + Objects.hashCode(this.fullName);
        hash = 79 * hash + Objects.hashCode(this.contacts);
        hash = 79 * hash + Objects.hashCode(this.sections);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Resume other = (Resume) obj;
        if (!Objects.equals(this.uuid, other.uuid)) {
            return false;
        }
        if (!Objects.equals(this.fullName, other.fullName)) {
            return false;
        }
        if (!Objects.equals(this.contacts, other.contacts)) {
            return false;
        }
        return Objects.equals(this.sections, other.sections);
    }

    @Override
    public int compareTo(final Resume r) {
        return uuid.compareTo(r.getUuid());
    }
}
