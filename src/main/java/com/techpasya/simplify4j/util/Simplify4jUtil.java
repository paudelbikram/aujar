package com.techpasya.simplify4j.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class Simplify4jUtil {

    public static List<Class<?>> getAllInterfaces(final Class<?> clazz){
        if (clazz == null) return null;
        return Arrays.asList(clazz.getInterfaces());
    }


    public static Class<?> getSuperclasse(final Class<?> cls){
        if (cls == null) return null;
        return cls.getSuperclass();
    }


    //TODO: Only return non primitive types
    public static List<Class<?>> getFieldClasses(final Class<?> cls){
        Field[] fields = cls.getDeclaredFields();
        if (fields.length == 0) return new ArrayList<>();
        List<Class<?>> fieldClasses = new ArrayList<>();
        for (Field field: fields) {
            Class<?> fieldClass = field.getType();
            fieldClasses.add(fieldClass);
        }
        return fieldClasses;
    }
}
