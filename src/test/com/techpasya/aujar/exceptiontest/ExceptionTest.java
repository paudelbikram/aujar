package com.techpasya.aujar.exceptiontest;

import com.techpasya.aujar.Aujar;
import com.techpasya.aujar.AujarException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ExceptionTest {


    @Test
    @DisplayName("Throws NullPointer when StartingClassPath is null")
    public void nullRootClassPathTest(){
        assertThrows(NullPointerException.class,
                ()->Aujar.build(null,
                        "com.techpasya.aujar.exceptiontest"));
    }

    @Test
    @DisplayName("Throws NullPointer when BoundaryClassPath is null")
    public void nullBoundaryPackagePathTest(){
        assertThrows(NullPointerException.class,
                ()->Aujar.build("com.techpasya.aujar.exceptiontest.ExceptionTest",
                        null));
    }


    @Test
    @DisplayName("Throws AujarException when StartingClass can not be found")
    public void startingClassNotFoundTest(){
        assertThrows(AujarException.class,
                ()->Aujar.build("com.techpasya.aujar.exceptiontest.NonExistentClass",
                        "com.techpasya.aujar.exceptiontest"));
    }
}
