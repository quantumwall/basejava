package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

public class SortedArrayStorageTest extends AbstractArrayStorageTest {
    
    private Resume[] expResultForGetAll = {
        new Resume("uuid1"),
        new Resume("uuid29"),
        new Resume("uuid34"),
        new Resume("uuid5")
    };

    public SortedArrayStorageTest() {
        super(new SortedArrayStorage());
    }
    
    @Override
    public void getAll(Resume[] expResult) {
        super.getAll(expResultForGetAll);
    }
}
