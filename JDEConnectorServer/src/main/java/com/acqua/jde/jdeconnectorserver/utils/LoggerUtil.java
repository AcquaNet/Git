/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acqua.jde.jdeconnectorserver.utils;

import com.acqua.jde.jdeconnectorserver.JDEConnectorServer;
import com.google.protobuf.ByteString;
import com.jde.jdeserverwp.servicios.CapturarLogResponse;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.stream.Stream;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author jgodi
 */
public class LoggerUtil {

    private static final Logger logger = LoggerFactory.getLogger(JDEConnectorServer.class);
    private static String LOGS_DIR = "/tmp/jde/JDEConnectorServerLog";
    private static String LOGS_PREFIJO = "jde_atina_server_";
    private static String LOGS_DATE_FORMAT = "yyyy-MM-dd";
    private static String LOGS_BEGIN_TRANSACTION = "BEGIN TID: ";
    private static String LOGS_END_TRANSACTION = "END TID: ";
    
    private boolean beginStart = false;
    private CapturarLogResponse.Builder builder;
    private String thread = "";
    private io.grpc.stub.StreamObserver<com.jde.jdeserverwp.servicios.CapturarLogResponse> response;
    

    public LoggerUtil(io.grpc.stub.StreamObserver<com.jde.jdeserverwp.servicios.CapturarLogResponse> response, CapturarLogResponse.Builder builder) {
        this.builder = builder;
        this.response = response;
    } 

    public void readLogs(Long transactionID, int minutes) throws IOException {

        logger.info("Starting LoggerUtil...");
        
        
        String szBeginTransactionID = LOGS_BEGIN_TRANSACTION.concat(Long.toString(transactionID));
        String szEndTransactionID = LOGS_END_TRANSACTION.concat(Long.toString(transactionID));
        

        // Get Today
        Date nowDate = new Date();

        // Determinar Fecha a partir de la cual tomar los logs
        Date limite = DateUtils.addMinutes(nowDate, minutes * -1);

        // Determinar Prefijo de Archivos a Leer
        String filter = LOGS_PREFIJO.concat(new SimpleDateFormat(LOGS_DATE_FORMAT).format(new Date()));
        
        logger.info("Starting LoggerUtil Filter:" + filter);

        // Creacion del Filtro para Leer Logs
        FileFilter nameFilter = new FileFilter() {
            
            @Override
            public boolean accept(File file) {

                if (file.getName().startsWith(filter)) {
                    
                    Date lastModified = new Date(file.lastModified());

                    return lastModified.compareTo(limite) > 0;
                    
                } else {
                    
                    return false;
                    
                }
            }
        };
        
        File logDir = new File(LOGS_DIR);
        
        File[] files = logDir.listFiles(nameFilter);
        
        Arrays.sort(files, Comparator.comparingLong(File::lastModified));
                
        for (File file : files) {
            
            logger.info("Starting LoggerUtil Reading:" + file.getName());
            
            processFile(file, szBeginTransactionID, szEndTransactionID);
            
            logger.info("Starting LoggerUtil File:" + file.getName() + " Processed");
             
        }
        
        logger.info("End LoggerUtil...");

    }
    
    private void processFile(File logToProcess, String szBeginTransactionID, String szEndTransactionID) throws IOException {

        try (Stream linesStream = Files.lines(logToProcess.toPath())) {
            
            linesStream.forEach(line -> {
                 
                if(((String) line).contains(szBeginTransactionID))
                {
                    beginStart = true;
                    
                    thread = StringUtils.substringBetween((String) line, "[", "]");
                    
                    logger.info("Starting LoggerUtil Thread:" + thread + " Line: " + ((String) line).replace(szBeginTransactionID, "99999999999999999"));
                     
                }
                
                if(beginStart)
                {
                    if(((String) line).contains(thread))
                    {
                         
                        ByteString bytes = ByteString.copyFromUtf8((String) line + System.lineSeparator());

                        CapturarLogResponse logResponse = builder.setFileData(bytes).build();

                        response.onNext(logResponse);
                    
                    }
                     
                }
                
                if(((String) line).contains(szEndTransactionID))
                {
                    beginStart = false;
                    
                    logger.info("Starting LoggerUtil Thread:" + thread + " Done");
                    
                    thread = "";
                    
                } 
                
            });
        } 
        
    }

}
