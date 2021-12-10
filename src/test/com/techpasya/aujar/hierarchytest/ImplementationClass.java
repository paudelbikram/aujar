package com.techpasya.aujar.hierarchytest;

public class ImplementationClass implements InterfaceClass {
    @Override
    public void overrideMe() {
        System.out.println("Implementation Class Overriding");
    }

    @Override
    public String whatDoIsay(String s) {
        return "Hello From ImplementationClass";
    }
}
