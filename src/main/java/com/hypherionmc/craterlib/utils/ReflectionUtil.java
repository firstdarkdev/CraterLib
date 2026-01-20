package com.hypherionmc.craterlib.utils;

import java.lang.reflect.Field;

public class ReflectionUtil {

    public static <T> T getPublic(Class<T> classZ, Object object, String fieldName) {
        try {
            Field field = object.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return (T) field.get(object);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

}
