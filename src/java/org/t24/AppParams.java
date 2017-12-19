/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.t24;

import javax.naming.InitialContext;
import logger.WebServiceLogger;
import lombok.Getter;
import lombok.Setter;


/**
 *
 * @author Temitope
 */
@Getter @Setter
public class AppParams {
 
    private  String Ofsuser;
    private  String Ofspass;
    private  String ImageBase;
    private  String ISOofsSource;
    private  String LogDir;
    private  String listeningDir;
    private String Host;
    private int port;
    private String OFSsource;
    private String T24Framework;
    
    
    
    
     public AppParams()
{
    try
    {

        javax.naming.Context ctx = (javax.naming.Context)new InitialContext().lookup("java:comp/env");
         Host = (String)ctx.lookup("HOST");
         port = Integer.parseInt((String)ctx.lookup("PORT"));
        OFSsource = (String)ctx.lookup("OFSsource");
        Ofsuser = (String)ctx.lookup("OFSuser");
        Ofspass = (String)ctx.lookup("OFSpass");
        ImageBase = (String)ctx.lookup("ImageBase");
        ISOofsSource = (String)ctx.lookup("ISO_OFSsource");
        LogDir = (String)ctx.lookup("LogDir");
         listeningDir = (String)ctx.lookup("ISOLogListenerDir");
          T24Framework = (String)ctx.lookup("T24Framework");
        
    }
    catch (Exception e)
    {
      System.out.println(e.getMessage());
    }
    
    
}
 
public WebServiceLogger getServiceLogger(String filename){
    
    return new WebServiceLogger(LogDir,filename);
}
    
      
}
