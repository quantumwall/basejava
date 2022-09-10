package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

public class ArrayStorageTest extends AbstractArrayStorageTest {

    public ArrayStorageTest() {
        super(new ArrayStorage());
    }
    
    @Override
    public void getAll(Resume... expResult) {
        super.getAll(new Resume[0]);
    }
}
