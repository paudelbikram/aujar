package com.techpasya.aujar.wildcardtypeimpltest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import com.techpasya.aujar.Aujar;
import com.techpasya.aujar.AujarException;
import com.techpasya.aujar.model.ClassComponent;
import java.io.File;
import java.io.IOException;
import org.junit.jupiter.api.Test;

public class WildcardTypeImplTest {

  @Test
  public void testWildCardImpl() {
    Aujar aujar = null;
    try {
      aujar = Aujar.build("com.techpasya.aujar.wildcardtypeimpltest.Zoo",
          "com.techpasya.aujar.wildcardtypeimpltest");
      assertEquals(aujar.getTree(), getExpectedClassComponent());
      aujar.save("test-data/wildcartypeimpl.svg");
      assert (new File("test-data/wildcartypeimpl.svg").exists());
    } catch (AujarException | IOException e) {
      e.printStackTrace();
      fail();
    }
  }


  private ClassComponent getExpectedClassComponent() {
    ClassComponent rootClassComponent = new ClassComponent(Zoo.class);
    ClassComponent animalClassComponent = new ClassComponent(Animal.class);
    rootClassComponent.addClassContain(animalClassComponent);
    return rootClassComponent;
  }
}
