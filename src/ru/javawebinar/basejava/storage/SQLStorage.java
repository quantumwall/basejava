package ru.javawebinar.basejava.storage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.AbstractSection;
import ru.javawebinar.basejava.model.ContactType;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.model.SectionType;
import ru.javawebinar.basejava.sql.SQLHelper;
import ru.javawebinar.basejava.util.JsonParser;

public class SQLStorage implements Storage {

    private final SQLHelper sqlHelper;

    public SQLStorage(String dbURL, String dbUser, String dbPassword) {
        sqlHelper = new SQLHelper(dbURL, dbUser, dbPassword);
    }

    @Override
    public void clear() {
        sqlHelper.execute("DELETE FROM resume");
    }

    @Override
    public void update(Resume r) {
        LOG.log(Level.INFO, "Update {0}", r);
        var uuid = r.getUuid();
        sqlHelper.transactionalExecute(c -> {
            try ( var statement = c.prepareStatement("UPDATE resume SET uuid = ?, full_name = ? WHERE uuid = ?")) {
                statement.setString(1, uuid);
                statement.setString(2, r.getFullName());
                statement.setString(3, uuid);
                if (statement.executeUpdate() == 0) {
                    LOG.log(Level.WARNING, "Resume {0} does not exist", uuid);
                    throw new NotExistStorageException(uuid);
                }
            }
            deleteContacts(c, r);
            insertContacts(c, r);
            deleteSections(c, r);
            insertSections(c, r);
            return null;
        });
    }

    @Override
    public void save(Resume r) {
        LOG.log(Level.INFO, "Save {0}", r);
        var uuid = r.getUuid();
        sqlHelper.transactionalExecute(c -> {
            try ( var statement = c.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?, ?)")) {
                statement.setString(1, uuid);
                statement.setString(2, r.getFullName());
                statement.execute();
            }
            insertContacts(c, r);
            insertSections(c, r);
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        LOG.log(Level.INFO, "Get {0}", uuid);
        return sqlHelper.transactionalExecute(c -> {
            Resume resume;
            try ( var statement = c.prepareStatement("SELECT uuid, full_name FROM resume WHERE uuid = ?")) {
                statement.setString(1, uuid);
                var resultSet = statement.executeQuery();
                while (!resultSet.next()) {
                    LOG.log(Level.WARNING, "Resume {0} does not exist", uuid);
                    throw new NotExistStorageException(uuid);
                }
                resume = new Resume(uuid, resultSet.getString("full_name"));
            }
            addContacts(c, resume);
            addSections(c, resume);
            return resume;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.transactionalExecute(c -> {
            Map<String, Resume> resumes = new LinkedHashMap<>();
            try ( var statement = c.prepareStatement("SELECT uuid, full_name FROM resume ORDER BY full_name")) {
                var result = statement.executeQuery();
                while (result.next()) {
                    var uuid = result.getString("uuid");
                    var fullName = result.getString("full_name");
                    resumes.put(uuid, new Resume(uuid, fullName));
                }
            }
            try ( var statement = c.prepareStatement("SELECT resume_id, type, value FROM contact")) {
                var result = statement.executeQuery();
                while (result.next()) {
                    var uuid = result.getString("resume_id");
                    addContact(result, resumes.get(uuid));
                }
            }
            try ( var statement = c.prepareStatement("SELECT resume_id, type, value FROM section")) {
                var result = statement.executeQuery();
                while (result.next()) {
                    var uuid = result.getString("resume_id");
                    addSection(result, resumes.get(uuid));
                }
            }
            return new ArrayList(resumes.values());
        });
    }

    @Override
    public void delete(String uuid) {
        LOG.log(Level.INFO, "Delete {0}", uuid);
        sqlHelper.execute("DELETE FROM resume WHERE uuid = ?", s -> {
            s.setString(1, uuid);
            if (s.executeUpdate() == 0) {
                LOG.log(Level.WARNING, "Resume {0} is not exist", uuid);
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }

    @Override
    public int size() {
        return sqlHelper.execute("SELECT COUNT(*) size FROM resume", s -> {
            ResultSet result = s.executeQuery();
            result.next();
            return result.getInt("size");
        });
    }

    private void deleteContacts(Connection connection, Resume resume) throws SQLException {
        sqlHelper.execute("DELETE FROM contact WHERE resume_id = ?", connection, s -> {
            s.setString(1, resume.getUuid());
            s.execute();
            return null;
        });
    }

    private void insertContacts(Connection connection, Resume resume) throws SQLException {
        sqlHelper.execute("INSERT INTO contact (resume_id, type, value) VALUES (?, ?, ?)", connection, s -> {
            var uuid = resume.getUuid();
            for (Map.Entry<ContactType, String> pair : resume.getContacts().entrySet()) {
                s.setString(1, uuid);
                s.setString(2, pair.getKey().name());
                s.setString(3, pair.getValue());
                s.addBatch();
            }
            s.executeBatch();
            return null;
        });
    }

    private void addContacts(Connection c, Resume resume) {
        sqlHelper.execute("SELECT type, value FROM contact WHERE resume_id = ?", c, s -> {
            s.setString(1, resume.getUuid());
            var resultSet = s.executeQuery();
            while (resultSet.next()) {
                addContact(resultSet, resume);
            }
            return null;
        });
    }

    private void addContact(ResultSet result, Resume resume) throws SQLException {
        var type = result.getString("type");
        if (type != null) {
            var value = result.getString("value");
            resume.addContact(ContactType.valueOf(type), value);
        }
    }

    private void insertSections(Connection connection, Resume resume) {
        var uuid = resume.getUuid();
        sqlHelper.execute("INSERT INTO section (resume_id, type, value) VALUES (?, ?, ?)", connection, s -> {
            for (Map.Entry<SectionType, AbstractSection> pair : resume.getSections().entrySet()) {
                s.setString(1, uuid);
                s.setString(2, pair.getKey().name());
                s.setString(3, JsonParser.write(pair.getValue(), AbstractSection.class));
                s.addBatch();
            }
            s.executeBatch();
            return null;
        });
    }

    private void addSections(Connection connection, Resume resume) {
        sqlHelper.execute("SELECT type, value FROM section WHERE resume_id = ?", connection, s -> {
            s.setString(1, resume.getUuid());
            var resultSet = s.executeQuery();
            while (resultSet.next()) {
                addSection(resultSet, resume);
            }
            return null;
        });
    }

    private void addSection(ResultSet resultSet, Resume resume) throws SQLException {
        var type = resultSet.getString("type");
        if (type != null) {
            var value = JsonParser.read(resultSet.getString("value"), AbstractSection.class);
            resume.addSection(SectionType.valueOf(type), value);
        }
    }

    private void deleteSections(Connection connection, Resume resume) {
        sqlHelper.execute("DELETE FROM section WHERE resume_id = ?", connection, s -> {
            s.setString(1, resume.getUuid());
            s.execute();
            return null;
        });
    }

}
