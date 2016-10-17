package rest.resources;


import java.util.ArrayList;  
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;  
import java.util.Map;
import java.util.Set;

import javax.ws.rs.Path;  
import javax.ws.rs.Produces;  
  
import javax.ws.rs.core.MediaType;  
 
import javax.ws.rs.GET;  


import rest.GetStatus;

import rest.bean.status;  



import com.amazonaws.services.ec2.model.Instance;

@Path("/cluster")  
public class StateResources {  
	//map of status object and key is the id of instance;
    private static Map<String,status> statusMap = new HashMap<String,status>();
    @GET  
    @Produces(MediaType.APPLICATION_JSON)  
    public List<status> getInstanceStatus() throws Exception{       
        List<status> status = new ArrayList<status>();
        Set<Instance> instances = new HashSet<Instance>();
        instances = GetStatus.status();
        for(Instance instance:instances){
        	status.add(new status(instance.getInstanceId(),instance.getState().toString()));
        }
        
        for(status s:status){
        	statusMap.put(s.getId(), s);
        }
          
        status.addAll( statusMap.values() );  
        return status;  
    }    
}  
