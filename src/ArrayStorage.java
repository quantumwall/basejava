import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    private int size = 0;

    void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    void save(Resume r) {
        if(size < storage.length) {
            for(int i = 0; i < size; i++) {
                if(storage[i].uuid.equals(r.uuid)) {
                    System.out.println("Резюме уже присутствует в хранилище");
                    return;
                }
            }
            storage[size++] = r;
        }
    }

    Resume get(String uuid) {
        for(int i = 0; i < size; i++) {
            if(storage[i].uuid.equals(uuid)) {
                return storage[i];
            }
        }
        return null;
    }

    void delete(String uuid) {
        for(int i = 0; i < size; i++) {
            if(storage[i].uuid.equals(uuid)) {
                storage[i] = storage[--size];
                storage[size] = null;
                break;
            }
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    int size() {
        return size;
    }
}
