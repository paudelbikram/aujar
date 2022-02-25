package com.techpasya.aujar;

import com.techpasya.aujar.clazzloader.AujarClassLoader;
import com.techpasya.aujar.graph.AjEdge;
import com.techpasya.aujar.model.ClassComponent;
import com.techpasya.aujar.model.SearchBoundary;
import com.techpasya.aujar.util.AujarUtil;
import com.techpasya.aujar.visualize.GraphBuilder;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.IOException;
import java.util.*;

public class Aujar {

    private ClassComponent rootClassComponent;
    private final Class<?> rootClass;
    private final SearchBoundary searchBoundary;
    private AujarException aujarException = null;
    private final HashMap<Class<?>, ClassComponent> classComponentContainers;
    private boolean doneBuildingTree;

    private Aujar(final Class<?> rootClass, final SearchBoundary searchBoundry) {
        this.rootClass = rootClass;
        this.classComponentContainers = new HashMap<>();
        this.searchBoundary = searchBoundry;
        this.doneBuildingTree = false;
    }


    private void resetException() {
        this.aujarException = null;
    }


    public static Aujar build(final String startingClassPath, final String boundaryPackagePath)
            throws AujarException {
        Objects.requireNonNull(startingClassPath, "Starting Class Path is required");
        Objects.requireNonNull(boundaryPackagePath, "Boundary Package Path is required");
        Class<?> rootClass = null;
        try {
            rootClass = Class.forName(startingClassPath);
            return new Aujar(rootClass, new SearchBoundary(boundaryPackagePath));
        } catch (ClassNotFoundException e) {
            rootClass = AujarClassLoader.classesSuccessfullyLoaded.get(startingClassPath);
            if (rootClass != null) {
                return new Aujar(rootClass, new SearchBoundary(boundaryPackagePath));
            }
            throw new AujarException("Starting Class Path can not be found");
        }
    }

    // TODO This one does not take starting classpath, rather just packagename and builds the whole visualize off of packagename.
    public static Aujar build(final String boundaryPackagePath) {
        Objects.requireNonNull(boundaryPackagePath, "Boundary Package Path is required");
        throw new NotImplementedException();
    }


    public void save(final String downLoadPath) throws IOException {
        Map<Class<?>, List<AjEdge>> adjClasses;
        if (doneBuildingTree) {
            adjClasses = AujarUtil.convertToDirectedGraph(rootClassComponent).getAdjClasses();
        } else {
            adjClasses = AujarUtil.convertToDirectedGraph(getTree()).getAdjClasses();
        }
        GraphBuilder.build(adjClasses, downLoadPath);
    }


    public ClassComponent getRootClassComponent() {
        return rootClassComponent;
    }

    public Exception getException() {
        return aujarException;
    }


    public ClassComponent getTree() {
        if (doneBuildingTree) {
            return rootClassComponent;
        }
        resetException();
        rootClassComponent = initializeClassComponent(rootClass);
        recursiveBuild(rootClassComponent);
        doneBuildingTree = true;
        return rootClassComponent;
    }

    public Map<Class<?>, List<AjEdge>> getGraph() {
        if (doneBuildingTree) {
            return AujarUtil.convertToDirectedGraph(rootClassComponent).getAdjClasses();
        }
        return AujarUtil.convertToDirectedGraph(getTree()).getAdjClasses();
    }

    public String getJsonTree() {
        if (doneBuildingTree) {
            return rootClassComponent.toJson();
        }
        return getTree().toJson();
    }

    private ClassComponent initializeClassComponent(Class<?> cls) {
        ClassComponent classComponent = new ClassComponent(cls);
        classComponent.setClassImplements(getClassComponents(AujarUtil.getAllInterfaces(cls)));
        classComponent.setClassInherits(getClassComponent(AujarUtil.getSuperclasse(cls)));
        classComponent.setClassContains(getClassComponents(AujarUtil.getFieldClasses(cls)));
        return classComponent;
    }

    //DFS
    private void recursiveBuild(ClassComponent classComponent) {
        Stack<ClassComponent> stack = new Stack<>();
        stack.add(classComponent);

        while (!stack.isEmpty()) {
            ClassComponent currentClassComponent = stack.pop();

            Class<?> clz = currentClassComponent.getClazz();
            currentClassComponent.copyMembers(initializeClassComponent(clz));
            classComponentContainers.put(clz, currentClassComponent);
            currentClassComponent.getAllTheClassComponents().forEach(c -> {
                if (!classComponentContainers.containsKey(c.getClazz())) {
                    stack.push(c);
                }
            });
        }
    }


    private ClassComponent getClassComponent(Class<?> cls) {
        if (cls == null || cls.getPackage() == null) {
            return null;
        }
        if (!searchBoundary.isFromThePackage(cls.getPackage().getName())) {
            return null;
        }
        return new ClassComponent(cls);
    }


    private List<ClassComponent> getClassComponents(List<Class<?>> clazzes) {
        if (clazzes == null || clazzes.size() == 0) {
            return new ArrayList<>();
        }
        List<ClassComponent> listOfClassComponents = new ArrayList<>();
        for (Class<?> cls : clazzes) {
            if (cls != null) {
                final ClassComponent clsComponent = getClassComponent(cls);
                if (clsComponent != null) {
                    listOfClassComponents.add(getClassComponent(cls));
                }
            }
        }
        return listOfClassComponents;
    }
}
