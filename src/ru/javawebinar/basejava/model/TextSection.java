package ru.javawebinar.basejava.model;

import java.util.Objects;

public class TextSection extends AbstractSection {

    private static final long serialVersionUID = 1L;
    private String text;

    public TextSection(String text) {
        Objects.requireNonNull(text, "text must be non null");
        this.text = text;
    }

    public String getText() {
        return text;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 71 * hash + Objects.hashCode(this.text);
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
        final TextSection other = (TextSection) obj;
        return Objects.equals(this.text, other.text);
    }

    @Override
    public String toString() {
        return text;
    }

}
