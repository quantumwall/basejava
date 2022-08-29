package ru.javawebinar.basejava.model;

/**
 * Initial resume class
 */
public class Resume implements Comparable<Resume> {

    // Unique identifier
    private String uuid;

    public Resume(String uuid) {
        this.uuid = uuid;
    }

    public void setUuid(final String uuid) {
        this.uuid = uuid;
    }

    public String getUuid() {
        return uuid;
    }

    @Override
    public String toString() {
        return uuid;
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
        final String rUuid = r.getUuid();
        final String currentUuid = uuid;
        if(rUuid.startsWith("uuid")) {
            int rDigits = Integer.valueOf(rUuid.substring(4, rUuid.length()));
            int currentDigits = Integer.valueOf(uuid.substring(4, uuid.length()));
            return Integer.compare(currentDigits, rDigits);
        }
        return currentUuid.compareTo(rUuid);
    }
}
