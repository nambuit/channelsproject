/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nibbsnip.service;

import com.google.gson.Gson;
import java.math.BigDecimal;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import org.apache.log4j.Level;
import org.json.JSONObject;
import org.json.XML;
import org.t24.AppParams;
import org.t24.T24Link;
import org.t24.T24TAFCLink;
import org.t24.T24TAFJLink;

/**
 *
 * @author Temitope
 */
@WebService(serviceName = "NIBBSNIPInterface")
public class NIBBSNIPInterface {
    AppParams options;  
     T24Link t24;       
     String logfilename = "NIBBSNIPInterface";
       
    
    
    public NIBBSNIPInterface(){
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

     @WebMethod(operationName = "nameenquirysingleitem")
    public String nameenquirysingleitem(@WebParam(name = "nesinglerequest") String input) {
        
        NESingleResponse nameEnquiry = new NESingleResponse();
        
         Gson gson = new Gson();
        try{
            
           
          
        JSONObject object = XML.toJSONObject(input);       

        object = (JSONObject)object.get("NESingleRequest");
//    
   String json = object.toString();
     NESingleRequest request = (NESingleRequest) gson.fromJson(json, NESingleRequest.class);
     
    nameEnquiry.setAccountNumber(request.getAccountNumber());
     nameEnquiry.setResponseCode("00");
      nameEnquiry.setDestinationInstitutionCode(request.getDestinationInstitutionCode());
            
    }
        catch(Exception e){
        nameEnquiry.setResponseCode("32");
    }
        return options.ObjectToXML(nameEnquiry);
}
    
      @WebMethod(operationName = "fundtransfersingleitem_dc")
    public String fundtransfersingleitem_dc(@WebParam(name = "ftsinglecreditrequest") String ftsinglecreditrequest) {
        FTSingleCreditResponse creditRequest = new FTSingleCreditResponse();
        Gson gson = new Gson();
        try{
            
             JSONObject object = XML.toJSONObject(ftsinglecreditrequest);       
   
//    
        object = (JSONObject)object.get("FTSingleCreditRequest");
//    
   String json = object.toString();
     FTSingleCreditRequest requestCredit = (FTSingleCreditRequest) gson.fromJson(json, FTSingleCreditRequest.class);
            
            
    }
        catch(Exception e){
        creditRequest.setResponseCode("33");
    }
        return options.ObjectToXML(creditRequest);
}
    
      @WebMethod(operationName = "fundtransfersingleitem_dd")
    public String fundtransfersingleitem_dd(@WebParam(name = "ftsingledebitrequest") String ftsingledebitrequest) {
        FTSingleDebitResponse debitRequest = new FTSingleDebitResponse();
        Gson gson = new Gson();
        
        try{
            
             JSONObject object = XML.toJSONObject(ftsingledebitrequest);       
   
//    
        object = (JSONObject)object.get("FTSingleDebitRequest");
//    
   String json = object.toString();
     FTSingleDebitRequest requestDebit = (FTSingleDebitRequest) gson.fromJson(json, FTSingleDebitRequest.class);
            
            
            
    }
        catch(Exception e){
        debitRequest.setResponseCode("34");
    }
        return options.ObjectToXML(debitRequest);
}
    
        @WebMethod(operationName = "txnstatusquerysingleitem")
    public String txnstatusquerysingleitem(@WebParam(name = "tsquerysinglerequest") String tsquerysinglerequest) {
        TSQuerySingleResponse txnRequest = new TSQuerySingleResponse();
        Gson gson = new Gson();
        try{
            
            JSONObject object = XML.toJSONObject(tsquerysinglerequest);       
   
//    
        object = (JSONObject)object.get("TSQuerySingleRequest");
//    
   String json = object.toString();
     TSQuerySingleRequest requestQuery = (TSQuerySingleRequest) gson.fromJson(json, TSQuerySingleRequest.class);
            
            
    }
        catch(Exception e){
        txnRequest.setResponseCode("34");
    }
        return options.ObjectToXML(txnRequest);
    }

    @WebMethod(operationName = "balanceenquiry")
    public String balanceenquiry(@WebParam(name = "balancerequest") String balancerequest) {
        BalanceEnquiryResponse accountbalance = new BalanceEnquiryResponse();
        

        try {
       
     BalanceEnquiryRequest request = (BalanceEnquiryRequest) options.XMLToObject(balancerequest,new BalanceEnquiryRequest());
     
    accountbalance.setAuthorizationCode(request.getAuthorizationCode());
     accountbalance.setAvailableBalance(BigDecimal.valueOf(23345.34));
      accountbalance.setChannelCode(request.getChannelCode());
       accountbalance.setAuthorizationCode(request.getAuthorizationCode());
        accountbalance.setTargetAccountName("Olamide and Co Investment Limited");
         accountbalance.setDestinationInstitutionCode(request.getDestinationInstitutionCode());
          accountbalance.setSessionID(request.getSessionID());
           accountbalance.setTargetBankVerificationNumber(request.getTargetBankVerificationNumber());
            accountbalance.setResponseCode("00");
             accountbalance.setTargetAccountNumber(request.getTargetAccountNumber());
            
     
        } catch (Exception d) {
            accountbalance.setResponseCode("12");
        }
        return options.ObjectToXML(accountbalance);
    }

    @WebMethod(operationName = "fundtransferAdvice_dc")
    public String fundtransferAdvice_dc(@WebParam(name = "creditrequest") String creditrequest) {
        FTAdviceCreditResponse creditresponse = new FTAdviceCreditResponse();
        Gson gson = new Gson();

        try {
             JSONObject object = XML.toJSONObject(creditrequest);       
//    
        object = (JSONObject)object.get("FTAdviceCreditResponse");
//    
   String json = object.toString();
     FTAdviceCreditRequest request = (FTAdviceCreditRequest) gson.fromJson(json, FTAdviceCreditRequest.class);
        } catch (Exception d) {
            creditresponse.setResponseCode("12");
        }
        return options.ObjectToXML(creditresponse);
    }

    @WebMethod(operationName = "fundtransferAdvice_dd")
    public String fundtransferAdvice_dd(@WebParam(name = "debitrequest") String debitrequest) {
        FTAdviceDebitResponse debitresponse = new FTAdviceDebitResponse();
        
        Gson gson = new Gson();

        try {
             JSONObject object = XML.toJSONObject(debitrequest);       
//    
        object = (JSONObject)object.get("FTAdviceDebitResponse");
//    
   String json = object.toString();
     FTAdviceDebitRequest request = (FTAdviceDebitRequest) gson.fromJson(json, FTAdviceDebitRequest.class);
        } catch (Exception d) {
            debitresponse.setResponseCode("12");
        }
        return options.ObjectToXML(debitresponse);
    }

    @WebMethod(operationName = "amountblock")
    public String amountblock(@WebParam(name = "amountblockrequest") String amountblockrequest) {
        AmountBlockResponse amountblockresponse = new AmountBlockResponse();
        Gson gson = new Gson();

        try {
             JSONObject object = XML.toJSONObject(amountblockrequest);       
//    
        object = (JSONObject)object.get("AmountBlockResponse");
//    
   String json = object.toString();
     AmountBlockRequest request = (AmountBlockRequest) gson.fromJson(json, AmountBlockRequest.class);
        } catch (Exception d) {
            amountblockresponse.setResponseCode("12");
        }
        return options.ObjectToXML(amountblockresponse);
    }

    @WebMethod(operationName = "amountunblock")
    public String amountunblock(@WebParam(name = "amountunblockrequest") String amountunblockrequest) {
        AmountUnblockResponse amountunblockresponse = new AmountUnblockResponse();
        Gson gson = new Gson();

        try {
             JSONObject object = XML.toJSONObject(amountunblockrequest);       
//    
        object = (JSONObject)object.get("AmountUnblockResponse");
//    
   String json = object.toString();
     AmountUnblockRequest request = (AmountUnblockRequest) gson.fromJson(json, AmountUnblockRequest.class);
        } catch (Exception d) {
            amountunblockresponse.setResponseCode("12");
        }
        return options.ObjectToXML(amountunblockresponse);
    }
    
    @WebMethod(operationName = "accountblock")
    public String accountblock(@WebParam(name = "AccountBlockIn") String input) {
        AccountBlockResponse AccBlockReq = new AccountBlockResponse();
       Gson gson = new Gson();
        try{
          
             JSONObject object = XML.toJSONObject(input);       

        object = (JSONObject)object.get("AccountBlockRequest");
//    
   String json = object.toString();
     AccountBlockRequest request = (AccountBlockRequest) gson.fromJson(json, AccountBlockRequest.class);  
        }catch(Exception d){
            AccBlockReq.setResponseCode("32");
        }
        return options.ObjectToXML(AccBlockReq);
    }
    
    @WebMethod(operationName = "accountunblock")
    public String accountunblock(@WebParam(name = "AccountUnblockIn") String AccountUnblockIn) {
        AccountUnblockResponse AccountUnblockOut = new AccountUnblockResponse();
        Gson gson = new Gson();
        try{
            
            JSONObject object = XML.toJSONObject(AccountUnblockIn);       
            object = (JSONObject)object.get("AmountUnblockRequest");
            String json = object.toString();
            AmountUnblockRequest request = (AmountUnblockRequest)gson.fromJson(json, AmountUnblockRequest.class);
            
        }catch(Exception d){
            AccountUnblockOut.setResponseCode("32");
        }
        return options.ObjectToXML(AccountUnblockOut);
    }
    
    @WebMethod(operationName = "financialinstitutionlist")
    public String financialinstitutionlist(@WebParam(name = "FinancialInstitutionListIn") String FinancialInstitutionListIn) {
        FinancialInstitutionListResponse FinancialInstitutionListOut = new FinancialInstitutionListResponse();
        Gson gson = new Gson();
        try{
            JSONObject object = XML.toJSONObject(FinancialInstitutionListIn);       
            object = (JSONObject)object.get("FinancialInstitutionListRequest");
            String json = object.toString();
            FinancialInstitutionListRequest request = (FinancialInstitutionListRequest)gson.fromJson(json, FinancialInstitutionListRequest.class);
        }catch(Exception d){
            FinancialInstitutionListOut.setResponseCode("32");
        }
        return options.ObjectToXML(FinancialInstitutionListOut);
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
    

       
}


