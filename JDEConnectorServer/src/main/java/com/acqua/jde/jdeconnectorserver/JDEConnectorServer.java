/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acqua.jde.jdeconnectorserver;
 
import com.acqua.jde.jdeconnectorserver.classloader.DynamicURLClassLoader;
import com.acqua.jde.jdeconnectorserver.configuration.ServerConfiguration;
import com.acqua.jde.jdeconnectorserver.exceptions.InternalServerException;
import com.acqua.jde.jdeconnectorserver.server.JDERestServer;
import com.acqua.jde.jdeconnectorserververificador.Licenciador;
import java.io.File; 
import java.io.UnsupportedEncodingException; 
import java.net.URL;
import java.net.URLClassLoader;
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
public class JDEConnectorServer {

    private static final Logger logger = LoggerFactory.getLogger(JDERestServer.class);

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
    
    // -------------------------------------------------------------
    // PARAMETROS Versiones de Libreria
    // -------------------------------------------------------------
     
    @Option(name = "-jdeLibWrappedVersion", usage = "jde-lib-wrapped Version")
    public String jdeLibWrappedVersion;
    
    @Option(name = "-StdWebServiceVersion", usage = "StdWebService Version")
    public String stdWebServiceVersion;
    
    // -------------------------------------------------------------
    // Secret Key
    // -------------------------------------------------------------
    
    @Option(name = "-secretKey", usage = "Secret Key")
    public String secretKey;
    
    @Option(name = "-tokenExpiration", usage = "Token Expiration in Milliseconds")
    public long tokenExpiration;

    public void iniciarAplicacion(String[] args) throws InternalServerException, InterruptedException {

        // ================================================
        // Iniciando Aplicacion
        // ================================================ 
        //
        // https://www.dovydasvenckus.com/rest/2017/08/20/jersey-on-embedded-jetty/
        // Argumentos
        // 
        String currentUsersHomeDir = System.getProperty("user.dir");
          
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
                
                logger.info("------------------------------------------------------");
                logger.info("Checking License...");
                
                valido = lic.verificarLicencia("jdelicense.dat", newLicense);
                
                logger.info("License has been checked");
                 
                
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
            cfg.setSecretKey(secretKey);
            cfg.setTokenExpiration(tokenExpiration);
        }
          
        // ================================================
        // Agregando 
        //    jde-lib-wrapped-1.0.0 
        //    StdWebService-1.0.0
        // al Classpath
        // ================================================
        //
         
        
        
        try {
            
            logger.info("------------------------------------------------------");
            logger.info("Loading libraries...");
            
            File jarToAdd1 = new File("/tmp/jde/lib/jde-lib-wrapped-" + jdeLibWrappedVersion + ".jar");
            
            File jarToAdd2 = new File("/tmp/jde/lib/StdWebService-" + stdWebServiceVersion + ".jar");
            
            URL urlJdeLibWrapped = jarToAdd1.toURI().toURL();
            
            URL urlStdWebService = jarToAdd2.toURI().toURL();
            
            DynamicURLClassLoader.addURL(urlJdeLibWrapped);
            
            DynamicURLClassLoader.addURL(urlStdWebService); 
            
            logger.info("Libraries loaded: ");
            logger.info("          " + urlJdeLibWrapped.toString());
            logger.info("          " + urlStdWebService.toString());
            
        } catch (Exception ex) {
            
            logger.info("Error. Cannot load libraries:");
            logger.info("     " + ex.getMessage());
            logger.info("See log for more detail");
            logger.error(ex.getMessage(), ex);
            
        }
        
        // ================================================
        // Mostrar ClassPaht
        // ================================================
        //
        logger.info("*------------------------------------------------------*");
        logger.info("Current Class Path:");
        
        ClassLoader cl = ClassLoader.getSystemClassLoader();
        URL[] urls = ((URLClassLoader)cl).getURLs();

        for(URL url: urls){
        	logger.info((url.getFile()));
        }
        
        // ================================================
        // Mostrar Configuracion
        // ================================================
        //
        logger.info("*------------------------------------------------------*");
        logger.info("Directorio de Trabajo: " + currentUsersHomeDir); 
        logger.info("Version: " + "1.0.0"); 

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
        logger.info("          Expiracion = [" + Long.toString(cfg.getTokenExpiration()) + "]");
        
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

            JDEConnectorServer mainApp = new JDEConnectorServer();

            mainApp.iniciarAplicacion(args);

        } catch (Exception ex) {

            logger.info("Error. No se pudo iniciar la aplicacion:");
            logger.info("     " + ex.getMessage());
            logger.info("Ver log para mas detalle");
            logger.error(ex.getMessage(), ex);

        }

    }

}
