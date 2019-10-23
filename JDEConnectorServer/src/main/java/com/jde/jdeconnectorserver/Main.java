/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jde.jdeconnectorserver;
 
import com.jde.jdeconnectorserver.configuration.ServerConfiguration;
import com.jde.jdeconnectorserver.exceptions.InternalServerException;
import com.jde.jdeconnectorserver.server.JDERestServer;
import com.jde.jdeconnectorserververificador.Licenciador;
import java.io.File; 
import java.io.UnsupportedEncodingException; 
import java.security.InvalidKeyException; 
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option; 

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author jgodi
 */
public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    private static String DIR_INSTALACION = "service-files";
    private static String METADATA = "metadata";

    @Option(name = "-help", usage = "Ayuda")
    public boolean help;

    @Option(name = "-logdetail", usage = "Muestra Log con detalle")
    public boolean logdetail;
    
    // -------------------------------------------------------------
    // PARAMETROS DEL SERVICIO DE ARRANQUE
    // -------------------------------------------------------------
    
    @Option(name = "-ipServicio", usage = "IP del Servicio Expuesto")
    public String ipServer;
    
    @Option(name = "-portServicio", usage = "Puerto del Servicio")
    public int portLServer;
    
    @Option(name = "-localIP", usage = "IP local")
    public String localIp;
     
    // -------------------------------------------------------------
    // PARAMETROS LICENCIA
    // -------------------------------------------------------------
     
    @Option(name = "-clientcod", usage = "Client Code")
    public String clientcod;

    public void iniciarAplicacion(String[] args) throws InternalServerException, InterruptedException {

        // ================================================
        // Iniciando Aplicacion
        // ================================================ 
        //
        // https://www.dovydasvenckus.com/rest/2017/08/20/jersey-on-embedded-jetty/
        // Argumentos
        // 
        String currentUsersHomeDir = System.getProperty("user.dir");
        
        File directorioDeInstalacion = new File(currentUsersHomeDir + File.separator + DIR_INSTALACION);
        
        File metadataDir = new File(directorioDeInstalacion + File.separator + METADATA);

        // ================================================
        // Parseo de Comandos
        // ================================================
        //
        CmdLineParser parser = new CmdLineParser(this);

        // parse the arguments.
        try {
            parser.parseArgument(args);
        } catch (CmdLineException e) {
            mostrarAyuda();
            logger.error("Error leyendo parametros" + e.getMessage());
            System.exit(0);
        }
        
        if(help)
        {
            mostrarAyuda();
            System.exit(0);
        }
        
        ServerConfiguration cfg = new ServerConfiguration();
        
        // -----------------------------------------
        // Licencia
        // -----------------------------------------
        //
        
        if(clientcod == null)
        {
            mostrarAyuda();
            System.exit(0);
        }
        else
        {
            Licenciador lic = new Licenciador();
            
            boolean valido;
            
            try {
                
                String newLicense = lic.getLicense(clientcod);
                
                valido = lic.verificarLicencia("jdelicense.dat", newLicense);
                
                
                
            } catch (NoSuchPaddingException ex) {
                valido = false;
            } catch (InvalidKeyException ex) {
                valido = false;
            } catch (UnsupportedEncodingException ex) {
                valido = false;
            } catch (IllegalBlockSizeException ex) {
                valido = false;
            } catch (BadPaddingException ex) {
                valido = false;
            } catch (Exception ex) {
                valido = false;
            }
             
            if(!valido)
            {
                System.out.println("Invalid License"); 
                System.exit(0);
            }
        }
         
        if(ipServer == null || localIp == null || portLServer == 0 )
        {
            mostrarAyuda();
            System.exit(0);
        }
        else
        {
            cfg.setIpServicio(ipServer);
            cfg.setIpLocalServicio(localIp);
            cfg.setPortServicio(portLServer);
        }
          
        
        // ================================================
        // Mostrar Configuracion
        // ================================================
        //
        logger.info("*------------------------------------------------------*");
        logger.info("Directorio de Trabajo: " + currentUsersHomeDir);
        logger.info("Directorio Instalacion: " + directorioDeInstalacion.getAbsolutePath());
        logger.info("Directorio Metadata: " + metadataDir.getAbsolutePath()); 

        mostrarConfiguracion(cfg);
   
        // ================================================
        // Iniciando Servidor RESTFUL
        // ================================================
        
        JDERestServer service = new JDERestServer(cfg);

        try {

            service.startServer();

        } catch (Exception ex) {
            logger.info("Error. No se pudo iniciar el servidor. Error:");
            logger.info("     " + ex.getMessage());
            logger.info("Ver log para mas detalle");
            logger.error(ex.getMessage(), ex);
        }

    }

    private void mostrarConfiguracion(ServerConfiguration cfg) {

        logger.info("------------------------------------------------------");
        logger.info("Configuracion: ");
        logger.info("       SERVICIO: ");
        logger.info("          IP del Servicio = [" + cfg.getIpServicio() + "]");
        logger.info("          Puerto del Servicio = [" + Integer.toString(cfg.getPortServicio()) + "]");
        logger.info("          IP local del Servicio = [" + cfg.getIpLocalServicio() + "]");
        
    }

    private void mostrarAyuda() {

        logger.info("------------------------------------------------------");
        logger.info("Usar: ");
        logger.info(" java -jar JDEConnectorServer-1.0.0.jar -help -user JDE -pass JDE -environment JDV920 -role *ALL  -logdetail");
        logger.info("------------------------------------------------------");

    }
     
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        try {

            Main mainApp = new Main();

            mainApp.iniciarAplicacion(args);

        } catch (Exception ex) {

            logger.info("Error. No se pudo iniciar la aplicacion:");
            logger.info("     " + ex.getMessage());
            logger.info("Ver log para mas detalle");
            logger.error(ex.getMessage(), ex);

        }

    }

}
