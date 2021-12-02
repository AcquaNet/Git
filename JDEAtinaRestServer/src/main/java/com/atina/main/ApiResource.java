/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.atina.main;

import com.atina.cliente.connector.JDEAtinaConnector;
import com.atina.cliente.exception.ConnectionException;
import com.atina.exception.CustomException;
import com.atina.model.CreateTokenResponse;
import com.atina.model.LoginRequest;
import com.atina.model.LoginResponse;
import com.atina.model.LogoutRequest;
import com.atina.model.LogoutResponse;
import com.atina.model.OperationsResponse;
import com.atina.model.ParseTokenResponse; 
import com.atina.model.ConnectionsResponse; 
import com.atina.service.ConnectionPool;
import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;
import io.quarkus.runtime.configuration.ProfileManager; 
import java.util.HashMap;
import java.util.List;
import java.util.Map; 
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.info.License;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.jboss.resteasy.annotations.jaxrs.HeaderParam;

/**
 *
 * @author jgodi
 */
@OpenAPIDefinition(
    info = @Info(
        title="Atina JD Rest API",
        version = "1.0.0",
        contact = @Contact(
            name = "Example API Support",
            url = "http://atina.com/contact",
            email = "techsupport@atina.com"),
        license = @License(
            name = "Apache 2.0",
            url = "https://www.apache.org/licenses/LICENSE-2.0.html"))
)
@Path("/")
public class ApiResource {

    @ConfigProperty(name = "SERVIDOR_NAME")
    String servidorName;

    @ConfigProperty(name = "SERVIDOR_PORT")
    Integer servidorPort;

    @ConfigProperty(name = "AMBIENTE")
    String ambiente;

    @ConfigProperty(name = "PRODUCT_NAME")
    String productName;


    @CheckedTemplate(requireTypeSafeExpressions = false)
    public static class Templates {
        public static native TemplateInstance index();
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    public String getIndex() {

        String activeProfile = ProfileManager.getActiveProfile();

        String alfa = Templates.index()
                .data("activeProfile", activeProfile)
                .data("ambiente",ambiente)
                .data("productName",productName.toUpperCase())
                .render();

        return alfa;
    }

    @POST
    @Operation(summary = "API use to login into JDE.",
            description = "You can provide user credentials otherwise you can use Token instead.")
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Tag(name = "Credentials", description = "Credentials API's")
            @APIResponses(
            value = {
                    @APIResponse(
                            responseCode = "200",
                            description = "User has been logged",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(type = SchemaType.OBJECT, implementation = LoginResponse.class)))
            }
    )
    public Response login(@HeaderParam("Token") String token, @HeaderParam("ChannelId") String channelId, @HeaderParam("TransactionId") Long transactionId ,LoginRequest loginRequest) {

        int channelIdValue = 0;

        try
        {

            JDEAtinaConnector connector;

            if(channelId == null || channelId.isEmpty() || channelId.equals("0"))
            {

                channelIdValue = ConnectionPool.getInstance().getAvailableChannel();

                connector = ConnectionPool.getInstance().createConnectorChannel(
                                                    servidorName,
                                                    servidorPort,
                                                    channelIdValue);

            } else
            {
                channelIdValue = Integer.parseInt(channelId);

                connector = ConnectionPool.getInstance().getConnectorChannel(channelIdValue);

            }



            Map<String, Object> entityData = new HashMap<String, Object>();

            entityData.put("Transaction ID", transactionId);
            entityData.put("JDE Token", token==null?"":token);
            entityData.put("JDE User", loginRequest.getUser());
            entityData.put("JDE Password", loginRequest.getPassword());
            entityData.put("JDE Environment", loginRequest.getEnvironment());
            entityData.put("JDE Role", loginRequest.getRole());
            entityData.put("Session Id", Integer.toString(0));

            Map<String,Object> auth = (Map<String,Object>) connector.authenticate("FromUserData", entityData);
            
            transactionId = (Long) auth.get("Transaction ID");

            LoginResponse response = new LoginResponse((String)  auth.get("userAddressBookNo"));

            return Response.ok(response).header("Token", (String)  auth.get("token"))
                                        .header("ChannelId",  Integer.toString(channelIdValue))
                                        .header("SessionId",  ((Long) auth.get("sessionId")).intValue())
                                        .header("TransactionId", transactionId )
                                        .build();

        } catch (ConnectionException | NumberFormatException ex)
        {
            throw new CustomException(ex.getMessage(), ex,Integer.toString(channelIdValue),transactionId);

        }  catch (Exception  ex)
        {
            throw new CustomException(ex.getMessage(), ex,Integer.toString(channelIdValue),transactionId);

        }

    }

    @POST
    @Operation(summary = "API use to logout from JDE.",
            description = "You need to provide Token. This process will disconnect all JDE connections too.")
    @Path("/logout")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Tag(name = "Credentials", description = "Credentials API's")
            @APIResponses(
            value = {
                    @APIResponse(
                            responseCode = "200",
                            description = "User has been logout",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(type = SchemaType.OBJECT, implementation = LogoutResponse.class)))
            }
    )
    public Response logout(@HeaderParam("Token") String token,  @HeaderParam("ChannelId") String channelId , @HeaderParam("TransactionId") Long transactionId, LogoutRequest logoutRequest) {

        int channelIdValue = 0;

        try {

            JDEAtinaConnector connector;

            if(channelId == null || channelId.isEmpty() || channelId.equals("0"))
            {
                channelIdValue = ConnectionPool.getInstance().getAvailableChannel();

                connector = ConnectionPool.getInstance().createConnectorChannel(
                                                    servidorName,
                                                    servidorPort,
                                                    channelIdValue);

            } else
            {
                channelIdValue = Integer.parseInt(channelId);

                connector = ConnectionPool.getInstance().getConnectorChannel(channelIdValue);

            }

            Map<String, Object> entityData = new HashMap<String, Object>();

            entityData.put("Transaction ID", transactionId);
            entityData.put("JDE Token", token);

            Map<String, Object> auth = (Map<String, Object>) connector.authenticate("LogoutTokenData", entityData);

            ConnectionPool.getInstance().removeConnectorChannel(channelIdValue);
            
            transactionId = (Long) auth.get("Transaction ID");

            LogoutResponse response = new LogoutResponse();

            return Response.ok().header("Token", auth.get("token"))
                                        .header("ChannelId",  "")
                                        .header("SessionId",  0L)
                                        .header("TransactionId", transactionId )
                                        .build();

        } catch (ConnectionException | NumberFormatException ex) {

            throw new CustomException(ex.getMessage(), ex, Integer.toString(channelIdValue),0L);

        } catch (Exception ex) {

            throw new CustomException(ex.getMessage(), ex, Integer.toString(channelIdValue),0L);

        }
    }

    @POST
    @Operation(summary = "API use to create a Token.",
            description = "The token can be used instead of user credentials due exposing passwords to developers.")
    @Path("/token/create")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Tag(name = "Credentials", description = "Credentials API's")
            @APIResponses(
            value = {
                    @APIResponse(
                            responseCode = "200",
                            description = "Token has been created",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(type = SchemaType.OBJECT, implementation = CreateTokenResponse.class)))
            }
    )
    public Response createToken(@HeaderParam("Token") String token,  @HeaderParam("ChannelId") String channelId, @HeaderParam("TransactionId") Long transactionId , com.atina.model.CreateTokenRequest createTokenRequest) {

        int channelIdValue = 0;

        try {

            JDEAtinaConnector connector;

            if(channelId == null || channelId.isEmpty() || channelId.equals("0"))
            {
                channelIdValue = ConnectionPool.getInstance().getAvailableChannel();

                connector = ConnectionPool.getInstance().createConnectorChannel(
                                                    servidorName,
                                                    servidorPort,
                                                    channelIdValue);

            } else
            {
                channelIdValue = Integer.parseInt(channelId);

                connector = ConnectionPool.getInstance().getConnectorChannel(channelIdValue);

            }

            Map<String, Object> entityData = new HashMap<String, Object>();

            entityData.put("Transaction ID", transactionId);
            entityData.put("JDE User", createTokenRequest.getUser());
            entityData.put("JDE Password", createTokenRequest.getPassword());
            entityData.put("JDE Environment", createTokenRequest.getEnvironment());
            entityData.put("JDE Role", createTokenRequest.getRole());
            entityData.put("JDE Token", token);

            
            Map<String, Object> responseVal = (Map<String, Object>) connector.processAtinaToken("CreateToken", entityData);

            CreateTokenResponse response = new CreateTokenResponse();
             
            transactionId = (Long) responseVal.get("Transaction ID");

            return Response.ok().header("Token", responseVal.get("token"))
                                        .header("ChannelId",  Integer.toString(channelIdValue))
                                        .header("SessionId",  0L)
                                        .header("TransactionId", transactionId )
                                        .build();

        } catch (ConnectionException | NumberFormatException ex) {

            throw new CustomException(ex.getMessage(), ex, Integer.toString(channelIdValue),0L);

        } catch (Exception ex) {

            throw new CustomException(ex.getMessage(), ex, Integer.toString(channelIdValue),0L);

        }
    }
    
    @POST
    @Operation(summary = "API use to view a Token values.",
            description = "This API is used to check tokens. User, Environment, Roles, Sessions, and token expiration.")
    @Path("/token/parse")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Tag(name = "Credentials", description = "Credentials API's")
            @APIResponses(
            value = {
                    @APIResponse(
                            responseCode = "200",
                            description = "Token has been created",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(type = SchemaType.OBJECT, implementation = ParseTokenResponse.class)))
            }
    )
    public Response parseToken(@HeaderParam("Token") String token,  @HeaderParam("ChannelId") String channelId, @HeaderParam("TransactionId") Long transactionId , com.atina.model.ParseTokenRequest parseTokenRequest) {

        int channelIdValue = 0;

        try {

            JDEAtinaConnector connector;

            if(channelId == null || channelId.isEmpty() || channelId.equals("0"))
            {
                channelIdValue = ConnectionPool.getInstance().getAvailableChannel();

                connector = ConnectionPool.getInstance().createConnectorChannel(
                                                    servidorName,
                                                    servidorPort,
                                                    channelIdValue);

            } else
            {
                channelIdValue = Integer.parseInt(channelId);

                connector = ConnectionPool.getInstance().getConnectorChannel(channelIdValue);

            }

            Map<String, Object> entityData = new HashMap<String, Object>();

            entityData.put("Transaction ID", transactionId);
            entityData.put("JDE Token", token);
             
            Map<String, Object> responseVal = (Map<String, Object>) connector.processAtinaToken("ParseToken", entityData);
            
            transactionId = (Long) responseVal.get("Transaction ID");

            ParseTokenResponse response = new ParseTokenResponse((String)responseVal.get("JDE User"),
                                                                "",
                                                                (String) responseVal.get("JDE Environment"),
                                                                (String) responseVal.get("JDE Role"),
                                                                (String) responseVal.get("Token Expiration"));

            return Response.ok(response).header("Token", responseVal.get("token"))
                                        .header("ChannelId",  Integer.toString(channelIdValue))
                                        .header("SessionId",  responseVal.get("sessionId"))
                                        .header("TransactionId", transactionId )
                                        .build();

        } catch (ConnectionException | NumberFormatException ex) {

            throw new CustomException(ex.getMessage(), ex, Integer.toString(channelIdValue),0L);

        } catch (Exception ex) {

            throw new CustomException(ex.getMessage(), ex, Integer.toString(channelIdValue),0L);

        }
    }
    
    @GET
    @Operation(summary = "List all Web Services exposed.",
            description = "Show all web services that you can invoke.")
    @Path("/metadata/operations")
    @Produces(MediaType.APPLICATION_JSON)
    @Tag(name = "Metadata", description = "Process Metadata")
            @APIResponses(
            value = {
                    @APIResponse(
                            responseCode = "200",
                            description = "Operations has been retrieved",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(type = SchemaType.OBJECT, implementation = OperationsResponse.class)))
            }
    )
    public Response getOperations(@HeaderParam("Token") String token,  @HeaderParam("ChannelId") String channelId, @HeaderParam("TransactionId") Long transactionId) {

        return getOperationsWithFilter(token,channelId,transactionId,"");
    }
    
    @GET
    @Operation(summary = "List all Web Services exposed using filter.",
            description = "Show all web services that you can invoke filtering by name.")
    @Path("/metadata/operations/{filter}")
    @Produces(MediaType.APPLICATION_JSON)
    @Tag(name = "Metadata", description = "Process Metadata")
            @APIResponses(
            value = {
                    @APIResponse(
                            responseCode = "200",
                            description = "Operations has been retrieved",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(type = SchemaType.OBJECT, implementation = OperationsResponse.class)))
            }
    )
    public Response getOperationsWithFilter(@HeaderParam("Token") String token,  @HeaderParam("ChannelId") String channelId, @HeaderParam("TransactionId") Long transactionId, @PathParam("filter") String filter) {

        int channelIdValue = 0;

        try {

            JDEAtinaConnector connector;

            if(channelId == null || channelId.isEmpty() || channelId.equals("0"))
            {
                channelIdValue = ConnectionPool.getInstance().getAvailableChannel();

                connector = ConnectionPool.getInstance().createConnectorChannel(
                                                    servidorName,
                                                    servidorPort,
                                                    channelIdValue);

            } else
            {
                channelIdValue = Integer.parseInt(channelId);

                connector = ConnectionPool.getInstance().getConnectorChannel(channelIdValue);

            }

            Map<String, Object> entityData = new HashMap<String, Object>();

            entityData.put("Transaction ID", transactionId);
            entityData.put("JDE Token", token);
            entityData.put("Filter", filter);
             
            Map<String, Object> responseVal = (Map<String, Object>) connector.metadata("Operations", entityData);
            
            transactionId = (Long) responseVal.get("Transaction ID");
             
            return Response.ok(((Map)responseVal.get("Operations")).values()).header("Token", responseVal.get("token"))
                                        .header("ChannelId",  Integer.toString(channelIdValue))
                                        .header("SessionId",  responseVal.get("sessionId"))
                                        .header("TransactionId", transactionId )
                                        .build();

        } catch (ConnectionException | NumberFormatException ex) {

            throw new CustomException(ex.getMessage(), ex, Integer.toString(channelIdValue),0L);

        } catch (Exception ex) {

            throw new CustomException(ex.getMessage(), ex, Integer.toString(channelIdValue),0L);

        }
    }
    
    @GET
    @Operation(summary = "Show input parameters for a Web Service.",
            description = "Show input parameters for a Web Service expecified.")
    @Path("/metadata/parameters-input/{operationName}")
    @Produces(MediaType.APPLICATION_JSON)
    @Tag(name = "Metadata", description = "Process Metadata")
            @APIResponses(
            value = {
                    @APIResponse(
                            responseCode = "200",
                            description = "Parameters has been retrieved",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(type = SchemaType.OBJECT, implementation = OperationsResponse.class)))
            }
    )
    public Response getOperationMetadataInput(@HeaderParam("Token") String token,  @HeaderParam("ChannelId") String channelId, @HeaderParam("TransactionId") Long transactionId, @PathParam("operationName") String operation) {

        int channelIdValue = 0;

        try {

            JDEAtinaConnector connector;

            if(channelId == null || channelId.isEmpty() || channelId.equals("0"))
            {
                channelIdValue = ConnectionPool.getInstance().getAvailableChannel();

                connector = ConnectionPool.getInstance().createConnectorChannel(
                                                    servidorName,
                                                    servidorPort,
                                                    channelIdValue);

            } else
            {
                channelIdValue = Integer.parseInt(channelId);

                connector = ConnectionPool.getInstance().getConnectorChannel(channelIdValue);

            }

            Map<String, Object> entityData = new HashMap<String, Object>();

            entityData.put("Transaction ID", transactionId);
            entityData.put("JDE Token", token);
            entityData.put("Operation", operation);
             
            Map<String, Object> responseVal = (Map<String, Object>) connector.metadata("Input", entityData);
            
            transactionId = (Long) responseVal.get("Transaction ID");
             
            return Response.ok(((List)responseVal.get("Parameters"))).header("Token", responseVal.get("token"))
                                        .header("ChannelId",  Integer.toString(channelIdValue))
                                        .header("SessionId",  responseVal.get("sessionId"))
                                        .header("TransactionId", transactionId )
                                        .build();

        } catch (ConnectionException | NumberFormatException ex) {

            throw new CustomException(ex.getMessage(), ex, Integer.toString(channelIdValue),0L);

        } catch (Exception ex) {

            throw new CustomException(ex.getMessage(), ex, Integer.toString(channelIdValue),0L);

        }
    }
    
    @GET
    @Operation(summary = "Show output parameters for a Web Service.",
            description = "Show output parameters for a Web Service expecified.")
    @Path("/metadata/parameters-output/{operationName}")
    @Produces(MediaType.APPLICATION_JSON)
    @Tag(name = "Metadata", description = "Process Metadata")
            @APIResponses(
            value = {
                    @APIResponse(
                            responseCode = "200",
                            description = "Parameters has been retrieved",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(type = SchemaType.OBJECT, implementation = OperationsResponse.class)))
            }
    )
    public Response getOperationMetadataOutput(@HeaderParam("Token") String token,  @HeaderParam("ChannelId") String channelId, @HeaderParam("TransactionId") Long transactionId, @PathParam("operationName") String operation) {

        int channelIdValue = 0;

        try {

            JDEAtinaConnector connector;

            if(channelId == null || channelId.isEmpty() || channelId.equals("0"))
            {
                channelIdValue = ConnectionPool.getInstance().getAvailableChannel();

                connector = ConnectionPool.getInstance().createConnectorChannel(
                                                    servidorName,
                                                    servidorPort,
                                                    channelIdValue);

            } else
            {
                channelIdValue = Integer.parseInt(channelId);

                connector = ConnectionPool.getInstance().getConnectorChannel(channelIdValue);

            }

            Map<String, Object> entityData = new HashMap<String, Object>();

            entityData.put("Transaction ID", transactionId);
            entityData.put("JDE Token", token);
            entityData.put("Operation", operation);
             
            Map<String, Object> responseVal = (Map<String, Object>) connector.metadata("Output", entityData);
            
            transactionId = (Long) responseVal.get("Transaction ID");
             
            return Response.ok(((List)responseVal.get("Parameters"))).header("Token", responseVal.get("token"))
                                        .header("ChannelId",  Integer.toString(channelIdValue))
                                        .header("SessionId",  responseVal.get("sessionId"))
                                        .header("TransactionId", transactionId )
                                        .build();

        } catch (ConnectionException | NumberFormatException ex) {

            throw new CustomException(ex.getMessage(), ex, Integer.toString(channelIdValue),0L);

        } catch (Exception ex) {

            throw new CustomException(ex.getMessage(), ex, Integer.toString(channelIdValue),0L);

        }
    }
    
    @GET
    @Operation(summary = "Create input payload for a Web Service.",
            description = "Create input payload for a Web Service that you can use to invoke a WS.")
    @Path("/metadata/payload-input/{operationName}")
    @Produces(MediaType.APPLICATION_JSON)
    @Tag(name = "Metadata", description = "Process Metadata")
            @APIResponses(
            value = {
                    @APIResponse(
                            responseCode = "200",
                            description = "Payload has been retrieved",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(type = SchemaType.OBJECT, implementation = OperationsResponse.class)))
            }
    )
    public Response getInputPayloadForOperation(@HeaderParam("Token") String token,  @HeaderParam("ChannelId") String channelId, @HeaderParam("TransactionId") Long transactionId, @PathParam("operationName") String operation) {

        int channelIdValue = 0;

        try {

            JDEAtinaConnector connector;

            if(channelId == null || channelId.isEmpty() || channelId.equals("0"))
            {
                channelIdValue = ConnectionPool.getInstance().getAvailableChannel();

                connector = ConnectionPool.getInstance().createConnectorChannel(
                                                    servidorName,
                                                    servidorPort,
                                                    channelIdValue);

            } else
            {
                channelIdValue = Integer.parseInt(channelId);

                connector = ConnectionPool.getInstance().getConnectorChannel(channelIdValue);

            }

            Map<String, Object> entityData = new HashMap<String, Object>();

            entityData.put("Transaction ID", transactionId);
            entityData.put("JDE Token", token);
            entityData.put("Operation", operation);
             
            Map<String, Object> responseVal = (Map<String, Object>) connector.metadata("Payload", entityData);
            
            transactionId = (Long) responseVal.get("Transaction ID");
             
            return Response.ok((((Map)responseVal.get("Parameters"))).get("JSON Schema Input")).header("Token", responseVal.get("token"))
                                        .header("ChannelId",  Integer.toString(channelIdValue))
                                        .header("SessionId",  responseVal.get("sessionId"))
                                        .header("TransactionId", transactionId )
                                        .build();

        } catch (ConnectionException | NumberFormatException ex) {

            throw new CustomException(ex.getMessage(), ex, Integer.toString(channelIdValue),0L);

        } catch (Exception ex) {

            throw new CustomException(ex.getMessage(), ex, Integer.toString(channelIdValue),0L);

        }
    }
    
    @GET
    @Operation(summary = "Create output payload for a Web Service.",
            description = "Show output payload for a Web Service.")
    @Path("/metadata/payload-output/{operationName}")
    @Produces(MediaType.APPLICATION_JSON)
    @Tag(name = "Metadata", description = "Process Metadata")
            @APIResponses(
            value = {
                    @APIResponse(
                            responseCode = "200",
                            description = "Payload has been retrieved",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(type = SchemaType.OBJECT, implementation = OperationsResponse.class)))
            }
    )
    public Response getOutputPayloadForOperation(@HeaderParam("Token") String token,  @HeaderParam("ChannelId") String channelId, @HeaderParam("TransactionId") Long transactionId, @PathParam("operationName") String operation) {

        int channelIdValue = 0;

        try {

            JDEAtinaConnector connector;

            if(channelId == null || channelId.isEmpty() || channelId.equals("0"))
            {
                channelIdValue = ConnectionPool.getInstance().getAvailableChannel();

                connector = ConnectionPool.getInstance().createConnectorChannel(
                                                    servidorName,
                                                    servidorPort,
                                                    channelIdValue);

            } else
            {
                channelIdValue = Integer.parseInt(channelId);

                connector = ConnectionPool.getInstance().getConnectorChannel(channelIdValue);

            }

            Map<String, Object> entityData = new HashMap<String, Object>();

            entityData.put("Transaction ID", transactionId);
            entityData.put("JDE Token", token);
            entityData.put("Operation", operation);
             
            Map<String, Object> responseVal = (Map<String, Object>) connector.metadata("Payload", entityData);
            
            transactionId = (Long) responseVal.get("Transaction ID");
             
            return Response.ok((((Map)responseVal.get("Parameters"))).get("JSON Schema Output")).header("Token", responseVal.get("token"))
                                        .header("ChannelId",  Integer.toString(channelIdValue))
                                        .header("SessionId",  responseVal.get("sessionId"))
                                        .header("TransactionId", transactionId )
                                        .build();

        } catch (ConnectionException | NumberFormatException ex) {

            throw new CustomException(ex.getMessage(), ex, Integer.toString(channelIdValue),0L);

        } catch (Exception ex) {

            throw new CustomException(ex.getMessage(), ex, Integer.toString(channelIdValue),0L);

        }
    }
    
    @GET
    @Operation(summary = "Get connectios opened",
            description = "Show all user connected to microservices")
    @Path("/monitor/connections")
    @Produces(MediaType.APPLICATION_JSON)
    @Tag(name = "Monitor", description = "Monitor Microservice")
            @APIResponses(
            value = {
                    @APIResponse(
                            responseCode = "200",
                            description = "Payload has been retrieved",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(type = SchemaType.OBJECT, implementation = ConnectionsResponse.class)))
            }
    )
    public Response getConnectios(@HeaderParam("ChannelId") String channelId, @HeaderParam("TransactionId") Long transactionId) {

        int channelIdValue = 0;

        try {

            JDEAtinaConnector connector;

            if(channelId == null || channelId.isEmpty() || channelId.equals("0"))
            {
                channelIdValue = ConnectionPool.getInstance().getAvailableChannel();

                connector = ConnectionPool.getInstance().createConnectorChannel(
                                                    servidorName,
                                                    servidorPort,
                                                    channelIdValue);

            } else
            {
                channelIdValue = Integer.parseInt(channelId);

                connector = ConnectionPool.getInstance().getConnectorChannel(channelIdValue);

            }

            Map<String, Object> entityData = new HashMap<String, Object>();

            entityData.put("Transaction ID", transactionId);
   
            Map<String, Object> responseVal = (Map<String, Object>) connector.monitor("Connections", entityData);
            
            transactionId = (Long) responseVal.get("Transaction ID");
             
            return Response.ok((((List)responseVal.get("Connections"))))
                                        .header("ChannelId",  Integer.toString(channelIdValue))
                                        .header("TransactionId", transactionId )
                                        .build();

        } catch (ConnectionException | NumberFormatException ex) {

            throw new CustomException(ex.getMessage(), ex, Integer.toString(channelIdValue),0L);

        } catch (Exception ex) {

            throw new CustomException(ex.getMessage(), ex, Integer.toString(channelIdValue),0L);

        }
    }

}
