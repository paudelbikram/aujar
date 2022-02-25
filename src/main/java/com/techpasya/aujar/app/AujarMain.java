package com.techpasya.aujar.app;

import com.techpasya.aujar.Aujar;
import com.techpasya.aujar.clazzloader.AujarClassLoader;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class AujarMain {
    public static void main(String... args) {
        try {
            System.out.println("Running Aujar with arguments :" + Arrays.toString(args));
            validateInput(args);
            AujarClassLoader aujarClassLoader = new AujarClassLoader();
            aujarClassLoader.loadClasses(args[0]);
            final String boundaryPath = args[1];
            final String targetClass = args[2];
            final String outputSvg = args.length == 4 ? args[3] : null;
            Aujar aujar = Aujar.build(targetClass, boundaryPath);
            aujar.save(outputSvg != null ? outputSvg : System.getProperty("user.dir") + File.separator + "graph.svg");
        } catch (Exception e) {
            e.printStackTrace();
            exitWithHelp();
        }
    }

    private static void validateInput(String... args) {
        if (args.length < 3 || args.length > 4) {
            System.err.println("Number of arguments can not be less than 3 or more than 4.");
            exitWithHelp();
        }
        final String codeSource = args[0];
        final Path codeSourcePath = Paths.get(args[0]);
        if (!Files.exists(codeSourcePath)) {
            System.err.println(codeSourcePath + " does not exists.");
            exitWithHelp();
        }
        if (Files.isRegularFile(codeSourcePath) && (!codeSource.endsWith(".jar"))) {
            System.err.println("CodeSource can only be a jar file or a folder with jar files.");
            exitWithHelp();
        }


        if (args.length == 4) {
            final String outputSvg = args[3];
            Path outputSvgPath = Paths.get(outputSvg);
            if (!outputSvgPath.endsWith(".svg")) {
                System.err.println("OutputFile File must be svg.");
                exitWithHelp();
            }
            if (!Files.exists(outputSvgPath.getParent())) {
                System.err.println(outputSvgPath.getParent() + " does not exists.");
                exitWithHelp();
            }
        }
    }


    private static void exitWithHelp() {
        help();
        System.exit(1);
    }


    private static void help() {
        System.out.println("AUJAR HELP");
        System.out.println("---------------------------------");
        System.out.println("Number of Arguments : 4");
        System.out.println("Argument 1 [Required]: [CodeSource] Source of java source code. This could be a folder with jar files or just a jar file ");
        System.out.println("Argument 2 [Required]: [BoundaryPath] Boundary path to limit the classes to search for.");
        System.out.println("Argument 3 [Required]: [TargetClass] A complete path to target class. Currently only supports one class target class per run.");
        System.out.println("Argument 4 [Optional]: [OutputFile] Name of a svg file. Currently only supports svg file and graph layout is circular. If nothing provided, default value './graph.svg' will be used.");
        System.out.println("Examples");
        System.out.println("---------------------------------");
        System.out.println("./aujar  a.jar  com.example  com.example.SampleClass  myfolder/svgs/SmapleClass.svg");
        System.out.println("./aujar  sample/jar/lib  com.mylibrary  com.mylibrary.LibraryClass");
        System.out.println("./aujar  c.jar com.example com.example.AnotherSampleClass myfolder/svgs/AnotherSampleClass.svg");
    }
}

