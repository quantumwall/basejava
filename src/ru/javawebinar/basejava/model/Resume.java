package ru.javawebinar.basejava.model;

import java.util.UUID;

/**
 * Initial resume class
 */
public class Resume implements Comparable<Resume> {

    private final String uuid;
    private String fullName;

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
}
