package ru.javawebinar.basejava.model;

import java.util.ArrayList;
import java.util.List;

public class Company {

    private String name;
    private String website;
    private List<Period> periods = new ArrayList<>();

    public Company(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public void addPeriod(Period period) {
        if (period != null) {
            periods.add(period);
        }
    }

    public List<Period> getPeriods() {
        return new ArrayList<>(periods);
    }

    public void display() {
        System.out.printf("%s ", name);
        System.out.println(website);
        for (Period period : periods) {
            period.display();
        }
    }
}
