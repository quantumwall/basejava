package ru.javawebinar.basejava;

import java.io.File;

public class MainFile {

    public static void main(String[] args) {
        recursionWalk(new File(System.getProperty("user.dir")), 0);
    }

    private static void recursionWalk(File dir, int tabCount) {
        for (File file : dir.listFiles()) {
            System.out.printf("%s%s\n", "\t".repeat(tabCount), file.getName());
            if (file.isDirectory()) {
                recursionWalk(file, tabCount++);
            }
        }
    }
}
