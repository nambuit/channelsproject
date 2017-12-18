/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nibbsnip.service;

import com.google.gson.Gson;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import static jdk.nashorn.tools.ShellFunctions.input;
import nibbsofis.service.AirTimeTopupNotificationRequest;
import org.json.JSONObject;
import org.json.XML;

/**
 *
 * @author Temitope
 */
@WebService(serviceName = "NIBBSNIPInterface")
public class NIBBSNIPInterface {

     @WebMethod(operationName = "nameenquirysingleitem")
    public String nameenquirysingleitem(@WebParam(name = "nesinglerequest") String input) {
        
        NESingleResponse nameEnquiry = new NESingleResponse();
         Gson gson = new Gson();
        try{
          
             JSONObject object = XML.toJSONObject(input);       
   
//    
        object = (JSONObject)object.get("NESingleRequest");
//    
   String json = object.toString();
     NESingleRequest request = (NESingleRequest) gson.fromJson(json, NESingleRequest.class);
            
    }
        catch(Exception e){
        nameEnquiry.setResponseCode("32");
    }
        return ObjectToXML(nameEnquiry);
}
    
    
    
    
      @WebMethod(operationName = "fundtransfersingleitem_dc")
    public FTSingleCreditResponse fundtransfersingleitem_dc(@WebParam(name = "ftsinglecreditrequest") FTSingleCreditRequest ftsinglecreditrequest) {
        FTSingleCreditResponse creditRequest = new FTSingleCreditResponse();
        try{
            
    }
        catch(Exception e){
        creditRequest.setResponseCode("33");
    }
        return creditRequest;
}
    
      @WebMethod(operationName = "fundtransfersingleitem_dd")
    public FTSingleDebitResponse fundtransfersingleitem_dd(@WebParam(name = "ftsingledebitrequest") FTSingleDebitRequest ftsingledebitrequest) {
        FTSingleDebitResponse debitRequest = new FTSingleDebitResponse();
        try{
            
    }
        catch(Exception e){
        debitRequest.setResponseCode("34");
    }
        return debitRequest;
}
    
        @WebMethod(operationName = "txnstatusquerysingleitem")
    public TSQuerySingleResponse txnstatusquerysingleitem(@WebParam(name = "tsquerysinglerequest") TSQuerySingleRequest tsquerysinglerequest) {
        TSQuerySingleResponse txnRequest = new TSQuerySingleResponse();
        try{
            
    }
        catch(Exception e){
        txnRequest.setResponseCode("34");
    }
        return txnRequest;
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
    public AccountBlockResponse accountblock(@WebParam(name = "AccountBlockIn") AccountBlockRequest AccountBlockIn) {
        AccountBlockResponse AccountBlockOut = new AccountBlockResponse();
        try{
            
        }catch(Exception d){
            
        }
        return AccountBlockOut;
    }
    
    @WebMethod(operationName = "accountunblock")
    public AccountUnblockResponse accountunblock(@WebParam(name = "AccountUnblockIn") AccountUnblockRequest AccountUnblockIn) {
        AccountUnblockResponse AccountUnblockOut = new AccountUnblockResponse();
        try{
            
        }catch(Exception d){
            
        }
        return AccountUnblockOut;
    }
    
    @WebMethod(operationName = "financialinstitutionlist")
    public List<FinancialInstitutionListResponse> financialinstitutionlist(@WebParam(name = "FinancialInstitutionListIn") FinancialInstitutionListRequest FinancialInstitutionListIn) {
        List<FinancialInstitutionListResponse> FinancialInstitutionListOut = new ArrayList<>();
        try{
            
        }catch(Exception d){
            
        }
        return FinancialInstitutionListOut;
    }
    
    @WebMethod(operationName = "mandateadvice")
    public MandateAdviceResponse mandateadvice(@WebParam(name = "MandateAdviceIn") MandateAdviceRequest MandateAdviceIn) {
        MandateAdviceResponse MandateAdviceOut = new MandateAdviceResponse();
        try{
            
        }catch(Exception d){
            
        }
        return MandateAdviceOut;

    }
    
    
       private String ObjectToXML(Object object){
       try{
    JAXBContext jcontext = JAXBContext.newInstance(object.getClass());
    Marshaller m = jcontext.createMarshaller();
    m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
    StringWriter sw = new StringWriter();
    m.marshal(object, sw);
    return sw.toString();
       }
       catch(Exception y){
           return(y.getMessage());
       }


}
       
}


