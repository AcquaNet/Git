/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.atina;
 
import picocli.CommandLine;
import picocli.CommandLine.Option;

/**
 *
 * @author jgodi
 */
@CommandLine.Command
public class MainCommand implements Runnable {

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
        System.out.println(generateService.generateFiles(config));
    }
     
    
}
