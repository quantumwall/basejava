package ru.javawebinar.basejava.storage;

import static ru.javawebinar.basejava.storage.AbstractStorage.compareByNameAndUuid;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.ContactType;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.sql.SQLHelper;

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
            return null;
        });
    }

    @Override
    public void save(Resume r) {
        LOG.log(Level.INFO, "Save {0}", r);
        var uuid = r.getUuid();
        sqlHelper.execute("INSERT INTO resume (uuid, full_name) VALUES (?, ?)", s -> {
            s.setString(1, uuid);
            s.setString(2, r.getFullName());
            s.execute();
            return null;
        });
        sqlHelper.execute("INSERT INTO contact (resume_id, type, value) VALUES (?, ?, ?)", s -> {
            for (Map.Entry<ContactType, String> pair : r.getContacts().entrySet()) {
                s.setString(1, uuid);
                s.setString(2, pair.getKey().name());
                s.setString(3, pair.getValue());
                s.addBatch();
            }
            s.executeBatch();
            return null;
        });
    }

    @Override

    public Resume get(String uuid) {
        LOG.log(Level.INFO, "Get {0}", uuid);
        return sqlHelper.execute("""
                                 SELECT * FROM resume r 
                                 LEFT JOIN contact c
                                 ON r.uuid = c.resume_id
                                 WHERE r.uuid = ?""",
                s -> {
                    s.setString(1, uuid);
                    var result = s.executeQuery();
                    while (!result.next()) {
                        LOG.log(Level.WARNING, "Resume {0} does not exist", uuid);
                        throw new NotExistStorageException(uuid);
                    }
                    var resume = new Resume(uuid, result.getString("full_name"));
                    do {
                        var value = result.getString("value");
                        var type = ContactType.valueOf(result.getString("type"));
                        resume.addContact(type, value);
                    } while (result.next());
                    return resume;
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
    public List<Resume> getAllSorted() {
        return sqlHelper.transactionalExecute(c -> {
            Map<String, Resume> resumes = new HashMap<>();
            try (var statement = c.prepareStatement("SELECT uuid, full_name FROM resume")) {
                var result = statement.executeQuery();
                while (result.next()) {
                    var uuid = result.getString("uuid");
                    var fullName = result.getString("full_name");
                    resumes.put(uuid, new Resume(uuid, fullName));
                }
            }
            try (var statement = c.prepareStatement("SELECT resume_id, type, value FROM contact")) {
                var result = statement.executeQuery();
                while (result.next()) {
                    var uuid = result.getString("resume_id");
                    var type = ContactType.valueOf(result.getString("type"));
                    var value = result.getString("value");
                    resumes.get(uuid).addContact(type, value);
                }
            }
            var result = new ArrayList<Resume>();
            resumes.forEach((k, v) -> result.add(v));
            Collections.sort(result, compareByNameAndUuid);
            return result;
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
}
