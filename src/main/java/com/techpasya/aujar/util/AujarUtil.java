package com.techpasya.aujar.util;

import com.techpasya.aujar.graph.AjEdge;
import com.techpasya.aujar.graph.AjGraph;
import com.techpasya.aujar.graph.EdgeType;
import com.techpasya.aujar.model.ClassComponent;
import sun.reflect.generics.reflectiveObjects.GenericArrayTypeImpl;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;
import sun.reflect.generics.reflectiveObjects.TypeVariableImpl;
import sun.reflect.generics.reflectiveObjects.WildcardTypeImpl;

import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class AujarUtil {

    public static List<Class<?>> getAllInterfaces(final Class<?> clazz) {
        if (clazz == null) {
            throw new IllegalArgumentException("Class can no tbe null");
        }
        return Arrays.asList(clazz.getInterfaces());
    }


    public static Class<?> getSuperclasse(final Class<?> cls) {
        if (cls == null) {
            throw new IllegalArgumentException("Class can no tbe null");
        }
        return cls.getSuperclass();
    }


    private static void addGenericTypes(List<Class<?>> fieldClasses, final Type genericType) {
        if (genericType instanceof WildcardTypeImpl) {
            //These are like ? wildcards might have upperbounds
            Type[] upperBoundsTypes = ((WildcardTypeImpl) genericType)
                    .getUpperBounds();
            for (Type upperBoundType : upperBoundsTypes) {
                if (upperBoundType instanceof Class<?>) {
                    fieldClasses.add((Class<?>) upperBoundType);
                } else {
                    addGenericTypes(fieldClasses, upperBoundType);
                }
            }

            Type[] lowerBounds = ((WildcardTypeImpl) genericType)
                    .getLowerBounds();
            for (Type lowerBound : lowerBounds) {
                if (lowerBound instanceof Class<?>) {
                    fieldClasses.add((Class<?>) lowerBound);
                } else {
                    addGenericTypes(fieldClasses, lowerBound);
                }
            }
        } else if (genericType instanceof TypeVariableImpl) {
            for (Type type : ((TypeVariableImpl) genericType).getBounds()) {
                if (type instanceof Class<?>) {
                    fieldClasses.add((Class<?>) type);
                } else {
                    addGenericTypes(fieldClasses, type);
                }
            }
            //These are like K, V and bounds to super class Object
        } else if (genericType instanceof ParameterizedTypeImpl) {
            ParameterizedType parameterizedType = (ParameterizedType) genericType;
            Type[] typeArguments = parameterizedType.getActualTypeArguments();
            Type rawType = parameterizedType.getRawType();
            Type ownerType = parameterizedType.getOwnerType();

            if (rawType != null) {
                if (rawType instanceof Class<?>) {
                    fieldClasses.add((Class<?>) rawType);
                } else {
                    addGenericTypes(fieldClasses, rawType);
                }
            }
            if (ownerType != null) {
                if (ownerType instanceof Class<?>) {
                    fieldClasses.add((Class<?>) ownerType);
                } else {
                    addGenericTypes(fieldClasses, ownerType);
                }
            }

            for (Type typeArgument : typeArguments) {
                if (typeArgument instanceof Class<?>) {
                    fieldClasses.add((Class<?>) typeArgument);
                } else {
                    addGenericTypes(fieldClasses, typeArgument);
                }
            }
        } else if (genericType instanceof GenericArrayTypeImpl) {
            Type type = ((GenericArrayType) genericType).getGenericComponentType();
            if (type instanceof Class<?>) {
                fieldClasses.add((Class<?>) type);
            } else {
                addGenericTypes(fieldClasses, type);
            }
        } else if (genericType instanceof Class<?>) {
            fieldClasses.add((Class<?>) genericType);
        } else {
            System.err.println("Found UnExpected Types");
        }
    }


    public static List<Class<?>> getFieldClasses(final Class<?> cls) {
        if (cls == null) {
            throw new IllegalArgumentException("Class can not be null");
        }
        Field[] fields = cls.getDeclaredFields();
        if (fields.length == 0) {
            return new ArrayList<>();
        }
        List<Class<?>> fieldClasses = new ArrayList<>();
        for (Field field : fields) {
            final Type genericType = field.getGenericType();
            if (genericType instanceof Class<?>) {
                fieldClasses.add(field.getType());
            } else {
                addGenericTypes(fieldClasses, genericType);
            }
        }
        return fieldClasses;
    }


    private static void recursiveGraphCreation(AjGraph graph, ClassComponent root) {
        final Class<?> rootClass = root.getClazz();
        if (!graph.hasClass(root.getClazz())) {
            graph.addVertex(rootClass);
        }
        final ClassComponent extendsCC = root.getClassInherits();
        if (extendsCC != null) {
            graph.addEdge(rootClass, new AjEdge(rootClass, extendsCC.getClazz(), EdgeType.EXTENDS));
            recursiveGraphCreation(graph, extendsCC);
        }
        for (final ClassComponent implmentsCC : root.getClassImplements()) {
            graph.addEdge(rootClass,
                    new AjEdge(rootClass, implmentsCC.getClazz(), EdgeType.IMPLEMENTS));
            recursiveGraphCreation(graph, implmentsCC);
        }
        for (final ClassComponent containsCC : root.getClassContains()) {
            graph.addEdge(rootClass,
                    new AjEdge(rootClass, containsCC.getClazz(), EdgeType.CONTAINS));
            recursiveGraphCreation(graph, containsCC);
        }
    }


    public static AjGraph convertToDirectedGraph(final ClassComponent rootClassComponent) {
        AjGraph graph = new AjGraph();
        recursiveGraphCreation(graph, rootClassComponent);
        return graph;
    }
}
