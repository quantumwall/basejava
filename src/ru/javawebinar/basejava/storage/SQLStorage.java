package ru.javawebinar.basejava.storage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.sql.SQLHelper;

public class SQLStorage implements Storage {

    private final SQLHelper sqlHelper;

    public SQLStorage(String dbURL, String dbUser, String dbPassword) {
        sqlHelper = new SQLHelper(dbURL, dbUser, dbPassword);
    }

    @Override
    public void clear() {
        sqlHelper.execute("DELETE FROM resume", s -> {
            s.execute();
            return null;
        });
    }

    @Override
    public void update(Resume r) {
        LOG.log(Level.INFO, "Update {0}", r);
        String uuid = r.getUuid();
        sqlHelper.execute("UPDATE resume SET uuid = ?, full_name = ? WHERE uuid = ?", s -> {
            s.setString(1, uuid);
            s.setString(2, r.getFullName());
            s.setString(3, uuid);
            if (s.executeUpdate() == 0) {
                LOG.log(Level.WARNING, "Resume {0} does not exist", uuid);
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }

    @Override
    public void save(Resume r) {
        LOG.log(Level.INFO, "Save {0}", r);
        sqlHelper.execute("INSERT INTO resume (uuid, full_name) VALUES (?, ?)", s -> {
            s.setString(1, r.getUuid());
            s.setString(2, r.getFullName());
            try {
                s.execute();
            } catch (SQLException e) {
                throw new SQLException(r.getUuid(), e.getSQLState());
            }
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        LOG.log(Level.INFO, "Get {0}", uuid);
        return sqlHelper.execute("SELECT * FROM resume WHERE uuid = ?", s -> {
            s.setString(1, uuid);
            ResultSet result = s.executeQuery();
            while (!result.next()) {
                LOG.log(Level.WARNING, "Resume {0} does not exist", uuid);
                throw new NotExistStorageException(uuid);
            }
            return new Resume(uuid, result.getString("full_name"));
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
        return sqlHelper.execute("SELECT * FROM resume ORDER BY full_name", s -> {
            ResultSet result = s.executeQuery();
            List<Resume> resumes = new ArrayList<>();
            while (result.next()) {
                resumes.add(new Resume(result.getString("uuid"), result.getString("full_name")));
            }
            return resumes;
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

}
