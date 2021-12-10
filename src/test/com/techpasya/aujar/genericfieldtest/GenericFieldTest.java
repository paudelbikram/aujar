package com.techpasya.aujar.genericfieldtest;

import com.techpasya.aujar.Aujar;
import com.techpasya.aujar.AujarException;
import com.techpasya.aujar.model.ClassComponent;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class GenericFieldTest {


    @Test
    public void testGenericFieldWithMultipleTypeParameters(){
        Aujar aujar = null;
        try {
            aujar = Aujar.build("com.techpasya.aujar.genericfieldtest.ClassWithGenericField",
                    "com.techpasya.aujar.genericfieldtest");
            assertEquals(aujar.getTree(), getExpectedClassComponent());
        } catch (AujarException e) {
            e.printStackTrace();
            fail();
        }
    }


    private ClassComponent getExpectedClassComponent(){
        ClassComponent rootClassComponent = new ClassComponent(ClassWithGenericField.class);
        ClassComponent classYComponent = new ClassComponent(ClassY.class);
        ClassComponent classZComponent = new ClassComponent(ClassZ.class);
        rootClassComponent.addClassContain(classYComponent);
        rootClassComponent.addClassContain(classZComponent);
        return rootClassComponent;
    }
}
