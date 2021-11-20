/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.atina.ppal;
  
import com.google.devtools.common.options.OptionsParser;
import com.google.shade.protobuf.ByteString;
import com.jde.jdeserverwp.servicios.CapturarLogRequest;
import com.jde.jdeserverwp.servicios.CapturarLogResponse;
import com.jde.jdeserverwp.servicios.EjecutarOperacionRequest;
import com.jde.jdeserverwp.servicios.EjecutarOperacionResponse;
import com.jde.jdeserverwp.servicios.EjecutarOperacionValores;
import com.jde.jdeserverwp.servicios.GetJsonsForOperationResponse;
import com.jde.jdeserverwp.servicios.GetMetadataRequest;
import com.jde.jdeserverwp.servicios.GetMetadataResponse;
import com.jde.jdeserverwp.servicios.IsConnectedRequest;
import com.jde.jdeserverwp.servicios.IsConnectedResponse;
import com.jde.jdeserverwp.servicios.JDEServiceGrpc;
import com.jde.jdeserverwp.servicios.LogoutRequest;
import com.jde.jdeserverwp.servicios.Operacion;
import com.jde.jdeserverwp.servicios.OperacionesRequest;
import com.jde.jdeserverwp.servicios.OperacionesResponse;
import com.jde.jdeserverwp.servicios.ProcessTokenRequest;
import com.jde.jdeserverwp.servicios.ProcessTokenResponse;
import com.jde.jdeserverwp.servicios.SessionRequest;
import com.jde.jdeserverwp.servicios.SessionResponse;
import com.jde.jdeserverwp.servicios.TipoDelParametroInput;
import com.jde.jdeserverwp.servicios.TipoDelParametroOutput;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader; 
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections; 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC; 
import java.io.InputStream;   
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays; 
import java.util.Iterator;
import java.util.List; 
import java.util.regex.Pattern; 
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
        TestLoggindAndGetAddressBookWS("TestLoggindAndGetAddressBookWS");
          
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
        ShowHidden("ShowHidden"),
        GetLog("GetLog"),
        TestIsConnectedWS("TestIsConnectedWS"),
        TestLogoutWS("TestLogoutWS"),
        TestLogindWS("TestLogindWS"),
        TestGetAddressBookWSWithSessionId("TestGetAddressBookWSWithSessionId"),
        CreateToken("CreateToken"),
        GetMetadataWS("GetMetadataWS"),
        GetJsonWS("GetJsonWS"),
        GetMetadataOperations("GetMetadataOperations"),
        ParseToken("ParseToken");

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
        
        public static List<ModesHiddenOptions> hiddenValues() 
        {   
            return Arrays.asList(ModesHiddenOptions.values());
        
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
        
        if (   !options.mode.isEmpty()    
                && (modeHidden == ModesHiddenOptions.ShowHidden)
           ) 
        {
            printUsageWithHiddenOptions(parser);
            return;
            
        }
        
        if ( (  (  !options.mode.isEmpty()    
                && (mode == ModesOptions.TestLoggindAndGetAddressBookWS)
                ) ||
                (  !options.mode.isEmpty()    
                && (modeHidden == ModesHiddenOptions.GetMetadataWS 
                    || modeHidden == ModesHiddenOptions.GetJsonWS
                    || modeHidden == ModesHiddenOptions.GetMetadataOperations
                    || modeHidden == ModesHiddenOptions.TestLogindWS 
                    || modeHidden == ModesHiddenOptions.CreateToken)
                )
              )
            && (options.serverName.isEmpty()
            || options.serverPort.isEmpty() 
            || options.user.isEmpty()
            || options.password.isEmpty()
            || options.environment.isEmpty())) {

            printUsage(parser);

            return;
        } 
        
        if ( (  
                (  !options.mode.isEmpty()    
                && (modeHidden == ModesHiddenOptions.TestIsConnectedWS 
                    || modeHidden == ModesHiddenOptions.TestLogoutWS 
                    || modeHidden == ModesHiddenOptions.GetLog
                    || modeHidden == ModesHiddenOptions.TestGetAddressBookWSWithSessionId)
                )
              )
            && (    options.serverName.isEmpty()
                 || options.serverPort.isEmpty() 
               )) {

            printUsage(parser);
            
            System.out.println("Error: "); 
            System.out.println("   ");
            System.out.println("   Mode ["+ options.mode + "] requires microservices values"); 

            return;
        }
          
        if ( !options.mode.isEmpty() 
             && mode == ModesOptions.TestLoggindAndGetAddressBookWS
             && options.addressbookno.isEmpty())
        {
            printUsage(parser);    
            System.out.println("Error: "); 
            System.out.println("   ");
            System.out.println("   Mode ["+ options.mode + "] requires addressbookno option"); 
            return;
        }
        
        if ( !options.mode.isEmpty() 
             && (modeHidden == ModesHiddenOptions.TestIsConnectedWS 
                || modeHidden == ModesHiddenOptions.TestLogoutWS 
                || modeHidden == ModesHiddenOptions.TestGetAddressBookWSWithSessionId )
             && options.sessionId.isEmpty())
        {
            printUsage(parser);    
            System.out.println("Error: "); 
            System.out.println("   ");
            System.out.println("   Mode ["+ options.mode + "] requires sessionId option"); 
            return;
        }
        
        if ( !options.mode.isEmpty() 
             && (modeHidden == ModesHiddenOptions.GetLog 
                )
             && options.transactionId.isEmpty())
        {
            printUsage(parser);    
            System.out.println("Error: "); 
            System.out.println("   ");
            System.out.println("   Mode ["+ options.mode + "] requires transactionId option"); 
            return;
        }
        
        if ( !options.mode.isEmpty() 
             && (modeHidden == ModesHiddenOptions.ParseToken
                )
             && options.token.isEmpty())
        {
            printUsage(parser);    
            System.out.println("Error: "); 
            System.out.println("   ");
            System.out.println("   Mode ["+ options.mode + "] requires Token option"); 
            return;
        }
        
        if ( !options.mode.isEmpty() 
             && (modeHidden == ModesHiddenOptions.GetMetadataWS
                || modeHidden == ModesHiddenOptions.GetJsonWS)
             && options.operationId.isEmpty())
        {
            printUsage(parser);    
            System.out.println("Error: "); 
            System.out.println("   ");
            System.out.println("   Mode ["+ options.mode + "] requires operationId option"); 
            return;
        }
            
        String operationKey = "oracle.e1.bssv.JP010000.AddressBookManager.getAddressBook";
        
        String transactionIdStr = DateTimeFormatter.ofPattern("yyyyMMddHHmmss").format(LocalDateTime.now());
        Long transactionId = Long.parseLong(transactionIdStr);
        
        System.out.println("TID: " + transactionIdStr); 
        
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
            
            String token = "";
             
            // ===========================  
            // Login                       
            // ===========================  
            //
            
            if(mode == ModesOptions.TestLoggindAndGetAddressBookWS 
                    || modeHidden == ModesHiddenOptions.GetMetadataWS
                    || modeHidden == ModesHiddenOptions.GetMetadataOperations
                    || modeHidden == ModesHiddenOptions.GetJsonWS)  
            {
                try {

                    SessionResponse tokenResponse = stub.login(
                            SessionRequest.newBuilder()
                                    .setUser(configuracion.getUser())
                                    .setPassword(configuracion.getPassword())
                                    .setEnvironment(configuracion.getEnvironment())
                                    .setRole(configuracion.getRole())
                                    .setWsconnection(configuracion.getWsConnection()) 
                                    .setTransactionID(transactionId)
                                    .build());

                    sessionID = (int) tokenResponse.getSessionId();

                    System.out.println("User " + configuracion.getUserDetail() +  " connected with Session ID [" + tokenResponse.getSessionId() + "]");

                    endMessage.add("User " + configuracion.getUserDetail() + " connected with Session ID " + tokenResponse.getSessionId());

                } catch (Exception ex) {

                    logger.error("Error with Login:  " + ex.getMessage(),ex) ;

                    throw new RuntimeException("Error Logeando", null);

                }
                
                // ===========================  
                // Get Operations                       
                // ===========================  
                //
                if(modeHidden == ModesHiddenOptions.GetMetadataOperations)
                {
                    try {

                        OperacionesResponse operaciones = stub.operaciones(
                            OperacionesRequest.newBuilder()
                                    .setConnectorName("WS")
                                    .setUser(configuracion.getUser())
                                    .setPassword(configuracion.getPassword())
                                    .setEnvironment(configuracion.getEnvironment())
                                    .setRole(configuracion.getRole())
                                    .setSessionId(sessionID)
                                    .setWsconnection(configuracion.getWsConnection())
                                    .build());
   
                        File output = new File("/tmp/jd_operations.txt");
                        
                        FileOutputStream fop = new FileOutputStream(output);
                        
                        if (!output.exists()) {
                            output.createNewFile();
                        }       
                            
                        ByteString.copyFromUtf8("--------------------------------------------------------------------------").writeTo(fop);
                        ByteString.copyFrom(System.getProperty("line.separator").getBytes()).writeTo(fop);
                        
                        for (Operacion operacion : operaciones.getOperacionesList()) {
                            
                            ByteString.copyFromUtf8("Operacion [" + operacion.getNombreOperacion() + "]").writeTo(fop);
                            ByteString.copyFrom(System.getProperty("line.separator").getBytes()).writeTo(fop);
        
                        }
 
                        fop.flush();
                        
                        fop.close();
                        
                        endMessage.add("Metadata File: " + output);
                        
                    } catch (Exception ex) {

                        logger.error("Error getting Metadata Operations: " + ex.getMessage(),ex);

                        throw new RuntimeException("Error Logeando", ex);

                    }
                    
                }
                
                // ===========================  
                // Get Metadata                       
                // ===========================  
                //
             
                if(modeHidden == ModesHiddenOptions.GetMetadataWS)
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
                                        .setOperacionKey(options.operationId)
                                        .setTransactionID(transactionId)
                                        .build());
 
                        String[] operationArray = options.operationId.split("\\.");
                        
                        File output = new File("/tmp/jd_" + operationArray[3] + "_" + operationArray[4] + "_" + operationArray[5] +".txt");
                        
                        FileOutputStream fop = new FileOutputStream(output);
                        
                        if (!output.exists()) {
                            output.createNewFile();
                        }       
                        
                        ByteString.copyFrom(System.getProperty("line.separator").getBytes()).writeTo(fop);
                           
                        ByteString.copyFromUtf8("--------------------------------------------------------------------------").writeTo(fop);                       
                        ByteString.copyFrom(System.getProperty("line.separator").getBytes()).writeTo(fop);
                        ByteString.copyFromUtf8("Input").writeTo(fop);
                        ByteString.copyFrom(System.getProperty("line.separator").getBytes()).writeTo(fop);
                        
                        for (TipoDelParametroInput parameter : operaciones.getListaDeParametrosInputList()) {

                            int level = 0;

                            saveParameterInput(parameter, level, fop);

                        } 
                
                        ByteString.copyFromUtf8("--------------------------------------------------------------------------").writeTo(fop);
                        ByteString.copyFrom(System.getProperty("line.separator").getBytes()).writeTo(fop);
                        ByteString.copyFromUtf8("Ouptut").writeTo(fop);
                        ByteString.copyFrom(System.getProperty("line.separator").getBytes()).writeTo(fop);
                         
                        for (TipoDelParametroOutput parameter : operaciones.getListaDeParametrosOutputList()) {

                            int level = 0;

                            saveParameterOutput(parameter, level, fop);

                        }
                        
                        ByteString.copyFromUtf8("--------------------------------------------------------------------------").writeTo(fop);
                        
                        fop.flush();
                        
                        fop.close();
                        
                        endMessage.add("Metadata File: " + output);
                        
                    } catch (Exception ex) {

                        logger.error("Error getting Metadata: " + ex.getMessage(),ex);

                        throw new RuntimeException("Error Logeando", ex);

                    }

                }
                
                // ===========================  
                // Get Metadata                       
                // ===========================  
                //
             
                if(modeHidden == ModesHiddenOptions.GetJsonWS)
                {
                    try {

                        GetJsonsForOperationResponse operaciones = stub.getJsonsForOperation(
                                GetMetadataRequest.newBuilder()
                                .setConnectorName("WS")
                                .setUser(configuracion.getUser())
                                .setPassword(configuracion.getPassword())
                                .setEnvironment(configuracion.getEnvironment())
                                .setRole(configuracion.getRole())
                                .setSessionId(sessionID)
                                .setWsconnection(configuracion.getWsConnection())
                                .setOperacionKey(options.operationId)
                                .build());
 
                        String[] operationArray = options.operationId.split("\\.");
                        
                        File output = new File("/tmp/jd_" + operationArray[3] + "_" + operationArray[4] + "_" + operationArray[5] +".json");
                        
                        FileOutputStream fop = new FileOutputStream(output);
                        
                        if (!output.exists()) {
                            output.createNewFile();
                        }       
                        
                        ByteString.copyFromUtf8(operaciones.getInputAsJson()).writeTo(fop);
                         
                        fop.flush();
                        
                        fop.close();
                        
                        endMessage.add("JSON File: " + output);
                        
                    } catch (Exception ex) {

                        logger.error("Error getting Metadata: " + ex.getMessage(),ex);

                        throw new RuntimeException("Error Logeando", ex);

                    }

                }
                
                // ===========================  
                // Invoke WS              
                // ===========================  
                // 

                if(mode == ModesOptions.TestLoggindAndGetAddressBookWS)
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
                                        .setTransactionID(transactionId)
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
                                    .setTransactionID(transactionId)
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
             
            if(modeHidden != null && (modeHidden == ModesHiddenOptions.TestLogindWS))
            {
                try {
                    
                    SessionResponse tokenResponse = stub.login(
                                        SessionRequest.newBuilder()
                                        .setUser(configuracion.getUser())
                                        .setPassword(configuracion.getPassword())
                                        .setEnvironment(configuracion.getEnvironment())
                                        .setRole(configuracion.getRole())
                                        .setWsconnection(configuracion.getWsConnection())
                                        .setTransactionID(transactionId)
                                        .build());

                    sessionID = (int) tokenResponse.getSessionId();
                    
                    token = tokenResponse.getJwtToken();

                    System.out.println("User " + configuracion.getUserDetail() +  " connected with Session ID [" + tokenResponse.getSessionId() + "]");
                    System.out.println("     Token [" + token + "]");
 
                    endMessage.add("User " + configuracion.getUserDetail() + " connected with Session ID " + tokenResponse.getSessionId());
                    endMessage.add("Token: [" + token + "]");
                    

                } catch (Exception ex) {

                    logger.error("Error with Login:  " + ex.getMessage(),ex) ;

                    throw new RuntimeException("Error Logeando", null);

                }
                 
            }
            
            if(modeHidden != null && (modeHidden == ModesHiddenOptions.CreateToken))
            {
                try {
                     
                    ProcessTokenResponse processTokenResponse = stub.processToken(
                                        ProcessTokenRequest.newBuilder()
                                        .setAction("create")
                                        .setUser(configuracion.getUser()) 
                                        .setEnvironment(configuracion.getEnvironment())
                                        .setRole(configuracion.getRole()) 
                                        .setTransactionID(transactionId)
                                        .build());
                     
                    token = processTokenResponse.getJwtToken(); 
                    System.out.println("User " + configuracion.getUserDetail() +  " with Session ID [" + configuracion.getSession().toString() + "]");
                    System.out.println("     Token [" + token + "]");
 
                    endMessage.add("User " + configuracion.getUserDetail() +  " with Session ID [" + configuracion.getSession().toString() + "]");
                    endMessage.add("Token: [" + token + "]");
                    

                } catch (Exception ex) {

                    logger.error("Error with Login:  " + ex.getMessage(),ex) ;

                    throw new RuntimeException("Error Logeando", null);

                }
                 
            }
            
            if(modeHidden != null && (modeHidden == ModesHiddenOptions.ParseToken))
            {
                try {
                     
                    ProcessTokenResponse processTokenResponse = stub.processToken(
                                        ProcessTokenRequest.newBuilder()
                                        .setAction("parse")
                                        .setJwtToken(options.token) 
                                        .setTransactionID(transactionId)
                                        .build());
                    
                    ConfiguracionServer configuracionResponse = new ConfiguracionServer();
                    
                    configuracionResponse.setUser(processTokenResponse.getUser());
                    configuracionResponse.setEnvironment(processTokenResponse.getEnvironment());
                    configuracionResponse.setRole(processTokenResponse.getRole());
                    configuracionResponse.setSession(((Long)processTokenResponse.getSessionId()).intValue());
   
                    System.out.println("User " + configuracionResponse.getUserDetail() +  " with Session ID [" + configuracionResponse.getSession().toString() + "]");
                    System.out.println("     Token [" + options.token + "]");
 
                    endMessage.add("User " + configuracionResponse.getUserDetail() +  " with Session ID [" + configuracionResponse.getSession().toString() + "]");
                    endMessage.add("Token: [" + options.token + "]");
                    

                } catch (Exception ex) {

                    logger.error("Error with Login:  " + ex.getMessage(),ex) ;

                    throw new RuntimeException("Error Logeando", null);

                }
                 
            }
            
            if(modeHidden != null && modeHidden == ModesHiddenOptions.TestIsConnectedWS)
            {
                try {
                    
                    sessionID = Integer.parseInt(options.sessionId);

                    IsConnectedResponse tokenResponse = stub.isConnected(
                            IsConnectedRequest.newBuilder()
                                    .setSessionId(sessionID)
                                    .setWsconnection(configuracion.getWsConnection())
                                    .setTransactionID(transactionId)
                                    .build());

                    isConnected = tokenResponse.getConnected();

                    System.out.println("User is Connected with session ID [" + sessionID + "] ? " + isConnected);

                } catch (io.grpc.StatusRuntimeException ex) {
 
                    String[] msg = ex.getMessage().split(Pattern.quote("|"));
                    
                    if(msg.length == 3 && msg[1].startsWith("There is not a session in poll connections for session id"))
                    {
                        logger.error(msg[1]);
                    } else
                    {
                        throw new RuntimeException("Error runnng isConnected: " + ex.getMessage(), null);
                    }
                    
 
                } catch (Exception ex) {

                    logger.error("Error runnng isConnected" + ex.getMessage()) ;

                    throw new RuntimeException("Error runnng isConnected", null);

                }
                
            }
            
            if(modeHidden != null && modeHidden == ModesHiddenOptions.TestLogoutWS)
            {
                
                try {
                    
                    sessionID = Integer.parseInt(options.sessionId);

                    SessionResponse tokenResponse = stub.logout(
                            LogoutRequest.newBuilder()
                                    .setSessionId(sessionID)
                                    .setWsconnection(configuracion.getWsConnection())
                                    .setTransactionID(transactionId)
                                    .build());

                    sessionID = (int) tokenResponse.getSessionId();

                    System.out.println("Logout [" + sessionID + "]");

                    endMessage.add("User " + configuracion.getUserDetail() + " disconnected. Current session ID " + tokenResponse.getSessionId());

                } catch (Exception ex) {

                    logger.error("Error runnng logout" + ex.getMessage()) ;

                    throw new RuntimeException("Error runnng logout", null);

                }
                
            }
            
            if(modeHidden != null && modeHidden == ModesHiddenOptions.TestGetAddressBookWSWithSessionId)
            {

                    try {

                        sessionID = Integer.parseInt(options.sessionId);
                        
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
                                        .setTransactionID(transactionId)
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
           
            if(modeHidden != null && modeHidden == ModesHiddenOptions.GetLog)
            {
                // ===========================
                // Get Logs
                // ===========================
                CapturarLogRequest request = CapturarLogRequest.newBuilder()
                        .setTransactionID(Long.parseLong(options.transactionId))
                        .build(); 

                Iterator<CapturarLogResponse> response = stub.capturarLog(request);
                
                File output = new File("/tmp/jd" + options.transactionId + ".log");
                
                FileOutputStream fop = new FileOutputStream(output);

                if (!output.exists()) {
                    output.createNewFile();
                }

                while (response.hasNext()) {

                    ByteString data = response.next().getFileData();

                    data.writeTo(fop);

                    fop.flush();
 
                }

                fop.close();
                
                endMessage.add("Log File: " + output);
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
    
    private static void printUsageWithHiddenOptions(OptionsParser parser) {

        System.out.println("Usage: java -jar jd-check-microservice OPTIONS");

        System.out.println("   " + parser.describeOptions(Collections.<String, String>emptyMap(), OptionsParser.HelpVerbosity.LONG));
        
        System.out.println("   ");
        
        System.out.println("   " + "Mode Hidden values:" + ModesHiddenOptions.hiddenValues());
         
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
    
    private static void saveParameterInput(TipoDelParametroInput parameter, int level,FileOutputStream fop) throws IOException {
        
        level++;

        String space = StringUtils.repeat(".", level * 2);
        
        ByteString.copyFromUtf8(space + "Parameter Name: [" + parameter.getNombreDelParametro() + "]" + " Type [" + parameter.getTipoDelParametroJava() + "] Repeated: " + parameter.getRepeatedParameter()).writeTo(fop);
        ByteString.copyFrom(System.getProperty("line.separator").getBytes()).writeTo(fop);
        
        for (TipoDelParametroInput input : parameter.getSubParametroList()) {
            if (!input.getNombreDelParametro().isEmpty()) {
                saveParameterInput(input, level,fop);
            }

        }
        
        fop.flush();

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
    
    private static void saveParameterOutput(TipoDelParametroOutput parameter, int level, FileOutputStream fop) throws IOException {
    
        level++;

        String space = StringUtils.repeat(".", level * 2);

        ByteString.copyFromUtf8(space + "Parameter Name: [" + parameter.getNombreDelParametro() + "]" + " Type [" + parameter.getTipoDelParametroJava() + "] Repeated: " + parameter.getRepeatedParameter()).writeTo(fop);
        ByteString.copyFrom(System.getProperty("line.separator").getBytes()).writeTo(fop);
        
        for (TipoDelParametroOutput input : parameter.getSubParametroList()) {
            if (!input.getNombreDelParametro().isEmpty()) {
                saveParameterOutput(input, level,fop);
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
