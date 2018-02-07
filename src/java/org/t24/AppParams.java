/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.t24;


import com.sun.xml.bind.StringInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import javax.naming.InitialContext;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import logger.WebServiceLogger;
import lombok.Getter;
import lombok.Setter;
import org.apache.log4j.Level;




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
     InputStream propertiesfile; 
    
    
    
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
           ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        propertiesfile = classLoader.getResourceAsStream("org/t24/interfacelogger.properties");
        
    }
    catch (Exception e)
    {
      System.out.println(e.getMessage());
    }
    
    
}
 
public WebServiceLogger getServiceLogger(String filename){
    
    return new WebServiceLogger(LogDir,filename,propertiesfile);
}
    
       public Object XMLToObject (String xml, Object object) throws Exception{
       try{
    JAXBContext jcontext = JAXBContext.newInstance(object.getClass());
    Unmarshaller um = jcontext.createUnmarshaller();

    //InputSource source = new InputSource(new StringReader(xml));
    
      return um.unmarshal(new StringInputStream(xml));
   
       }
       catch(Exception y){
           throw (y);
       }
}
       
    public String ObjectToXML(Object object){
       try{
    JAXBContext jcontext = JAXBContext.newInstance(object.getClass());
    Marshaller m = jcontext.createMarshaller();
    m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
    StringWriter sw = new StringWriter();
    
    m.marshal(object, sw);
    
    return sw.toString();
       }
       catch(Exception y){
          getServiceLogger("XMLConversion").LogError(y.getMessage(), y, Level.FATAL);
          return "";
       }
      
}
    
    
  public RemittaResponseCodes getRemittaCode(String message){
   
      RemittaResponseCodes respcode = RemittaResponseCodes.UNKNOWN_ERROR;
      
   message = message.toLowerCase();
   
   if(message.contains("ACCOUNT RECORD MISSING".toLowerCase())||message.contains("found that matched the selection criteria"))
   {
      respcode =  RemittaResponseCodes.INVALID_ACCOUNT;
   }
   
     if(message.contains("is inactive")){
      respcode =  RemittaResponseCodes.DORMANT_ACCOUNT;
   }
   
     
          if(message.contains("IS FLAGGED FOR ONLINE CLOSURE".toLowerCase())){
      respcode =  RemittaResponseCodes.CLOSED_ACCOUNT;
   }
          
        if(message.contains("Insolvent".toLowerCase())){
      respcode =  RemittaResponseCodes.ACCOUNT_BLOCKED;
   }
   
       
       return respcode;
  }


    
//    public static void main(String [] args){
//        
//   //   ISOResponse sd = new ISOResponse();
//     AppParams param = new AppParams();
////        sd.setErrorMessgae("");
////        sd.setISOMessage("xfsss");
////        sd.setIsSuccessful(Boolean.TRUE);
////        
////        String xml = param.ObjectToXML(sd);
//        
//        try{
//            
//            String sed = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n" +
//"<AccountUnblockRequest>\n" +
//"<SessionID>000001100913103301000000000001</SessionID>\n" +
//"<DestinationInstitutionCode>000002</DestinationInstitutionCode>\n" +
//"<ChannelCode>7</ChannelCode>\n" +
//"<ReferenceCode>xxxxxxxxxxxxxxx</ReferenceCode>\n" +
//"<TargetAccountName>Ajibade Oluwasegun</TargetAccountName>\n" +
//"<TargetBankVerificationNumber>1033000442</TargetBankVerificationNumber>\n" +
//"<TargetAccountNumber>2222002345</TargetAccountNumber>\n" +
//"<ReasonCode>0001</ReasonCode>\n" +
//"<Narration>Transfer from 000002 to 0YY</Narration>\n" +
//"</AccountUnblockRequest>";
//            
//        AccountUnblockRequest ds = (AccountUnblockRequest) param.XMLToObject(sed, new AccountUnblockRequest());
//        String sss = param.ObjectToXML(ds);
//       ds =  (AccountUnblockRequest) param.XMLToObject(sss, new AccountUnblockRequest());
//        ds.getChannelCode();
//        }
//        catch(Exception d){
//           System.out.println(d.getMessage());
//        }
//        
//    }
//    
    
    
}
