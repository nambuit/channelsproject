/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.t24;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import org.apache.log4j.Level;

/**
 * REST Web Service
 *
 * @author Administrator
 */
@Path("OFSAPI")
public class OFSAPIResource {

    @Context
    private UriInfo context;
       AppParams options;  
     T24Link t24;       
     String logfilename = "OFSAPI";
       
     
    
    

    public OFSAPIResource()
{
    try
    {
 
        options = new AppParams();
        
        
        
        t24 = "TAFJ".equals(options.getT24Framework().trim().toUpperCase())? new T24TAFJLink():
              new T24TAFCLink(options.getHost(), options.getPort(), options.getOFSsource()); 
        
    }
    catch (Exception e)
    {   
        options.getServiceLogger(logfilename).LogError(e.getMessage(), e, Level.FATAL);
    }
}

    
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String ProcessOfs(@Context UriInfo uriInfo) {
        
       try{
          
           String ofs = uriInfo.getRequestUri().getQuery();
                   
           return  t24.PostMsg(ofs);
       }
       catch(Exception d){
           return d.getMessage();
       }
    }


}
