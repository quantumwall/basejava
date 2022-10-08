package ru.javawebinar.basejava;

import java.io.File;

public class MainFile {

    public static void main(String[] args) {
        recursionWalk(new File(System.getProperty("user.dir")));
    }

    private static void recursionWalk(File dir) {
        for (File file : dir.listFiles()) {
            System.out.println(file.getAbsolutePath());
            if (file.isDirectory()) {
                recursionWalk(file);
            }
        }
    }
}
