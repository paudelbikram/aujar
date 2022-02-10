package com.techpasya.aujar.clazzloader;

import com.techpasya.aujar.AujarException;

import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Stream;

public class AujarClassLoader {
    public static List<String> classesFailedToLoad = new ArrayList<>();
    public static Map<String, Class<?>> classesSuccessfullyLoaded = new HashMap<>();


    public void loadClasses(final String path) throws AujarException {
        Path providedPath = Paths.get(path);
        if (Files.isDirectory(providedPath)) {
            loadClassesFromFolder(providedPath);
        } else if (path.endsWith(".jar")) {
            loadClassesFromJarFile(providedPath.toString());
        }
        if (!classesFailedToLoad.isEmpty()) {
            throw new AujarException("Following classes not found "+ Arrays.toString(classesFailedToLoad.toArray()));
        }
    }

    private void loadClassesFromFolder(final Path folderPath) {
        try (Stream<Path> paths = Files.walk(folderPath)) {
            paths.filter(Files::isRegularFile)
            .filter(p -> p.getFileName().endsWith(".jar"))
            .forEach(p ->loadClassesFromJarFile(p.toString()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void loadClassesFromJarFile(final String jarFilePath)  {
        try {
            JarFile jarFile = new JarFile(jarFilePath);
            Enumeration<JarEntry> e = jarFile.entries();
            URL[] urls = new URL[]{ new URL("jar:file:" + jarFilePath+"!/") };
            URLClassLoader cl = URLClassLoader.newInstance(urls);
            while (e.hasMoreElements()) {
                JarEntry je = e.nextElement();
                if((!je.isDirectory()) && je.getName().endsWith(".class")){
                    String className = je.getName()
                            .replace("/", ".")
                            .replace(".class", "");
                    try {
                        Class<?> classLoaded = cl.loadClass(className);
                        classesSuccessfullyLoaded.put(className, classLoaded);
                    } catch (ClassNotFoundException ex) {
                        classesFailedToLoad.add(className);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
