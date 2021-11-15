/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.atina;
 
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import picocli.CommandLine;
 
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
      
    private final GenerateIniFiles generateService;
    
    public MainCommand(GenerateIniFiles greetingService) {  
        this.generateService = greetingService;
    }
    
    @Override
    public void run() {
        
        Configuration config = new Configuration(user,password);
        
        OutputStreamWriter consoleWriter = new OutputStreamWriter(System.out);
        
        BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
        
        try {
        
            // =======================================
            // JDE Authenticate
            // =======================================
            //
            
            
            
            // =======================================
            // Reade JDE Instances
            // =======================================
            //
            
            
            // =======================================
            // Show JDE Instance
            // =======================================
            //
            
            consoleWriter.write("Select HTML Instance:");
            consoleWriter.flush();
            
            
            
            // =======================================
            // Select JDE Instance
            // =======================================
             
            String name = consoleReader.readLine();
            
            
            // =======================================
            // Validate JDE Instance
            // =======================================
            //
            
            consoleWriter.write("            Instance:" + name);
            
            // =======================================
            // Close Console
            // =======================================
            
            consoleWriter.close();
            
            consoleReader.close();
            
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        
        
        
        System.out.println(generateService.generateFiles(config));
    }
     
    
}
