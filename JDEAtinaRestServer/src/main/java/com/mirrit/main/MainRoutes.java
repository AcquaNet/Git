/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mirrit.main;

import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;
import io.quarkus.runtime.configuration.ProfileManager;
import io.quarkus.vertx.web.Route;
import io.quarkus.vertx.web.Route.HandlerType;
import io.quarkus.vertx.web.Route.HttpMethod;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext; 
import javax.enterprise.context.ApplicationScoped; 
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
    
    @Route(path = "/authenticate", methods = HttpMethod.GET, produces = "text/html", order = 1)
    String authenticate(RoutingContext ctx) {
    
        String activeProfile = ProfileManager.getActiveProfile();
          
        String alfa = Templates.index()
                .data("activeProfile", activeProfile)
                .data("ambiente",ambiente)
                .data("productName",productName.toUpperCase())
                .render(); 
          
        return alfa;
    }
    
     
    @Route(path = "/*",  type = HandlerType.FAILURE, produces = "application/json")
    void unsupported(UnsupportedOperationException e, HttpServerResponse response) {
      response.setStatusCode(501).end(e.getMessage());
    }
}
