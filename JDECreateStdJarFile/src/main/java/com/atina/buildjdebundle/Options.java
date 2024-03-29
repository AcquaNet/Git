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
            name = "accion",
            abbrev = 'a',
            help = "Accion: 1: Generate JDE Bundle 2: Build WS 3: Both",
            category = "startup",
            defaultValue = "3"
    )
    public String accion;
    
    @Option(
            name = "jdeInstallPath",
            abbrev = 'i',
            help = "Enter JDE Path installed",
            category = "startup",
            defaultValue = "/tmp/build_jde_libs/"
    )
    public String jdeInstallPath;
  
    @Option(
            name = "jdbcDriver",
            abbrev = 'j',
            help = "Enter JDBC Driver Folder",
            category = "startup",
            defaultValue = "/tmp/build_jde_libs/JDBC_Vendor_Drivers"
    )
    public String jdbcDriver;

    @Option(
            name = "version",
            abbrev = 'o',
            help = "Enter Version",
            category = "startup",
            defaultValue = "1.0.0"
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
            name = "settings",
            abbrev = 's',
            help = "settings.xml to use Ex. /apache-maven-3.8.1/conf/settings.xml",
            category = "startup",
            defaultValue = "/tmp/build_jde_libs/settings.xml"
    )
    public String settings;
    
    @Option(
            name = "clean",
            abbrev = 'c',
            help = "clean",
            category = "startup",
            defaultValue = "Y"
    )
    public String clean;
   
}
