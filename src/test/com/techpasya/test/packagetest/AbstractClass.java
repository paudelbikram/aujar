package com.techpasya.test.packagetest;

public abstract class AbstractClass {

    public static String intro  = "I am Abstract Class";


    public void whoAreYou(){
        System.out.println(intro);
    }

    abstract void overrideme();

}
