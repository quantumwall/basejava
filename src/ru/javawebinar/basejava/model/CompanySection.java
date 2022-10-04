package ru.javawebinar.basejava.model;

import java.util.ArrayList;
import java.util.List;

public class CompanySection extends AbstractSection {

    private SectionType type;
    private List<Company> companies = new ArrayList<>();

    public CompanySection(SectionType type) {
        this.type = type;
    }

    public void addCompany(Company company) {
        if (company != null) {
            companies.add(company);
        }
    }

    public SectionType getType() {
        return type;
    }

    public List<Company> getCompanies() {
        return new ArrayList<Company>(companies);
    }

    @Override
    public void display() {
        System.out.println(type.getTitle());
        for (Company company : companies) {
            company.display();
        }
    }
}
