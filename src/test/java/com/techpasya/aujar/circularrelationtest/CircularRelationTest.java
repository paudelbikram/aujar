package com.techpasya.aujar.circularrelationtest;

import com.techpasya.aujar.Aujar;
import com.techpasya.aujar.AujarException;
import com.techpasya.aujar.model.ClassComponent;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class CircularRelationTest {

    @Test
    public void testCircularRelation() {
        Aujar aujar = null;
        try {
            aujar = Aujar.build("com.techpasya.aujar.circularrelationtest.City",
                    "com.techpasya.aujar.circularrelationtest");
            assertEquals(aujar.getTree(), getExpectedClassComponent());
            aujar.save("test-data/circularrelationtest.svg");
            assert (new File("test-data/circularrelationtest.svg").exists());
        } catch (AujarException | IOException e) {
            e.printStackTrace();
            fail();
        }
    }

    //Here for circular relation we stop as soon as we find out it is circular
    //but we are still going to have a leaf duplicate class component which tells us there was
    //circular relation
    private ClassComponent getExpectedClassComponent() {
        ClassComponent rootClassComponent = new ClassComponent(City.class);
        ClassComponent cityClassComponent = new ClassComponent(City.class);
        rootClassComponent.addClassContain(cityClassComponent);
        return rootClassComponent;
    }
}
