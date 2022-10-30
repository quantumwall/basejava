package ru.javawebinar.basejava.storage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.sql.SQLHelper;

public class SQLStorage implements Storage {

    private final SQLHelper sqlHelper;
    private String query;

    public SQLStorage(String dbURL, String dbUser, String dbPassword) {
        sqlHelper = new SQLHelper(dbURL, dbUser, dbPassword);
    }

    @Override
    public void clear() {
        query = "DELETE FROM resume";
        sqlHelper.execute(query, s -> {
            s.execute();
            return null;
        });

    }

    @Override
    public void update(Resume r) {
        LOG.info("Update " + r);
        String uuid = r.getUuid();
        query = "UPDATE resume SET uuid = ?, full_name = ? WHERE uuid = ?";
        sqlHelper.execute(query, s -> {
            s.setString(1, uuid);
            s.setString(2, r.getFullName());
            s.setString(3, uuid);
            if (s.executeUpdate() < 1) {
                LOG.warning("Resume " + uuid + " is not exist");
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }

    @Override
    public void save(Resume r) {
        LOG.info("Save " + r);
        query = "INSERT INTO resume (uuid, full_name) VALUES (?, ?)";
        sqlHelper.execute(query, s -> {
            s.setString(1, r.getUuid());
            s.setString(2, r.getFullName());
            try {
                s.executeUpdate();
            } catch (SQLException e) {
                LOG.warning("Resume " + r + " id already exists");
                throw new ExistStorageException(r.getUuid());
            }
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        LOG.info("Get " + uuid);
        query = "SELECT * FROM resume WHERE uuid = ?";
        ResultSet result = sqlHelper.execute(query, s -> {
            s.setString(1, uuid);
            return s.executeQuery();
        });
        try {
            while (!result.next()) {
                LOG.warning("Resume " + uuid + " is not exist");
                throw new NotExistStorageException(uuid);
            }
            return new Resume(uuid, result.getString("full_name"));
        } catch (SQLException e) {
            throw new StorageException(e);
        }

    }

    @Override
    public void delete(String uuid) {
        LOG.info("Delete " + uuid);
        query = "DELETE FROM resume WHERE uuid = ?";
        sqlHelper.execute(query, s -> {
            s.setString(1, uuid);
            if (s.executeUpdate() < 1) {
                LOG.warning("Resume " + uuid + " is not exist");
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        query = "SELECT * FROM resume ORDER BY uuid";
        ResultSet result = sqlHelper.execute(query, s -> {
            return s.executeQuery();
        });
        List<Resume> resumes = new ArrayList<>();
        try {
            while (result.next()) {
                resumes.add(new Resume(result.getString("uuid"), result.getString("full_name")));
            }
            return resumes;
        } catch (Exception e) {
            throw new StorageException(e);
        }
    }

    @Override
    public int size() {
        query = "SELECT COUNT(*) size FROM resume";
        ResultSet result = sqlHelper.execute(query, s -> {
            return s.executeQuery();
        });
        try {
            result.next();
            return result.getInt("size");
        } catch (Exception e) {
            throw new StorageException(e);
        }

    }
}
