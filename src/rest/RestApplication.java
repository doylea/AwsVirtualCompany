package rest;

import org.codehaus.jackson.jaxrs.JacksonJsonProvider;  
import org.glassfish.jersey.filter.LoggingFilter;  
import org.glassfish.jersey.server.ResourceConfig;  
   

public class RestApplication extends ResourceConfig {  
    public RestApplication() {  
   
     //converter service 
     packages("rest.resources");  
     //register JSON converter.
     register(JacksonJsonProvider.class);  
   
    }  
}  
