/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.atina.main;

import com.atina.cliente.connector.JDEAtinaConnector;
import com.atina.cliente.exceptions.ConnectionException;
import com.atina.cliente.exceptions.InternalConnectorException;
import com.atina.model.ChannelKey;
import com.atina.model.LoginRequest;
import com.atina.model.LoginResponse;
import com.atina.model.LogoutRequest;
import com.atina.model.LogoutResponse;
import com.atina.service.ConnectionPool;
import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;
import io.quarkus.runtime.configuration.ProfileManager;
import io.quarkus.vertx.web.Body;
import io.quarkus.vertx.web.Header;
import io.quarkus.vertx.web.Route;
import io.quarkus.vertx.web.Route.HandlerType;
import io.quarkus.vertx.web.Route.HttpMethod;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext; 
import java.util.HashMap;
import java.util.Map;
import javax.enterprise.context.ApplicationScoped; 
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import org.eclipse.microprofile.config.inject.ConfigProperty; 
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.info.License;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

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
@ApplicationScoped
public class MainRoutes {
        
    private static final org.jboss.logging.Logger LOG = org.jboss.logging.Logger.getLogger(MainRoutes.class); 
 
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
      
    @Route(path = "/", methods = HttpMethod.GET, produces = "text/html", order = 1)
    String getIndex(RoutingContext ctx) {
    
        String activeProfile = ProfileManager.getActiveProfile();
          
        String alfa = Templates.index()
                .data("activeProfile", activeProfile)
                .data("ambiente",ambiente)
                .data("productName",productName.toUpperCase())
                .render(); 
          
        return alfa;
    }
    
    @Route(path = "/login", methods = HttpMethod.POST, order = 1)
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Tag(name = "Login", description = "Authenticate in JDE")
    LoginResponse login(@Header("Token") String token, @Body LoginRequest login) {
     
        ChannelKey channelKey = new ChannelKey(login.getUser(),login.getPassword(), login.getEnvironment(),login.getRole());
        
        JDEAtinaConnector connector = ConnectionPool.getInstance().getConnectorChannel(
                                                servidorName, 
                                                servidorPort, 
                                                channelKey);
        
        Map<String, Object> entityData = new HashMap<String, Object>();

        entityData.put("Transaction ID", login.getTransactionId());
        entityData.put("JDE Token", token==null?"":token);
        entityData.put("JDE User", login.getUser());
        entityData.put("JDE Password", login.getPassword());
        entityData.put("JDE Environment", login.getEnvironment());
        entityData.put("JDE Role", login.getRole());
        entityData.put("Session Id", Integer.toString(0));
          
        Map<String,Object> auth = (Map<String,Object>) connector.authenticate("FromUserData", entityData);
            
            return new LoginResponse((String)  auth.get("token"),(String)  auth.get("userAddressBookNo"), ((Long) auth.get("sessionId")).intValue());
            
//        try
//        {
//            
//            Map<String,Object> auth = (Map<String,Object>) connector.authenticate("FromUserData", entityData);
//            
//            return new LoginResponse((String)  auth.get("token"),(String)  auth.get("userAddressBookNo"), ((Long) auth.get("sessionId")).intValue());
//            
//        } catch (ConnectionException | InternalConnectorException ex)
//        {
//            new WebApplicationException(ex.getMessage(),ex,401);
//            
//        } catch (Exception ex)
//        {
//            new WebApplicationException(ex.getMessage(),ex,500);
//        }
//        
//        return null;
    }
    
    @Route(path = "/logout", methods = HttpMethod.POST, order = 1)
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Tag(name = "Logout", description = "Authenticate in JDE")
    LogoutResponse logout(@Header("Token") String token, @Header("SessionId") String sessionId, @Body LogoutRequest login) {
    
         
        JDEAtinaConnector connector = ConnectionPool.getInstance().getConnectorChannel( 
                                                Integer.parseInt(sessionId));
        
        Map<String, Object> entityData = new HashMap<String, Object>();

        entityData.put("Transaction ID", login.getTransactionId());
        entityData.put("JDE Token", token); 
                
        Map<String,Object> auth = (Map<String,Object>) connector.authenticate("LogoutTokenData", entityData);
        
        ConnectionPool.getInstance().removeConnectorChannel( 
                                                Integer.parseInt(sessionId));
         
        return new LogoutResponse((String) auth.get("token"),0);
    }
    
     
    @Route(path = "/*",  type = HandlerType.FAILURE, produces = "application/json")
    void unsupported(UnsupportedOperationException e, HttpServerResponse response) {
      response.setStatusCode(501).end(e.getMessage());
    }
}
 