/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.atina.buildjdebundle;

import java.util.List;

import com.google.devtools.common.options.Option;
import com.google.devtools.common.options.OptionsBase;

/**
 *
 * @author jgodi
 */
public class Options extends OptionsBase {

   
    @Option(
            name = "jdeInstallPath",
            abbrev = 'i',
            help = "Enter JDE Path installed",
            category = "startup",
            defaultValue = ""
    )
    public String jdeInstallPath;
  
    @Option(
            name = "jdbcDriver",
            abbrev = 'j',
            help = "Enter JDBC Driver",
            category = "startup",
            defaultValue = ""
    )
    public String jdbcDriver;

    @Option(
            name = "version",
            abbrev = 'o',
            help = "Enter Version",
            category = "startup",
            defaultValue = ""
    )
    public String version;
    
    @Option(
            name = "localRepo",
            abbrev = 'r',
            help = "Enter Maven Local Repo",
            category = "startup",
            defaultValue = ""
    )
    public String localRepo;
    
    @Option(
            name = "outputName",
            abbrev = 'n',
            help = "Output Name",
            category = "startup",
            defaultValue = ""
    )
    public String outputName;
   
}
