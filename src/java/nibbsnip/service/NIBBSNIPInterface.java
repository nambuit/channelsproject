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
    public BalanceEnquiryResponse balanceenquiry(@WebParam(name = "balancerequest") BalanceEnquiryRequest balancerequest) {
        BalanceEnquiryResponse accountbalance = new BalanceEnquiryResponse();

        try {

        } catch (Exception d) {
            accountbalance.setResponseCode("12");
        }
        return accountbalance;
    }

    @WebMethod(operationName = "fundtransferAdvice_dc")
    public FTAdviceCreditResponse fundtransferAdvice_dc(@WebParam(name = "creditrequest") FTAdviceCreditRequest creditrequest) {
        FTAdviceCreditResponse creditresponse = new FTAdviceCreditResponse();

        try {

        } catch (Exception d) {
            creditresponse.setResponseCode("12");
        }
        return creditresponse;
    }

    @WebMethod(operationName = "fundtransferAdvice_dd")
    public FTAdviceDebitResponse fundtransferAdvice_dd(@WebParam(name = "debitrequest") FTAdviceDebitRequest debitrequest) {
        FTAdviceDebitResponse debitresponse = new FTAdviceDebitResponse();

        try {

        } catch (Exception d) {
            debitresponse.setResponseCode("12");
        }
        return debitresponse;
    }

    @WebMethod(operationName = "amountblock")
    public AmountBlockResponse amountblock(@WebParam(name = "amountblockrequest") AmountBlockRequest amountblockrequest) {
        AmountBlockResponse amountblockresponse = new AmountBlockResponse();

        try {

        } catch (Exception d) {
            amountblockresponse.setResponseCode("12");
        }
        return amountblockresponse;
    }

    @WebMethod(operationName = "amountunblock")
    public AmountUnblockResponse amountunblock(@WebParam(name = "amountunblockrequest") AmountUnblockRequest amountunblockrequest) {
        AmountUnblockResponse amountunblockresponse = new AmountUnblockResponse();

        try {

        } catch (Exception d) {
            amountunblockresponse.setResponseCode("12");
        }
        return amountunblockresponse;
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


