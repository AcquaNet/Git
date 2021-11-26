/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mirrit.facade;

import java.io.File;
import java.time.format.DateTimeFormatter;
import javax.enterprise.context.ApplicationScoped;
import org.jboss.logging.Logger;

/**
 *
 * @author jgodi
 */
@ApplicationScoped
public class CleanerManager {
    
    private static final Logger LOG = Logger.getLogger(CleanerManager.class); 
         
    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    static private String logPath = "/tmp/console-log";
    static private String logFileName = "trace";
    
    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss:SSS");
    
    private CleanerManager() {
    }
    
    public static CleanerManager getInstance() {
        return CleanerManagerHolder.INSTANCE;
    }
    
    private static class CleanerManagerHolder {

        private static final CleanerManager INSTANCE = new CleanerManager();
    }
      
    
    public Long cleanTmpLogs(long diasAlmacenados)
    {
          
        Long qtyRecordsFilesDeleted = 0L;
        
        File folder = new File(logPath);
        
        if (folder.exists()) {
            
            File[] listFiles = folder.listFiles();
            
            long eligibleForDeletion = System.currentTimeMillis() - (diasAlmacenados * 24 * 60 * 60 * 1000);
                
            for (File listFile: listFiles) {
                
                if (listFile.getName().startsWith(logFileName) &&
                    listFile.lastModified() < eligibleForDeletion) {
                    
                    if (!listFile.delete()) {
 
                        LOG.info("Sorry Unable to Delete Files.." + listFile.getName());
 
                    } else
                    {
                        LOG.info("File Deleted: " + listFile.getName());
                        qtyRecordsFilesDeleted++;
                    }
                    
                }
                
                
            }
            
            
        }
        
         
        // Persistir Comando
        //
        return qtyRecordsFilesDeleted;
        
        
    }
       
    
}
