package com.techpasya.aujar.tree;

import com.techpasya.aujar.model.ClassComponent;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TreeBuilder {

    /**
     * Takes the ClassComponent and DownloadPath and downloads tree image to downloadpath.
     * @param classComponent
     * @param downloadPath
     * @throws FileNotFoundException
     */
    public static void build(final ClassComponent classComponent, final String downloadPath) throws FileNotFoundException {
        Path downloadDir = Paths.get(downloadPath);
        if (Files.exists(downloadDir)){
            throw new FileNotFoundException("Download Directory Does not exists");
        }
        throw new NotImplementedException();
    }



}
