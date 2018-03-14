/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nibbsnip.service;

import com.google.gson.Gson;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.xml.bind.UnmarshalException;
import org.apache.log4j.Level;
import org.json.JSONObject;
import org.json.XML;
import org.t24.AppParams;
import org.t24.DBConnector;
import org.t24.DataItem;
import org.t24.NIBBsResponseCodes;
import org.t24.PGPEncrytionTool;
import org.t24.T24Link;
import org.t24.T24TAFCLink;
import org.t24.T24TAFJLink;
import org.t24.ofsParam;

/**
 *
 * @author Temitope
 */
@WebService(serviceName = "NIBBSNIPInterface")
public class NIBBSNIPInterface {
    AppParams options;  
     T24Link t24;       
     String logfilename = "NIBBSNIPInterface";
     NIBBsResponseCodes respcodes;  
     PGPEncrytionTool nipssm;
     DBConnector db;
    
    
    public NIBBSNIPInterface(){
   try
    {
 
        options = new AppParams();
        
        nipssm = new PGPEncrytionTool(options);
        
        
        t24 = "TAFJ".equals(options.getT24Framework().trim().toUpperCase())? new T24TAFJLink():
              new T24TAFCLink(options.getHost(), options.getPort(), options.getOFSsource()); 
        
        db = new DBConnector(options.getDBserver(),options.getDBuser(),options.getDBpass(),"NIPLogs");
        
    }
    catch (Exception e)
    {   
        options.getServiceLogger(logfilename).LogError(e.getMessage(), e, Level.FATAL);
    }
            
            
    }

        @WebMethod(operationName = "nameenquirysingleitem")
        public String nameenquirysingleitem(@WebParam(name = "nesinglerequest") String input) {     
        NESingleResponse nameEnquiry = new NESingleResponse();
          String monthlyTable = "";
        String sessionID="",AcctName ="",BVN = "";
         
        try{
                   
           input = nipssm.decrypt(input);
    
            NESingleRequest request = (NESingleRequest) options.XMLToObject(input, new NESingleRequest());
            
        List<Object> values = new ArrayList<>();
        List<String> headers = new ArrayList<>();
         //repopulating response object and logging request
         
             nameEnquiry.setSessionID(request.getSessionID());
             values.add(request.getSessionID());
             headers.add("SessionID");
             sessionID = request.getSessionID();
         
            nameEnquiry.setAccountNumber(request.getAccountNumber());
            values.add(request.getAccountNumber());
            headers.add("AccountNumber");

            nameEnquiry.setDestinationInstitutionCode(request.getDestinationInstitutionCode());
            values.add(request.getDestinationInstitutionCode());
            headers.add("DestinationInstitutionCode");

            nameEnquiry.setChannelCode(request.getChannelCode());
            values.add(request.getChannelCode());
            headers.add("ChannelCode");       

            Date date = new  Date();
            values.add(date);
            headers.add("TransactionDate");

            values.add("INWARD");
            headers.add("TranDirection");

            values.add("nameenquirysingleitem");
            headers.add("MethodName");
        
        

         SimpleDateFormat df = new SimpleDateFormat("MMMyyyy"); 
        
         monthlyTable = df.format(date)+"NIP_TRANSACTIONS";
        
        String createquery = options.getCreateNIPTableScript(monthlyTable);
        
        try{
            db.Execute(createquery);
        }
        catch(Exception r){
            
        }
     
       db.insertData(headers, values.toArray(),monthlyTable);
        
        
        
    
            ArrayList<List<String>> result = t24.getOfsData("NESingleRequest.NIP",options.getOfsuser(), options.getOfspass(), "@ID:EQ=" + request.getAccountNumber());
            headers = result.get(0);
     
           if(headers.size()!=result.get(1).size()){
               String msg = result.get(1).get(0);
               respcodes = options.getNIBBsCode(msg);
                 nameEnquiry.setResponseCode(respcodes.getCode());
                 
                return options.ObjectToXML(nameEnquiry);
               
           }
          respcodes = NIBBsResponseCodes.SUCCESS;
         nameEnquiry.setAccountNumber(request.getAccountNumber());
         nameEnquiry.setResponseCode(respcodes.getCode());
         AcctName = escape(result.get(1).get(headers.indexOf("AccountName")).replace("\"", "").trim());
         nameEnquiry.setAccountName(AcctName);
         BVN = escape(result.get(1).get(headers.indexOf("BankVerificationNumber")).replace("\"", "").trim());
         nameEnquiry.setBankVerificationNumber(BVN);
         nameEnquiry.setKYCLevel(escape(result.get(1).get(headers.indexOf("KYCLevel")).replace("\"", "").trim()));
         nameEnquiry.setDestinationInstitutionCode(request.getDestinationInstitutionCode());
         nameEnquiry.setChannelCode(request.getChannelCode());
         nameEnquiry.setSessionID(request.getSessionID());
         
         
            
    }
                catch(UnmarshalException r){
                
             respcodes = NIBBsResponseCodes.Format_error;
            nameEnquiry.setResponseCode(respcodes.getCode());
        }
        
        catch(Exception e){
        respcodes = NIBBsResponseCodes.System_malfunction;
        nameEnquiry.setResponseCode(respcodes.getCode());
        
    }
    finally{
    try{
        
        String query = "Update "+monthlyTable+" set ResponseCode='"+respcodes.getCode()+"', StatusMessage='"+respcodes.getMessage()+"', AccountName='"+AcctName+"', BankVerificationNumber='"+BVN+"'  where SessionID='"+sessionID+"' and MethodName='nameenquirysingleitem'";
        db.Execute(query);
        
        }
        catch(Exception s)
           {
           
            }

}   
        //return options.ObjectToXML(nameEnquiry);
        
        return nipssm.encrypt(options.ObjectToXML(nameEnquiry));
}
        
      
    
      @WebMethod(operationName = "fundtransfersingleitem_dc")
    public String fundtransfersingleitem_dc(@WebParam(name = "ftsinglecreditrequest") String ftsinglecreditrequest) {
        FTSingleCreditResponse response = new FTSingleCreditResponse();
        String monthlyTable = "";
        String sessionID = "";
        String CompanyCode = "NG0010001";     

        
        try{

       ftsinglecreditrequest = nipssm.decrypt(ftsinglecreditrequest);
            
        FTSingleCreditRequest request = (FTSingleCreditRequest) options.XMLToObject(ftsinglecreditrequest, new FTSingleCreditRequest());
        
        List<Object> values = new ArrayList<>();
        List<String> headers = new ArrayList<>();
         //repopulating response object and logging request
        response.setAmount(request.getAmount());
        values.add(Double.parseDouble(request.getAmount().toString()));
        headers.add("Amount");
        
        response.setBeneficiaryAccountName(request.getBeneficiaryAccountName());
        values.add(request.getBeneficiaryAccountName());
        headers.add("BeneficiaryAccountName");
        
        response.setBeneficiaryAccountNumber(request.getBeneficiaryAccountNumber());
        values.add(request.getBeneficiaryAccountNumber());
        headers.add("BeneficiaryAccountNumber");
        
        response.setBeneficiaryBankVerificationNumber(request.getBeneficiaryBankVerificationNumber());
        values.add(request.getBeneficiaryBankVerificationNumber());
        headers.add("BeneficiaryBankVerificationNumber");
        
        response.setChannelCode(request.getChannelCode());
        values.add(request.getChannelCode());
        headers.add("ChannelCode");
        
        response.setDestinationInstitutionCode(request.getDestinationInstitutionCode());
        values.add(request.getDestinationInstitutionCode());
        headers.add("DestinationInstitutionCode");
        
        response.setOriginatorAccountName(request.getOriginatorAccountName());
        values.add(request.getOriginatorAccountName());
        headers.add("OriginatorAccountName");
        
        response.setOriginatorAccountNumber(request.getOriginatorAccountNumber());
        values.add(request.getOriginatorAccountNumber());
        headers.add("OriginatorAccountNumber");
        
         response.setOriginatorBankVerificationNumber(request.getOriginatorBankVerificationNumber());
         values.add(request.getOriginatorBankVerificationNumber());
         headers.add("OriginatorBankVerificationNumber");
        
         response.setOriginatorKYCLevel(request.getOriginatorKYCLevel());
         values.add(request.getOriginatorKYCLevel());
         headers.add("OriginatorKYCLevel");
       
        response.setSessionID(request.getSessionID());
        values.add(request.getSessionID());
        headers.add("SessionID");
        sessionID = request.getSessionID();
         
        response.setTransactionLocation(request.getTransactionLocation());
        values.add(request.getTransactionLocation());
        headers.add("TransactionLocation");
        
            response.setNameEnquiryRef(request.getNameEnquiryRef());
        values.add(request.getNameEnquiryRef());
        headers.add("NameEnquiryRef");
        
        response.setTransactionLocation(request.getNarration());
        values.add(request.getNarration());
        headers.add("Narration");
        
        response.setTransactionLocation(request.getPaymentReference());
        values.add(request.getPaymentReference());
        headers.add("PaymentReference");
        
       // request.

        
        values.add("INWARD");
        headers.add("TranDirection");
        
              values.add("fundtransfersingleitem_dc");
            headers.add("MethodName");
        
            
             String datestr = request.getSessionID().substring(6,18);
       
       SimpleDateFormat sdf = new SimpleDateFormat("yymmddHHmmss");
 
        Date date = sdf.parse(datestr);
       
        
          values.add(date);
          headers.add("TransactionDate");
        
         SimpleDateFormat df = new SimpleDateFormat("MMMyyyy"); 
        
         monthlyTable = df.format(date)+"NIP_TRANSACTIONS";
        
        String createquery = options.getCreateNIPTableScript(monthlyTable);
        
        try{
            db.Execute(createquery);
        }
        catch(Exception r){
            
        }
     
       db.insertData(headers, values.toArray(),monthlyTable);
        
     
     
       String[] ofsoptions = new String[] { "", "I", "PROCESS", "2", "0" };
       String[] credentials = new String[] {options.getOfsuser(), options.getOfspass() };
       List<DataItem> items = new LinkedList<>();
       SimpleDateFormat ndf = new SimpleDateFormat("yyyyMMdd"); 
     
    
       String trandate = ndf.format(date);
       
       
       ofsParam param = new ofsParam();
     
        param.setCredentials(credentials);
           param.setOperation("FUNDS.TRANSFER");
           
           
           param.setVersion("FT.REQ.DIRECT.DEBIT.NIP.");
           
           param.setTransaction_id("");
          
           param.setOptions(ofsoptions);
           
          
           DataItem item = new DataItem();
           
           
          ofsParam paramv = new ofsParam(); 
           
         paramv.setTransaction_id(CompanyCode);
         ofsoptions[2] ="VALIDATE";     
         
         paramv.setOptions(ofsoptions);
         paramv.setDataItems(items);
         
         paramv.setOperation("NIP.PARAM.TABLE");
         
         paramv.setCredentials(credentials);
         
         String ofstrv = t24.generateOFSTransactString(paramv);
         String resultv =  t24.PostMsg(ofstrv);
         
         resultv = resultv.substring(resultv.indexOf("RECEIVABLE.ACCOUNT:1:1="));
         
         String recievableacct = resultv.split(",")[0].split("=")[1];
         
         ofsoptions[2] ="PROCESS"; 
//           item = new DataItem();
//           item.setItemHeader("CREDIT.VALUE.DATE");
//           item.setItemValues(new String[] {trandate});
//           items.add(item);
//           
       //    item = new DataItem();
           item.setItemHeader("DEBIT.CURRENCY");
           item.setItemValues(new String[] {"NGN"});
           items.add(item);
           
           item = new DataItem();
           item.setItemHeader("CREDIT.CURRENCY");
           item.setItemValues(new String[] {"NGN"});
           items.add(item);
           
           item = new DataItem();
           item.setItemHeader("CREDIT.AMOUNT");
           item.setItemValues(new String[] {request.getAmount().toString()});
           items.add(item);
           
           
           
           
           
           
           
           item = new DataItem();
           item.setItemHeader("DEBIT.ACCT.NO");
           item.setItemValues(new String[] {recievableacct});
           items.add(item);
                      
           item = new DataItem();
           item.setItemHeader("CREDIT.ACCT.NO");
           item.setItemValues(new String[] {request.getBeneficiaryAccountNumber()});
           items.add(item);
           
           item = new DataItem();
           item.setItemHeader("CREDIT.THEIR.REF");
           
           if(request.getNarration().length()>15){
               request.setNarration(request.getNarration().substring(15));
           }
           
           item.setItemValues(new String[] {request.getNarration()});
           items.add(item);
           

           
           param.setDataItems(items);
           
           String ofstr = t24.generateOFSTransactString(param);

           String result = t24.PostMsg(ofstr);
           
           if(t24.IsSuccessful(result)){
              response.setPaymentReference(request.getPaymentReference());
               
               items.clear();
               param = new ofsParam();
               param.setCredentials(credentials);
               param.setOperation("NIBBS.FT.REF.TABLE");
               param.setOptions(ofsoptions);
               
               
               param.setTransaction_id(request.getPaymentReference());
               
               item = new DataItem();
               item.setItemHeader("T24.ID");
               item.setItemValues(new String[] {result.split("/")[0]});
               items.add(item);
               
               item = new DataItem();
               item.setItemHeader("TXN.DATE");
               item.setItemValues(new String[] {trandate});
               
               
               items.add(item);

               param.setDataItems(items);
               
                ofstr = t24.generateOFSTransactString(param);

                t24.PostMsg(ofstr);
                
                respcodes = NIBBsResponseCodes.SUCCESS;         
                response.setResponseCode(respcodes.getCode());
                
               
           }
           else{
                        if(result.contains("/")){
                respcodes = options.getNIBBsCode(result.split("/")[3]);
                
                if(respcodes==NIBBsResponseCodes.System_malfunction){
                    throw new Exception (result);
                }
                  response.setResponseCode(respcodes.getCode());
             
               }
               else{
                   throw new Exception (result);
               }
               
              
           }
     
        
        
        
            
    }
            catch(UnmarshalException r){
                
             respcodes = NIBBsResponseCodes.Format_error;
            response.setResponseCode(respcodes.getCode());
        }
        catch(Exception e){
        respcodes = NIBBsResponseCodes.System_malfunction;
        response.setResponseCode(respcodes.getCode());
    }
      finally{
    try{
        
        String query = "Update "+monthlyTable+" set ResponseCode='"+respcodes.getCode()+"', StatusMessage='"+respcodes.getMessage()+"' where SessionID='"+sessionID+"' and MethodName='fundtransfersingleitem_dc'";
        db.Execute(query);
        
        }
        catch(Exception s)
           {
           
            }

}   
      
      //  return options.ObjectToXML(response);
        
          return nipssm.encrypt(options.ObjectToXML(response));
   
    }
 
    
      @WebMethod(operationName = "fundtransfersingleitem_dd")
    public String fundtransfersingleitem_dd(@WebParam(name = "ftsingledebitrequest") String ftsingledebitrequest) {
        FTSingleDebitResponse response = new FTSingleDebitResponse();     
        
        String monthlyTable = "";
        String sessionID = "";
        
        try{
            
       ftsingledebitrequest = nipssm.decrypt(ftsingledebitrequest);     
       FTSingleDebitRequest request = (FTSingleDebitRequest) options.XMLToObject(ftsingledebitrequest, new FTSingleDebitRequest());
       
            List<Object> values = new ArrayList<>();
        List<String> headers = new ArrayList<>();
         //repopulating response object and logging request
        response.setAmount(request.getAmount());
        values.add(Double.parseDouble(request.getAmount().toString()));
        headers.add("Amount");
        
        response.setBeneficiaryAccountName(request.getBeneficiaryAccountName());
        values.add(request.getBeneficiaryAccountName());
        headers.add("BeneficiaryAccountName");
        
        response.setBeneficiaryAccountNumber(request.getBeneficiaryAccountNumber());
        values.add(request.getBeneficiaryAccountNumber());
        headers.add("BeneficiaryAccountNumber");
        
        response.setBeneficiaryBankVerificationNumber(request.getBeneficiaryBankVerificationNumber());
        values.add(request.getBeneficiaryBankVerificationNumber());
        headers.add("BeneficiaryBankVerificationNumber");
        
        response.setChannelCode(request.getChannelCode());
        values.add(request.getChannelCode());
        headers.add("ChannelCode");
        
        response.setDestinationInstitutionCode(request.getDestinationInstitutionCode());
        values.add(request.getDestinationInstitutionCode());
        headers.add("DestinationInstitutionCode");
        
       
        response.setSessionID(request.getSessionID());
        values.add(request.getSessionID());
        headers.add("SessionID");
        sessionID = request.getSessionID();
        
        response.setNameEnquiryRef(request.getNameEnquiryRef());
        values.add(request.getNameEnquiryRef());
        headers.add("NameEnquiryRef");
         
        response.setTransactionLocation(request.getTransactionLocation());
        values.add(request.getTransactionLocation());
        headers.add("TransactionLocation");
        

        
        values.add("INWARD");
        headers.add("TranDirection");
        
           values.add("fundtransfersingleitem_dd");
            headers.add("MethodName");
        
        
             String datestr = request.getSessionID().substring(6,18);
       
       SimpleDateFormat sdf = new SimpleDateFormat("yymmddHHmmss");
 
        Date date = sdf.parse(datestr);
       
        
          values.add(date);
          headers.add("TransactionDate");
        
         SimpleDateFormat df = new SimpleDateFormat("MMMyyyy"); 
        
         monthlyTable = df.format(date)+"NIP_TRANSACTIONS";
        
        String createquery = options.getCreateNIPTableScript(monthlyTable);
        
        try{
            db.Execute(createquery);
        }
        catch(Exception r){
            
        }
     
       db.insertData(headers, values.toArray(),monthlyTable);
       String[] ofsoptions = new String[] { "", "I", "PROCESS", "2", "0" };
       String[] credentials = new String[] {options.getOfsuser(), options.getOfspass() };
       List<DataItem> items = new LinkedList<>();
       SimpleDateFormat ndf = new SimpleDateFormat("yyyyMMdd"); 
   
    
       String trandate = ndf.format(date);
       
       
       ofsParam param = new ofsParam();
     
        param.setCredentials(credentials);
           param.setOperation("FUNDS.TRANSFER");
           
           
           param.setVersion("FT.REQ.DIRECT.DEBIT.NIP.");
           
           param.setTransaction_id("");
          
           param.setOptions(ofsoptions);
           
          
           DataItem item = new DataItem();
//           item.setItemHeader("DEBIT.VALUE.DATE");
//           item.setItemValues(new String[] {trandate});
//           items.add(item);
           
      
//           item = new DataItem();
//           item.setItemHeader("CREDIT.VALUE.DATE");
//           item.setItemValues(new String[] {trandate});
//           items.add(item);
           
 //          item = new DataItem();
           item.setItemHeader("DEBIT.CURRENCY");
           item.setItemValues(new String[] {"NGN"});
           items.add(item);
           
           item = new DataItem();
           item.setItemHeader("CREDIT.CURRENCY");
           item.setItemValues(new String[] {"NGN"});
           items.add(item);
           
           item = new DataItem();
           item.setItemHeader("DEBIT.AMOUNT");
           item.setItemValues(new String[] {request.getAmount().toString()});
           items.add(item);
           
           item = new DataItem();
           item.setItemHeader("CREDIT.ACCT.NO");
           item.setItemValues(new String[] {request.getBeneficiaryAccountNumber()});
           items.add(item);
                      
           item = new DataItem();
           item.setItemHeader("DEBIT.ACCT.NO");
           item.setItemValues(new String[] {request.getDebitAccountNumber()});
           items.add(item);
           
           item = new DataItem();
           item.setItemHeader("CREDIT.THEIR.REF");
           
           if(request.getNarration().length()>65){
               request.setNarration(request.getNarration().substring(65));
           }
           
           item.setItemValues(new String[] {request.getNarration()});
           items.add(item);
           

           
           param.setDataItems(items);
           
           String ofstr = t24.generateOFSTransactString(param);

           String result = t24.PostMsg(ofstr);
           
           if(t24.IsSuccessful(result)){
              response.setPaymentReference(request.getPaymentReference());
               
               items.clear();
               param = new ofsParam();
               param.setCredentials(credentials);
               param.setOperation("NIBBS.FT.REF.TABLE");
               param.setOptions(ofsoptions);
               
               
               param.setTransaction_id(request.getPaymentReference());
               
               item = new DataItem();
               item.setItemHeader("T24.ID");
               item.setItemValues(new String[] {result.split("/")[0]});
               items.add(item);
               
               item = new DataItem();
               item.setItemHeader("TXN.DATE");
               item.setItemValues(new String[] {trandate});
               
               
               items.add(item);

               param.setDataItems(items);
               
                ofstr = t24.generateOFSTransactString(param);

                t24.PostMsg(ofstr);
                
                respcodes = NIBBsResponseCodes.SUCCESS;
                response.setPaymentReference(request.getPaymentReference());
                response.setAmount(request.getAmount());
                response.setNarration(request.getNarration());
                response.setResponseCode(respcodes.getCode());
                response.setSessionID(request.getSessionID());
                response.setChannelCode(request.getChannelCode());
                response.setDestinationInstitutionCode(request.getDestinationInstitutionCode());
              //  response.setOriginatorAccountName(request.());
                 response.setNameEnquiryRef(request.getNameEnquiryRef());
               
           }
           else{
                        if(result.contains("/")){
                respcodes = options.getNIBBsCode(result.split("/")[3]);
                
                if(respcodes==NIBBsResponseCodes.System_malfunction){
                    throw new Exception (result);
                }
                  response.setResponseCode(respcodes.getCode());
             
               }
               else{
                   throw new Exception (result);
               }
               
              
           }
     
       
       
        }
        catch(UnmarshalException r){
                
             respcodes = NIBBsResponseCodes.Format_error;
            response.setResponseCode(respcodes.getCode());
        }
        catch(Exception e){
        respcodes = NIBBsResponseCodes.System_malfunction;
        response.setResponseCode(respcodes.getCode());
    }
        
          finally{
    try{
        
        String query = "Update "+monthlyTable+" set ResponseCode='"+respcodes.getCode()+"', StatusMessage='"+respcodes.getMessage()+"' where SessionID='"+sessionID+"' and MethodName='fundtransfersingleitem_dd'";
        db.Execute(query);
        
        }
        catch(Exception s)
           {
           
            }

}  
      //  return options.ObjectToXML(response);
        
         return nipssm.encrypt(options.ObjectToXML(response));
}
    
    
     @WebMethod(operationName = "txnstatusquerysingleitem")
    public String txnstatusquerysingleitem(@WebParam(name = "tsquerysinglerequest") String tsquerysinglerequest) {
        TSQuerySingleResponse response = new TSQuerySingleResponse();
     String sessionID ="", monthlyTable="";
     
    Connection conn = null;
     
        try{
            
            
             tsquerysinglerequest = nipssm.decrypt(tsquerysinglerequest);
     
             TSQuerySingleRequest request = (TSQuerySingleRequest) options.XMLToObject(tsquerysinglerequest, new TSQuerySingleRequest());
             
             
                     
        List<Object> values = new ArrayList<>();
        List<String> headers = new ArrayList<>();
         //repopulating response object and logging request
         
             response.setSessionID(request.getSessionID());
             values.add(request.getSessionID());
             headers.add("SessionID");
             sessionID = request.getSessionID();

            response.setSourceInstitutionCode(request.getSourceInstitutionCode());
            values.add(request.getSourceInstitutionCode());
            headers.add("DestinationInstitutionCode");

            response.setChannelCode(request.getChannelCode());
            values.add(request.getChannelCode());
            headers.add("ChannelCode");       

            values.add("INWARD");
            headers.add("TranDirection");

            values.add("txnstatusquerysingleitem");
            headers.add("MethodName");
        
        
       
       String datestr = request.getSessionID().substring(6,18);
       
       SimpleDateFormat sdf = new SimpleDateFormat("yymmddHHmmss");
 
        Date date = sdf.parse(datestr);
       
        
          values.add(date);
          headers.add("TransactionDate");
        

         SimpleDateFormat df = new SimpleDateFormat("MMMyyyy"); 
        
         monthlyTable = df.format(date)+"NIP_TRANSACTIONS";
        
        String createquery = options.getCreateNIPTableScript(monthlyTable);
        
        try{
            db.Execute(createquery);
        }
        catch(Exception r){
            
        }
     
       db.insertData(headers, values.toArray(),monthlyTable);
            
      
       
           
     ResultSet rs = db.getData("Select * from "+monthlyTable+" where SessionID='"+request.getSessionID()+"' and MethodName  like'%fundtransfersingleitem%'",conn);
     
     if(!rs.next()){
         respcodes = NIBBsResponseCodes.Invalid_transaction;
         response.setResponseCode(respcodes.getCode());
         return nipssm.encrypt(options.ObjectToXML(response));        
     }
     else{
          respcodes = NIBBsResponseCodes.SUCCESS;
          response.setResponseCode(rs.getString("ResponseCode"));
     }
      
      
    
     
      
    }
     catch(UnmarshalException r){
                
             respcodes = NIBBsResponseCodes.Format_error;
            response.setResponseCode(respcodes.getCode());
        }
        catch(Exception e){
        respcodes = NIBBsResponseCodes.System_malfunction;
        response.setResponseCode(respcodes.getCode());
    }
        
       finally{
    try{
        
        String query = "Update "+monthlyTable+" set ResponseCode='"+respcodes.getCode()+"', StatusMessage='"+respcodes.getMessage()+"' where SessionID='"+sessionID+"' and MethodName='txnstatusquerysingleitem'";
        db.Execute(query);
        conn.close();
        
        }
        catch(Exception s)
           {
           
            }

}  
        
      //  return options.ObjectToXML(response);
        
         return nipssm.encrypt(options.ObjectToXML(response));
}
    
    
    

    @WebMethod(operationName = "balanceenquiry")
    public String balanceenquiry(@WebParam(name = "balancerequest") String balancerequest) {
        BalanceEnquiryResponse response = new BalanceEnquiryResponse();
        
        String sessionID = "",monthlyTable ="", AvailableBalance="";       

        try {
       
           balancerequest = nipssm.decrypt(balancerequest);
            
            BalanceEnquiryRequest request = (BalanceEnquiryRequest) options.XMLToObject(balancerequest,new BalanceEnquiryRequest());
       
                          
        List<Object> values = new ArrayList<>();
        List<String> headers = new ArrayList<>();
         //repopulating response object and logging request
         
             response.setSessionID(request.getSessionID());
             values.add(request.getSessionID());
             headers.add("SessionID");
             sessionID = request.getSessionID();
             
             response.setDestinationInstitutionCode(request.getDestinationInstitutionCode());
               headers.add("DestinationInstitutionCode");
              values.add(request.getDestinationInstitutionCode());
             
            
            response.setTargetAccountName(request.getTargetAccountName());
              headers.add("TargetAccountName");
            values.add(request.getTargetAccountName());
             
              response.setTargetAccountNumber(request.getTargetAccountNumber());
             values.add(request.getTargetAccountNumber());
             headers.add("TargetAccountNumber");
             
             response.setTargetBankVerificationNumber(request.getTargetBankVerificationNumber());
             values.add(request.getTargetBankVerificationNumber());
             headers.add("TargetBankVerificationNumber");
             
            response.setAuthorizationCode(request.getAuthorizationCode());
            values.add(request.getAuthorizationCode());
            headers.add("AuthorizationCode");

            response.setChannelCode(request.getChannelCode());
            values.add(request.getChannelCode());
            headers.add("ChannelCode");       

            values.add("INWARD");
            headers.add("TranDirection");

            values.add("balanceenquiry");
            headers.add("MethodName");
        
        
       
       String datestr = request.getSessionID().substring(6,18);
       
       SimpleDateFormat sdf = new SimpleDateFormat("yymmddHHmmss");
 
        Date date = sdf.parse(datestr);
       
        
          values.add(date);
          headers.add("TransactionDate");
        

         SimpleDateFormat df = new SimpleDateFormat("MMMyyyy"); 
        
         monthlyTable = df.format(date)+"NIP_TRANSACTIONS";
        
        String createquery = options.getCreateNIPTableScript(monthlyTable);
        
        try{
            db.Execute(createquery);
        }
        catch(Exception r){
            
        }
     
       db.insertData(headers, values.toArray(),monthlyTable);
            
            
            
        
     
     ArrayList<List<String>> result = t24.getOfsData("BalanceEnquiryRequest.NIP",options.getOfsuser(), options.getOfspass(), "@ID:EQ=" + request.getTargetAccountNumber());
        headers = result.get(0);
     
           if(headers.size()!=result.get(1).size()){
               
              String msg = result.get(1).get(0);
              
              respcodes = options.getNIBBsCode(msg);
            response.setResponseCode(respcodes.getCode());  
           }
           else{
               
           
            AvailableBalance = escape(result.get(1).get(headers.indexOf("AvailableBalance")).replace("\"", "").trim().replace(",", ""));
           response.setAvailableBalance(BigDecimal.valueOf(Double.parseDouble(AvailableBalance)));
            respcodes = NIBBsResponseCodes.SUCCESS;
           response.setResponseCode(respcodes.getCode());
           }
         
           
    
         
     
        } 
        
             catch(UnmarshalException r){
                
             respcodes = NIBBsResponseCodes.Format_error;
            response.setResponseCode(respcodes.getCode());
        }
        
        
        catch (Exception d) {
           respcodes = NIBBsResponseCodes.System_malfunction;
           response.setResponseCode(respcodes.getCode());
        }
       // return options.ObjectToXML(accountbalance);
        
       finally{
    try{
        
        String query = "Update "+monthlyTable+" set ResponseCode='"+respcodes.getCode()+"', StatusMessage='"+respcodes.getMessage()+"', AvailableBalance='"+AvailableBalance+"'  where SessionID='"+sessionID+"' and MethodName='balanceenquiry'";
        db.Execute(query);
        
        }
        catch(Exception s)
           {
           
            }

}   
        //return op
       
        return nipssm.encrypt(options.ObjectToXML(response));
    }

    @WebMethod(operationName = "fundtransferAdvice_dc")
    public String fundtransferAdvice_dc(@WebParam(name = "creditrequest") String creditrequest) {
        
        FTAdviceCreditResponse response = new FTAdviceCreditResponse();
     String sessionID ="", monthlyTable = "";
        
        try {
       creditrequest = nipssm.decrypt(creditrequest);
       FTAdviceCreditRequest request = (FTAdviceCreditRequest) options.XMLToObject(creditrequest, new FTAdviceCreditRequest());
       
             List<Object> values = new ArrayList<>();
        List<String> headers = new ArrayList<>();
         //repopulating response object and logging request
         response.setAmount(request.getAmount());
        values.add(Double.parseDouble(request.getAmount().toString()));
        headers.add("Amount");
        
        response.setBeneficiaryAccountName(request.getBeneficiaryAccountName());
        values.add(request.getBeneficiaryAccountName());
        headers.add("BeneficiaryAccountName");
        
        response.setBeneficiaryAccountNumber(request.getBeneficiaryAccountNumber());
        values.add(request.getBeneficiaryAccountNumber());
        headers.add("BeneficiaryAccountNumber");
        
        response.setBeneficiaryBankVerificationNumber(request.getBeneficiaryBankVerificationNumber());
        values.add(request.getBeneficiaryBankVerificationNumber());
        headers.add("BeneficiaryBankVerificationNumber");
        
        response.setChannelCode(request.getChannelCode());
        values.add(request.getChannelCode());
        headers.add("ChannelCode");
        
        response.setDestinationInstitutionCode(request.getDestinationInstitutionCode());
        values.add(request.getDestinationInstitutionCode());
        headers.add("DestinationInstitutionCode");
        
        response.setOriginatorAccountName(request.getOriginatorAccountName());
        values.add(request.getOriginatorAccountName());
        headers.add("OriginatorAccountName");
        
        response.setOriginatorAccountNumber(request.getOriginatorAccountNumber());
        values.add(request.getOriginatorAccountNumber());
        headers.add("OriginatorAccountNumber");
        
         response.setOriginatorBankVerificationNumber(request.getOriginatorBankVerificationNumber());
         values.add(request.getOriginatorBankVerificationNumber());
         headers.add("OriginatorBankVerificationNumber");
         
        response.setNameEnquiryRef(request.getNameEnquiryRef());
        values.add(request.getNameEnquiryRef());
        headers.add("NameEnquiryRef");
        
         response.setOriginatorKYCLevel(request.getOriginatorKYCLevel());
         values.add(request.getOriginatorKYCLevel());
         headers.add("OriginatorKYCLevel");
       
        response.setSessionID(request.getSessionID());
        values.add(request.getSessionID());
        headers.add("SessionID");
        sessionID = request.getSessionID();
         
        response.setTransactionLocation(request.getTransactionLocation());
        values.add(request.getTransactionLocation());
        headers.add("TransactionLocation");
        
        response.setTransactionLocation(request.getNarration());
        values.add(request.getNarration());
        headers.add("Narration");
        
        response.setTransactionLocation(request.getPaymentReference());
        values.add(request.getPaymentReference());
        headers.add("PaymentReference");
        
       // request.

        
        values.add("INWARD");
        headers.add("TranDirection");
        
              values.add("fundtransferAdvice_dc");
            headers.add("MethodName");
        
            
            
        
        
       String datestr = request.getSessionID().substring(6,18);
       
       SimpleDateFormat sdf = new SimpleDateFormat("yymmddHHmmss");
 
        Date date = sdf.parse(datestr);
       
        
          values.add(date);
          headers.add("TransactionDate");
        
         SimpleDateFormat df = new SimpleDateFormat("MMMyyyy"); 
        
         monthlyTable = df.format(date)+"NIP_TRANSACTIONS";
        
        String createquery = options.getCreateNIPTableScript(monthlyTable);
        
        try{
            db.Execute(createquery);
        }
        catch(Exception r){
            
        }
     
       db.insertData(headers, values.toArray(),monthlyTable);
       
       respcodes = NIBBsResponseCodes.SUCCESS;
            response.setResponseCode(respcodes.getCode());
       

        } 
        
                catch(UnmarshalException r){
                
             respcodes = NIBBsResponseCodes.Format_error;
            response.setResponseCode(respcodes.getCode());
        }
        
        
        catch (Exception d) {
            
            respcodes = NIBBsResponseCodes.System_malfunction;
            response.setResponseCode(respcodes.getCode());
        }
        
          finally{
    try{
        
        String query = "Update "+monthlyTable+" set ResponseCode='"+respcodes.getCode()+"', StatusMessage='"+respcodes.getMessage()+"'  where SessionID='"+sessionID+"' and MethodName='fundtransferAdvice_dc'";
        db.Execute(query);
        
        }
        catch(Exception s)
           {
           
            }

        }
        //return options.ObjectToXML(response);
          return nipssm.encrypt(options.ObjectToXML(response));
        
    }
    
    

    @WebMethod(operationName = "fundtransferAdvice_dd")
    public String fundtransferAdvice_dd(@WebParam(name = "debitrequest") String debitrequest) {
         FTAdviceDebitResponse response = new FTAdviceDebitResponse();
        String sessionID ="", monthlyTable ="";
        try {
                       
           debitrequest = nipssm.decrypt(debitrequest);
           
     FTAdviceDebitRequest request = (FTAdviceDebitRequest) options.XMLToObject(debitrequest, new FTAdviceDebitRequest());
     
        List<Object> values = new ArrayList<>();
        List<String> headers = new ArrayList<>();
     
       //repopulating response object
    
        response.setAmount(request.getAmount());
        values.add(Double.parseDouble(request.getAmount().toString()));
        headers.add("Amount");
        
        response.setBeneficiaryAccountName(request.getBeneficiaryAccountName());
        values.add(request.getBeneficiaryAccountName());
        headers.add("BeneficiaryAccountName");
        
        response.setBeneficiaryAccountNumber(request.getBeneficiaryAccountNumber());
        values.add(request.getBeneficiaryAccountNumber());
        headers.add("BeneficiaryAccountNumber");
        
        response.setBeneficiaryBankVerificationNumber(request.getBeneficiaryBankVerificationNumber());
        values.add(request.getBeneficiaryBankVerificationNumber());
        headers.add("BeneficiaryBankVerificationNumber");
        
        response.setChannelCode(request.getChannelCode());
        values.add(request.getChannelCode());
        headers.add("ChannelCode");
        
        response.setNameEnquiryRef(request.getNameEnquiryRef());
        values.add(request.getNameEnquiryRef());
        headers.add("NameEnquiryRef");
        
        response.setDestinationInstitutionCode(request.getDestinationInstitutionCode());
        values.add(request.getDestinationInstitutionCode());
        headers.add("DestinationInstitutionCode");
        
       
        response.setSessionID(request.getSessionID());
        values.add(request.getSessionID());
        headers.add("SessionID");
        sessionID = request.getSessionID();
         
        response.setTransactionLocation(request.getTransactionLocation());
        values.add(request.getTransactionLocation());
        headers.add("TransactionLocation");
        
  
        
        values.add("INWARD");
        headers.add("TranDirection");
        
           values.add("fundtransfersingleitem_dd");
            headers.add("MethodName");
        
        
             String datestr = request.getSessionID().substring(6,18);
       
       SimpleDateFormat sdf = new SimpleDateFormat("yymmddHHmmss");
 
        Date date = sdf.parse(datestr);
       
        
          values.add(date);
          headers.add("TransactionDate");
        
         SimpleDateFormat df = new SimpleDateFormat("MMMyyyy"); 
        
         monthlyTable = df.format(date)+"NIP_TRANSACTIONS";
        
        String createquery = options.getCreateNIPTableScript(monthlyTable);
        
        try{
            db.Execute(createquery);
        }
        catch(Exception r){
            
        }
     
       db.insertData(headers, values.toArray(),monthlyTable);
       
        respcodes = NIBBsResponseCodes.SUCCESS;
            response.setResponseCode(respcodes.getCode());
       
       
        }
        catch(UnmarshalException r){
                
             respcodes = NIBBsResponseCodes.Format_error;
            response.setResponseCode(respcodes.getCode());
        }
        
        catch (Exception d) {
            
            respcodes = NIBBsResponseCodes.System_malfunction;
            response.setResponseCode(respcodes.getCode());
        }
       
              finally{
    try{
        
        String query = "Update "+monthlyTable+" set ResponseCode='"+respcodes.getCode()+"', StatusMessage='"+respcodes.getMessage()+"'  where SessionID='"+sessionID+"' and MethodName='fundtransferAdvice_dc'";
        db.Execute(query);
        
        }
        catch(Exception s)
           {
           
            }

        }
     
        
       // return options.ObjectToXML(response);
        
         return nipssm.encrypt(options.ObjectToXML(response));
    }
    

    @WebMethod(operationName = "amountblock")
    public String amountblock(@WebParam(name = "amountblockrequest") String amountblockrequest) {
        AmountBlockResponse response = new AmountBlockResponse();
       String sessionID ="", monthlyTable ="";

        try 
        {
                           
              amountblockrequest = nipssm.decrypt(amountblockrequest);   
              
                  AmountBlockRequest request = (AmountBlockRequest)options.XMLToObject(amountblockrequest, new AmountBlockRequest());

                  ofsParam param = new ofsParam();
                  String[] credentials = new String[] {options.getOfsuser(), options.getOfspass() };
                  param.setCredentials(credentials);
                  
                   List<Object> values = new ArrayList<>();
                      List<String> headers = new ArrayList<>();
     
       //repopulating response object
    
        response.setAmount(request.getAmount());
        values.add(Double.parseDouble(request.getAmount().toString()));
        headers.add("Amount");
        
        response.setTargetAccountName(request.getTargetAccountName());
        values.add(request.getTargetAccountName());
        headers.add("TargetAccountName");
        
        response.setReferenceCode(request.getReferenceCode());
        values.add(request.getReferenceCode());
        headers.add("ReferenceCode");
        
        response.setChannelCode(request.getChannelCode());
        values.add(request.getChannelCode());
        headers.add("ChannelCode");
                
        response.setNarration(request.getNarration());
        values.add(request.getNarration());
        headers.add("Narration");
        
        response.setDestinationInstitutionCode(request.getDestinationInstitutionCode());
        values.add(request.getDestinationInstitutionCode());
        headers.add("DestinationInstitutionCode");
        
        response.setReasonCode(request.getReasonCode());
        values.add(request.getDestinationInstitutionCode());
        headers.add("ReasonCode");
        
       
        response.setSessionID(request.getSessionID());
        values.add(request.getSessionID());
        headers.add("SessionID");
        sessionID = request.getSessionID();
         
        response.setTargetAccountNumber(request.getTargetAccountNumber());
        values.add(request.getTargetAccountNumber());
        headers.add("TransactionLocation");
        
  
        
        values.add("INWARD");
        headers.add("TranDirection");
        
           values.add("amountblock");
            headers.add("MethodName");
 
                  param.setOperation("AC.LOCKED.EVENTS");
                  param.setTransaction_id("");
                  String[] ofsoptions = new String[] { "", "I", "PROCESS", "2", "0" };
                  param.setOptions(ofsoptions);

                  List<DataItem> items = new LinkedList<>();
                  DataItem item = new DataItem();
                  item.setItemHeader("ACCOUNT.NUMBER");
                  item.setItemValues(new String[] {request.getTargetAccountNumber()});
                  items.add(item);


                  item = new DataItem();
                  item.setItemHeader("LOCKED.AMOUNT");
                  item.setItemValues(new String[] {request.getAmount().toString()});
                  items.add(item);

                  item = new DataItem();
                  item.setItemHeader("TRANSACTION.REF");
                  item.setItemValues(new String[] {""});
                  items.add(item);
//
//                  item = new DataItem();
//                  item.setItemHeader("REASON.CODE");
//                  item.setItemValues(new String[] {request.getReasonCode()});
//                  items.add(item);
                   if(request.getNarration().length()>16){
                   request.setNarration(request.getNarration().substring(16));
                  }

                  item = new DataItem();
                  item.setItemHeader("DESCRIPTION");
                  item.setItemValues(new String[] {request.getNarration()});
                  items.add(item);

                  param.setDataItems(items);
             
             String datestr = request.getSessionID().substring(6,18);
       
       SimpleDateFormat sdf = new SimpleDateFormat("yymmddHHmmss");
 
        Date date = sdf.parse(datestr);
       
        
          values.add(date);
          headers.add("TransactionDate");
        
         SimpleDateFormat df = new SimpleDateFormat("MMMyyyy"); 
        
         monthlyTable = df.format(date)+"NIP_TRANSACTIONS";
        
        String createquery = options.getCreateNIPTableScript(monthlyTable);
        
        try{
            db.Execute(createquery);
        }
        catch(Exception r){
            
        }
     
       db.insertData(headers, values.toArray(),monthlyTable);         
                  
                     //ACLK1308680628
                  String ofstr = t24.generateOFSTransactString(param);

                   String result = t24.PostMsg(ofstr);

                  if(t24.IsSuccessful(result)){
                      
                     respcodes = NIBBsResponseCodes.SUCCESS;
                     response.setResponseCode(respcodes.getCode());
                     
                     
                     String t24ref = result.split("/")[0];
                     
                  param.setOperation("NIBBS.FT.REF.TABLE");
                  param.setTransaction_id(request.getReferenceCode()); 
                  
                  items.clear();
                  
                  item = new DataItem();
                  item.setItemHeader("T24.ID");
                  item.setItemValues(new String[] {t24ref});
                  items.add(item);


//                  item = new DataItem();
//                  item.setItemHeader("LOCKED.AMOUNT");
//                  item.setItemValues(new String[] {request.getAmount().toString()});
//                  items.add(item);
//
//                  item = new DataItem();
//                  item.setItemHeader("TRANSACTION.REF");
//                  item.setItemValues(new String[] {t24ref});
//                  items.add(item);
//
//                  item = new DataItem();
//                  item.setItemHeader("REASON.CODE");
//                  item.setItemValues(new String[] {request.getReasonCode()});
//                  items.add(item);
//                   if(request.getNarration().length()>16){
//                   request.setNarration(request.getNarration().substring(16));
//                  }
//
//                  item = new DataItem();
//                  item.setItemHeader("DESCRIPTION");
//                  item.setItemValues(new String[] {request.getNarration()});
//                  items.add(item);
                     
                    ofstr = t24.generateOFSTransactString(param);

                    result = t24.PostMsg(ofstr);
                     

              }
                  else{
                      
                        if(result.contains("/")){
                respcodes = options.getNIBBsCode(result.split("/")[3]);
                
                if(respcodes==NIBBsResponseCodes.System_malfunction){
                    throw new Exception (result);
                }
                  response.setResponseCode(respcodes.getCode());
             
               }
               else{
                   throw new Exception (result);
               }
               

                  }

               }
                    catch(UnmarshalException r){
                
             respcodes = NIBBsResponseCodes.Format_error;
            response.setResponseCode(respcodes.getCode());
        }
               
               catch (Exception d) {
                   options.getServiceLogger("service_monitor").LogError(d.getMessage(), d, Level.ERROR);
                   respcodes=NIBBsResponseCodes.System_malfunction;
                   
                   response.setResponseCode(respcodes.getCode());
               //setReasonCode("1111");
               }
        
        
              finally{
    try{
        
        String query = "Update "+monthlyTable+" set ResponseCode='"+respcodes.getCode()+"', StatusMessage='"+respcodes.getMessage()+"'  where SessionID='"+sessionID+"' and MethodName='amountblock'";
        db.Execute(query);
        
        }
        catch(Exception s)
           {
           
            }

        }
     
              // return options.ObjectToXML(response);
               
                return nipssm.encrypt(options.ObjectToXML(response)); 
           }

    
    
           @WebMethod(operationName = "amountunblock")
           public String amountunblock(@WebParam(name = "amountunblockrequest") String amountunblockrequest) {
               AmountUnblockResponse response = new AmountUnblockResponse();
          String sessionID ="", monthlyTable ="";

               try {

                   amountunblockrequest = nipssm.decrypt(amountunblockrequest);
                   
                  AmountUnblockRequest request = (AmountUnblockRequest) options.XMLToObject(amountunblockrequest, new AmountUnblockRequest());

            ofsParam param = new ofsParam();
            String[] credentials = new String[] { options.getOfsuser(), options.getOfspass() };
               param.setCredentials(credentials);
                  param.setOperation("AC.LOCKED.EVENTS");
                 
                  String[] ofsoptions = new String[] { "", "R", "PROCESS", "2", "0" };
                  param.setOptions(ofsoptions);
                  
                  
                       List<Object> values = new ArrayList<>();
                      List<String> headers = new ArrayList<>();
     
       //repopulating response object
    
        response.setAmount(request.getAmount());
        values.add(Double.parseDouble(request.getAmount().toString()));
        headers.add("Amount");
        
        response.setTargetAccountName(request.getTargetAccountName());
        values.add(request.getTargetAccountName());
        headers.add("TargetAccountName");
        
        response.setReferenceCode(request.getReferenceCode());
        values.add(request.getReferenceCode());
        headers.add("ReferenceCode");
        
        response.setChannelCode(request.getChannelCode());
        values.add(request.getChannelCode());
        headers.add("ChannelCode");
                
        response.setNarration(request.getNarration());
        values.add(request.getNarration());
        headers.add("Narration");
        
        response.setDestinationInstitutionCode(request.getDestinationInstitutionCode());
        values.add(request.getDestinationInstitutionCode());
        headers.add("DestinationInstitutionCode");
        
        response.setReasonCode(request.getReasonCode());
        values.add(request.getDestinationInstitutionCode());
        headers.add("ReasonCode");
        
       
        response.setSessionID(request.getSessionID());
        values.add(request.getSessionID());
        headers.add("SessionID");
        sessionID = request.getSessionID();
         
        response.setTargetAccountNumber(request.getTargetAccountNumber());
        values.add(request.getTargetAccountNumber());
        headers.add("TransactionLocation");
        
  
        
        values.add("INWARD");
        headers.add("TranDirection");
        
           values.add("amountunblock");
            headers.add("MethodName");
            
            
            
            
               
             String datestr = request.getSessionID().substring(6,18);
       
       SimpleDateFormat sdf = new SimpleDateFormat("yymmddHHmmss");
 
        Date date = sdf.parse(datestr);
       
        
          values.add(date);
          headers.add("TransactionDate");
        
         SimpleDateFormat df = new SimpleDateFormat("MMMyyyy"); 
        
         monthlyTable = df.format(date)+"NIP_TRANSACTIONS";
        
        String createquery = options.getCreateNIPTableScript(monthlyTable);
        
        try{
            db.Execute(createquery);
        }
        catch(Exception r){
            
        }
     
       db.insertData(headers, values.toArray(),monthlyTable);         
            
            
            

                  List<DataItem> items = new LinkedList<>();
       DataItem item = new DataItem();
       
            
          ofsParam paramv = new ofsParam(); 
           
         paramv.setTransaction_id(request.getReferenceCode());
         ofsoptions[2] ="VALIDATE";     
         
         paramv.setOptions(ofsoptions);
         paramv.setDataItems(items);
         
         paramv.setOperation("NIBBS.FT.REF.TABLE");
         
         paramv.setCredentials(credentials);
         
         String ofstrv = t24.generateOFSTransactString(paramv);
         String resultv =  t24.PostMsg(ofstrv);
         
         resultv = resultv.substring(resultv.indexOf("T24.ID:1:1="));
         
         String t24ref = resultv.split(",")[0].split("=")[1];
         
         ofsoptions[2] ="PROCESS"; 
       
       
       
       item.setItemHeader("ACCOUNT.NUMBER");
                  item.setItemValues(new String[] {request.getTargetAccountNumber()});
                  items.add(item);     

       param.setTransaction_id(t24ref);
 
       param.setDataItems(items);
                     //ACLK1308680628
                  String ofstr = t24.generateOFSTransactString(param);

                 String result = t24.PostMsg(ofstr);

              if(t24.IsSuccessful(result)){
                      
                     respcodes = NIBBsResponseCodes.SUCCESS;
                     response.setResponseCode(respcodes.getCode());

              }
                  else{
                      
                        if(result.contains("/")){
                respcodes = options.getNIBBsCode(result.split("/")[3]);
                
                if(respcodes==NIBBsResponseCodes.System_malfunction){
                    throw new Exception (result);
                }
                  response.setResponseCode(respcodes.getCode());
             
               }
               else{
                   throw new Exception (result);
               }
               

                  }

               }
                    catch(UnmarshalException r){
                
             respcodes = NIBBsResponseCodes.Format_error;
            response.setResponseCode(respcodes.getCode());
            
        } catch (Exception d) {
                   options.getServiceLogger("service_monitor").LogError(d.getMessage(), d, Level.ERROR); 
                   response.setResponseCode("12");
               }
                            finally{
    try{
        
        String query = "Update "+monthlyTable+" set ResponseCode='"+respcodes.getCode()+"', StatusMessage='"+respcodes.getMessage()+"'  where SessionID='"+sessionID+"' and MethodName='amountunblock'";
        db.Execute(query);
        
        }
        catch(Exception s)
           {
           
            }

        }
              // return options.ObjectToXML(response);
               
                 return nipssm.encrypt(options.ObjectToXML(response));
           }

           
           
           @WebMethod(operationName = "accountblock")
           public String accountblock(@WebParam(name = "AccountBlockIn") String AccountBlockIn) {
               AccountBlockResponse response = new AccountBlockResponse();
              
               try{

             AccountBlockIn = nipssm.decrypt(AccountBlockIn);
                   
            AccountBlockRequest request = (AccountBlockRequest) options.XMLToObject(AccountBlockIn, new AccountBlockRequest());  

             ofsParam param = new ofsParam();
             String[] credentials = new String[] {options.getOfsuser(), options.getOfspass() };
             param.setCredentials(credentials);
             param.setOperation("POSTING.RESTRICT");
             param.setTransaction_id("");
             String[] ofsoptions = new String[] { "", "I", "PROCESS", "2", "0" };
             param.setOptions(ofsoptions);

            List<DataItem> items = new LinkedList<>();
            DataItem item = new DataItem();
            item.setItemHeader("ACCOUNT.NUMBER");
             item.setItemValues(new String[] {request.getTargetAccountNumber()});
             items.add(item);


              item = new DataItem();
              item.setItemHeader("REASON.CODE");
              item.setItemValues(new String[] {request.getReasonCode()});
           
              items.add(item);



                  param.setDataItems(items);
                     //ACLK1308680628
                  String ofstr = t24.generateOFSTransactString(param);

                  String result = t24.PostMsg(ofstr);

                 
              if(t24.IsSuccessful(result)){
                      
                     respcodes = NIBBsResponseCodes.SUCCESS;
                     response.setResponseCode(respcodes.getCode());

              }
                  else{
                      
                        if(result.contains("/")){
                respcodes = options.getNIBBsCode(result.split("/")[3]);
                
                if(respcodes==NIBBsResponseCodes.System_malfunction){
                    throw new Exception (result);
                }
                  response.setResponseCode(respcodes.getCode());
             
               }
               else{
                   throw new Exception (result);
               }
               

                  }

               }
                    catch(UnmarshalException r)
                    {
                
             respcodes = NIBBsResponseCodes.Format_error;
            response.setResponseCode(respcodes.getCode());
            
                 }
               catch(Exception d){
                  respcodes=NIBBsResponseCodes.System_malfunction;
                  response.setResponseCode(respcodes.getCode());
               }
              // return options.ObjectToXML(response);
               
                return nipssm.encrypt(options.ObjectToXML(response));
    }
    
    @WebMethod(operationName = "accountunblock")
    public String accountunblock(@WebParam(name = "AccountUnblockIn") String AccountUnblockIn) {
        AccountUnblockResponse response = new AccountUnblockResponse();
      
        try{
            
           AccountUnblockIn = nipssm.decrypt(AccountUnblockIn);

            AccountUnblockRequest request = (AccountUnblockRequest) options.XMLToObject(AccountUnblockIn, new AccountUnblockRequest());
            
             ofsParam param = new ofsParam();
             String[] credentials = new String[] { options.getOfsuser(), options.getOfspass() };
             param.setCredentials(credentials);
             param.setOperation("POSTING.RESTRICT");
             param.setTransaction_id(request.getReferenceCode());
             String[] ofsoptions = new String[] { "", "R", "PROCESS", "2", "0" };
             param.setOptions(ofsoptions);
           
           List<DataItem> items = new LinkedList<>();
           DataItem item = new DataItem();
           item.setItemHeader("ACCOUNT.NUMBER");
           item.setItemValues(new String[] {request.getTargetAccountNumber()});
           items.add(item);     

           
           param.setDataItems(items);
              //ACLK1308680628
           String ofstr = t24.generateOFSTransactString(param);

           String result = t24.PostMsg(ofstr);
       
              if(t24.IsSuccessful(result)){
                      
                     respcodes = NIBBsResponseCodes.SUCCESS;
                     response.setResponseCode(respcodes.getCode());

              }
                  else{
                      
                        if(result.contains("/")){
                respcodes = options.getNIBBsCode(result.split("/")[3]);
                
                if(respcodes==NIBBsResponseCodes.System_malfunction){
                    throw new Exception (result);
                }
                  response.setResponseCode(respcodes.getCode());
             
               }
               else{
                   throw new Exception (result);
               }
               

                  }

               }
                    catch(UnmarshalException r){
                
             respcodes = NIBBsResponseCodes.Format_error;
            response.setResponseCode(respcodes.getCode());
            
        }catch(Exception d){
            respcodes=NIBBsResponseCodes.System_malfunction;
            response.setResponseCode(respcodes.getCode());
        }
        //return options.ObjectToXML(response);
        
         return nipssm.encrypt(options.ObjectToXML(response));
    }
    
    @WebMethod(operationName = "financialinstitutionlist")
    public String financialinstitutionlist(@WebParam(name = "FinancialInstitutionListIn") String FinancialInstitutionListIn) {
        FinancialInstitutionListResponse response = new FinancialInstitutionListResponse();
       
        try{
            FinancialInstitutionListIn = nipssm.decrypt(FinancialInstitutionListIn);
            
            FinancialInstitutionListRequest request = (FinancialInstitutionListRequest)options.XMLToObject(FinancialInstitutionListIn, new FinancialInstitutionListRequest());
            
            
                       List<Object> values = new ArrayList<>();
                      List<String> headers = new ArrayList<>();
                      
  
                       headers.add("BatchNumber");                                 
                       headers.add("InstitutionCode");
                       headers.add("InstitutionName");                                 
                       headers.add("Category");
                       
                       for(Record record:request.getRecord()){
                      values.clear();
                      
                      values.add(request.getHeader().getBatchNumber());
                      values.add(record.getInstitutionCode());
                      values.add(record.getInstitutionName());
                      values.add(record.getCategory());
                      try{
                      db.insertData(headers, values.toArray(),"NIP_Institutions"); 
                      }
                      catch(SQLServerException d){
                          if (d.getMessage().contains("Cannot insert duplicate key")){
                         
                          }
                          else{
                              throw(d);
                          }
                      }
                      
                       }
                       
                       response.setBatchNumber(request.getHeader().getBatchNumber());
                       response.setChannelCode(request.getHeader().getChannelCode());
                       response.setDestinationInstitutionCode("0087654");
                       response.setNumberOfRecords(request.getHeader().getNumberOfRecords());
                       respcodes = NIBBsResponseCodes.SUCCESS;
                       response.setResponseCode(respcodes.getCode());
                       
            
        }                   
        catch(UnmarshalException r){
                
             respcodes = NIBBsResponseCodes.Format_error;
            response.setResponseCode(respcodes.getCode());
            
        }catch(Exception d){
            respcodes=NIBBsResponseCodes.System_malfunction;
            response.setResponseCode(respcodes.getCode());
        }
        //return options.ObjectToXML(response);
        
          return nipssm.encrypt(options.ObjectToXML(response));
    }
    
    
    @WebMethod(operationName = "mandateadvice")
    public String mandateadvice(@WebParam(name = "MandateAdviceIn") String MandateAdviceIn) {
        MandateAdviceResponse MandateAdviceOut = new MandateAdviceResponse();
        Gson gson = new Gson();
        try{
           JSONObject object = XML.toJSONObject(MandateAdviceIn);       
           object = (JSONObject)object.get("MandateAdviceRequest");
           String json = object.toString();
           MandateAdviceRequest request = (MandateAdviceRequest)gson.fromJson(json, MandateAdviceRequest.class); 
        }catch(Exception d){
            MandateAdviceOut.setResponseCode("32");
        }
        return options.ObjectToXML(MandateAdviceOut);

    }
    
   public static String escape(String text){
      
      return text.replace("&", "&amp;").replace("\"", "&quot;").replace("'", "&apos;").replace("<", "&lt;").replace(">", "&gt;");
  }
       
//   
//    @WebMethod(operationName = "PGPEncryption")
//    public String PGPEncryption(@WebParam(name = "message") String message, String mode) {
//        
//        switch(mode.toLowerCase()){
//            case"dec":
//                String resp =  nipssm.decrypt(message);
//                return resp;
//                
//            default:
//                return nipssm.encrypt(message);
//        
//    }
//    
//    
//    
//    
//    }
   
}


