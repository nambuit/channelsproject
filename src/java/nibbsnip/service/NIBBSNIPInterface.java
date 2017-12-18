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


