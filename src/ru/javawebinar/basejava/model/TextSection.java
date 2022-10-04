package ru.javawebinar.basejava.model;

public class TextSection extends AbstractSection {

    private SectionType type;
    private String text;

    public TextSection(SectionType type) {
        this.type = type;
    }

    public TextSection(SectionType type, String text) {
        this.type = type;
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public SectionType getType() {
        return type;
    }

    @Override
    public void display() {
        System.out.println(type.getTitle());
        System.out.println(text);
    }

}
