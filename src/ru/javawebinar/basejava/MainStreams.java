package ru.javawebinar.basejava;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainStreams {

    public static void main(String[] args) {
        System.out.println(minValue(new int[]{1, 4, 4, 3, 5, 6, 3}));
        var list = new ArrayList<Integer>();
        list.add(2);
        list.add(2);
        list.add(45);
        list.add(43);
        list.add(43);
        var newList = oddOrEven(list);
        newList.forEach(System.out::println);
    }

    private static int minValue(int[] values) {
        return Arrays.stream(values)
                .distinct()
                .sorted()
                .reduce((x, y) -> x * 10 + y)
                .getAsInt();
    }

    public static List<Integer> oddOrEven(List<Integer> integers) {
        boolean sumIsEven = integers.stream().mapToInt(Integer::valueOf).sum() % 2 == 0;
        return integers.stream()
                .mapToInt(Integer::valueOf)
                .filter(i -> (i % 2 == 0) == sumIsEven)
                .boxed()
                .toList();
    }
}
