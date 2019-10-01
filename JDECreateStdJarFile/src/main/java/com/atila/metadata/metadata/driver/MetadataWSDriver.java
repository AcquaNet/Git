/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.atila.metadata.metadata.driver;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import org.codehaus.plexus.util.IOUtil;

/**
 *
 * @author jgodi
 */
public class MetadataWSDriver {
    
     
    private static String WS_JSON = "ws.json";
    private static String VO_JSON = "vo.json"; 
    
    public static void saveMetadataLocallyFromResource(String destDir) throws IOException {
        
        URL url = Thread.currentThread().getContextClassLoader().getResource(WS_JSON);

        InputStream inputStream = url.openConnection().getInputStream();

        IOUtil.copy(inputStream, new FileOutputStream(new File(destDir + File.separator + WS_JSON)));
         
        url = Thread.currentThread().getContextClassLoader().getResource(VO_JSON);

        inputStream = url.openConnection().getInputStream();

        IOUtil.copy(inputStream, new FileOutputStream(new File(destDir + File.separator + VO_JSON)));
         
    }
      
}
