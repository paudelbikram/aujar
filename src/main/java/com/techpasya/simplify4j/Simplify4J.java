package com.techpasya.simplify4j;

import com.techpasya.simplify4j.model.ClassComponent;
import com.techpasya.simplify4j.model.SearchBoundary;
import com.techpasya.simplify4j.util.Simplify4jUtil;

import java.util.*;

public class Simplify4J {

    private ClassComponent rootClassComponent;
    private String rootClassPath;
    private SearchBoundary searchBoundary;
    private Simplify4JException simplify4JException = null; //Should this
    private HashMap<Class<?>, ClassComponent> classComponentContainers;



    public Simplify4J(final String rootClassPath, final SearchBoundary searchBoundry){
        Objects.requireNonNull(rootClassPath, "Root Class Path is required");
        Objects.requireNonNull(searchBoundry, "Search Boundary is required");
        this.rootClassPath = rootClassPath;
        this.classComponentContainers = new HashMap<>();
        this.searchBoundary = searchBoundry;
    }


    public ClassComponent getRootClassComponent(){
        return rootClassComponent;
    }


    public Exception getException(){
        return simplify4JException;
    }


    public void build(){
        try {
            final Class<?> rootClass = Class.forName(rootClassPath);
            rootClassComponent = initializeClassComponent(rootClass);
            recursiveBuild(rootClassComponent);
        } catch (ClassNotFoundException e) {
            this.simplify4JException = new Simplify4JException("Class Not Found", e);
        }
    }

    private ClassComponent initializeClassComponent(Class<?> cls){
        ClassComponent classComponent = new ClassComponent(cls);
        classComponent.setClassImplements(getClassComponents(Simplify4jUtil.getAllInterfaces(cls)));
        classComponent.setClassInherits(getClassComponent(Simplify4jUtil.getSuperclasse(cls)));
        classComponent.setClassContains(getClassComponents(Simplify4jUtil.getFieldClasses(cls)));
        return classComponent;
    }


    private void recursiveBuild(ClassComponent classComponent){
        List<ClassComponent> associatedClassComponents = classComponent.getAllTheClassComponents();
        for(ClassComponent clsComponent: associatedClassComponents){
            Class<?> clz = clsComponent.getClassName();
            clsComponent.copyMembers(initializeClassComponent(clz));
            recursiveBuild(clsComponent);
        }
    }



    private ClassComponent getClassComponent(Class<?> cls){
        if (cls == null || cls.getPackage() == null){
            return null;
        }
        if (!searchBoundary.isFromThePackage(cls.getPackage().getName())){
            return null;
        }
        ClassComponent classComponent = classComponentContainers.get(cls);
        if (classComponent != null){
            return classComponent;
        }
        return new ClassComponent(cls);
    }


    private List<ClassComponent> getClassComponents(List<Class<?>> clazzes){
        if (clazzes == null || clazzes.size() == 0){
            return new ArrayList<>();
        }
        List<ClassComponent> listOfClassComponents = new ArrayList<>();
        for(Class<?> cls : clazzes) {
            if(cls != null){
                final ClassComponent clsComponent = getClassComponent(cls);
                if (clsComponent != null){
                    listOfClassComponents.add(getClassComponent(cls));
                }
            }
        }
        return listOfClassComponents;
    }




}
