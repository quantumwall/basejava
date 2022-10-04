package ru.javawebinar.basejava.model;

public enum ContactType {
    PHONE("Тел"),
    SKYPE("Skype"),
    EMAIL("Почта"),
    LINKEDIN("Профиль LinkedIn"),
    GITHUB("Профиль GitHub"),
    STACKOVERFLOW("Профиль Stackoverflow"),
    HOMEPAGE("Домашнаяя страница");

    private final String title;

    private ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
