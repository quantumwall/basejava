package ru.javawebinar.basejava;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class MainStreams {

    public static void main(String[] args) {
        System.out.println(minValue(new int[]{1, 4, 4, 3, 5, 6, 3}));
        var list = oddOrEven(getList());
        list.forEach(System.out::println);
    }

    private static int minValue(int[] values) {
        return Arrays.stream(values)
                .distinct()
                .sorted()
                .reduce((x, y) -> x * 10 + y)
                .getAsInt();
    }

    public static List<Integer> oddOrEven(List<Integer> integers) {
        IntStream intStream = integers.stream().mapToInt(Integer::valueOf);
        long sum = intStream.summaryStatistics().getSum();
        return integers.stream()
                .mapToInt(Integer::valueOf)
                .filter(i -> i % 2 == sum % 2)
                .boxed()
                .toList();
    }

    private static List<Integer> getList() {
        var result = new ArrayList<Integer>();
        for (int i = 1; i <= 10; i++) {
            result.add(i);
        }
        return result;
    }
}
