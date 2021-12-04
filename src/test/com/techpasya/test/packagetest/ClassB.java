package com.techpasya.test.packagetest;

public class ClassB implements InterfaceA{
    @Override
    public void callMe() {
        System.out.println("Class B calling callMe");
    }

    @Override
    public String whatDoIsay(String s) {
        return "From ClassB";
    }
}
