package ru.javawebinar.basejava.model;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Resume implements Comparable<Resume> {

    private final String uuid;
    private String fullName;
    private Map<ContactType, String> contacts = new HashMap<>();
    private Map<SectionType, AbstractSection> sections = new HashMap<>();

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

    public void addSection(SectionType type, AbstractSection section) {
        sections.put(type, section);
    }

    public AbstractSection getSection(SectionType type) {
        return sections.get(type);
    }

    @Override
    public String toString() {
        return String.format("%s %s\n", uuid, fullName);
    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
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
        return uuid.equals(other.uuid);
    }

    @Override
    public int compareTo(final Resume r) {
        return uuid.compareTo(r.getUuid());
    }

    public void display() {
        displayName();
        displayContacts();
        displaySections();
    }

    private void displayName() {
        System.out.println(fullName);
    }

    private void displayContacts() {
        for (ContactType type : ContactType.values()) {
            System.out.printf("%s ", type.getTitle());
            System.out.println(contacts.get(type));
        }
    }

    private void displaySections() {
        for (SectionType type : SectionType.values()) {
            sections.get(type).display();
        }
    }
}
