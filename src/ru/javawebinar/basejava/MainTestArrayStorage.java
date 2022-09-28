package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.*;
import ru.javawebinar.basejava.storage.Storage;

/**
 * Test for your com.urise.webapp.storage.ArrayStorage implementation
 */
public class MainTestArrayStorage {

    static final Storage ARRAY_STORAGE = new MapResumeStorage();

    public static void main(String[] args) {
        Resume r1 = new Resume("uuid1", "Catherin");
        Resume r2 = new Resume("uuid2", "Annabel");
        Resume r3 = new Resume("uuid3", "Bettany");

        ARRAY_STORAGE.save(r1);
        ARRAY_STORAGE.save(r2);
        ARRAY_STORAGE.save(r3);

        System.out.println("Get r1: " + ARRAY_STORAGE.get(r1.getUuid()));
        System.out.println("Size: " + ARRAY_STORAGE.size());

        //System.out.println("Get dummy: " + ARRAY_STORAGE.get("dummy"));
        {
            System.out.println("Update uuid2");
            Resume resumeToUpdate = new Resume("uuid2", "Tiffany");
//            System.out.println("Before update " + (resumeToUpdate == ARRAY_STORAGE.get("uuid2")));
            System.out.println("Before update " + (resumeToUpdate == ARRAY_STORAGE.get("uuid2")));
            ARRAY_STORAGE.update(resumeToUpdate);
//            System.out.println("After update " + (resumeToUpdate == ARRAY_STORAGE.get("uuid2")));
            System.out.println("After update " + (resumeToUpdate == ARRAY_STORAGE.get("uuid2")));
//            ARRAY_STORAGE.update(new Resume("uuid2"));
        }

        printAll();
        ARRAY_STORAGE.delete(r1.getUuid());
        printAll();
        ARRAY_STORAGE.clear();
        printAll();

        System.out.println("Size: " + ARRAY_STORAGE.size());
    }

    static void printAll() {
        System.out.println("\nGet All");
        for (Resume r : ARRAY_STORAGE.getAllSorted()) {
            System.out.println(r);
        }
    }
}
