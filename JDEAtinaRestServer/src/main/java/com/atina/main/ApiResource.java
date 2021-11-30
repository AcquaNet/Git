/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.atina.main;
 
import com.atina.cliente.connector.JDEAtinaConnector;
import com.atina.cliente.exception.ConnectionException;
import com.atina.exception.CustomException;
import com.atina.model.ChannelKey;
import com.atina.model.LoginRequest;
import com.atina.model.LoginResponse;
import com.atina.model.LogoutResponse;
import com.atina.service.ConnectionPool;
import com.jde.jdeserverwp.servicios.LogoutRequest;
import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;
import io.quarkus.runtime.configuration.ProfileManager; 
import java.util.HashMap;
import java.util.Map;
import javax.ws.rs.Consumes; 
import javax.ws.rs.GET;
import javax.ws.rs.NotAcceptableException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
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
import org.jboss.resteasy.annotations.jaxrs.HeaderParam;
import org.jboss.resteasy.reactive.RestHeader;
  
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
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Tag(name = "Login", description = "Authenticate in JDE")
            @APIResponses(
            value = {
                    @APIResponse(
                            responseCode = "200",
                            description = "User has been logged",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(type = SchemaType.OBJECT, implementation = LoginResponse.class)))
            }
    ) 
    public Response login(@HeaderParam("Token") String token ,LoginRequest loginRequest) {
     
        try
        {
        
            ChannelKey channelKey = new ChannelKey(loginRequest.getUser(),loginRequest.getPassword(), loginRequest.getEnvironment(),loginRequest.getRole());
            
            JDEAtinaConnector connector = ConnectionPool.getInstance().getConnectorChannel(
                                                    servidorName, 
                                                    servidorPort, 
                                                    channelKey);

            Map<String, Object> entityData = new HashMap<String, Object>();

            entityData.put("Transaction ID", loginRequest.getTransactionId());
            entityData.put("JDE Token", token==null?"":token);
            entityData.put("JDE User", loginRequest.getUser());
            entityData.put("JDE Password", loginRequest.getPassword());
            entityData.put("JDE Environment", loginRequest.getEnvironment());
            entityData.put("JDE Role", loginRequest.getRole());
            entityData.put("Session Id", Integer.toString(0));
           
            Map<String,Object> auth = (Map<String,Object>) connector.authenticate("FromUserData", entityData);
            
            LoginResponse response = new LoginResponse((String)  auth.get("token"),(String)  auth.get("userAddressBookNo"), ((Long) auth.get("sessionId")).intValue());
            
            return Response.ok(response).build();
            
        } catch (Exception ex)
        {
            throw new CustomException(ex.getMessage(), ex); 
            
        }  
        
    }
     
    @POST
    @Path("/logout")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Tag(name = "Logout", description = "Disconnect From JDE")
            @APIResponses(
            value = {
                    @APIResponse(
                            responseCode = "200",
                            description = "User has been logout",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(type = SchemaType.OBJECT, implementation = LogoutResponse.class)))
            }
    )
    public Response logout(@HeaderParam("Token") String token, LogoutRequest logoutRequest) {
        
        String sessionId = "0";
        
        JDEAtinaConnector connector = ConnectionPool.getInstance().getConnectorChannel( 
                                                Integer.parseInt(sessionId));
        
        Map<String, Object> entityData = new HashMap<String, Object>();

        entityData.put("Transaction ID", logoutRequest.getTransactionID());
        entityData.put("JDE Token", token); 
                
        Map<String,Object> auth = (Map<String,Object>) connector.authenticate("LogoutTokenData", entityData);
        
        ConnectionPool.getInstance().removeConnectorChannel( 
                                                Integer.parseInt(sessionId));
        
        LogoutResponse response = new LogoutResponse("", 0);

        return Response.ok(response).build();
        
    }
         
}
