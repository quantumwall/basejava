/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    private int index = 0;

    void clear() {
        for(int i = 0; i < index; i++) {
            storage[i] = null;
        }
        index = 0;
    }

    void save(Resume r) {
        if(index < storage.length) {
            storage[index++] = r;
        }
    }

    Resume get(String uuid) {
        for(int i = 0; i < index; i++) {
            if(storage[i].uuid.equals(uuid)) {
                return storage[i];
            }
        }
        return null;
    }

    void delete(String uuid) {
        for(int i = 0; i < index; i++) {
            if(storage[i].uuid.equals(uuid)) {
                System.arraycopy(storage, i + 1, storage, i, index - i - 1);
                storage[--index] = null;
                break;
            }
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        Resume[] allResume = new Resume[index];
        System.arraycopy(storage, 0, allResume, 0, index);
        return allResume;
    }

    int size() {
        return index;
    }
}
