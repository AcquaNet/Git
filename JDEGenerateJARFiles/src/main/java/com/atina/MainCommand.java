/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.atina;
   
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter; 
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList; 
import java.util.HashMap;
import javax.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import picocli.CommandLine; 
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
  
/**
 *
 * @author jgodi
 */
@CommandLine.Command
public class MainCommand implements Runnable {
    
  
    @CommandLine.Option(names = {"-s", "--server"}, description = "JDE URL of Server Manager", arity = "0..1",  required = true, interactive = true)
    String server;
    
    @CommandLine.Option(names = {"-u", "--user"}, description = "JDE User for Enterprise Server Manager", arity = "0..1",  required = true, interactive = true)
    String user;
    
    @CommandLine.Option(names = {"-p", "--password"}, description = "JDE Password for Enterprise Server Manager", arity = "0..1",   required = true, interactive = true)
    String password;
    
    @CommandLine.Option(names = {"-d", "--debug"}, description = "Debug Option", required = false , defaultValue = "N")
    String debug;
  
    
    public MainCommand() {  
      
    }
    
    @Override
    public void run() {
         
        
        OutputStreamWriter consoleWriter = new OutputStreamWriter(System.out);
        
        ArrayList<String> endMessage = new ArrayList<String>();
        
        try { 
 
            // =======================================
            // JDE Authenticate
            // =======================================
            //
            
            boolean showDetail = debug.equalsIgnoreCase("y");
            
            BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
        
            consoleWriter.write("=================================================================== ");
            
            
            // =====================================================
            // Create Working Folder
            // =====================================================
                
            File workingFolder = new File(WORKING_FOLDER);

            if(!workingFolder.exists())
            {
                FileUtils.forceMkdir(workingFolder); 
                
                consoleWriter.write(System.getProperty( "line.separator" ));
                consoleWriter.write("Folder : " + WORKING_FOLDER + " has been created");
                consoleWriter.write(System.getProperty( "line.separator" ));
                consoleWriter.write(System.getProperty( "line.separator" ));
                
            } else
            {
                FileUtils.deleteQuietly(new File(WORKING_FOLDER + File.separator + JDBJ));
                FileUtils.deleteQuietly(new File(WORKING_FOLDER + File.separator + INTEROP));
                FileUtils.deleteQuietly(new File(WORKING_FOLDER + File.separator + JDELOG));
            }
             
        
            consoleWriter.write(System.getProperty( "line.separator" ));
            consoleWriter.write(System.getProperty( "line.separator" ));
            consoleWriter.write("Authenticating : " + server + (showDetail?API_AUTHENTICATE:""));
            consoleWriter.write(System.getProperty( "line.separator" ));
            consoleWriter.flush();
              
            String auth = user.trim() + ":" + password.trim();
            
            byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(StandardCharsets.ISO_8859_1));
  
            String authHeader = "Basic " + new String(encodedAuth);
             
            HttpResponse<String> authorization = smClient.authenticate(server,authHeader,API_AUTHENTICATE);
            
            if(authorization.statusCode() !=204)
            {
                consoleWriter.write("  Error validating JDE User.");
                  
                consoleWriter.write(System.getProperty( "line.separator" ));
                
                consoleWriter.write("                  Server : " + server + (showDetail?API_AUTHENTICATE:""));
                  
                consoleWriter.write(System.getProperty( "line.separator" ));
                
                consoleWriter.write("                  Auth   : " + authHeader);
                  
                consoleWriter.write(System.getProperty( "line.separator" ));
                
                consoleWriter.write("          Rest API return: " + authorization.statusCode() );
                
                consoleWriter.write(System.getProperty( "line.separator" ));
                
                consoleWriter.write("                           " + authorization.body());
                
                consoleWriter.write(System.getProperty( "line.separator" ));
                 
                consoleWriter.flush();
            
                throw new RuntimeException("Error Validating JDE User");
                
            }
            
            Optional<String> tokenHeader = authorization.headers().firstValue("TOKEN");
             
            if(!tokenHeader.isPresent())
            {
                consoleWriter.write("Error: Invalid Token");
                
                consoleWriter.write(System.getProperty( "line.separator" ));
                
                consoleWriter.flush();
                
                throw new RuntimeException("Invalid Token");
                
            }
            
                        
            consoleWriter.write("         Cookie: " + tokenHeader.orElse("Invalid"));
            consoleWriter.write(System.getProperty( "line.separator" ));
            consoleWriter.write(" .................................................. ");
            consoleWriter.write(System.getProperty( "line.separator" ));
            consoleWriter.flush();
            
            // =======================================
            // Reade JDE Instances
            // =======================================
            //
            
            consoleWriter.write("Getting Server Groups : " + server + (showDetail?API_GROUPS:""));
            consoleWriter.flush();
            
            HttpResponse<String> serverGroups = smClient.readServerGroupInfo(server,tokenHeader.orElse("Invalid"),API_GROUPS);
            
            if(serverGroups.statusCode() !=200)
            {
                consoleWriter.write("  Error getting Server Groups.  ");
                
                consoleWriter.write(System.getProperty( "line.separator" ));
                
                consoleWriter.write("          Rest API return: " + serverGroups.statusCode() );
                
                consoleWriter.write(System.getProperty( "line.separator" ));
                
                consoleWriter.write("                           " + serverGroups.body());
                
                consoleWriter.write(System.getProperty( "line.separator" ));
                
                consoleWriter.flush();
                
                throw new RuntimeException("Error Getting Server Group Information");
                
            }
            
            
            // =======================================
            // Parse Response JDE Server Groups
            // =======================================
            //
            
            ObjectMapper mapper = new ObjectMapper();
            
            Map<String, Object> map = mapper.readValue(serverGroups.body(), Map.class);
            
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
            
            consoleWriter.write(System.getProperty( "line.separator" ));
            for(String part: jdeInstances) {
                consoleWriter.write(part + ", ");
            }
            consoleWriter.write(System.getProperty( "line.separator" ));
            consoleWriter.write(" .................................................. ");        
            consoleWriter.write(System.getProperty( "line.separator" ));
            consoleWriter.flush();
             
            
            // =======================================
            // Show JDE Instance
            // =======================================
            //
            
            boolean exit = false;
            String option = "";
            
            while(!exit)
            {
                consoleWriter.write(System.getProperty( "line.separator" ));
                consoleWriter.write("Select HTML Instance:");
                consoleWriter.write(System.getProperty( "line.separator" ));
                
                consoleWriter.flush();
                
                for(int i = 0; i < jdeInstances.size(); i++) {
                     consoleWriter.write(Integer.toString(i) + " - " + jdeInstances.get(i));
                     consoleWriter.write(System.getProperty( "line.separator" ));
                     consoleWriter.flush();
                }
                
                consoleWriter.write( "Q - Quit");
                consoleWriter.write(System.getProperty( "line.separator" ));
                consoleWriter.flush();
                
                consoleWriter.write(System.getProperty( "line.separator" ));
                
                // =======================================
                // Select JDE Instance
                // =======================================
             
                String name = consoleReader.readLine();
                
                consoleWriter.write(System.getProperty( "line.separator" ));
                consoleWriter.write( "Option Selected: " + name);
                consoleWriter.write(System.getProperty( "line.separator" ));
                consoleWriter.flush();
                
                if(name.trim().equalsIgnoreCase("q"))
                {
                    exit = true;
                    consoleWriter.write(System.getProperty( "line.separator" ));
                    consoleWriter.write( "    EXIT ");
                    consoleWriter.flush();
                    
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
                
                consoleWriter.write(System.getProperty( "line.separator" ));
                consoleWriter.write("JDE Instance selected: " + option); 
                consoleWriter.write(System.getProperty( "line.separator" ));
                consoleWriter.write(" .................................................. ");  
                consoleWriter.write(System.getProperty( "line.separator" ));
                consoleWriter.flush(); 
                
                consoleWriter.write("Getting Instance Values: " + server + (showDetail?API_INSTANCE_INFO:"") + " for Instance Name=" + option);
                consoleWriter.write(System.getProperty( "line.separator" )); 
                consoleWriter.flush();
            
                HttpResponse<String> instanceInfo = smClient.getJDEInstanceValues(server,tokenHeader.orElse("Invalid"),API_INSTANCE_INFO,option);
                  
                if(instanceInfo.statusCode() !=200)
                {
                    consoleWriter.write("  Error getting Server Groups.  ");

                    consoleWriter.write(System.getProperty( "line.separator" ));

                    consoleWriter.write("          Rest API return: " + instanceInfo.statusCode() );

                    consoleWriter.write(System.getProperty( "line.separator" ));

                    consoleWriter.write("                           " + instanceInfo.body());

                    consoleWriter.write(System.getProperty( "line.separator" ));

                    consoleWriter.flush();

                    throw new RuntimeException("Error Getting Server Group Information");

                }
                 
            
                Map<String, Object> mapInstanceInfo = mapper.readValue(instanceInfo.body(), Map.class);
                
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
                        consoleWriter.write(key + " =>" + valuesInstanceInfo.get(key));
                        consoleWriter.write(System.getProperty( "line.separator" )); 
                        consoleWriter.flush();
                    }
                }
                
                   
                // =====================================================
                // Process jdbj.ini
                // =====================================================
                
                String status = processIniFile(JDBJ,valuesInstanceInfo,consoleWriter);
                
                endMessage.add(status);
                
                if(showDetail)
                {
                    consoleWriter.write(System.getProperty( "line.separator" )); 
                    consoleWriter.write(status); 
                    consoleWriter.write(System.getProperty( "line.separator" )); 
                    consoleWriter.flush();
                }
                
                status = processIniFile(INTEROP,valuesInstanceInfo,consoleWriter);
                
                endMessage.add(status);
                
                if(showDetail)
                {
                    consoleWriter.write(System.getProperty( "line.separator" )); 
                    consoleWriter.write(status); 
                    consoleWriter.write(System.getProperty( "line.separator" )); 
                    consoleWriter.flush();
                }
                
                status = processIniFile(JDELOG,valuesInstanceInfo,consoleWriter);
                
                endMessage.add(status);
                
                if(showDetail)
                {
                    consoleWriter.write(System.getProperty( "line.separator" )); 
                    consoleWriter.write(status); 
                    consoleWriter.write(System.getProperty( "line.separator" )); 
                    consoleWriter.flush();
                }
                 
             
            }
            
            // =======================================
            // Close Console
            // =======================================
              
            consoleReader.close();
            
        } catch (RuntimeException ex) {
             //
        }catch (IOException ex) {
            System.out.println(ex.getMessage());
        } catch (InterruptedException ex) {
            System.out.println(ex.getMessage());
        }  
        
        // =======================================
        // Close Console
        // =======================================
        
        try {
            consoleWriter.write(System.getProperty( "line.separator" )); 
            consoleWriter.write("------------------------------------------------------------------------");
            consoleWriter.write(System.getProperty( "line.separator" )); 
            consoleWriter.write("GENERATION SUCESSS");
            consoleWriter.write(System.getProperty( "line.separator" )); 
            consoleWriter.write("------------------------------------------------------------------------");
            consoleWriter.write(System.getProperty( "line.separator" )); 
            for(String line:endMessage)
            {
                consoleWriter.write(line);
                consoleWriter.write(System.getProperty( "line.separator" )); 
            }
            consoleWriter.write("------------------------------------------------------------------------");
            consoleWriter.write(System.getProperty( "line.separator" ));  
            consoleWriter.flush();
            consoleWriter.close();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
         
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
     
    private static String processIniFile(String fileName, HashMap<String, String> valuesInstanceInfo, OutputStreamWriter consoleWriter) {

        String returnValue = "";
        
        try {
            
            consoleWriter.write(System.getProperty("line.separator"));

            consoleWriter.write(" -------------------------------------------------------------- ");
            consoleWriter.write(System.getProperty("line.separator"));
            consoleWriter.write(" Processing File: " + INI_FOLDER + fileName);
            consoleWriter.write(System.getProperty("line.separator"));

            consoleWriter.flush();

            // --------------------------------------------------
            // The class loader that loaded the class
            // --------------------------------------------------
            
            ClassLoader classLoader = MainCommand.class.getClassLoader();

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
                        consoleWriter.write("  Missing key: " + key);
                        consoleWriter.write(System.getProperty("line.separator"));
                        consoleWriter.flush();
                    }

                }
                line = line + System.getProperty("line.separator");
                outStream.write(line.getBytes(StandardCharsets.UTF_8));
            }

            IOUtils.closeQuietly(inputStream);

            IOUtils.closeQuietly(outStream);
 
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
