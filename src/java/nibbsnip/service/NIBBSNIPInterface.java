/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nibbsnip.service;

import com.google.gson.Gson;
import java.io.StringWriter;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
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
        return ObjectToXML(nameEnquiry);
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
        return ObjectToXML(creditRequest);
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
        return ObjectToXML(debitRequest);
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
        return ObjectToXML(txnRequest);
    }

    @WebMethod(operationName = "balanceenquiry")
    public String balanceenquiry(@WebParam(name = "balancerequest") String balancerequest) {
        BalanceEnquiryResponse accountbalance = new BalanceEnquiryResponse();
        Gson gson = new Gson();

        try {
             JSONObject object = XML.toJSONObject(balancerequest);       
   
//    
        object = (JSONObject)object.get("BalanceEnquiryResponse");
//    
   String json = object.toString();
     BalanceEnquiryRequest request = (BalanceEnquiryRequest) gson.fromJson(json, BalanceEnquiryRequest.class);
        } catch (Exception d) {
            accountbalance.setResponseCode("12");
        }
        return ObjectToXML(accountbalance);
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
        return ObjectToXML(creditresponse);
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
        return ObjectToXML(debitresponse);
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
        return ObjectToXML(amountblockresponse);
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
        return ObjectToXML(amountunblockresponse);
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
        return ObjectToXML(AccBlockReq);
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
        return ObjectToXML(AccountUnblockOut);
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
        return ObjectToXML(FinancialInstitutionListOut);
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
        return ObjectToXML(MandateAdviceOut);

    }
    
    
       private String ObjectToXML(Object object){
       try{
    JAXBContext jcontext = JAXBContext.newInstance(object.getClass());
    Marshaller m = jcontext.createMarshaller();
    m.setProperty(Marshaller.JAXB_ENCODING, Boolean.TRUE);
    StringWriter sw = new StringWriter();
    m.marshal(object, sw);
    return sw.toString();
       }
       catch(Exception y){
           return(y.getMessage());
       }


}
       
}


