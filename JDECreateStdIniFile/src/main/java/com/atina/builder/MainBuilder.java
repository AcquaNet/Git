/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.atina.builder;
 
 
import com.atina.sm.SMClient;
import com.google.devtools.common.options.OptionsParser;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map; 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils; 
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import org.eclipse.jetty.client.api.ContentResponse;

/**
 *
 * @author jgodi
 */
public class MainBuilder {

    private static final Logger logger = LoggerFactory.getLogger(MainBuilder.class);
    
    // APIS
    
    static final String API_AUTHENTICATE = "/mgmtrestservice/authenticate";
    static final String API_GROUPS = "/mgmtrestservice/servergroupinfo";
    static final String API_INSTANCE_INFO = "/mgmtrestservice/configsummary";
    
    // FOLDERS
    
    static final String INI_FOLDER = "";
    private static final String WORKING_FOLDER = "/tmp/build_jde_libs";
    
    // FILES
     
    private static final String JDBJ = "jdbj.ini";
    private static final String INTEROP = "jdeinterop.ini";
    private static final String JDELOG = "jdelog.properties";
      
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
 
        showBanner();
                
        // -----------------------------------------------
        // create the command line parser
        // -----------------------------------------------
        OptionsParser parser = OptionsParser.newOptionsParser(Options.class);

        parser.parseAndExitUponError(args);

        // -----------------------------------------------
        // create the Options
        // -----------------------------------------------
        Options options = parser.getOptions(Options.class);

        if (options.server.isEmpty()
                || options.user.isEmpty()
                || options.password.isEmpty()) {

            printUsage(parser);

            return;
        } 
        
        ArrayList<String> endMessage = new ArrayList<String>();
        
        SMClient smClient = null;

        try {
            
            // -----------------------------------------------
            // Setting Logging 
            // -----------------------------------------------
            //
            setupLogging(WORKING_FOLDER);
              
            boolean showDetail = options.debug.equalsIgnoreCase("y");
            
            BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
          
            smClient = new SMClient();
            
            // =====================================================
            // Create Working Folder
            // =====================================================
                
            File workingFolder = new File(WORKING_FOLDER);

            if(!workingFolder.exists())
            {
                FileUtils.forceMkdir(workingFolder); 
                
                logger.info("");
                logger.info("Folder : " + WORKING_FOLDER + " has been created");
                logger.info("");
                logger.info("");
                
            } else
            {
                FileUtils.deleteQuietly(new File(WORKING_FOLDER + File.separator + JDBJ));
                FileUtils.deleteQuietly(new File(WORKING_FOLDER + File.separator + INTEROP));
                FileUtils.deleteQuietly(new File(WORKING_FOLDER + File.separator + JDELOG));
            }
             
            logger.info("Authenticating : " + options.server + (showDetail?API_AUTHENTICATE:""));  
              
            String auth = options.user.trim() + ":" + options.password.trim();
            
            byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(StandardCharsets.ISO_8859_1));
  
            String authHeader = "Basic " + new String(encodedAuth);
             
            ContentResponse authorization = smClient.authenticate(options.server+API_AUTHENTICATE,authHeader);
            
            if(authorization.getStatus() !=204)
            {
                logger.info("  Error validating JDE User."); 
                logger.info("                  Server : " + options.server + (showDetail?API_AUTHENTICATE:"")); 
                logger.info("                  Auth   : " + authHeader); 
                logger.info("          Rest API return: " + authorization.getStatus() ); 
                logger.info("                           " + authorization.getContentAsString());  
                
                throw new RuntimeException("Error Validating JDE User");
                
            }
            
            String tokenHeader = "";
            
            if(authorization.getHeaders().containsKey("TOKEN"))
            {
                tokenHeader = authorization.getHeaders().get("TOKEN");
            }  
               
            if(tokenHeader.isEmpty())
            {
                logger.info("Error: Invalid Token");   
                throw new RuntimeException("Invalid Token");
                
            }
            
                        
            logger.info("         Cookie: " + tokenHeader); 
            
            // =======================================
            // Reade JDE Instances
            // =======================================
            //
            
            logger.info("Getting Server Groups : " + options.server + (showDetail?API_GROUPS:"")); 
            
            ContentResponse serverGroups = smClient.readServerGroupInfo(options.server + API_GROUPS ,tokenHeader);
            
            if(serverGroups.getStatus() !=200)
            {
                logger.info("  Error getting Server Groups.  ");  
                logger.info("          Rest API return: " + serverGroups.getStatus() );  
                logger.info("                           " + serverGroups.getContentAsString());  
                throw new RuntimeException("Error Getting Server Group Information");
                
            } 
            
            // =======================================
            // Parse Response JDE Server Groups
            // =======================================
            //
            
            ObjectMapper mapper = new ObjectMapper();
            
            Map<String, Object> map = mapper.readValue(serverGroups.getContentAsString(), Map.class);
            
            ArrayList<String> jdeInstances = new ArrayList<String>();
            
            ArrayList<LinkedHashMap<String,Object>> result = (ArrayList<LinkedHashMap<String,Object>>) map.get("result");
            
            for(LinkedHashMap<String,Object> value:result)
            {
                
                if(((String) value.get("serverGroupName")).equals("default"))
                {
                    jdeInstances =  (ArrayList<String>) value.get("serverGroupInstances");
                    
                    break;
                    
                }
                
            }
            
            if(showDetail)
            { 
                for(String part: jdeInstances) {
                    logger.info(part + ", ");
                }
            } 
              
            // =======================================
            // Show JDE Instance
            // =======================================
            //
            
            boolean exit = false;
            String option = "";
            
            while(!exit)
            { 
                logger.info("Select HTML Instance:"); 
                 
                for(int i = 0; i < jdeInstances.size(); i++) {
                     logger.info(Integer.toString(i) + " - " + jdeInstances.get(i)); 
                }
                
                logger.info( "Q - Quit"); 
                  
                // =======================================
                // Select JDE Instance
                // =======================================
             
                String name = consoleReader.readLine();
                 
                logger.info( "Option Selected: " + name);  
                
                if(name.trim().equalsIgnoreCase("q"))
                {
                    exit = true; 
                    logger.info( "    EXIT "); 
                    
                } else
                {
                    if(isNumeric(name.trim()))
                    {
                        Integer valueOption = Integer.parseInt(name.trim());
                        
                        if(valueOption.intValue() >= 0 && valueOption.intValue() <= jdeInstances.size() -1)
                        {
                            option = jdeInstances.get(valueOption);
                            
                            exit = true;
                             
                        }
                    }
                }
                
              
            }
            
            // =======================================
            // Getting JDE Instance
            // =======================================
            //
            
            if(!option.isEmpty())
            {
                 
                logger.info("JDE Instance selected: " + option);  
                
                logger.info("Getting Instance Values: " + options.server + (showDetail?API_INSTANCE_INFO:"") + " for Instance Name '" + option + "'");
       
                ContentResponse instanceInfo = smClient.getJDEInstanceValues(options.server+API_INSTANCE_INFO,tokenHeader,option);
                  
                if(instanceInfo.getStatus() !=200)
                {
                    logger.info("  Error getting Server Groups.  "); 
                    logger.info("          Rest API return: " + instanceInfo.getStatus() ); 
                    logger.info("                           " + instanceInfo.getContentAsString()); 

                    throw new RuntimeException("Error Getting Server Group Information");

                }
                 
            
                Map<String, Object> mapInstanceInfo = mapper.readValue(instanceInfo.getContentAsString(), Map.class);
                
                HashMap<String, String> valuesInstanceInfo = new HashMap<String,String>();
  
                ArrayList<LinkedHashMap<String,Object>> resultInstanceInfo = (ArrayList<LinkedHashMap<String,Object>>) mapInstanceInfo.get("configurationSummary");

                for(LinkedHashMap<String,Object> value:resultInstanceInfo)
                {
                   valuesInstanceInfo.put((String) value.get("iniSection") + "|" + (String) value.get("name"), (String) value.get("value"));
                }
                
                if(showDetail)
                {
                    for(String key:valuesInstanceInfo.keySet())
                    {
                        logger.info(key + " =>" + valuesInstanceInfo.get(key)); 
                    }
                }
                
                   
                // =====================================================
                // Process jdbj.ini
                // =====================================================
                
                String status = processIniFile(JDBJ,valuesInstanceInfo);
                
                endMessage.add(status);
                
                if(showDetail)
                { 
                    logger.info(status);  
                }
                
                status = processIniFile(INTEROP,valuesInstanceInfo);
                
                endMessage.add(status);
                
                if(showDetail)
                {
                    logger.info(status);  
                }
                
                status = processIniFile(JDELOG,valuesInstanceInfo);
                
                endMessage.add(status);
                
                if(showDetail)
                {
                    logger.info(status);  
                }
                 
             
            }
            
            // =======================================
            // Close Console
            // =======================================
              
            consoleReader.close();
            
            smClient.stop();
               
        } catch (Exception ex) {
            
            logger.error(ex.getMessage(), ex);
            
            if(smClient!=null)
            {
                smClient.stop();
            }
            
        }
        
        logger.info("------------------------------------------------------------------------");
        logger.info("GENERATION SUCESSS");
        logger.info("------------------------------------------------------------------------");
        for (String line : endMessage) {
            logger.info(line);
        }
        logger.info("------------------------------------------------------------------------");

    }

    private static void printUsage(OptionsParser parser) {

        System.out.println("Usage: java -jar jd-create-jars-files.jar OPTIONS");

        System.out.println(parser.describeOptions(Collections.<String, String>emptyMap(), OptionsParser.HelpVerbosity.LONG));

    }

    private static void setupLogging(String destDir) {

        MDC.put("fileName", destDir + File.separator + "log.txt");
    }

      public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            int d = Integer.parseInt(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
      
    private static void showBanner() throws IOException {

        // --------------------------------------------------
        // The class loader that loaded the class
        // --------------------------------------------------
        ClassLoader classLoader = MainBuilder.class.getClassLoader();

        InputStream inputStream = classLoader.getResourceAsStream("banner.txt");

        // the stream holding the file content
        if (inputStream == null) {
            throw new IllegalArgumentException("Error: File not found in resource folder: " + INI_FOLDER + "banner.txt");
        }

        // --------------------------------------------------
        // Process Each Line
        // --------------------------------------------------
        InputStreamReader streamReader
                = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        BufferedReader reader = new BufferedReader(streamReader);

        String line;

        while ((line = reader.readLine()) != null) {

            System.out.println(line);

        }

        inputStream.close(); 

    }
     
    private static String processIniFile(String fileName, HashMap<String, String> valuesInstanceInfo) {

        String returnValue = "";
        
        try {
             
            logger.info(" Processing File: " + INI_FOLDER + fileName); 

            // --------------------------------------------------
            // The class loader that loaded the class
            // --------------------------------------------------
            
            ClassLoader classLoader = MainBuilder.class.getClassLoader();

            InputStream inputStream = classLoader.getResourceAsStream(INI_FOLDER + fileName);

            // the stream holding the file content
            if (inputStream == null) {
                throw new IllegalArgumentException("Error: File not found in resource folder: " + INI_FOLDER + fileName);
            }

            // --------------------------------------------------
            // Create Target File
            // --------------------------------------------------
            File targetFile = new File(WORKING_FOLDER + File.separator + fileName);
            OutputStream outStream = new FileOutputStream(targetFile);

            // --------------------------------------------------
            // Process Each Line
            // --------------------------------------------------
            InputStreamReader streamReader
                    = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(streamReader);

            String line;

            while ((line = reader.readLine()) != null) {

                if (line.contains("{{") && line.contains("}}")) {
                    String key = getStringBetweenTwoChars(line, "{{", "}}");
                    if (valuesInstanceInfo.containsKey(key)) {
                        line = line.replace("{{" + key + "}}", valuesInstanceInfo.get(key));
                    } else {
                        logger.info("  Missing key: " + key);  
                    }

                }
                line = line + System.getProperty("line.separator");
                outStream.write(line.getBytes(StandardCharsets.UTF_8));
            }
            
            inputStream.close();
            
            outStream.close(); 
 
            returnValue = " File: " + targetFile.toString() + " generated";

        } catch (Exception ex) {
            
            returnValue = "Error generating " + fileName + ": " + ex.getMessage();
        }
        
        return returnValue;
        
    }

    public static String getStringBetweenTwoChars(String input, String startChar, String endChar) {

        int start = input.indexOf(startChar);
        if (start != -1) {
            int end = input.indexOf(endChar, start + startChar.length());
            if (end != -1) {
                return input.substring(start + startChar.length(), end);
            }
        }
        return input; // return null; || return "" ;
    }

}
