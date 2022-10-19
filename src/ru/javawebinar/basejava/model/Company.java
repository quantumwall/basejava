package ru.javawebinar.basejava.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class Company implements Serializable {

    private static final long serialVersionUID = 1L;
    private Link link;
    private List<Period> periods;

    public Company() {

    }

    public Company(Link link, List<Period> periods) {
        Objects.requireNonNull(link, "link must be non null");
        Objects.requireNonNull(periods, "periods must be non null");
        this.link = link;
        this.periods = new ArrayList<>(periods);
    }

    public Link getLink() {
        return link;
    }

    public void addPeriod(Period period) {
        if (period != null) {
            periods.add(period);
        }
    }

    public List<Period> getPeriods() {
        return new ArrayList<>(periods);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + Objects.hashCode(this.link);
        hash = 59 * hash + Objects.hashCode(this.periods);
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
        final Company other = (Company) obj;
        if (!Objects.equals(this.link, other.link)) {
            return false;
        }
        return Objects.equals(this.periods, other.periods);
    }

    @Override
    public String toString() {
        return "Company{" + "link=" + link + ", periods=" + periods + '}';
    }

}
