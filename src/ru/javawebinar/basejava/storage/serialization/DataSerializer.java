package ru.javawebinar.basejava.storage.serialization;

import static ru.javawebinar.basejava.model.SectionType.OBJECTIVE;
import static ru.javawebinar.basejava.model.SectionType.QUALIFICATIONS;

import ru.javawebinar.basejava.model.ContactType;
import ru.javawebinar.basejava.model.Resume;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import ru.javawebinar.basejava.model.*;

public class DataSerializer implements Serializer {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    @Override
    public void serialize(Resume r, OutputStream os) throws IOException {
        try ( DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());
            Map<ContactType, String> contacts = r.getContacts();
            writeContacts(contacts, dos);
            Map<SectionType, AbstractSection> sections = r.getSections();
            writeSections(sections, dos);
        }
    }

    @Override
    public Resume unserialize(InputStream is) throws IOException {
        try ( DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            int size = dis.readInt();
            for (int i = 0; i < size; i++) {
                resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            }
            size = dis.readInt();
            for (int i = 0; i < size; i++) {
                SectionType type = Enum.valueOf(SectionType.class, dis.readUTF());
                resume.addSection(type, readSection(type, dis));
            }
            return resume;
        }
    }

    private void writeContacts(Map<ContactType, String> contacts, DataOutputStream dos) throws IOException {
        dos.writeInt(contacts.size());
        for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
            dos.writeUTF(entry.getKey().name());
            dos.writeUTF(entry.getValue());
        }
    }

    private void writeSections(Map<SectionType, AbstractSection> sections, DataOutputStream dos) throws IOException {
        dos.writeInt(sections.size());
        for (Map.Entry<SectionType, AbstractSection> entry : sections.entrySet()) {
            SectionType type = entry.getKey();
            AbstractSection section = entry.getValue();
            dos.writeUTF(type.name());
            switch (type) {
                case PERSONAL, OBJECTIVE ->
                    writeTextSection(section, dos);
                case ACHIEVEMENTS, QUALIFICATIONS ->
                    writeListSection(section, dos);
                default ->
                    writeCompanySection(section, dos);
            }
        }
    }

    private void writeTextSection(AbstractSection section, DataOutputStream dos) throws IOException {
        dos.writeUTF(((TextSection) section).getText());
    }

    private void writeListSection(AbstractSection section, DataOutputStream dos) throws IOException {
        List<String> items = ((ListSection) section).getItems();
        dos.writeInt(items.size());
        for (String item : items) {
            dos.writeUTF(item);
        }
    }

    private void writeCompanySection(AbstractSection section, DataOutputStream dos) throws IOException {
        List<Company> companies = ((CompanySection) section).getCompanies();
        dos.writeInt(companies.size());
        for (Company company : companies) {
            writeCompany(company, dos);
        }
    }

    private void writeCompany(Company company, DataOutputStream dos) throws IOException {
        dos.writeUTF(company.getLink().getName());
        dos.writeUTF(Objects.requireNonNullElse(company.getLink().getUrl(), ""));
        List<Period> periods = company.getPeriods();
        dos.writeInt(periods.size());
        for (Period period : periods) {
            writePeriod(period, dos);
        }
    }

    private void writePeriod(Period period, DataOutputStream dos) throws IOException {
        dos.writeUTF(period.getTitle());
        dos.writeUTF(Objects.requireNonNullElse(period.getDescription(), ""));
        dos.writeUTF(period.getEntryDate().format(formatter));
        dos.writeUTF(period.getExitDate().format(formatter));
    }

    private AbstractSection readSection(SectionType type, DataInputStream dis) throws IOException {
        return switch (type) {
            case PERSONAL, OBJECTIVE ->
                new TextSection(dis.readUTF());
            case ACHIEVEMENTS, QUALIFICATIONS -> {
                List<String> items = new ArrayList<>();
                for (int i = dis.readInt(); i > 0; i--) {
                    items.add(dis.readUTF());
                }
                yield new ListSection(items);
            }
            default -> {
                List<Company> companies = new ArrayList<>();
                for (int i = dis.readInt(); i > 0; i--) {
                    companies.add(readCompany(dis));
                }
                yield new CompanySection(companies);
            }
        };
    }

    private Company readCompany(DataInputStream dis) throws IOException {
        String name = dis.readUTF();
        String url = dis.readUTF();
        Link link = new Link(name, url.length() > 0 ? url : null);
        List<Period> periods = new ArrayList<>();
        for (int i = dis.readInt(); i > 0; i--) {
            periods.add(readPeriod(dis));
        }
        return new Company(link, periods);
    }

    private Period readPeriod(DataInputStream dis) throws IOException {
        String title = dis.readUTF();
        String description = dis.readUTF();
        LocalDate entryDate = LocalDate.parse(dis.readUTF(), formatter);
        LocalDate exitDate = LocalDate.parse(dis.readUTF(), formatter);
        return new Period(title, description.length() > 0 ? description : null, entryDate, exitDate);
    }
}