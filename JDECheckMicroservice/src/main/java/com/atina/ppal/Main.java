/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.atina.ppal;
  
import com.google.devtools.common.options.OptionsParser;
import com.jde.jdeserverwp.servicios.EjecutarOperacionRequest;
import com.jde.jdeserverwp.servicios.EjecutarOperacionResponse;
import com.jde.jdeserverwp.servicios.EjecutarOperacionValores;
import com.jde.jdeserverwp.servicios.GetMetadataRequest;
import com.jde.jdeserverwp.servicios.GetMetadataResponse;
import com.jde.jdeserverwp.servicios.IsConnectedRequest;
import com.jde.jdeserverwp.servicios.IsConnectedResponse;
import com.jde.jdeserverwp.servicios.JDEServiceGrpc;
import com.jde.jdeserverwp.servicios.LogoutRequest;
import com.jde.jdeserverwp.servicios.SessionRequest;
import com.jde.jdeserverwp.servicios.SessionResponse;
import com.jde.jdeserverwp.servicios.TipoDelParametroInput;
import com.jde.jdeserverwp.servicios.TipoDelParametroOutput;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader; 
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections; 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC; 
import java.io.InputStream;  
import java.util.Arrays;
import java.util.List; 
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author jgodi
 */
public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    
    // APIS
     
    // FOLDERS
     
    private static final String INI_FOLDER = "";
    private static final String WORKING_FOLDER = "/tmp/build_jde_libs";
     
    
    public enum ModesOptions {
        TestGetAddressBookWS("TestGetAddressBookWS"),
        TestIsConnected("TestIsConnected"),
        TestGetMetadataWSAddressBook("TestGetMetadataWSAddressBook");

        public final String modes;

        private ModesOptions(String modes) {
            this.modes = modes;
        }

        public static ModesOptions valueOfLabel(String label) {
            for (ModesOptions e : values()) {
                if (e.modes.equals(label)) {
                    return e;
                }
            }
            return null;
        }
        
        public static List<ModesOptions> publicValues() 
        {   
            return Arrays.asList(ModesOptions.values());
        
        }
    }
    
    public enum ModesHiddenOptions {
        GetLog("GetLog");

        public final String modesHidden;

        private ModesHiddenOptions(String modes) {
            this.modesHidden = modes;
        }

        public static ModesHiddenOptions valueOfLabel(String label) {
            for (ModesHiddenOptions e : values()) {
                if (e.modesHidden.equals(label)) {
                    return e;
                }
            }
            return null;
        }
        
        public static List<ModesOptions> hiddenValues() 
        {   
            return Arrays.asList(ModesOptions.values());
        
        }
    }
    
 
    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
 
        showBanner();
        
        // -----------------------------------------------
        // Setting Logging 
        // -----------------------------------------------
        //
        setupLogging(WORKING_FOLDER);
        
        System.out.println("------------------------------------------------------------------------");
        System.out.println("Checking Microservice..."); 
        
                
        // -----------------------------------------------
        // create the command line parser
        // -----------------------------------------------
        OptionsParser parser = OptionsParser.newOptionsParser(Options.class);

        parser.parseAndExitUponError(args);

        // -----------------------------------------------
        // create the Options
        // -----------------------------------------------
        Options options = parser.getOptions(Options.class);
        
        System.out.println(options.values()); 
          
        if (   options.serverName.isEmpty()
            || options.serverPort.isEmpty() 
            || options.user.isEmpty()
            || options.password.isEmpty()
            || options.environment.isEmpty()) {

            printUsage(parser);

            return;
        } 
        
        ModesOptions mode = ModesOptions.valueOfLabel(options.mode);
        ModesHiddenOptions modeHidden = ModesHiddenOptions.valueOfLabel(options.mode);
        
        if (mode==null && modeHidden == null)
        {
            printUsage(parser);        
            System.out.println("Error: "); 
            System.out.println("   ");
            System.out.println("   Invalid Mode [" + options.mode + "] Valid values: " + ModesOptions.publicValues());  
            return;
        }
        
        if ( !options.mode.isEmpty() 
             && mode == ModesOptions.TestGetAddressBookWS
             && options.addressbookno.isEmpty())
        {
            printUsage(parser);    
            System.out.println("Error: "); 
            System.out.println("   ");
            System.out.println("   Mode ["+ options.mode + "] requires addressbookno option"); 
            return;
        }
            
        
        ArrayList<String> endMessage = new ArrayList<>();
        
        ManagedChannel channel = null;

        try {
             
            // -----------------------------------------------
            // Create Configuration
            // -----------------------------------------------
            //
            
            ConfiguracionServer configuracion = new ConfiguracionServer();

            configuracion.setUser(options.user);
            configuracion.setPassword(options.password);
            configuracion.setEnvironment(options.environment);
            configuracion.setRole(options.role);
            configuracion.setSession(0);

            configuracion.setServidorServicio(options.serverName); // Servidor Hub
            configuracion.setPuertoServicio(Integer.parseInt(options.serverPort));
              
            configuracion.setWsConnection(Boolean.TRUE);
            
            // ===========================  
            // Crear Canal de Comunicacion  
            // ===========================  
            //
            channel = ManagedChannelBuilder.forAddress(configuracion.getServidorServicio(), configuracion.getPuertoServicio())
                    .usePlaintext()
                    .build();
            
            // =========================== 
            // Creacion del Stub           
            // ===========================  
            // 
            JDEServiceGrpc.JDEServiceBlockingStub stub = JDEServiceGrpc.newBlockingStub(channel);
            
            int sessionID = 0;
             
            // ===========================  
            // Login                       
            // ===========================  
            //
            
            if(mode == ModesOptions.TestGetAddressBookWS || mode == ModesOptions.TestGetMetadataWSAddressBook)  
            {
                try {

                    SessionResponse tokenResponse = stub.login(
                            SessionRequest.newBuilder()
                                    .setUser(configuracion.getUser())
                                    .setPassword(configuracion.getPassword())
                                    .setEnvironment(configuracion.getEnvironment())
                                    .setRole(configuracion.getRole())
                                    .setWsconnection(configuracion.getWsConnection())
                                    .build());

                    sessionID = (int) tokenResponse.getSessionId();

                    System.out.println("User " + configuracion.getUserDetail() +  " connected with Session ID [" + tokenResponse.getSessionId() + "]");

                    endMessage.add("User " + configuracion.getUserDetail() + " connected with Session ID " + tokenResponse.getSessionId());

                } catch (Exception ex) {

                    logger.error("Error with Login:  " + ex.getMessage(),ex) ;

                    throw new RuntimeException("Error Logeando", null);

                }
                
                // ===========================  
                // Get Metadata                       
                // ===========================  
                //
            
                String operationKey = "oracle.e1.bssv.JP010000.AddressBookManager.getAddressBook";

                if(mode == ModesOptions.TestGetMetadataWSAddressBook)
                {
                    try {

                        GetMetadataResponse operaciones = stub.getMetadaParaOperacion(
                                GetMetadataRequest.newBuilder()
                                        .setConnectorName("WS")
                                        .setUser(configuracion.getUser())
                                        .setPassword(configuracion.getPassword())
                                        .setEnvironment(configuracion.getEnvironment())
                                        .setRole(configuracion.getRole())
                                        .setSessionId(sessionID)
                                        .setWsconnection(configuracion.getWsConnection())
                                        .setOperacionKey(operationKey)
                                        .build());

                        logger.info("Input  ");

                        for (TipoDelParametroInput parameter : operaciones.getListaDeParametrosInputList()) {

                            int level = 0;

                            printParameter(parameter, level);

                        }

                        logger.info("Output  ");

                        for (TipoDelParametroOutput parameter : operaciones.getListaDeParametrosOutputList()) {

                            int level = 0;

                            printParameterOutput(parameter, level);

                        }

                    } catch (Exception ex) {

                        logger.error("Error ejecutando metodo ");

                        throw new RuntimeException("Error Logeando", ex);

                    }

                }
                
                // ===========================  
                // Invoke WS              
                // ===========================  
                // 

                if(mode == ModesOptions.TestGetAddressBookWS)
                {

                    try {

                        EjecutarOperacionValores.Builder itemId = EjecutarOperacionValores.newBuilder();
                        itemId.setNombreDelParametro("entityId");
                        itemId.setValueAsInteger(Integer.parseInt(options.addressbookno));

                        EjecutarOperacionValores.Builder item = EjecutarOperacionValores.newBuilder();
                        item.setNombreDelParametro("entity");
                        item.addListaDeValores(itemId);

                        EjecutarOperacionResponse ejecutarOperacionesResponse = stub.ejecutarOperacion(
                                EjecutarOperacionRequest.newBuilder()
                                        .setConnectorName("WS")
                                        .setOperacionKey(operationKey)
                                        .setUser(configuracion.getUser())
                                        .setPassword(configuracion.getPassword())
                                        .setEnvironment(configuracion.getEnvironment())
                                        .setRole(configuracion.getRole())
                                        .setWsconnection(configuracion.getWsConnection())
                                        .setSessionId(sessionID)
                                        .addListaDeValores(item.build())
                                        .build());


                        List<EjecutarOperacionResponse> values = ejecutarOperacionesResponse.getListaDeValoresList();

                        if (values != null && !values.isEmpty()) {

                            for (EjecutarOperacionResponse response : values) {
                                if (response.getNombreDelParametro().equals("addressBookResult")) {
                                    for (EjecutarOperacionResponse response1 : response.getListaDeValoresList()) {
                                        for (EjecutarOperacionResponse response2 : response1.getListaDeValoresList()) {
                                            if (response2.getNombreDelParametro().equals("description1")) {
                                                endMessage.add("Address Book Name: " + response2.getValueAsString());
                                            }
                                        } 
                                    } 
                                } 
                            } 
                        }

                        logger.info("WS JP010000.AddressBookManager.getAddressBook has been called correctly");

                    }
                    catch (Exception ex) {

                        logger.error("Error runnng isConnected" + ex.getMessage()) ;

                        throw new RuntimeException("Error runnng isConnected", null);

                    }

                }
                
                // ===========================  
                // Logout                   
                // ===========================  
                //
            
                try {

                    SessionResponse tokenResponse = stub.logout(
                            LogoutRequest.newBuilder()
                                    .setSessionId(sessionID)
                                    .setWsconnection(configuracion.getWsConnection())
                                    .build());

                    sessionID = (int) tokenResponse.getSessionId();

                    System.out.println("Logout [" + sessionID + "]");

                    endMessage.add("User " + configuracion.getUserDetail() + " disconnected. Current session ID " + tokenResponse.getSessionId());

                } catch (Exception ex) {

                    logger.error("Error runnng logout" + ex.getMessage()) ;

                    throw new RuntimeException("Error runnng logout", null);

                }
            
            }  
            
            // ===========================  
            // Is Connected                       
            // ===========================  
            //
            
            Boolean isConnected = Boolean.FALSE;
            
            if(mode == ModesOptions.TestIsConnected)  
            {
               
                try {

                    IsConnectedResponse tokenResponse = stub.isConnected(
                            IsConnectedRequest.newBuilder()
                                    .setSessionId(sessionID)
                                    .setWsconnection(configuracion.getWsConnection())
                                    .build());

                    isConnected = tokenResponse.getConnected();

                    System.out.println("User is Connected with session ID [" + sessionID + "] ? " + isConnected);

                } catch (Exception ex) {

                    logger.error("Error runnng isConnected" + ex.getMessage()) ;

                    throw new RuntimeException("Error runnng isConnected", null);

                }
            
            }
              
            
            if(modeHidden != null && modeHidden == ModesHiddenOptions.GetLog)
            {
                
            }
            
            logger.info("------------------------------------------------------------------------");
            logger.info("CHECK SUCESSS");
            logger.info("------------------------------------------------------------------------");
            
            for (String line : endMessage) {
                logger.info(line);
            }
            
            logger.info("------------------------------------------------------------------------");
             
               
        } catch (Exception ex) {
            
            logger.error(ex.getMessage(), ex);
            
             
        }
        
        if(channel != null)
        { 
            channel.shutdown();
        }
        
        

    }

    private static void printUsage(OptionsParser parser) {

        System.out.println("Usage: java -jar jd-check-microservice OPTIONS");

        System.out.println("   " + parser.describeOptions(Collections.<String, String>emptyMap(), OptionsParser.HelpVerbosity.LONG));
        
        System.out.println("   ");
        
        System.out.println("   " + "Mode values:" + ModesOptions.publicValues());
         
        System.out.println("   ");
          
    }

    private static void setupLogging(String destDir) {

        MDC.put("fileName", destDir + File.separator + "log.txt");
    }

     
      
    private static void showBanner() throws IOException {

        // --------------------------------------------------
        // The class loader that loaded the class
        // --------------------------------------------------
        ClassLoader classLoader = Main.class.getClassLoader();

        InputStream inputStream = classLoader.getResourceAsStream("banner.txt");

        // the stream holding the file content
        if (inputStream == null) {
            throw new IllegalArgumentException("Error: File not found in resource folder: " + INI_FOLDER + "banner.txt");
        }

        // --------------------------------------------------
        // Process Each Line
        // --------------------------------------------------
        InputStreamReader streamReader
                = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        BufferedReader reader = new BufferedReader(streamReader);

        String line;

        while ((line = reader.readLine()) != null) {

            System.out.println(line);

        }

        inputStream.close(); 

    }
    
    private static void printParameter(TipoDelParametroInput parameter, int level) {
        level++;

        String space = StringUtils.repeat(".", level * 2);

        logger.info(space + "Parameter Name: [" + parameter.getNombreDelParametro() + "]" + " Type [" + parameter.getTipoDelParametroJava() + "] Repeated: " + parameter.getRepeatedParameter());

        for (TipoDelParametroInput input : parameter.getSubParametroList()) {
            if (!input.getNombreDelParametro().isEmpty()) {
                printParameter(input, level);
            }

        }

    }

    private static void printParameterOutput(TipoDelParametroOutput parameter, int level) {
        level++;

        String space = StringUtils.repeat(".", level * 2);

        logger.info(space + "Parameter Name: [" + parameter.getNombreDelParametro() + "]" + " Type [" + parameter.getTipoDelParametroJava() + "] Repeated: " + parameter.getRepeatedParameter());

        for (TipoDelParametroOutput input : parameter.getSubParametroList()) {
            if (!input.getNombreDelParametro().isEmpty()) {
                printParameterOutput(input, level);
            }
        }

    }
 
      
}
