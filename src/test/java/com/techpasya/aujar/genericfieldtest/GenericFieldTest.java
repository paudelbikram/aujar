package com.techpasya.aujar.genericfieldtest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import com.techpasya.aujar.Aujar;
import com.techpasya.aujar.AujarException;
import com.techpasya.aujar.model.ClassComponent;
import java.io.File;
import java.io.IOException;
import org.junit.jupiter.api.Test;

public class GenericFieldTest {


  @Test
  public void testGenericFieldWithMultipleTypeParameters() {
    Aujar aujar = null;
    try {
      aujar = Aujar.build("com.techpasya.aujar.genericfieldtest.ClassWithGenericField",
          "com.techpasya.aujar.genericfieldtest");
      assertEquals(aujar.getTree(), getExpectedClassComponent());
      aujar.save("test-data/genericfieldtest.svg");
      assert (new File("test-data/genericfieldtest.svg").exists());
    } catch (AujarException | IOException e) {
      e.printStackTrace();
      fail();
    }
  }


  private ClassComponent getExpectedClassComponent() {
    ClassComponent rootClassComponent = new ClassComponent(ClassWithGenericField.class);
    ClassComponent classYComponent = new ClassComponent(ClassY.class);
    ClassComponent classZComponent = new ClassComponent(ClassZ.class);
    rootClassComponent.addClassContain(classYComponent);
    rootClassComponent.addClassContain(classZComponent);
    return rootClassComponent;
  }
}
