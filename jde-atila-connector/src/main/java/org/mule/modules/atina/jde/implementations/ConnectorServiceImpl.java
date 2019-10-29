package org.mule.modules.atina.jde.implementations;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.mule.modules.atina.jde.exceptions.ExternalConnectorException;
import org.mule.modules.atina.jde.exceptions.InternalConnectorException;
import org.mule.modules.atina.jde.interfaces.ConnectorServiceInterface;
import org.mule.modules.atina.jde.models.JDEAtilaConfiguracion;
import org.mule.modules.atina.jde.models.ParametroInput;

import com.google.shade.common.cache.CacheBuilder;
import com.google.shade.common.cache.CacheLoader;
import com.google.shade.common.cache.LoadingCache;
import com.google.shade.protobuf.ByteString;
import com.google.shade.protobuf.Timestamp;
import com.jde.jdeserverwp.servicios.EjecutarOperacionRequest;
import com.jde.jdeserverwp.servicios.EjecutarOperacionResponse;
import com.jde.jdeserverwp.servicios.EjecutarOperacionValores;
import com.jde.jdeserverwp.servicios.GetJsonsForOperationResponse;
import com.jde.jdeserverwp.servicios.GetMetadataRequest;
import com.jde.jdeserverwp.servicios.GetMetadataResponse;
import com.jde.jdeserverwp.servicios.IsConnectedRequest;
import com.jde.jdeserverwp.servicios.IsConnectedResponse;
import com.jde.jdeserverwp.servicios.JDEServiceGrpc.JDEServiceBlockingStub;
import com.jde.jdeserverwp.servicios.LogoutRequest;
import com.jde.jdeserverwp.servicios.Operacion;
import com.jde.jdeserverwp.servicios.OperacionesRequest;
import com.jde.jdeserverwp.servicios.OperacionesResponse;
import com.jde.jdeserverwp.servicios.SessionRequest;
import com.jde.jdeserverwp.servicios.SessionResponse;
import com.jde.jdeserverwp.servicios.TipoDelParametroInput;
import com.jde.jdeserverwp.servicios.TipoDelParametroOutput;

import io.grpc.StatusRuntimeException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConnectorServiceImpl implements ConnectorServiceInterface {

    private static final Logger logger = LoggerFactory.getLogger(ConnectorServiceImpl.class);

    private JDEServiceBlockingStub stub;
    private JDEAtilaConfiguracion configuracion;

    @SuppressWarnings("rawtypes")
    private LoadingCache cacheMetadataInput;
    @SuppressWarnings("rawtypes")
    private LoadingCache cacheMetadataInputAsHashMap;
    @SuppressWarnings("rawtypes")
    private LoadingCache cacheMetadataOutput;

    public ConnectorServiceImpl() {

        super();

        CacheLoader<String, List<TipoDelParametroInput>> metadataInput;

        CacheLoader<String, HashMap<String, ParametroInput>> metadataInputAsHashMap;

        CacheLoader<String, List<TipoDelParametroOutput>> metadataOutput;

        metadataInput = new CacheLoader<String, List<TipoDelParametroInput>>() {

            @Override
            public List<TipoDelParametroInput> load(String operation) throws InternalConnectorException {
                return getMetadataForInput(operation);
            }
        };

        metadataInputAsHashMap = new CacheLoader<String, HashMap<String, ParametroInput>>() {

            @Override
            public HashMap<String, ParametroInput> load(String operation) throws Exception {
                return getMetadataForInputAsHashMap(operation);
            }

        };

        metadataOutput = new CacheLoader<String, List<TipoDelParametroOutput>>() {

            @Override
            public List<TipoDelParametroOutput> load(String operation) throws InternalConnectorException {
                return getMetadataForOutput(operation);
            }
        };

        this.cacheMetadataInput = CacheBuilder.newBuilder()
                .
                maximumSize(50)
                .
                expireAfterAccess(10, TimeUnit.MINUTES)
                .
                build(metadataInput);

        this.cacheMetadataInputAsHashMap = CacheBuilder.newBuilder()
                .
                maximumSize(50)
                .
                expireAfterAccess(10, TimeUnit.MINUTES)
                .
                build(metadataInputAsHashMap);

        this.cacheMetadataOutput = CacheBuilder.newBuilder()
                .
                maximumSize(50)
                .
                expireAfterAccess(10, TimeUnit.MINUTES)
                .
                build(metadataOutput);
    }

    @Override
    public void login(JDEServiceBlockingStub stub, JDEAtilaConfiguracion configuracion)
            throws InternalConnectorException, ExternalConnectorException {

        logger.info("JDE Atina Service - Login... ");

        SessionResponse tokenResponse = null;

        try {

            tokenResponse = stub.login(
                    SessionRequest.newBuilder()
                            .setUser(configuracion.getJdeUser())
                            .setPassword(configuracion.getJdePassword())
                            .setEnvironment(configuracion.getJdeEnvironment())
                            .setRole(configuracion.getJdeRole())
                            .setJwtToken(configuracion.getToken())
                            .setWsconnection(configuracion.getWsConnection())
                            .setSessionId(configuracion.getSessionID())
                            .build());

        } catch (StatusRuntimeException e) {

            logger.error("JDE Atina Service + Error: " + e.getMessage());

            if (e.getMessage()
                    .endsWith("%ExternalServiceException%") ||
                    e.getMessage()
                            .endsWith("%InternalServiceException%"))
            {

                String[] tokens = StringUtils.split(e.getMessage(), "|");

                String errorMessage = tokens[0];
                String claseDeLaOperacion = tokens[1];
                String metodoDeLaOperacion = tokens[2];
                int httpStatus = 0;
                String httpStatusReason = "";
                String request = "";
                String response = "";
                String e1Message = "";

                throw new ExternalConnectorException(errorMessage, claseDeLaOperacion, metodoDeLaOperacion, httpStatus, httpStatusReason, request, response, e1Message, e);

            }
            else
            {

                String errorMessage = e.getMessage();
                String claseDeLaOperacion = "Login";
                String metodoDeLaOperacion = "Login";
                int httpStatus = 510;
                String httpStatusReason = "";
                String request = "";
                String response = "";

                throw new InternalConnectorException(errorMessage, claseDeLaOperacion, metodoDeLaOperacion, httpStatus, httpStatusReason, request, response, e);

            }

        }

        if (tokenResponse != null) {

            logger.info("JDE Atina Service - SessionID: [" + tokenResponse.getSessionId() + "]");
        }

        logger.info("JDE Atina Service - End login ");

        configuracion.setSessionID(tokenResponse.getSessionId());
        
        configuracion.setToken(tokenResponse.getJwtToken());
        
    }

    @Override
    public void logout(JDEServiceBlockingStub stub, JDEAtilaConfiguracion configuracion)
            throws InternalConnectorException, ExternalConnectorException {

        logger.info("JDE Atina Service - Logout... ");

        SessionResponse tokenResponse = null;

        try {

            tokenResponse = stub.logout(
                    LogoutRequest.newBuilder()
                            .setWsconnection(configuracion.getWsConnection())
                            .setSessionId(configuracion.getSessionID())
                            .setJwtToken(configuracion.getToken())
                            .build());

        } catch (StatusRuntimeException e) {

            logger.error("JDE Atina Service + Error: " + e.getMessage());

            if (e.getMessage()
                    .endsWith("%ExternalServiceException%") ||
                    e.getMessage()
                            .endsWith("%InternalServiceException%"))
            {

                String[] tokens = StringUtils.split(e.getMessage(), "|");

                String errorMessage = tokens[0];
                String claseDeLaOperacion = tokens[1];
                String metodoDeLaOperacion = tokens[2];
                int httpStatus = 0;
                String httpStatusReason = "";
                String request = "";
                String response = "";
                String e1Message = "";

                throw new ExternalConnectorException(errorMessage, claseDeLaOperacion, metodoDeLaOperacion, httpStatus, httpStatusReason, request, response, e1Message, e);

            }
            else
            {

                String errorMessage = e.getMessage();
                String claseDeLaOperacion = "Logout";
                String metodoDeLaOperacion = "Logout";
                int httpStatus = 510;
                String httpStatusReason = "";
                String request = "";
                String response = "";

                throw new InternalConnectorException(errorMessage, claseDeLaOperacion, metodoDeLaOperacion, httpStatus, httpStatusReason, request, response, e);

            }

        }

        if (tokenResponse != null) {

            logger.info("JDE Atina Service - SessionID: [" + tokenResponse.getSessionId() + "]");
        }

        logger.info("JDE Atina Service - End logout ");

        configuracion.setSessionID(tokenResponse.getSessionId());
    }

    @Override
    public boolean isConnected(JDEServiceBlockingStub stub, JDEAtilaConfiguracion configuracion)
            throws InternalConnectorException, ExternalConnectorException {

        logger.info("JDE Atina Service - isConnected... ");

        IsConnectedResponse tokenResponse = null;

        try {

            tokenResponse = stub.isConnected(
                    IsConnectedRequest.newBuilder()
                            .setWsconnection(configuracion.getWsConnection())
                            .setSessionId(configuracion.getSessionID())
                            .setJwtToken(configuracion.getToken())
                            .build());

        } catch (StatusRuntimeException e) {

            logger.error("JDE Atina Service + Error: " + e.getMessage());

            if (e.getMessage()
                    .endsWith("%ExternalServiceException%") ||
                    e.getMessage()
                            .endsWith("%InternalServiceException%"))
            {

                String[] tokens = StringUtils.split(e.getMessage(), "|");

                String errorMessage = tokens[0];
                String claseDeLaOperacion = tokens[1];
                String metodoDeLaOperacion = tokens[2];
                int httpStatus = 0;
                String httpStatusReason = "";
                String request = "";
                String response = "";
                String e1Message = "";

                throw new ExternalConnectorException(errorMessage, claseDeLaOperacion, metodoDeLaOperacion, httpStatus, httpStatusReason, request, response, e1Message, e);

            }
            else
            {

                String errorMessage = e.getMessage();
                String claseDeLaOperacion = "IsConnected";
                String metodoDeLaOperacion = "IsConnected";
                int httpStatus = 510;
                String httpStatusReason = "";
                String request = "";
                String response = "";

                throw new InternalConnectorException(errorMessage, claseDeLaOperacion, metodoDeLaOperacion, httpStatus, httpStatusReason, request, response, e);

            }

        }

        if (tokenResponse != null) {

            return tokenResponse.getConnected();
        }

        logger.info("JDE Atina Service - End isConnected ");

        return false;

    }

    @Override
    public Map<String, String> getMetadataOperations(JDEServiceBlockingStub stub, JDEAtilaConfiguracion configuracion)
            throws InternalConnectorException {

        logger.info("JDE Atina Service - ConnectorServiceImpl - getMetadataOperations ...");

        HashMap<String, String> operations = new HashMap<String, String>();

        OperacionesResponse operacionesResponse = null;

        try {

            operacionesResponse = stub.operaciones(
                    OperacionesRequest.newBuilder()
                            .setConnectorName("WS")
                            .setUser(configuracion.getJdeUser())
                            .setPassword(configuracion.getJdePassword())
                            .setEnvironment(configuracion.getJdeEnvironment())
                            .setRole(configuracion.getJdeRole())
                            .setJwtToken(configuracion.getToken())
                            .setSessionId(configuracion.getSessionID())
                            .setWsconnection(configuracion.getWsConnection())
                            .build());

            for (Operacion operacion : operacionesResponse.getOperacionesList()) {

                logger.info("Operation: " + operacion.getIdOperacion() + " > " + operacion.getNombreOperacion());

                operations.put(operacion.getIdOperacion(), operacion.getNombreOperacion());

            }

        } catch (StatusRuntimeException e) {

            logger.error("JDE Atina Service + Error: " + e.getMessage());

            String[] tokens = StringUtils.split(e.getMessage(), "|");

            String errorMessage = tokens[0];
            String claseDeLaOperacion = tokens[1];
            String metodoDeLaOperacion = tokens[2];
            int httpStatus = 510;
            String httpStatusReason = "";
            String request = "";
            String response = "";

            throw new InternalConnectorException(errorMessage, claseDeLaOperacion, metodoDeLaOperacion, httpStatus, httpStatusReason, request, response, e);

        } catch (Exception e) {

            logger.error("JDE Atina Service + Error: " + e.getMessage());

            String[] tokens = StringUtils.split(e.getMessage(), "|");

            String errorMessage = tokens[0];
            String claseDeLaOperacion = tokens[1];
            String metodoDeLaOperacion = tokens[2];
            int httpStatus = 510;
            String httpStatusReason = "";
            String request = "";
            String response = "";

            throw new InternalConnectorException(errorMessage, claseDeLaOperacion, metodoDeLaOperacion, httpStatus, httpStatusReason, request, response, e);

        }

        return operations;
    }
    
    @Override
    public Object getJsonFromOperations(JDEServiceBlockingStub stub, JDEAtilaConfiguracion configuracion,String entityType, Map<String, Object> entityData)
            throws InternalConnectorException {

        logger.info("JDE Atina Service - ConnectorServiceImpl - getJsonFromOperations ...");

        HashMap<String, String> operations = new HashMap<String, String>();

        GetJsonsForOperationResponse operacionesResponse = null;
         

        try {
        	 

            operacionesResponse = stub.getJsonsForOperation(
            		GetMetadataRequest.newBuilder()
                            .setConnectorName("WS")
                            .setUser(configuracion.getJdeUser())
                            .setPassword(configuracion.getJdePassword())
                            .setEnvironment(configuracion.getJdeEnvironment())
                            .setRole(configuracion.getJdeRole())
                            .setJwtToken((String) entityData.get("JDE Token"))
                            .setSessionId(configuracion.getSessionID())
                            .setWsconnection(configuracion.getWsConnection())
                            .setOperacionKey(entityType)
                            .build());
   
            operations.put("JDE Token", operacionesResponse.getJwtToken());
            operations.put("JSON Schema", operacionesResponse.getInputAsJson());

        } catch (StatusRuntimeException e) {

            logger.error("JDE Atina Service + Error: " + e.getMessage());

            String[] tokens = StringUtils.split(e.getMessage(), "|");

            String errorMessage = tokens[0];
            String claseDeLaOperacion = tokens[1];
            String metodoDeLaOperacion = tokens[2];
            int httpStatus = 510;
            String httpStatusReason = "";
            String request = "";
            String response = "";

            throw new InternalConnectorException(errorMessage, claseDeLaOperacion, metodoDeLaOperacion, httpStatus, httpStatusReason, request, response, e);

        } catch (Exception e) {

            logger.error("JDE Atina Service + Error: " + e.getMessage());

            String[] tokens = StringUtils.split(e.getMessage(), "|");

            String errorMessage = tokens[0];
            String claseDeLaOperacion = tokens[1];
            String metodoDeLaOperacion = tokens[2];
            int httpStatus = 510;
            String httpStatusReason = "";
            String request = "";
            String response = "";

            throw new InternalConnectorException(errorMessage, claseDeLaOperacion, metodoDeLaOperacion, httpStatus, httpStatusReason, request, response, e);

        }

        return operations;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<TipoDelParametroInput> getInputMetadataForOperation(JDEServiceBlockingStub stub, JDEAtilaConfiguracion configuracion, String operation)
            throws InternalConnectorException {

        logger.info("DRAGONFISH - ConnectorServiceImpl - getInputMetadataForOperation for operation: " + operation);

        this.stub = stub;
        this.configuracion = configuracion;

        List<TipoDelParametroInput> returnValue = null;

        try {

            returnValue = (List<TipoDelParametroInput>) cacheMetadataInput.get(operation);

        } catch (Exception e) {

            String[] tokens = StringUtils.split(e.getMessage(), "|");

            String errorMessage = e.getCause()
                    .getMessage();
            String claseDeLaOperacion = tokens[1];
            String metodoDeLaOperacion = tokens[2];
            int httpStatus = 510;
            String httpStatusReason = "";
            String request = "";
            String response = "";

            throw new InternalConnectorException(errorMessage, claseDeLaOperacion, metodoDeLaOperacion, httpStatus, httpStatusReason, request, response, e);

        }

        return returnValue;

    }

    @SuppressWarnings("unchecked")
    @Override
    public List<TipoDelParametroOutput> getOutputMetadataForOperation(JDEServiceBlockingStub stub, JDEAtilaConfiguracion configuracion, String operation)
            throws InternalConnectorException {

        logger.info("DRAGONFISH - ConnectorServiceImpl - getInputMetadataForOperation for operation: " + operation);

        this.stub = stub;
        this.configuracion = configuracion;

        List<TipoDelParametroOutput> returnValue = null;

        try {

            returnValue = (List<TipoDelParametroOutput>) cacheMetadataOutput.get(operation);

        } catch (Exception e) {

            String[] tokens = StringUtils.split(e.getMessage(), "|");

            String errorMessage = e.getCause()
                    .getMessage();
            String claseDeLaOperacion = tokens[1];
            String metodoDeLaOperacion = tokens[2];
            int httpStatus = 510;
            String httpStatusReason = "";
            String request = "";
            String response = "";

            throw new InternalConnectorException(errorMessage, claseDeLaOperacion, metodoDeLaOperacion, httpStatus, httpStatusReason, request, response, e);

        }

        return returnValue;

    }

    @SuppressWarnings("unchecked")
    @Override
    public Object ejecutarServicio(JDEServiceBlockingStub stub,
            JDEAtilaConfiguracion configuracion, String entityType, Map<String, Object> entityData)
            throws InternalConnectorException, ExternalConnectorException {

        this.stub = stub;

        this.configuracion = configuracion;

        Object returnValue = null;

        // --------------------------------------------------------------
        // Optener Metadata de Input
        // --------------------------------------------------------------
        //

        List<TipoDelParametroInput> metadataInput = null;

        try {

            metadataInput = (List<TipoDelParametroInput>) cacheMetadataInput.get(entityType);

        } catch (Exception e) {

            String[] tokens = StringUtils.split(e.getMessage(), "|");

            String errorMessage = e.getCause()
                    .getMessage();
            String claseDeLaOperacion = tokens[1];
            String metodoDeLaOperacion = tokens[2];
            int httpStatus = 510;
            String httpStatusReason = "";
            String request = "";
            String response = "";

            throw new InternalConnectorException(errorMessage, claseDeLaOperacion, metodoDeLaOperacion, httpStatus, httpStatusReason, request, response, e);

        }

        HashMap<String, ParametroInput> metadataInputAsHashMap = null;

        try {

            metadataInputAsHashMap = (HashMap<String, ParametroInput>) cacheMetadataInputAsHashMap.get(entityType);

        } catch (Exception e) {

            String[] tokens = StringUtils.split(e.getMessage(), "|");

            String errorMessage = e.getCause()
                    .getMessage();
            String claseDeLaOperacion = tokens[1];
            String metodoDeLaOperacion = tokens[2];
            int httpStatus = 510;
            String httpStatusReason = "";
            String request = "";
            String response = "";

            throw new InternalConnectorException(errorMessage, claseDeLaOperacion, metodoDeLaOperacion, httpStatus, httpStatusReason, request, response, e);

        }

        // --------------------------------------------------------------
        // Optener Metadata de Output
        // --------------------------------------------------------------
        //

        List<TipoDelParametroOutput> metadataOutput = null;

        try {

            metadataOutput = (List<TipoDelParametroOutput>) cacheMetadataOutput.get(entityType);

        } catch (Exception e) {

            String[] tokens = StringUtils.split(e.getMessage(), "|");

            String errorMessage = e.getCause()
                    .getMessage();
            String claseDeLaOperacion = tokens[1];
            String metodoDeLaOperacion = tokens[2];
            int httpStatus = 510;
            String httpStatusReason = "";
            String request = "";
            String response = "";

            throw new InternalConnectorException(errorMessage, claseDeLaOperacion, metodoDeLaOperacion, httpStatus, httpStatusReason, request, response, e);

        }

        try {

            // --------------------------------------------------------------
            // CREACION DEL ARRAY DE VALORES EjecutarOperacionValores
            // --------------------------------------------------------------
            //

            ArrayList<EjecutarOperacionValores> valores = new ArrayList<EjecutarOperacionValores>();

            // --------------------------------------------------------------
            // request
            // --------------------------------------------------------------
            //
            
            String token = "";
            
            if(entityData.containsKey("JDE Token"))
            {
            	token = (String) entityData.get("JDE Token");
            	
            	entityData.remove("JDE Token");
            	
            }

            ArrayList<EjecutarOperacionValores> listaValoresValidos = procesarRequest(metadataInput, entityData, metadataInputAsHashMap, 0);

            valores.addAll(listaValoresValidos);

            // --------------------------------------------------------------
            // Ejecuta la operacion
            // --------------------------------------------------------------
            //

            EjecutarOperacionResponse ejecutarOperacionesResponse = stub.ejecutarOperacion(EjecutarOperacionRequest
                    .newBuilder()
                    .setConnectorName("WS")
                    .setOperacionKey(entityType)
                    .setUser(configuracion.getJdeUser())
                    .setPassword(configuracion.getJdePassword())
                    .setEnvironment(configuracion.getJdeEnvironment())
                    .setRole(configuracion.getJdeRole())
                    .setSessionId(configuracion.getSessionID())
                    .setJwtToken(token)
                    .setWsconnection(configuracion.getWsConnection())
                    .addAllListaDeValores(valores)
                    .build());

            logger.info(" Valores Retornados: ");

            logger.info(" Parameter Name: " + ejecutarOperacionesResponse.getNombreDelParametro());
            logger.info(" Parameter Type: " + ejecutarOperacionesResponse.getTipoDelParametro());
            logger.info(" Is Object: " + ejecutarOperacionesResponse.getIsObject());
            logger.info(" Is Repeated: " + ejecutarOperacionesResponse.getRepeatedParameter());

            int level = 0;

            if (ejecutarOperacionesResponse.getRepeatedParameter())
            {
                ArrayList<HashMap<String, Object>> response = new ArrayList<HashMap<String, Object>>();

                List<EjecutarOperacionResponse> respuestaMap = ejecutarOperacionesResponse.getListaDeValoresList();

                if (respuestaMap != null && !respuestaMap.isEmpty()) {

                    Iterator<EjecutarOperacionResponse> valoresAProcesar = respuestaMap.iterator();

                    while (valoresAProcesar.hasNext()) {

                        EjecutarOperacionResponse resultado = valoresAProcesar.next();

                        HashMap<String, Object> obj = new HashMap<String, Object>();

                        for (EjecutarOperacionResponse respuestaAProcesar : resultado.getListaDeValoresList()) {

                            level++;
                            procesarRespuesta(respuestaAProcesar, obj, level);
                            level--;

                        }

                        response.add(obj);
                    }

                }

                returnValue = response;
                ((HashMap<String, Object>) returnValue).put("JDE Token", (String) ejecutarOperacionesResponse.getJwtToken());

            } else if (ejecutarOperacionesResponse.getIsObject())
            {

                HashMap<String, Object> response = new HashMap<String, Object>();

                List<EjecutarOperacionResponse> respuestaMap = ejecutarOperacionesResponse.getListaDeValoresList();

                if (respuestaMap != null && !respuestaMap.isEmpty()) {

                    for (EjecutarOperacionResponse respuestaAProcesar : respuestaMap) {

                        level++;
                        procesarRespuesta(respuestaAProcesar, response, level);
                        level--;

                    }

                }

                returnValue = response;
                ((HashMap<String, Object>) returnValue).put("JDE Token", (String) ejecutarOperacionesResponse.getJwtToken());

            } else if (ejecutarOperacionesResponse.getTipoDelParametro()
                    .isEmpty()) {

                returnValue = new HashMap<String, Object>();
                
                ((HashMap<String, Object>) returnValue).put("JDE Token", (String) ejecutarOperacionesResponse.getJwtToken());
                 

            } else
            {

                HashMap<String, Object> response = new HashMap<String, Object>();

                ArrayList<TipoDelParametroOutput> newMetadataOutput = new ArrayList<TipoDelParametroOutput>();
                newMetadataOutput.add(metadataOutput.get(0)
                        .getSubParametro(0));

                if (ejecutarOperacionesResponse != null) {

                    procesarRespuesta(ejecutarOperacionesResponse, response, level);

                }

                returnValue = response.get(ejecutarOperacionesResponse.getNombreDelParametro());
                ((HashMap<String, Object>) returnValue).put("JDE Token", (String) ejecutarOperacionesResponse.getJwtToken());

            }

        } catch (StatusRuntimeException e) {

            if (e.getMessage()
                    .endsWith("%ExternalServiceException%"))
            {

                String[] tokens = StringUtils.split(e.getMessage(), "|");

                String errorMessage = tokens[0];
                String claseDeLaOperacion = tokens[1];
                String metodoDeLaOperacion = tokens[2];
                int httpStatus = 510;
                String httpStatusReason = "";
                String request = "";
                String response = "";
                String e1Message = "";

                throw new ExternalConnectorException(errorMessage, claseDeLaOperacion, metodoDeLaOperacion, httpStatus, httpStatusReason, request, response, e1Message, e);

            } else if (e.getMessage()
                    .endsWith("%WSException%"))
            {

                String[] tokens = StringUtils.split(e.getMessage(), "|");

                String errorMessage = tokens[0];
                String claseDeLaOperacion = tokens[1];
                String metodoDeLaOperacion = tokens[2];
                int httpStatus = 510;
                String httpStatusReason = "";
                String request = "";
                String response = "";
                String e1Message = tokens[1];

                throw new ExternalConnectorException(errorMessage, claseDeLaOperacion, metodoDeLaOperacion, httpStatus, httpStatusReason, request, response, e1Message, e);

            } else
            {
                String[] tokens = StringUtils.split(e.getMessage(), "|");

                String errorMessage = e.getMessage();
                String claseDeLaOperacion = tokens[1];
                String metodoDeLaOperacion = tokens[2];
                int httpStatus = 510;
                String httpStatusReason = "";
                String request = "";
                String response = "";

                throw new InternalConnectorException(errorMessage, claseDeLaOperacion, metodoDeLaOperacion, httpStatus, httpStatusReason, request, response, e);

            }

        }

        return returnValue;
    }

    @SuppressWarnings({
            "unchecked",
            "rawtypes"
    })
    private ArrayList<EjecutarOperacionValores> procesarRequest(List<TipoDelParametroInput> metadataInput, Map<String, Object> entityData,
            HashMap<String, ParametroInput> metadataInputAsHashMap, int level)
    {

        ArrayList<EjecutarOperacionValores> valoresARetornar = new ArrayList();

        String levelLog = StringUtils.repeat(" > ", level);

        if (entityData == null)
        {
            return valoresARetornar;
        }

        // --------------------------------------------------------------
        // Creacion de Parametros Siguientes
        // --------------------------------------------------------------
        //
        Set<Map.Entry<String, Object>> values = entityData.entrySet();

        Iterator<Map.Entry<String, Object>> iterador = values.iterator();

        logger.info(levelLog + "=================================================================================");

        logger.info(levelLog + "JDE Atina Service - Processing input value Request: " + values.toString());

        int y = 0;

        while (iterador.hasNext()) {

            logger.info(levelLog + "---------------------------------------------------------------------------------");

            EjecutarOperacionValores.Builder valorNuevo = EjecutarOperacionValores.newBuilder();

            Map.Entry<String, Object> valor = iterador.next();

            try
            {

                String className = valor.getValue()
                        .getClass()
                        .getName();

                logger.info(levelLog + "                                        Parameter No.: " + y);

                y++;

                valorNuevo.setNombreDelParametro(valor.getKey());

                logger.info(levelLog + "                                        Parameter Name recieved      : " + valor.getKey());
                logger.info(levelLog + "                                        Parameter Value received     : " + valor.getValue()
                        .toString());
                logger.info(levelLog + "                                        Parameter Value Type recieved: " + className);

                // --------------------------------------------------------------
                // Obtner Metadata del Input para Conversion del Tipo de Datos
                // --------------------------------------------------------------
                //
                ParametroInput metadataDelInput = metadataInputAsHashMap.get(valor.getKey());

                if (metadataDelInput == null)
                {
                    String msg = "Invalid Parameter [" + valor.getKey() + "]";
                    logger.error(msg);

                    String errorMessage = msg;
                    String claseDeLaOperacion = "";
                    String metodoDeLaOperacion = "";
                    int httpStatus = 510;
                    String httpStatusReason = "";
                    String request = "";
                    String response = "";

                    throw new InternalConnectorException(errorMessage, claseDeLaOperacion, metodoDeLaOperacion, httpStatus, httpStatusReason, request, response, null);

                }

                logger.info(levelLog + "                                        Metadata: Parameter Name: " + metadataDelInput.getParametro()
                        .getNombreDelParametro());
                logger.info(levelLog + "                                        Metadata: Parameter Type: " + metadataDelInput.getParametro()
                        .getTipoDelParametroJava());
                logger.info(levelLog + "                                        Metadata: Is a Object: " + metadataDelInput.getParametro()
                        .getIsObject());
                logger.info(levelLog + "                                        Metadata: Is Repeated: " + metadataDelInput.getParametro()
                        .getRepeatedParameter());

                // --------------------------------------------------------------
                // Get Metadata
                // --------------------------------------------------------------
                //

                if (className.equals("java.util.LinkedHashMap") ||
                        className.equals("java.util.HashMap")) {

                    level++;

                    ArrayList<EjecutarOperacionValores> valoresRetornados = procesarRequest(metadataInput, (Map<String, Object>) valor.getValue(),
                            metadataDelInput.getListaSubParametros(), level);

                    level--;

                    logger.info(levelLog + "---------------------------------------------------------------------------------");
                    logger.info(levelLog + "                                       New Value: \n" + valoresRetornados.toString());
                    logger.info(levelLog + "=================================================================================");

                    valorNuevo.addAllListaDeValores(valoresRetornados);

                    valoresARetornar.add(valorNuevo.build());

                } else if (className.equals("java.util.ArrayList")) {

                    EjecutarOperacionValores.Builder valoresNuevo = EjecutarOperacionValores.newBuilder();

                    List<Object> valores = (ArrayList<Object>) valor.getValue();

                    valoresNuevo.setNombreDelParametro(valor.getKey());

                    if (metadataDelInput.getParametro()
                            .getIsObject())
                    {

                        for (Object valorAProcesar : valores)
                        {
                            EjecutarOperacionValores.Builder valorXNuevo = EjecutarOperacionValores.newBuilder();

                            level++;

                            ArrayList<EjecutarOperacionValores> valoresRetornados = procesarRequest(metadataInput, (Map<String, Object>) valorAProcesar,
                                    metadataDelInput.getListaSubParametros(), level);

                            level--;

                            logger.info(levelLog + "---------------------------------------------------------------------------------");
                            logger.info(levelLog + "                                       New Value: \n" + valoresRetornados.toString());
                            logger.info(levelLog + "=================================================================================");

                            valorXNuevo.addAllListaDeValores(valoresRetornados);

                            valoresNuevo.addListaDeValores(valorXNuevo.build());

                        }

                    } else
                    {

                        for (Object valorAProcesar : valores)
                        {

                            EjecutarOperacionValores valoresRetornados = procesarObject(metadataDelInput.getParametro()
                                    .getNombreDelParametro(), metadataDelInput.getParametro()
                                    .getTipoDelParametroJava(), valorAProcesar);

                            valoresNuevo.addListaDeValores(valoresRetornados);

                        }

                    }

                    valoresARetornar.add(valoresNuevo.build());

                } else
                {

                    // ------------------------------------------------------------------------------------------------
                    // Los parametros se deben convertir al formato desde el formato de MULE al formato de SWAGGER
                    // ------------------------------------------------------------------------------------------------
                    // CONVERTIR AL FORMATO DEL METADATA

                    switch (metadataDelInput.getParametro()
                            .getTipoDelParametroJava()) {

                        case "java.lang.String":
                            switch (className) {
                                case "java.lang.String":
                                    valorNuevo.setValueAsString((String) valor.getValue());
                                    break;
                                case "java.lang.Integer":
                                    valorNuevo.setValueAsString(((Integer) valor.getValue()).toString());
                                    break;
                                case "java.lang.Double":
                                    valorNuevo.setValueAsString(((Double) valor.getValue()).toString());
                                    break;
                                case "java.lang.Long":
                                    valorNuevo.setValueAsString(((Long) valor.getValue()).toString());
                                    break;
                                case "java.lang.Float":
                                    valorNuevo.setValueAsString(((Float) valor.getValue()).toString());
                                    break;
                                default:

                                    String msg = "ERROR: Parameter: " + valor.getKey() + " of " + className + " has an error"
                                            + metadataDelInput.toString();
                                    logger.error(msg);

                                    String errorMessage = msg;
                                    String claseDeLaOperacion = "";
                                    String metodoDeLaOperacion = "";
                                    int httpStatus = 510;
                                    String httpStatusReason = "";
                                    String request = "";
                                    String response = "";

                                    throw new InternalConnectorException(errorMessage, claseDeLaOperacion, metodoDeLaOperacion, httpStatus, httpStatusReason, request, response,
                                            null);

                            }
                            break;
                        case "java.lang.Integer":
                            switch (className) {
                                case "java.lang.String":
                                    valorNuevo.setValueAsInteger(Integer.parseInt((String) valor.getValue()));
                                    break;
                                case "java.lang.Integer":
                                    valorNuevo.setValueAsInteger((Integer) valor.getValue());
                                    break;
                                default:
                                    String msg = "ERROR: Parameter: " + valor.getKey() + " Cannot be converted from " + className + " to Integer."
                                            + metadataDelInput.toString();
                                    logger.error(msg);

                                    String errorMessage = msg;
                                    String claseDeLaOperacion = "";
                                    String metodoDeLaOperacion = "";
                                    int httpStatus = 510;
                                    String httpStatusReason = "";
                                    String request = "";
                                    String response = "";

                                    throw new InternalConnectorException(errorMessage, claseDeLaOperacion, metodoDeLaOperacion, httpStatus, httpStatusReason, request, response,
                                            null);

                            }
                            break;
                        case "java.lang.Boolean":
                            switch (className) {
                                case "java.lang.Boolean":
                                    valorNuevo.setValueAsBoolean((Boolean) valor.getValue());
                                    break;
                                default:
                                    String msg = "ERROR: Parameter: " + valor.getKey() + " Cannot be converted from " + className + " to Boolean."
                                            + metadataDelInput.toString();
                                    logger.error(msg);

                                    String errorMessage = msg;
                                    String claseDeLaOperacion = "";
                                    String metodoDeLaOperacion = "";
                                    int httpStatus = 510;
                                    String httpStatusReason = "";
                                    String request = "";
                                    String response = "";

                                    throw new InternalConnectorException(errorMessage, claseDeLaOperacion, metodoDeLaOperacion, httpStatus, httpStatusReason, request, response,
                                            null);

                            }
                            break;
                        case "java.lang.Float":
                            switch (className) {
                                case "java.lang.String":
                                    valorNuevo.setValueAsFloat((Float) valor.getValue());
                                    break;
                                default:
                                    String msg = "ERROR: Parameter: " + valor.getKey() + " Cannot be converted from " + className + " to Float."
                                            + metadataDelInput.toString();
                                    logger.error(msg);

                                    String errorMessage = msg;
                                    String claseDeLaOperacion = "";
                                    String metodoDeLaOperacion = "";
                                    int httpStatus = 510;
                                    String httpStatusReason = "";
                                    String request = "";
                                    String response = "";

                                    throw new InternalConnectorException(errorMessage, claseDeLaOperacion, metodoDeLaOperacion, httpStatus, httpStatusReason, request, response,
                                            null);

                            }
                            break;
                        case "java.util.Date":
                            switch (className) {
                                case "java.util.Date":
                                    Date date = (java.util.Date) valor.getValue();
                                    Instant instant = date.toInstant();
                                    long seconds = instant.getEpochSecond();
                                    int nanos = (int) instant.getEpochSecond();
                                    valorNuevo.setValueAsDate(Timestamp.newBuilder()
                                            .setSeconds(seconds)
                                            .setNanos(nanos)
                                            .build());
                                    break;
                                default:
                                    String msg = "ERROR: Parameter: " + valor.getKey() + " Cannot be converted from " + className + " to Date."
                                            + metadataDelInput.toString();
                                    logger.error(msg);

                                    String errorMessage = msg;
                                    String claseDeLaOperacion = "";
                                    String metodoDeLaOperacion = "";
                                    int httpStatus = 510;
                                    String httpStatusReason = "";
                                    String request = "";
                                    String response = "";

                                    throw new InternalConnectorException(errorMessage, claseDeLaOperacion, metodoDeLaOperacion, httpStatus, httpStatusReason, request, response,
                                            null);

                            }
                            break;
                        case "java.lang.Byte":
                            valorNuevo.setValuesAsByteString((ByteString) valor.getValue());
                            break;
                        case "java.lang.Double":
                        case "BDecimal":
                        case "java.math.BigDecimal":
                            switch (className) {
                                case "java.lang.String":
                                    valorNuevo.setValueAsDouble(Double.valueOf((String) valor.getValue())
                                            .doubleValue());
                                    break;
                                case "java.lang.Integer":
                                    valorNuevo.setValueAsDouble(((Integer) valor.getValue()).doubleValue());
                                    break;
                                case "java.lang.Double":
                                    valorNuevo.setValueAsDouble((Double) valor.getValue());
                                    break;
                                case "java.lang.Long":
                                    valorNuevo.setValueAsDouble(((Long) valor.getValue()).doubleValue());
                                    break;
                                case "java.lang.Float":
                                    valorNuevo.setValueAsDouble(((Float) valor.getValue()).doubleValue());
                                    break;
                                case "java.math.BigDecimal":
                                    valorNuevo.setValueAsDouble(((java.math.BigDecimal) valor.getValue()).doubleValue());
                                    break;
                                default:
                                    String msg = "ERROR: Parameter: " + valor.getKey() + " Cannot be converted from " + className + " to Double."
                                            + metadataDelInput.toString();
                                    logger.error(msg);

                                    String errorMessage = msg;
                                    String claseDeLaOperacion = "";
                                    String metodoDeLaOperacion = "";
                                    int httpStatus = 510;
                                    String httpStatusReason = "";
                                    String request = "";
                                    String response = "";

                                    throw new InternalConnectorException(errorMessage, claseDeLaOperacion, metodoDeLaOperacion, httpStatus, httpStatusReason, request, response,
                                            null);

                            }
                            break;
                        case "java.lang.Long":
                        case "BInteger":
                        case "java.math.BigInteger":
                            switch (className) {
                                case "java.lang.String":
                                    valorNuevo.setValueAsLong(Long.valueOf((String) valor.getValue())
                                            .longValue());
                                    break;
                                case "java.lang.Integer":
                                    valorNuevo.setValueAsLong(((Integer) valor.getValue()).longValue());
                                    break;
                                case "java.lang.Long":
                                    valorNuevo.setValueAsLong((Long) valor.getValue());
                                    break;
                                case "java.lang.Double":
                                    valorNuevo.setValueAsLong(Double.valueOf((Long) valor.getValue())
                                            .longValue());
                                    break;
                                case "java.lang.Float":
                                    valorNuevo.setValueAsLong(Float.valueOf((Long) valor.getValue())
                                            .longValue());
                                    break;
                                case "java.math.BigInteger":
                                    valorNuevo.setValueAsLong(((java.math.BigInteger) valor.getValue()).longValue());
                                    break;
                                default:
                                    String msg = "ERROR: Parameter: " + valor.getKey() + " Cannot be converted from " + className + " to Long."
                                            + metadataDelInput.toString();
                                    logger.error(msg);

                                    String errorMessage = msg;
                                    String claseDeLaOperacion = "";
                                    String metodoDeLaOperacion = "";
                                    int httpStatus = 510;
                                    String httpStatusReason = "";
                                    String request = "";
                                    String response = "";

                                    throw new InternalConnectorException(errorMessage, claseDeLaOperacion, metodoDeLaOperacion, httpStatus, httpStatusReason, request, response,
                                            null);

                            }
                            break;
                        default:

                            String msg = "ERROR: Parametro: " + valor.getKey() + " Cannot be converted from " + className + ". There is not a rule."
                                    + metadataDelInput.toString();
                            logger.error(msg);

                            String errorMessage = msg;
                            String claseDeLaOperacion = "";
                            String metodoDeLaOperacion = "";
                            int httpStatus = 510;
                            String httpStatusReason = "";
                            String request = "";
                            String response = "";

                            throw new InternalConnectorException(errorMessage, claseDeLaOperacion, metodoDeLaOperacion, httpStatus, httpStatusReason, request, response, null);

                    }

                    logger.info(levelLog + "---------------------------------------------------------------------------------");
                    logger.info(levelLog + "                                        New Primitive Value:\n " + valorNuevo.toString());
                    logger.info(levelLog + "=================================================================================");

                    EjecutarOperacionValores valorNuevoValido = valorNuevo.build();

                    valoresARetornar.add(valorNuevoValido);
                }

            } catch (java.lang.NullPointerException e)
            {
                String msg = "ERROR: Invalid Parameter:" + valor.getKey() + " Value: " + valor.getValue();

                logger.error(msg);

                String errorMessage = msg;
                String claseDeLaOperacion = "";
                String metodoDeLaOperacion = "";
                int httpStatus = 510;
                String httpStatusReason = "";
                String request = "";
                String response = "";

                throw new InternalConnectorException(errorMessage, claseDeLaOperacion, metodoDeLaOperacion, httpStatus, httpStatusReason, request, response, null);

            }

        }

        return valoresARetornar;

    }

    private EjecutarOperacionValores procesarObject(String parameterName, String tipoDelParametroJava, Object valor)
    {

        EjecutarOperacionValores.Builder valorNuevo = EjecutarOperacionValores.newBuilder();

        String className = valor.getClass()
                .getName();

        switch (tipoDelParametroJava) {

            case "java.lang.String":
                switch (className) {
                    case "java.lang.String":
                        valorNuevo.setValueAsString((String) valor);
                        break;
                    case "java.lang.Integer":
                        valorNuevo.setValueAsString(((Integer) valor).toString());
                        break;
                    case "java.lang.Double":
                        valorNuevo.setValueAsString(((Double) valor).toString());
                        break;
                    case "java.lang.Long":
                        valorNuevo.setValueAsString(((Long) valor).toString());
                        break;
                    case "java.lang.Float":
                        valorNuevo.setValueAsString(((Float) valor).toString());
                        break;
                    default:

                        String msg = "ERROR: Parameter: " + parameterName + " of " + className + " cannot be converted to " + parameterName;

                        logger.error(msg);

                        String errorMessage = msg;
                        String claseDeLaOperacion = "";
                        String metodoDeLaOperacion = "";
                        int httpStatus = 510;
                        String httpStatusReason = "";
                        String request = "";
                        String response = "";

                        throw new InternalConnectorException(errorMessage, claseDeLaOperacion, metodoDeLaOperacion, httpStatus, httpStatusReason, request, response,
                                null);

                }
                break;
            case "java.lang.Integer":
                switch (className) {
                    case "java.lang.String":
                        valorNuevo.setValueAsInteger(Integer.parseInt((String) valor));
                        break;
                    case "java.lang.Integer":
                        valorNuevo.setValueAsInteger((Integer) valor);
                        break;
                    default:
                        String msg = "ERROR: Parameter: " + parameterName + " of " + className + " cannot be converted to " + parameterName;
                        logger.error(msg);

                        String errorMessage = msg;
                        String claseDeLaOperacion = "";
                        String metodoDeLaOperacion = "";
                        int httpStatus = 510;
                        String httpStatusReason = "";
                        String request = "";
                        String response = "";

                        throw new InternalConnectorException(errorMessage, claseDeLaOperacion, metodoDeLaOperacion, httpStatus, httpStatusReason, request, response,
                                null);

                }
                break;
            case "java.lang.Boolean":
                switch (className) {
                    case "java.lang.Boolean":
                        valorNuevo.setValueAsBoolean((Boolean) valor);
                        break;
                    default:
                        String msg = "ERROR: Parameter: " + parameterName + " of " + className + " cannot be converted to " + parameterName;
                        logger.error(msg);

                        String errorMessage = msg;
                        String claseDeLaOperacion = "";
                        String metodoDeLaOperacion = "";
                        int httpStatus = 510;
                        String httpStatusReason = "";
                        String request = "";
                        String response = "";

                        throw new InternalConnectorException(errorMessage, claseDeLaOperacion, metodoDeLaOperacion, httpStatus, httpStatusReason, request, response,
                                null);

                }
                break;
            case "java.lang.Float":
                switch (className) {
                    case "java.lang.String":
                        valorNuevo.setValueAsFloat((Float) valor);
                        break;
                    default:
                        String msg = "ERROR: Parameter: " + parameterName + " of " + className + " cannot be converted to " + parameterName;
                        logger.error(msg);

                        String errorMessage = msg;
                        String claseDeLaOperacion = "";
                        String metodoDeLaOperacion = "";
                        int httpStatus = 510;
                        String httpStatusReason = "";
                        String request = "";
                        String response = "";

                        throw new InternalConnectorException(errorMessage, claseDeLaOperacion, metodoDeLaOperacion, httpStatus, httpStatusReason, request, response,
                                null);

                }
                break;
            case "java.util.Date":
                switch (className) {
                    case "java.util.Date":
                        Date date = (java.util.Date) valor;
                        Instant instant = date.toInstant();
                        long seconds = instant.getEpochSecond();
                        int nanos = (int) instant.getEpochSecond();
                        valorNuevo.setValueAsDate(Timestamp.newBuilder()
                                .setSeconds(seconds)
                                .setNanos(nanos)
                                .build());
                        break;
                    default:
                        String msg = "ERROR: Parameter: " + parameterName + " of " + className + " cannot be converted to " + parameterName;
                        logger.error(msg);

                        String errorMessage = msg;
                        String claseDeLaOperacion = "";
                        String metodoDeLaOperacion = "";
                        int httpStatus = 510;
                        String httpStatusReason = "";
                        String request = "";
                        String response = "";

                        throw new InternalConnectorException(errorMessage, claseDeLaOperacion, metodoDeLaOperacion, httpStatus, httpStatusReason, request, response,
                                null);

                }
                break;
            case "java.lang.Byte":
                valorNuevo.setValuesAsByteString((ByteString) valor);
                break;
            case "java.lang.Double":
            case "BDecimal":
            case "java.math.BigDecimal":
                switch (className) {
                    case "java.lang.String":
                        valorNuevo.setValueAsDouble(Double.valueOf((String) valor)
                                .doubleValue());
                        break;
                    case "java.lang.Integer":
                        valorNuevo.setValueAsDouble(((Integer) valor).doubleValue());
                        break;
                    case "java.lang.Double":
                        valorNuevo.setValueAsDouble((Double) valor);
                        break;
                    case "java.lang.Long":
                        valorNuevo.setValueAsDouble(((Long) valor).doubleValue());
                        break;
                    case "java.lang.Float":
                        valorNuevo.setValueAsDouble(((Float) valor).doubleValue());
                        break;
                    case "java.math.BigDecimal":
                        valorNuevo.setValueAsDouble(((java.math.BigDecimal) valor).doubleValue());
                    default:
                        String msg = "ERROR: Parameter: " + parameterName + " of " + className + " cannot be converted to " + parameterName;

                        logger.error(msg);

                        String errorMessage = msg;
                        String claseDeLaOperacion = "";
                        String metodoDeLaOperacion = "";
                        int httpStatus = 510;
                        String httpStatusReason = "";
                        String request = "";
                        String response = "";

                        throw new InternalConnectorException(errorMessage, claseDeLaOperacion, metodoDeLaOperacion, httpStatus, httpStatusReason, request, response,
                                null);

                }
                break;
            case "java.lang.Long":
            case "BInteger":
            case "java.math.BigInteger":
                switch (className) {
                    case "java.lang.String":
                        valorNuevo.setValueAsLong(Long.valueOf((String) valor)
                                .longValue());
                        break;
                    case "java.lang.Integer":
                        valorNuevo.setValueAsLong(((Integer) valor).longValue());
                        break;
                    case "java.lang.Long":
                        valorNuevo.setValueAsLong((Long) valor);
                        break;
                    case "java.lang.Double":
                        valorNuevo.setValueAsLong(Double.valueOf((Long) valor)
                                .longValue());
                        break;
                    case "java.lang.Float":
                        valorNuevo.setValueAsLong(Float.valueOf((Long) valor)
                                .longValue());
                        break;
                    case "java.math.BigInteger":
                        valorNuevo.setValueAsDouble(((java.math.BigInteger) valor).longValue());
                    default:
                        String msg = "ERROR: Parameter: " + parameterName + " of " + className + " cannot be converted to " + parameterName;
                        logger.error(msg);

                        String errorMessage = msg;
                        String claseDeLaOperacion = "";
                        String metodoDeLaOperacion = "";
                        int httpStatus = 510;
                        String httpStatusReason = "";
                        String request = "";
                        String response = "";

                        throw new InternalConnectorException(errorMessage, claseDeLaOperacion, metodoDeLaOperacion, httpStatus, httpStatusReason, request, response,
                                null);

                }
                break;
            default:

                String msg = "ERROR: Parameter: " + parameterName + " of " + className + " cannot be converted to " + parameterName;
                logger.error(msg);

                String errorMessage = msg;
                String claseDeLaOperacion = "";
                String metodoDeLaOperacion = "";
                int httpStatus = 510;
                String httpStatusReason = "";
                String request = "";
                String response = "";

                throw new InternalConnectorException(errorMessage, claseDeLaOperacion, metodoDeLaOperacion, httpStatus, httpStatusReason, request, response, null);

        }

        EjecutarOperacionValores valorNuevoValido = valorNuevo.build();

        return valorNuevoValido;

    }

    @SuppressWarnings("unchecked")
    private HashMap<String, Object> procesarRespuesta(EjecutarOperacionResponse ejecutarOperacionesResponse,
            HashMap<String, Object> response, int level) {

        String levelLog = StringUtils.repeat(" > ", level);
        logger.info(levelLog + " ------------------------------------------------------------------ ");
        logger.info(levelLog + " Processing: " + ejecutarOperacionesResponse.getNombreDelParametro());
        logger.info(levelLog + " Parameter Type: " + ejecutarOperacionesResponse.getTipoDelParametro());
        logger.info(levelLog + " Is Object: " + ejecutarOperacionesResponse.getIsObject());
        logger.info(levelLog + " Is Repeated: " + ejecutarOperacionesResponse.getRepeatedParameter());

        if (ejecutarOperacionesResponse.getRepeatedParameter()) {

            String nombreParametro = ejecutarOperacionesResponse.getNombreDelParametro();

            HashMap<String, Object> valores = new HashMap<String, Object>();

            ArrayList<Object> lista = new ArrayList<Object>();

            for (EjecutarOperacionResponse respuestaList : ejecutarOperacionesResponse.getListaDeValoresList()) {

                logger.info(levelLog + " Processing Values: " + respuestaList.getNombreDelParametro());
                logger.info(levelLog + " Parameter Type: " + respuestaList.getTipoDelParametro());
                logger.info(levelLog + " Is Object: " + respuestaList.getIsObject());
                logger.info(levelLog + " Is Repeated: " + respuestaList.getRepeatedParameter());

                if (respuestaList.getIsObject() || respuestaList.getRepeatedParameter())
                {
                    level++;
                    valores = procesarRespuesta(respuestaList, valores, level);
                    level--;

                    lista.add((HashMap<String, Object>) valores.get(respuestaList.getNombreDelParametro()));
                }
                else
                {

                    level++;
                    valores = procesarRespuesta(respuestaList, valores, level);
                    level--;

                    if (!valores.isEmpty())
                    {
                        lista.add(valores.values()
                                .iterator()
                                .next());
                    }

                }

            }

            response.put(nombreParametro, lista);

        } else if (ejecutarOperacionesResponse.getIsObject()) {

            String nombreParametro = ejecutarOperacionesResponse.getNombreDelParametro();

            HashMap<String, Object> valores = new HashMap<String, Object>();

            for (EjecutarOperacionResponse respuestaMap : ejecutarOperacionesResponse.getListaDeValoresList()) {

                logger.info(levelLog + " Processing Values: " + respuestaMap.getNombreDelParametro());
                logger.info(levelLog + " Parameter Type: " + respuestaMap.getTipoDelParametro());
                logger.info(levelLog + " Is Object: " + respuestaMap.getIsObject());
                logger.info(levelLog + " Is Repeated: " + respuestaMap.getRepeatedParameter());

                level++;
                valores = procesarRespuesta(respuestaMap, valores, level);
                level--;

            }

            response.put(nombreParametro, valores);

        } else {

            String nombreParametro = ejecutarOperacionesResponse.getNombreDelParametro();

            String tipoDelParametro = ejecutarOperacionesResponse.getTipoDelParametro();

            logger.info(levelLog + " Processing Value: " + ejecutarOperacionesResponse.getNombreDelParametro());
            logger.info(levelLog + " Parameter Type: " + ejecutarOperacionesResponse.getTipoDelParametro());
            logger.info(levelLog + " Is Object: " + ejecutarOperacionesResponse.getIsObject());
            logger.info(levelLog + " Is Repeated: " + ejecutarOperacionesResponse.getRepeatedParameter());

            Object value = null;

            switch (tipoDelParametro) {

                case "java.lang.String":
                    value = ejecutarOperacionesResponse.getValueAsString();
                    break;
                case "java.lang.Integer":
                    value = ejecutarOperacionesResponse.getValueAsInteger();
                    break;
                case "java.lang.Boolean":
                    value = ejecutarOperacionesResponse.getValueAsBoolean();
                    break;
                case "java.lang.Long":
                    value = ejecutarOperacionesResponse.getValueAsLong();
                    break;
                case "java.lang.Float":
                    value = ejecutarOperacionesResponse.getValueAsFloat();
                    break;
                case "java.util.Date":
                case "org.joda.time.LocalDate":
                    value = toLocalDate(ejecutarOperacionesResponse.getValueAsDate());
                    break;
                case "java.lang.Byte":
                    value = ejecutarOperacionesResponse.getValueAsStringBytes();
                    String bytesEncoded = ((ByteString) value).toStringUtf8();
                    value = Base64.getDecoder()
                            .decode(bytesEncoded);
                    break;
                case "java.lang.Double":
                    value = ejecutarOperacionesResponse.getValueAsDouble();
                    break;
                case "BDecimal":
                case "java.math.BigDecimal":
                    value = ejecutarOperacionesResponse.getValueAsDouble();
                    break;
                case "BInteger":
                case "java.math.BigInteger":
                    value = ejecutarOperacionesResponse.getValueAsLong();
                    break;
                case "java.util.LinkedHashMap":
                    value = toLocalDate(ejecutarOperacionesResponse.getValueAsDate());
                    break;
                case "java.util.Calendar":
                    value = toLocalDateFromCalendar(ejecutarOperacionesResponse.getValueAsDate());
                    break;
                case "NULL":
                    value = null;
                    break;
                default:
                    logger.info(levelLog + "     Parameter: " + nombreParametro + " Type:" + tipoDelParametro + "  Invalid");
                    throw new InternalConnectorException("     Parameter: " + nombreParametro + " Type:" + tipoDelParametro + "  Invalid");
            }

            logger.info(levelLog + "     Parametro: " + nombreParametro + " Tipo del Parametro:" + tipoDelParametro + " Valor: " + (value == null ? "NULO" : value.toString()));

            response.put(nombreParametro, value);

        }

        return response;

    }

    @SuppressWarnings("unchecked")
    private HashMap<String, ParametroInput> getMetadataForInputAsHashMap(String operation) throws InternalConnectorException, ExecutionException
    {
        logger.info("----------------------------------------------------------------");
        logger.info("Getting Metadata As Hash Map for Operation: " + operation);

        List<TipoDelParametroInput> metadataInput = (List<TipoDelParametroInput>) cacheMetadataInput.get(operation);

        HashMap<String, ParametroInput> returnValue = new HashMap<String, ParametroInput>();

        for (TipoDelParametroInput param : metadataInput)
        {
            logger.info("----------------------------------------------------------------");
            logger.info("             Parameter Name: " + param.getNombreDelParametro());
            logger.info("             Parameter Type: " + param.getTipoDelParametroJava());
            logger.info("             Parameter Is Repeated: " + param.getRepeatedParameter());
            logger.info("             Parameter Is Object: " + param.getIsObject());

            ParametroInput parametro = new ParametroInput();

            parametro.setParametro(param);

            if (param.getIsObject())
            {

                parametro.setListaSubParametros(getMetadataForSubParameter(param.getSubParametroList(), 1));

            }

            returnValue.put(param.getNombreDelParametro(), parametro);

        }

        logger.info("----------------------------------------------------------------");

        return returnValue;

    }

    private HashMap<String, ParametroInput> getMetadataForSubParameter(List<TipoDelParametroInput> subpar, int level)
    {
        String levelLog = StringUtils.repeat("  >  ", level);

        HashMap<String, ParametroInput> listaSubParametros = new HashMap<String, ParametroInput>();

        for (TipoDelParametroInput param : subpar)
        {
            logger.info(levelLog + "----------------------------------------------------------------");
            logger.info(levelLog + "             Parameter Name: " + param.getNombreDelParametro());
            logger.info(levelLog + "             Parameter Type: " + param.getTipoDelParametroJava());
            logger.info(levelLog + "             Parameter Is Repeated: " + param.getRepeatedParameter());
            logger.info(levelLog + "             Parameter Is Object: " + param.getIsObject());

            ParametroInput parametro = new ParametroInput();

            parametro.setParametro(param);

            if (param.getIsObject() &&
                    !param.getTipoDelParametroJava()
                            .equals("BDecimal") &&
                    !param.getTipoDelParametroJava()
                            .equals("BInteger"))
            {
                level++;
                parametro.setListaSubParametros(getMetadataForSubParameter(param.getSubParametroList(), level));
                level--;

            }

            listaSubParametros.put(param.getNombreDelParametro(), parametro);

        }

        return listaSubParametros;
    }

    private List<TipoDelParametroInput> getMetadataForInput(String operation) throws InternalConnectorException
    {
        List<TipoDelParametroInput> metadataList = new ArrayList<TipoDelParametroInput>();

        try {

            GetMetadataResponse metadataResponse = stub.getMetadaParaOperacion(
                    GetMetadataRequest.newBuilder()
                            .setConnectorName("WS")
                            .setUser(configuracion.getJdeUser())
                            .setPassword(configuracion.getJdePassword())
                            .setEnvironment(configuracion.getJdeEnvironment())
                            .setRole(configuracion.getJdeRole())
                            .setSessionId(configuracion.getSessionID())
                            .setJwtToken(configuracion.getToken())
                            .setWsconnection(configuracion.getWsConnection())
                            .setOperacionKey(operation)
                            .build());

            for (TipoDelParametroInput tipoDelParametroInput : metadataResponse.getListaDeParametrosInputList()) {

                metadataList.add(tipoDelParametroInput);

            }

        } catch (StatusRuntimeException e) {

            logger.error("JDE Atina Service + Error: " + e.getMessage());

            String errorMessage = e.getMessage();
            String claseDeLaOperacion = "";
            String metodoDeLaOperacion = "";
            int httpStatus = 510;
            String httpStatusReason = "";
            String request = "";
            String response = "";

            throw new InternalConnectorException(errorMessage, claseDeLaOperacion, metodoDeLaOperacion, httpStatus, httpStatusReason, request, response, e);

        }

        return metadataList;

    }

    private List<TipoDelParametroOutput> getMetadataForOutput(String operation) throws InternalConnectorException
    {
        List<TipoDelParametroOutput> metadataList = new ArrayList<TipoDelParametroOutput>();

        try {

            GetMetadataResponse metadataResponse = stub.getMetadaParaOperacion(
                    GetMetadataRequest.newBuilder()
                            .setConnectorName("WS")
                            .setUser(configuracion.getJdeUser())
                            .setPassword(configuracion.getJdePassword())
                            .setEnvironment(configuracion.getJdeEnvironment())
                            .setRole(configuracion.getJdeRole())
                            .setSessionId(configuracion.getSessionID())
                            .setJwtToken(configuracion.getToken())
                            .setWsconnection(configuracion.getWsConnection())
                            .setOperacionKey(operation)
                            .build());

            List<TipoDelParametroOutput> valoresMetadata = metadataResponse.getListaDeParametrosOutputList();

            metadataList.addAll(valoresMetadata);

        } catch (StatusRuntimeException e) {

            logger.error("JDE Atina Service + Error: " + e.getMessage());

            String errorMessage = e.getMessage();
            String claseDeLaOperacion = "";
            String metodoDeLaOperacion = "";
            int httpStatus = 510;
            String httpStatusReason = "";
            String request = "";
            String response = "";

            throw new InternalConnectorException(errorMessage, claseDeLaOperacion, metodoDeLaOperacion, httpStatus, httpStatusReason, request, response, e);

        }

        return metadataList;

    }

    private LocalDate toLocalDate(Timestamp timestamp) {

        return LocalDateTime.ofInstant(Instant.ofEpochSecond(timestamp.getSeconds(), timestamp.getNanos()), ZoneId.of("UTC"))
                .toLocalDate();
    }

    private LocalDate toLocalDateFromCalendar(Timestamp timestamp) {

        return LocalDateTime.ofInstant(Instant.ofEpochSecond(timestamp.getSeconds(), timestamp.getNanos()), ZoneId.of("UTC"))
                .toLocalDate();
    }

}
