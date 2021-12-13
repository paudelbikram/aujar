package com.techpasya.aujar.typevariableimpltest;

import com.techpasya.aujar.Aujar;
import com.techpasya.aujar.AujarException;
import com.techpasya.aujar.model.ClassComponent;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class TypeVariableImplTest {

    @Test
    public void testTypeVariableImpl(){
        Aujar aujar = null;
        try {
            aujar = Aujar.build("com.techpasya.aujar.typevariableimpltest.Storage",
                    "com.techpasya.aujar.typevariableimpltest");
            assertEquals(aujar.getTree(), getExpectedClassComponent());
        } catch (AujarException e) {
            e.printStackTrace();
            fail();
        }
    }


    private ClassComponent getExpectedClassComponent(){
        ClassComponent rootClassComponent = new ClassComponent(Storage.class);
        return rootClassComponent;
    }
}
