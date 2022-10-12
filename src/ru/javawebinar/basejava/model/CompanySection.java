package ru.javawebinar.basejava.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CompanySection extends AbstractSection {

    private static final long serialVersionUID = 1L;
    private final List<Company> companies;

    public CompanySection(List<Company> companies) {
        Objects.requireNonNull(companies, "company must be non null");
        this.companies = companies;
    }

    public List<Company> getCompanies() {
        return new ArrayList<Company>(companies);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.companies);
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
        final CompanySection other = (CompanySection) obj;
        return Objects.equals(this.companies, other.companies);
    }

    @Override
    public String toString() {
        return companies.toString();
    }

}
