package com.techpasya.aujar.hierarchytest;

public class ChildClass extends ParentClass {

  private ConcreteClass concreteClass;
  private ImplementationClass implementationClass;

  public ChildClass(ConcreteClass concreteClass, ImplementationClass implementationClass) {
    this.concreteClass = concreteClass;
    this.implementationClass = implementationClass;
  }
}
