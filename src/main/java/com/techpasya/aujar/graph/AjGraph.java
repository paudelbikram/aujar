package com.techpasya.aujar.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AjGraph {

  private Map<Class<?>, List<AjEdge>> adjClasses = new HashMap<>();


  public Map<Class<?>, List<AjEdge>> getAdjClasses() {
    return adjClasses;
  }

  public void setAdjClasses(Map<Class<?>, List<AjEdge>> adjClasses) {
    this.adjClasses = adjClasses;
  }

  public void addVertex(Class<?> clazz) {
    adjClasses.putIfAbsent(clazz, new ArrayList<>());
  }

  public void removeCVertexAndAssociatedEdge(Class<?> clazz) {
    adjClasses.values().stream().forEach((v) -> {
      v.forEach((x) -> {
        if (x.getSource().equals(clazz) || x.getDestination().equals(clazz)) {
          v.remove(x);
        }
      });
    });
    adjClasses.remove(clazz);
  }


  public boolean hasClass(Class<?> clazz) {
    return adjClasses.keySet().contains(clazz);
  }


  public void addEdge(Class<?> source, AjEdge ajEdge) {
    adjClasses.get(source).add(ajEdge);
  }


}
