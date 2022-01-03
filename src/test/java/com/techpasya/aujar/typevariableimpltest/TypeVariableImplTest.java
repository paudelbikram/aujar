package com.techpasya.aujar.typevariableimpltest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import com.techpasya.aujar.Aujar;
import com.techpasya.aujar.AujarException;
import com.techpasya.aujar.model.ClassComponent;
import java.io.File;
import java.io.IOException;
import org.junit.jupiter.api.Test;

public class TypeVariableImplTest {

  @Test
  public void testTypeVariableImpl() {
    Aujar aujar = null;
    try {
      aujar = Aujar.build("com.techpasya.aujar.typevariableimpltest.Storage",
          "com.techpasya.aujar.typevariableimpltest");
      assertEquals(aujar.getTree(), getExpectedClassComponent());
      aujar.save("test-data/typevariableimpltest.svg");
      assert (new File("test-data/typevariableimpltest.svg").exists());
    } catch (AujarException | IOException e) {
      e.printStackTrace();
      fail();
    }
  }


  private ClassComponent getExpectedClassComponent() {
    ClassComponent rootClassComponent = new ClassComponent(Storage.class);
    return rootClassComponent;
  }
}
