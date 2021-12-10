package com.techpasya.aujar.util;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//TODO: Should this include self class in circular dependencies.
public final class AujarUtil {

    public static List<Class<?>> getAllInterfaces(final Class<?> clazz){
        if (clazz == null) throw new IllegalArgumentException("Class can no tbe null");
        return Arrays.asList(clazz.getInterfaces());
    }


    public static Class<?> getSuperclasse(final Class<?> cls){
        if (cls == null) throw new IllegalArgumentException("Class can no tbe null");
        return cls.getSuperclass();
    }


    public static List<Class<?>> getFieldClasses(final Class<?> cls){
        if (cls == null) throw new IllegalArgumentException("Class can not be null");
        Field[] fields = cls.getDeclaredFields();
        if (fields.length == 0) return new ArrayList<>();
        List<Class<?>> fieldClasses = new ArrayList<>();
        for (Field field: fields) {
            final Type genericType = field.getGenericType();
            if (genericType instanceof Class<?>){
                fieldClasses.add(field.getType());
            } else if (genericType instanceof ParameterizedType){
                ParameterizedType parameterizedType = (ParameterizedType) genericType;
                Type[] typeArguments = parameterizedType.getActualTypeArguments();
                Type rawType = parameterizedType.getRawType();
                Type ownerType = parameterizedType.getOwnerType();
                for (Type typeArgument: typeArguments){
                    fieldClasses.add((Class<?>)typeArgument);
                }
                if (rawType != null){
                    fieldClasses.add((Class<?>)rawType);
                }
                if (ownerType != null){
                    fieldClasses.add((Class<?>)ownerType);
                }
            }
        }
        return fieldClasses;
    }
}
