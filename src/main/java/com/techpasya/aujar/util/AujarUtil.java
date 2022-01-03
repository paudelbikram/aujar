package com.techpasya.aujar.util;

import com.techpasya.aujar.graph.AjEdge;
import com.techpasya.aujar.graph.AjGraph;
import com.techpasya.aujar.graph.EdgeType;
import com.techpasya.aujar.model.ClassComponent;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import sun.reflect.generics.reflectiveObjects.TypeVariableImpl;
import sun.reflect.generics.reflectiveObjects.WildcardTypeImpl;

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
      } else if (genericType instanceof ParameterizedType) {
        ParameterizedType parameterizedType = (ParameterizedType) genericType;
        Type[] typeArguments = parameterizedType.getActualTypeArguments();
        Type rawType = parameterizedType.getRawType();
        Type ownerType = parameterizedType.getOwnerType();
        for (Type typeArgument : typeArguments) {
          if (typeArgument instanceof WildcardTypeImpl) {
            Type[] upperBoundsTypes = ((WildcardTypeImpl) typeArgument)
                .getUpperBounds();
            for (Type upperBoundType : upperBoundsTypes) {
              fieldClasses.add((Class<?>) upperBoundType);
            }
          } else if (typeArgument instanceof TypeVariableImpl) {
            //These are like K, V and bounds to super class Object
            continue;
          } else {
            fieldClasses.add((Class<?>) typeArgument);
          }
        }
        if (rawType != null) {
          fieldClasses.add((Class<?>) rawType);
        }
        if (ownerType != null) {
          fieldClasses.add((Class<?>) ownerType);
        }
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
