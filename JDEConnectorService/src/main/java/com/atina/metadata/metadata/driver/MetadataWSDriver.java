/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.atina.metadata.metadata.driver;

import com.acqua.atina.jdeconnectorservice.JDEConnectorService;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import org.codehaus.plexus.util.IOUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author jgodi
 */
public class MetadataWSDriver {
    
    private static final Logger logger = LoggerFactory.getLogger(JDEConnectorService.class);
    
    private static String WS_JSON = "ws.json";
    private static String VO_JSON = "vo.json"; 
    
    public static void saveMetadataLocallyFromResource(String destDir) throws IOException {
        
        URL url = Thread.currentThread().getContextClassLoader().getResource(WS_JSON);
        
        logger.info("ATINA - MetadataWSDriver: saveMetadataLocallyFromResource() reading resource ws.json: " + url.toString());

        InputStream inputStream = url.openConnection().getInputStream();

        IOUtil.copy(inputStream, new FileOutputStream(new File(destDir + File.separator + WS_JSON)));
        
        logger.info("ATINA - MetadataWSDriver: saveMetadataLocallyFromResource() ws.json saved : " + destDir + File.separator + WS_JSON);
         
        url = Thread.currentThread().getContextClassLoader().getResource(VO_JSON);
        
        logger.info("ATINA - MetadataWSDriver: saveMetadataLocallyFromResource() reading resource vo.json: " + url.toString());

        inputStream = url.openConnection().getInputStream();

        IOUtil.copy(inputStream, new FileOutputStream(new File(destDir + File.separator + VO_JSON)));
        
        logger.info("ATINA - MetadataWSDriver: saveMetadataLocallyFromResource() vo.json saved : " + destDir + File.separator + VO_JSON);
         
    }
      
}
