package com.techpasya.simplify4j.model;

import java.util.ArrayList;
import java.util.List;

public class ClassComponent {
    private ClassComponent classInherits;
    private List<ClassComponent> classImplements;
    private List<ClassComponent> classContains;
    private Class<?> clazz;

    public ClassComponent(Class<?> clazz){
        classContains = new ArrayList<>();
        classImplements = new ArrayList<>();
        classInherits = null;
        this.clazz = clazz;
    }

    public Class<?> getClassName() {
        return clazz;
    }

    public void setClassName(Class<?> clazz) {
        this.clazz = clazz;
    }

    public ClassComponent getClassInherits() {
        return classInherits;
    }

    public void setClassInherits(ClassComponent classInherits) {
        this.classInherits = classInherits;
    }

    public List<ClassComponent> getClassImplements() {
        return classImplements;
    }

    public void setClassImplements(List<ClassComponent> classImplements) {
        this.classImplements = classImplements;
    }

    public List<ClassComponent> getClassContains() {
        return classContains;
    }

    public void setClassContains(List<ClassComponent> classContains) {
        this.classContains = classContains;
    }

    public void addClassImplement(ClassComponent classComponent){
        this.getClassImplements().add(classComponent);
    }

    public void addClassContain(ClassComponent classComponent){
        this.getClassContains().add(classComponent);
    }


    public List<ClassComponent> getAllTheClassComponents(){
        List<ClassComponent> allClassComponents = new ArrayList<>();

        if (getClassInherits() != null ){
            allClassComponents.add(getClassInherits());
        }
        if (getClassImplements() != null || getClassImplements().size() != 0){
            allClassComponents.addAll(getClassImplements());
        }
        if (getClassContains() != null || getClassContains().size() != 0){
            allClassComponents.addAll(getClassContains());
        }
        return allClassComponents;
    }


    public void copyMembers(final ClassComponent classComponent){
        this.setClassContains(classComponent.getClassContains());
        this.setClassInherits(classComponent.getClassInherits());
        this.setClassImplements(classComponent.getClassImplements());
    }
}
