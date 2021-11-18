/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.atina.builder;
  
import com.google.devtools.common.options.Option;
import com.google.devtools.common.options.OptionsBase;

/**
 *
 * @author jgodi
 */
public class Options extends OptionsBase {

    @Option(
            name = "server",
            abbrev = 's',
            help = "JDE URL of Server Manager",
            category = "startup",
            defaultValue = ""
    )
    public String server;
    
    @Option(
            name = "user",
            abbrev = 'u',
            help = "JDE User for Enterprise Server Manager",
            category = "startup",
            defaultValue = ""
    )
    public String user;
  
    @Option(
            name = "password",
            abbrev = 'p',
            help = "JDE Password for Enterprise Server Manager",
            category = "startup",
            defaultValue = ""
    )
    public String password;

    @Option(
            name = "debug",
            abbrev = 'd',
            help = "Debug Option",
            category = "startup",
            defaultValue = "N"
    )
    public String debug;
     
   
}
