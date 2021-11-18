/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.atina.buildjdebundle;
 
 
import com.google.devtools.common.options.OptionsParser;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

/**
 *
 * @author jgodi
 */
public class MainBuilder {

    private static final Logger logger = LoggerFactory.getLogger(MainBuilder.class);

    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
 
        // -----------------------------------------------
        // create the command line parser
        // -----------------------------------------------
        OptionsParser parser = OptionsParser.newOptionsParser(Options.class);

        parser.parseAndExitUponError(args);

        // -----------------------------------------------
        // create the Options
        // -----------------------------------------------
        Options options = parser.getOptions(Options.class);

        if (      options.jdbcDriver.isEmpty()
                || options.version.isEmpty()
                || options.localRepo.isEmpty()
                || options.jdeInstallPath.isEmpty()
                || options.settings.isEmpty()
                || options.accion.isEmpty()) {

            printUsage(parser);

            return;
        }

        try {
            
             
            
            logger.info("=======================================================");

        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }

    }

    private static void printUsage(OptionsParser parser) {

        System.out.println("Usage: java -jar server.jar OPTIONS");

        System.out.println(parser.describeOptions(Collections.<String, String>emptyMap(), OptionsParser.HelpVerbosity.LONG));

    }

    private static void setupLogging(String destDir) {

        MDC.put("fileName", destDir + File.separator + "log.txt");
    }

     

}
