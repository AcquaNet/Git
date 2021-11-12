/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.atina.metadata.utils;

import com.atina.metadata.utils.ArchiveUtil;
import java.io.File;
import java.io.IOException;
import java.util.jar.JarFile;
import org.apache.commons.io.FilenameUtils;

/**
 *
 * @author jgodi
 */
public class UnzipJar {

    public static void unzipJar(String destinationDir, String jarPath) throws IOException {
        
        File file = new File(jarPath);
        
        JarFile jar = new JarFile(file);
        
        String jarName = FilenameUtils.removeExtension(file.getName());

        String dirJarName = destinationDir + File.separator + jarName;
        
        File fileDirJarName = new File(dirJarName);
         
        fileDirJarName.mkdirs();
         
        ArchiveUtil.extract(file, fileDirJarName, false);
          
    }
}
