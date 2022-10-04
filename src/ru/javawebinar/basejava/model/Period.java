package ru.javawebinar.basejava.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Period {

    private String title;
    private String description;
    private LocalDate entryDate;
    private LocalDate exitDate;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yyyy");

    public Period(LocalDate entryDate, LocalDate exitDate) {
        this.entryDate = entryDate;
        this.exitDate = exitDate;
    }

    public LocalDate getEntryDate() {
        return entryDate;
    }

    public LocalDate getExitDate() {
        return exitDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void display() {
        System.out.printf("%s - %s\n", getFormattedDate(entryDate, formatter), getFormattedDate(exitDate, formatter));
        System.out.println(title);
        System.out.println(description);
    }

    private String getFormattedDate(LocalDate date, DateTimeFormatter formatter) {
        if (date == null) {
            return "настоящее время";
        }
        if (formatter == null) {
            return date.toString();
        }
        return date.format(formatter);
    }
}
