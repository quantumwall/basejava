package ru.javawebinar.basejava;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import ru.javawebinar.basejava.model.Resume;

public class MainReflection {

    public static void main(String[] args) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Resume r = new Resume("uuid1", "Stephen");
        Field field = r.getClass().getDeclaredFields()[1];
        field.setAccessible(true);
        System.out.println(field.getName());
        System.out.println(field.get(r));
        field.set(r, "new_uuid");
        String invokeResult = r.getClass().getMethod("toString").invoke(r).toString();
        System.out.println(invokeResult);
        System.out.println(r);
    }

}
