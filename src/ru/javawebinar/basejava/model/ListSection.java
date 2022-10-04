package ru.javawebinar.basejava.model;

import java.util.ArrayList;
import java.util.List;

public class ListSection extends AbstractSection {

    private SectionType type;
    private List<String> items = new ArrayList<>();

    public ListSection(SectionType type) {
        this.type = type;
    }

    public void addItem(String item) {
        if (item != null) {
            items.add(item);
        }
    }

    public List<String> getItems() {
        return items;
    }

    public SectionType getType() {
        return type;
    }

    @Override
    public void display() {
        System.out.println(type.getTitle());
        for (String item : items) {
            System.out.println(item);
        }
    }
}
