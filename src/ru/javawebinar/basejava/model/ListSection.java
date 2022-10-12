package ru.javawebinar.basejava.model;

import java.util.List;
import java.util.Objects;

public class ListSection extends AbstractSection {

    private static final long serialVersionUID = 1L;
    private final List<String> items;

    public ListSection(List<String> items) {
        Objects.requireNonNull(items, "items must be non null");
        this.items = items;
    }

    public List<String> getItems() {
        return items;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + Objects.hashCode(this.items);
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
        final ListSection other = (ListSection) obj;
        return Objects.equals(this.items, other.items);
    }

    @Override
    public String toString() {
        return items.toString();
    }

}
