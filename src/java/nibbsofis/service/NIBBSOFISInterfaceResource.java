/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nibbsofis.service;

import com.google.gson.Gson;
import java.io.StringReader;
import java.io.StringWriter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import org.json.JSONObject;
import org.json.XML;
import org.xml.sax.InputSource;

/**
 * REST Web Service
 *
 * @author Temitope
 */
@Path("NIBBSOFISInterface")
public class NIBBSOFISInterfaceResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of NIBBSOFISInterfaceResource
     */
    public NIBBSOFISInterfaceResource() {
    }

    /**
     * Retrieves representation of an instance of nibbsofis.service.NIBBSOFISInterfaceResource
     * @param input
     * @return an instance of java.lang.String
     */
    @POST
    @Path("fundtransfercreditnotification")
 
   @Produces(javax.ws.rs.core.MediaType.TEXT_XML)
    public String fundtransfercreditnotification(String input) {
        //TODO return proper representation object
       FundTransferNotificationResponse response = new FundTransferNotificationResponse();  
        Gson gson = new Gson();
        try{  
//    JSONObject object = XML.toJSONObject(input);       
//   
////    
//        object = (JSONObject)object.get("FundTransferNotificationRequest");
////    
//   String json = object.toString();
     FundTransferNotificationRequest request = (FundTransferNotificationRequest)XMLToObject(input,new FundTransferNotificationRequest()); 
///gson.fromJson(json, FundTransferNotificationRequest.class);
  
        response.setOFICode(request.getOFICode());
        response.setResponseCode("00");
        response.setResponseDescription("dffdd");
        response.setSessionID(request.getSessionID());
         return ObjectToXML(response);
}
  
        catch(Exception d){
    System.out.print(d.getMessage());
    return d.getMessage();
}
        
       
        
    }

    
    
   @POST
   @Path("airtimetopupnotification")
 
   @Produces(javax.ws.rs.core.MediaType.TEXT_XML)
    public String airtimetopupnotification(String input) {
        //TODO return proper representation object
       AirTimeTopupNotificationResponse response = new AirTimeTopupNotificationResponse();  
        Gson gson = new Gson();
        try{  
    JSONObject object = XML.toJSONObject(input);       
   
//    
        object = (JSONObject)object.get("AirTimeTopupNotificationRequest");
//    
   String json = object.toString();
     AirTimeTopupNotificationRequest request = (AirTimeTopupNotificationRequest) gson.fromJson(json, AirTimeTopupNotificationRequest.class);
  
        response.setOFICode(request.getOFICode());
        response.setResponseCode("00");
        response.setResponseDescription("dffdd");
        response.setSessionID(request.getSessionID());
         return ObjectToXML(response);
}
  
        catch(Exception d){
    System.out.print(d.getMessage());
    return d.getMessage();
}
        
       
        
    }
    
    
    
       @POST
   @Path("billpaymentnotification")
 
   @Produces(javax.ws.rs.core.MediaType.TEXT_XML)
    public String billpaymentnotification(String input) {
        //TODO return proper representation object
       AirTimeTopupNotificationResponse response = new AirTimeTopupNotificationResponse();  
        Gson gson = new Gson();
        try{  
    JSONObject object = XML.toJSONObject(input);       
   
//    
        object = (JSONObject)object.get("AirTimeTopupNotificationRequest");
//    
   String json = object.toString();
     AirTimeTopupNotificationRequest request = (AirTimeTopupNotificationRequest) gson.fromJson(json, AirTimeTopupNotificationRequest.class);
  
        response.setOFICode(request.getOFICode());
        response.setResponseCode("00");
        response.setResponseDescription("dffdd");
        response.setSessionID(request.getSessionID());
         return ObjectToXML(response);
}
  
        catch(Exception d){
    System.out.print(d.getMessage());
    return d.getMessage();
}
        
       
        
    }
    

    
    private String ObjectToXML(Object object) throws Exception{
       try{
    JAXBContext jcontext = JAXBContext.newInstance(object.getClass());
    Marshaller m = jcontext.createMarshaller();
    m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
    StringWriter sw = new StringWriter();
    m.marshal(object, sw);
    return sw.toString();
       }
       catch(Exception y){
           throw (y);
       }
}
    
 
    
       private Object XMLToObject (String xml, Object object) throws Exception{
       try{
    JAXBContext jcontext = JAXBContext.newInstance(object.getClass());
    Unmarshaller um = jcontext.createUnmarshaller();
   // um.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
    InputSource source = new InputSource(new StringReader(xml));
  
      return um.unmarshal(source);
   
       }
       catch(Exception y){
           throw (y);
       }
}
    
      
}
