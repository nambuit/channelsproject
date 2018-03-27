/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.t24;


import com.sun.xml.bind.StringInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.naming.InitialContext;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import logger.WebServiceLogger;
import lombok.Getter;
import lombok.Setter;
import nibbsnip.client.NIPInterface;
import nibbsnip.client.NIPInterface_Service;
import nibbsnip.service.TSQuerySingleRequest;
import nibbsnip.service.TSQuerySingleResponse;
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
     private String encryptionserver;
    private int encryptionport;
    private String encryptionkey;
    private String DBuser;
    private String DBpass;
    private String DBserver;
    
    
    
     public AppParams()
{
    try
    {

        javax.naming.Context ctx = (javax.naming.Context)new InitialContext().lookup("java:comp/env");
        Host = (String)ctx.lookup("HOST");
        port = Integer.parseInt((String)ctx.lookup("PORT"));
        encryptionserver = (String)ctx.lookup("encryptionHOST");
        encryptionport = Integer.parseInt((String)ctx.lookup("encryptionPORT"));
        encryptionkey = (String)ctx.lookup("encryptionKEY");
        OFSsource = (String)ctx.lookup("OFSsource");
        Ofsuser = (String)ctx.lookup("OFSuser");
        Ofspass = (String)ctx.lookup("OFSpass");
        ImageBase = (String)ctx.lookup("ImageBase");
        DBuser = (String)ctx.lookup("DBuser");
        DBpass = (String)ctx.lookup("DBpass");
        DBserver = (String)ctx.lookup("DBserver");
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
  
  
  public static String escape(String text){
      
      return text.replace("&", "&amp;").replace("\"", "&quot;").replace("'", "&apos;").replace("<", "&lt;").replace(">", "&gt;");
  }


  
    public NIBBsResponseCodes getNIBBsCode(String message){
   
      NIBBsResponseCodes respcode = NIBBsResponseCodes.System_malfunction;
      
    message = message.toLowerCase();
   
   if(message.contains("ACCOUNT RECORD MISSING".toLowerCase())||message.contains("found that matched the selection criteria")
           ||message.contains("MISSING ACCOUNT - RECORD".toLowerCase()) )
   {
      respcode =  NIBBsResponseCodes.Invalid_Account;
   }
   
     if(message.contains("is inactive")){
      respcode =  NIBBsResponseCodes.Dormant_Account;
   }
   
     
          if(message.contains("IS FLAGGED FOR ONLINE CLOSURE".toLowerCase())){
      respcode =  NIBBsResponseCodes.Invalid_Account;
   }
          
        if(message.contains("Insolvent".toLowerCase())){
      respcode =  NIBBsResponseCodes.Do_not_honor;
   }
        
               if(message.contains("HISTORY RECORD MISSING".toLowerCase())){
      respcode =  NIBBsResponseCodes.No_action_taken;
   }
   
        
          if(message.contains("Unauthorised overdraft".toLowerCase())||message.contains("will fall below Locked".toLowerCase())){
      respcode =  NIBBsResponseCodes.No_sufficient_funds;
   }
    
     
       
       return respcode;
  }
    
    
    
    
    
    
    
    public String getCreateNIPTableScript(String tableName){
        
        return "CREATE TABLE [dbo].["+tableName+"](\n" +
"	[SessionID] [varchar](100) NULL,\n" +
"	[DestinationInstitutionCode] [varchar](100) NULL,\n" +
"	[SourceInstitutionCode] [varchar](100) NULL,\n" +
"	[ChannelCode] [varchar](100) NULL,\n" +
"	[AccountNumber] [varchar](100) NULL,\n" +
"	[BankVerificationNumber] [varchar](100) NULL,\n" +
"	[ResponseCode] [varchar](100) NULL,\n" +
"	[NameEnquiryRef] [varchar](100) NULL,\n" +
"	[BeneficiaryAccountName] [varchar](100) NULL,\n" +
"	[BeneficiaryAccountNumber] [varchar](100) NULL,\n" +
"	[BeneficiaryBankVerificationNumber] [varchar](100) NULL,\n" +
"	[OriginatorAccountName] [varchar](100) NULL,\n" +
"	[OriginatorAccountNumber] [varchar](100) NULL,\n" +
"	[OriginatorBankVerificationNumber] [varchar](100) NULL,\n" +
"	[OriginatorKYCLevel] [varchar](100) NULL,\n" +
"	[TransactionLocation] [varchar](100) NULL,\n" +
"	[Narration] [varchar](1000) NULL,\n" +
"	[PaymentReference] [varchar](100) NULL,\n" +
"	[Amount] [money] NULL,\n" +
"	[DebitAccountName] [varchar](100) NULL,\n" +
"	[DebitAccountNumber] [varchar](100) NULL,\n" +
"	[DebitBankVerificationNumber] [varchar](100) NULL,\n" +
"	[MandateReferenceNumber] [varchar](100) NULL,\n" +
"	[BatchNumber] [varchar](100) NULL,\n" +
"	[NumberOfRecords] [varchar](100) NULL,\n" +
"	[RecID] [varchar](100) NULL,\n" +
"	[AuthorizationCode] [varchar](100) NULL,\n" +
"	[TargetAccountName] [varchar](100) NULL,\n" +
"	[TargetAccountNumber] [varchar](100) NULL,\n" +
"	[TargetBankVerificationNumber] [varchar](100) NULL,\n" +
"	[AvailableBalance] [money] NULL,\n" +
"	[TransactionFee] [money] NULL,\n" +
"	[DebitKYCLevel] [varchar](100) NULL,\n" +
"	[ReferenceCode] [varchar](100) NULL,\n" +
"	[ReasonCode] [varchar](100) NULL,\n" +
"	[AccountName] [varchar](100) NULL,\n" +
"	[BeneficiaryKYCLevel] [varchar](100) NULL,\n" +
"	[KYCLevel] [varchar](100) NULL,\n" +
"	[InstitutionCode] [varchar](100) NULL,\n" +
"	[InstitutionName] [varchar](100) NULL,\n" +
"	[Category] [varchar](100) NULL,\n" +
"	[TranDirection] [nvarchar](50) NULL,\n" +
"	[StatusMessage] [nvarchar](500) NULL,\n" +
"	[TransactionDate] [datetime] NULL,\n" +
"	[MethodName] [nvarchar](50) NULL\n" +
") ON [PRIMARY]";
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
    
    
    public String formatAmt(String amt) throws Exception{
        
        try{
            DecimalFormat df = new DecimalFormat("0.00");
            
            Double Amount = Double.parseDouble(amt);
            
           amt = df.format(Amount);
           
           return amt;
        }
        catch(Exception g){
            throw(g);
        }
    }
    
    public  NIBBsResponseCodes CheckTransactionStatus(String sessionid, String InstCode, PGPEncrytionTool nipssm){
        
        NIBBsResponseCodes respcode = NIBBsResponseCodes.Status_unknown;
        
        try{
            TSQuerySingleRequest request = new TSQuerySingleRequest();
            
            request.setChannelCode("1");
            request.setSessionID(sessionid);
            request.setSourceInstitutionCode(InstCode);
            
            String requeststr = this.ObjectToXML(request);
            
            requeststr = nipssm.encrypt(requeststr);
            
             NIPInterface_Service nipclient =   new NIPInterface_Service();
            NIPInterface nip = nipclient.getNIPInterfacePort();
            
          String nipresponse =  nip.txnstatusquerysingleitem(requeststr);
           
          nipresponse = nipssm.decrypt(nipresponse);
          
          TSQuerySingleResponse response = (TSQuerySingleResponse) this.XMLToObject(nipresponse, new TSQuerySingleResponse());
          
         respcode = respcode.getResponseObject(response.getResponseCode());
            
          return respcode;  
            
            
          }
        catch(Exception t){
            return NIBBsResponseCodes.System_malfunction;
        }
        
    }
    
    
     public void ExecuteCredits(DBConnector db, PGPEncrytionTool nipssm, T24Link t24){
       
        Connection conn = null;
       
       try{
      SimpleDateFormat ndf = new SimpleDateFormat("yyyyMMdd");
           
           ResultSet rs = db.getData("Select * from NIPPendingCredits",conn);
           
           while (rs.next()){
             
               String sessionid = rs.getString("SessionID");
               String ofstr = rs.getString("OFSMessage");
               String sourceinstcode = rs.getString("DestinationInstitutionCode");
               String compcode = rs.getString("CompanyCode");
               Date date = rs.getTimestamp("TransactionDate");
   
    
               String trandate = ndf.format(date);
               
               
              NIBBsResponseCodes respcode =  CheckTransactionStatus(sessionid, sourceinstcode, nipssm);
              
           String code =   respcode.getCode();
               
             if(code.equals("00"))
             { 
              
              String result = t24.PostMsg(ofstr);
              
              
                if(t24.IsSuccessful(result)){
             
                  
               
                ofsParam param = new ofsParam();
                String[] credentials = new String[] { getOfsuser(), getOfspass(),compcode };
                param.setCredentials(credentials);
                 
                List<DataItem> items = new LinkedList<>();
               DataItem item = new DataItem();
               
                String[] ofsoptions = new String[] { "", "I", "PROCESS", "2", "0" };
               
          
               param.setCredentials(credentials);
               param.setOperation("NIBBS.FT.REF.TABLE");
               param.setOptions(ofsoptions);
               
               
               param.setTransaction_id(sessionid);
               
          
               item.setItemHeader("T24.ID");
               item.setItemValues(new String[] {result.split("/")[0]});
               items.add(item);
               
               item = new DataItem();
               item.setItemHeader("DATE");
               item.setItemValues(new String[] {trandate});
               
               
               items.add(item);

               param.setDataItems(items);
               
                ofstr = t24.generateOFSTransactString(param);

                t24.PostMsg(ofstr);
                
               db.Execute("delete from NIPPendingCredits where SessionID ='"+sessionid+"'");
                
               
           }
           else{
                    
                    String errormessage;
                    
                        if(result.contains("/")){
                         errormessage =    result.split("/")[3];
               
                        }
                        else{
                              errormessage =    result;
                        }
               
             
               
                     
                db.Execute("Update  NIPPendingCredits set StatusMessage='"+errormessage+"' where  SessionID ='"+sessionid+"'");
               
              
           }
             }
             else{
                  db.Execute("Update  NIPPendingCredits set StatusMessage='"+respcode.getMessage()+"', ResponseCode='"+respcode.getCode()+"' where  SessionID ='"+sessionid+"'"); 
             }
             
             
           }
       
         
           
         
   }
       catch(Exception d){
          System.out.println(d.getMessage());
       }
       
   }
    
    
    
    
     
}
