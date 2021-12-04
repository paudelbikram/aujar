package com.techpasya.test.packagetest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.techpasya.simplify4j.Simplify4J;
import com.techpasya.simplify4j.model.ClassComponent;
import com.techpasya.simplify4j.model.SearchBoundary;

import static java.lang.reflect.Modifier.TRANSIENT;

public class SimplePackageTest {

    public static void main(String... args) {
        testPackage();
        System.out.println("Done with test");
    }


    private static boolean testPackage(){
        Simplify4J simplify4J = new Simplify4J("com.techpasya.test.packagetest.ClassD",
                new SearchBoundary("com.techpasya.test"));
        simplify4J.build();
        final ClassComponent root = simplify4J.getRootClassComponent();
        final Gson gson = new GsonBuilder()
                .excludeFieldsWithModifiers(TRANSIENT) // STATIC|TRANSIENT in the default configuration
                .create();
        String json = gson.toJson(root);
        return true;
    }
}
