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
      
    
    // Mode: TestWS: Test BSFN
    @Option(
            name = "mode",
            abbrev = 'm',
            help = "Modes",
            category = "startup",
            defaultValue = "Test_WS_GetAB"
    )
    
    public String mode;
    
    @Option(
            name = "addressbookno",
            abbrev = 'a',
            help = "Address Book No",
            category = "startup",
            defaultValue = ""
    )
    
    public String addressbookno;
    
    @Option(
            name = "sessionId",
            abbrev = 'i',
            help = "Session Id",
            category = "startup",
            defaultValue = ""
    )
    
    public String sessionId;

     
    public String values() {
        return  "user=" + user + ", environment=" + environment + ", role=" + role + ", serverName=" + serverName + ", serverPort=" + serverPort + ", mode=" + mode + ", addressbookno=" + addressbookno + ", sessionId=" + sessionId;
    }
  
}
