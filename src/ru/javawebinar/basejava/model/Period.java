package ru.javawebinar.basejava.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class Period implements Serializable {

    private static final long serialVersionUID = 1L;
    private String title;
    private String description;
    private final LocalDate entryDate;
    private final LocalDate exitDate;

    public Period(String title, String description, LocalDate entryDate, LocalDate exitDate) {
        Objects.requireNonNull(title, "title must be non null");
        Objects.requireNonNull(entryDate, "entryDate must be non null");
        this.title = title;
        this.description = description;
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

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.title);
        hash = 97 * hash + Objects.hashCode(this.description);
        hash = 97 * hash + Objects.hashCode(this.entryDate);
        hash = 97 * hash + Objects.hashCode(this.exitDate);
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
        final Period other = (Period) obj;
        if (!Objects.equals(this.title, other.title)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.entryDate, other.entryDate)) {
            return false;
        }
        return Objects.equals(this.exitDate, other.exitDate);
    }

    @Override
    public String toString() {
        return "Period{" + "title=" + title + ", description=" + description + ", entryDate=" + entryDate + ", exitDate=" + exitDate + '}';
    }

//    public void display() {
//        System.out.printf("%s - %s\n", getFormattedDate(entryDate, formatter), getFormattedDate(exitDate, formatter));
//        System.out.println(title);
//        System.out.println(description);
//    }
//
//    private String getFormattedDate(LocalDate date, DateTimeFormatter formatter) {
//        if (date == null) {
//            return "настоящее время";
//        }
//        if (formatter == null) {
//            return date.toString();
//        }
//        return date.format(formatter);
//    }
}
