package com.techpasya.aujar.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ClassComponent {

    private ClassComponent classInherits;
    private List<ClassComponent> classImplements;
    private List<ClassComponent> classContains;
    private Class<?> clazz;

    public ClassComponent(Class<?> clazz) {
        classContains = new ArrayList<>();
        classImplements = new ArrayList<>();
        classInherits = null;
        this.clazz = clazz;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public void setClazz(Class<?> clazz) {
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

    public void addClassImplement(ClassComponent classComponent) {
        this.getClassImplements().add(classComponent);
    }

    public void addClassContain(ClassComponent classComponent) {
        this.getClassContains().add(classComponent);
    }


    public List<ClassComponent> getAllTheClassComponents() {
        List<ClassComponent> allClassComponents = new ArrayList<>();
        if (getClassInherits() != null) {
            allClassComponents.add(getClassInherits());
        }
        if (getClassImplements() != null || getClassImplements().size() != 0) {
            allClassComponents.addAll(getClassImplements());
        }
        if (getClassContains() != null || getClassContains().size() != 0) {
            allClassComponents.addAll(getClassContains());
        }
        return allClassComponents;
    }


    public void copyMembers(final ClassComponent classComponent) {
        this.setClassContains(classComponent.getClassContains());
        this.setClassInherits(classComponent.getClassInherits());
        this.setClassImplements(classComponent.getClassImplements());
    }

    public String toJson() {
        return "{" +
                "\"classInherits\":" + classInherits +
                ", \"classImplements\":" + (classImplements == null ? "null"
                : Arrays.toString(classImplements.toArray())) +
                ", \"classContains\":" + (classContains == null ? "null"
                : Arrays.toString(classContains.toArray())) +
                ", \"clazz\":\"" + clazz.toString() + "\"" +
                '}';
    }

    @Override
    public String toString() {
        return toJson();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ClassComponent that = (ClassComponent) o;
        return Objects.equals(getClassInherits(), that.getClassInherits())
                && getClassImplements().equals(that.getClassImplements())
                && getClassContains().equals(that.getClassContains())
                && Objects.equals(getClazz(), that.getClazz());
    }

    @Override
    public int hashCode() {
        return Objects
                .hash(getClassInherits(), getClassImplements(), getClassContains(), getClazz());
    }
}
