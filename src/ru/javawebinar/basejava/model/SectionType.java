package ru.javawebinar.basejava.model;

public enum SectionType {
    PERSONAL("Личные качества"),
    OBJECTIVE("Позиция"),
    ACHIEVEMENTS("Достижения"),
    QUALIFICATIONS("Квалификация"),
    EXPERIENCE("Опыт"),
    EDUCATION("Образование");

    private final String title;

    private SectionType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
