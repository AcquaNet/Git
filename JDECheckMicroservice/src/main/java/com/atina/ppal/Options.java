/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.atina.ppal;
  
import com.google.devtools.common.options.Option;
import com.google.devtools.common.options.OptionsBase;

/**
 *
 * @author jgodi
 */
public class Options extends OptionsBase {

    @Option(
            name = "user",
            abbrev = 'u',
            help = "JDE User",
            category = "startup",
            defaultValue = ""
    )
    
    public String user;
    
    @Option(
            name = "password",
            abbrev = 'w',
            help = "JDE Password",
            category = "startup",
            defaultValue = ""
    )
    public String password;
    
    @Option(
            name = "environment",
            abbrev = 'e',
            help = "JDE Environment",
            category = "startup",
            defaultValue = ""
    )
    public String environment;
    
    @Option(
            name = "role",
            abbrev = 'r',
            help = "JDE Role",
            category = "startup",
            defaultValue = ""
    )
    public String role;
    
    @Option(
            name = "serverName",
            abbrev = 's',
            help = "JD Micreserver Name or IP",
            category = "startup",
            defaultValue = ""
    )
    public String serverName;
    
    @Option(
            name = "serverPort",
            abbrev = 'p',
            help = "JD Micreserver Port",
            category = "startup",
            defaultValue = ""
    )
    public String serverPort;
      
    @Option(
            name = "mode",
            abbrev = 'd',
            help = "Debug Option",
            category = "startup",
            defaultValue = "B"
    )
    
    public String mode;
    
    @Option(
            name = "entityId",
            abbrev = 'a',
            help = "Address Book Id",
            category = "startup",
            defaultValue = "1"
    )
    
    public String entityId;
      
}
