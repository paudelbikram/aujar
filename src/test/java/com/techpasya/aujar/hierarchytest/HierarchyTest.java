package com.techpasya.aujar.hierarchytest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import com.techpasya.aujar.Aujar;
import com.techpasya.aujar.AujarException;
import com.techpasya.aujar.model.ClassComponent;
import java.io.File;
import java.io.IOException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class HierarchyTest {

  @Test
  @DisplayName("Simple Class Hierarchy Using Interface, Abstract Class and Parent Class")
  public void testClassHierarchy() {
    Aujar aujar = null;
    try {
      aujar = Aujar.build("com.techpasya.aujar.hierarchytest.ChildClass",
          "com.techpasya.aujar.hierarchytest");
      assertEquals(aujar.getTree(), getExpectedClassComponent());
      aujar.save("test-data/hierarchytest.svg");
      assert (new File("test-data/hierarchytest.svg").exists());
    } catch (AujarException | IOException e) {
      e.printStackTrace();
      fail();
    }
  }


  private ClassComponent getExpectedClassComponent() {
    ClassComponent rootClassComponent = new ClassComponent(ChildClass.class);
    ClassComponent concreteClassComponent = new ClassComponent(ConcreteClass.class);
    ClassComponent implementationClassComponent = new ClassComponent(ImplementationClass.class);
    ClassComponent parentClassComponent = new ClassComponent(ParentClass.class);
    rootClassComponent.addClassContain(concreteClassComponent);
    rootClassComponent.addClassContain(implementationClassComponent);
    rootClassComponent.setClassInherits(parentClassComponent);

    ClassComponent abstractClassComponent = new ClassComponent(AbstractClass.class);
    concreteClassComponent.setClassInherits(abstractClassComponent);

    ClassComponent interfaceClassComponent = new ClassComponent(InterfaceClass.class);
    implementationClassComponent.addClassImplement(interfaceClassComponent);

    return rootClassComponent;
  }


}
