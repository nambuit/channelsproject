/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nibbsnip.service;

import com.google.gson.Gson;
import java.math.BigDecimal;
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
import org.t24.DataItem;
import org.t24.NIBBsResponseCodes;
import org.t24.PGPEncrytionTool;
import org.t24.RemittaResponseCodes;
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
    
    
    public NIBBSNIPInterface(){
   try
    {
 
        options = new AppParams();
        
        nipssm = new PGPEncrytionTool(options);
        
        
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
        
         
        try{
    
    
            NESingleRequest request = (NESingleRequest) options.XMLToObject(input, new NESingleRequest());
     
            ArrayList<List<String>> result = t24.getOfsData("NESingleRequest.NIP",options.getOfsuser(), options.getOfspass(), "@ID:EQ=" + request.getAccountNumber());
            List<String> headers = result.get(0);
     
           if(headers.size()!=result.get(1).size()){
               String msg = result.get(1).get(0);
               respcodes = options.getNIBBsCode(msg);
                 nameEnquiry.setResponseCode(respcodes.getCode());
                 
                return options.ObjectToXML(nameEnquiry);
               
           }
          respcodes = NIBBsResponseCodes.SUCCESS;
         nameEnquiry.setAccountNumber(request.getAccountNumber());
         nameEnquiry.setResponseCode(respcodes.getCode());
         nameEnquiry.setAccountName(result.get(1).get(headers.indexOf("AccountName")).replace("\"", "").trim());
         nameEnquiry.setBankVerificationNumber(result.get(1).get(headers.indexOf("BankVerificationNumber")).replace("\"", "").trim());
         nameEnquiry.setKYCLevel(result.get(1).get(headers.indexOf("KYCLevel")).replace("\"", "").trim());
         nameEnquiry.setDestinationInstitutionCode(request.getDestinationInstitutionCode());
         nameEnquiry.setChannelCode(request.getChannelCode());
         nameEnquiry.setSessionID(request.getSessionID());
        
            
    }
        catch(Exception e){
        respcodes = NIBBsResponseCodes.System_malfunction;
        nameEnquiry.setResponseCode(respcodes.getCode());
        
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
        FTSingleDebitResponse response = new FTSingleDebitResponse();        
        try{
            
       FTSingleDebitRequest request = (FTSingleDebitRequest) options.XMLToObject(ftsingledebitrequest, new FTSingleDebitRequest());
                     
    }
        catch(Exception e){
        respcodes = NIBBsResponseCodes.System_malfunction;
        response.setResponseCode(respcodes.getCode());
    }
        return options.ObjectToXML(response);
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
     
        ArrayList<List<String>> result = t24.getOfsData("TRANS.STATUS.QUE.REQ.NIP",options.getOfsuser(), options.getOfspass(), "@ID:EQ=" + requestQuery.getSessionID());
        List<String> headers = result.get(0);
     
           if(headers.size()!=result.get(1).size()){
               
               throw new Exception(result.get(1).get(0));
           }
           
           requestQuery.setChannelCode(requestQuery.getChannelCode());
           requestQuery.setSessionID(requestQuery.getSessionID());
           requestQuery.setSourceInstitutionCode(requestQuery.getSourceInstitutionCode());
            
            
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
     
     ArrayList<List<String>> result = t24.getOfsData("BalanceEnquiryRequest.NIP",options.getOfsuser(), options.getOfspass(), "@ID:EQ=" + accountbalance.getTargetAccountNumber());
        List<String> headers = result.get(0);
     
           if(headers.size()!=result.get(1).size()){
               
               throw new Exception(result.get(1).get(0));
           }
           
           String AvailableBalance = result.get(1).get(headers.indexOf("AvailableBalance")).replace("\"", "").trim().replace(",", "");
           accountbalance.setAvailableBalance(BigDecimal.valueOf(Double.parseDouble(AvailableBalance))); 
           accountbalance.setResponseCode("00");
           accountbalance.setChannelCode(request.getChannelCode());
           accountbalance.setSessionID(request.getSessionID());
           accountbalance.setAuthorizationCode(request.getAuthorizationCode());
           accountbalance.setDestinationInstitutionCode(request.getDestinationInstitutionCode());
           accountbalance.setTargetAccountName(result.get(1).get(headers.indexOf("TargetAccountName")).replace("\"", "").trim());
           accountbalance.setTargetAccountNumber(result.get(1).get(headers.indexOf("TargetAccountNumber")).replace("\"", "").trim());
           accountbalance.setTargetBankVerificationNumber(result.get(1).get(headers.indexOf("TargetBank")).replace("\"", "").trim());
           
    
            
     
        } catch (Exception d) {
            accountbalance.setResponseCode("12");
        }
        return options.ObjectToXML(accountbalance);
    }

    @WebMethod(operationName = "fundtransferAdvice_dc")
    public String fundtransferAdvice_dc(@WebParam(name = "creditrequest") String creditrequest) {
        
        FTAdviceCreditResponse response = new FTAdviceCreditResponse();
     
        
        try {
           
     FTAdviceCreditRequest request = (FTAdviceCreditRequest) options.XMLToObject(creditrequest, new FTAdviceCreditRequest());
     
     
     
     //repopulating response object
        response.setAmount(request.getAmount());
        response.setBeneficiaryAccountName(request.getBeneficiaryAccountName());
        response.setBeneficiaryAccountNumber(request.getBeneficiaryAccountNumber());
        response.setBeneficiaryBankVerificationNumber(request.getBeneficiaryBankVerificationNumber());
        response.setChannelCode(request.getChannelCode());
        response.setDestinationInstitutionCode(request.getDestinationInstitutionCode());
        response.setOriginatorAccountName(request.getOriginatorAccountName());
        response.setOriginatorAccountNumber(request.getOriginatorAccountNumber());
        response.setOriginatorBankVerificationNumber(request.getOriginatorBankVerificationNumber());
        response.setOriginatorKYCLevel(request.getOriginatorKYCLevel());
        response.setSessionID(request.getSessionID());
        response.setTransactionLocation(request.getTransactionLocation());
     
     
     
     
       String[] ofsoptions = new String[] { "", "I", "PROCESS", "", "0" };
       String[] credentials = new String[] {options.getOfsuser(), options.getOfspass() };
       List<DataItem> items = new LinkedList<>();
       SimpleDateFormat ndf = new SimpleDateFormat("yyyyMMdd"); 
       Date date = new  Date();
    
       String trandate = ndf.format(date);
       
       
       ofsParam param = new ofsParam();
     
        param.setCredentials(credentials);
           param.setOperation("FUNDS.TRANSFER");
           
           
           param.setVersion("FT.REQ.DIRECT.DEBIT.NIP");
           
           param.setTransaction_id("");
          
           param.setOptions(ofsoptions);
           
          
           DataItem item = new DataItem();
           item.setItemHeader("DEBIT.VALUE.DATE");
           item.setItemValues(new String[] {trandate});
           items.add(item);
           
      
           item = new DataItem();
           item.setItemHeader("CREDIT.VALUE.DATE");
           item.setItemValues(new String[] {trandate});
           items.add(item);
           
           item = new DataItem();
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
           item.setItemValues(new String[] {"recievables"});
           items.add(item);
                      
           item = new DataItem();
           item.setItemHeader("CREDIT.ACCT.NO");
           item.setItemValues(new String[] {request.getBeneficiaryAccountNumber()});
           items.add(item);
           
           item = new DataItem();
           item.setItemHeader("REM.REF");
           
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
               item.setItemValues(new String[] {ndf.format(trandate)});
               
               
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
                response.setOriginatorAccountName(request.getOriginatorAccountName());
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
       
       
       
     
        } catch (Exception d) {
            
            respcodes = NIBBsResponseCodes.System_malfunction;
            response.setResponseCode(respcodes.getCode());
        }
        return options.ObjectToXML(response);
    }

    @WebMethod(operationName = "fundtransferAdvice_dd")
    public String fundtransferAdvice_dd(@WebParam(name = "debitrequest") String debitrequest) {
         FTAdviceDebitResponse response = new FTAdviceDebitResponse();
        
        try {
                       
            //debitrequest = nipssm.decrypt(debitrequest);
           
     FTAdviceDebitRequest request = (FTAdviceDebitRequest) options.XMLToObject(debitrequest, new FTAdviceDebitRequest());
     
       //repopulating response object
        response.setAmount(request.getAmount());
        response.setBeneficiaryAccountName(request.getBeneficiaryAccountName());
        response.setBeneficiaryAccountNumber(request.getBeneficiaryAccountNumber());
        response.setBeneficiaryBankVerificationNumber(request.getBeneficiaryBankVerificationNumber());
        response.setChannelCode(request.getChannelCode());
        response.setDestinationInstitutionCode(request.getDestinationInstitutionCode());
        response.setMandateReferenceNumber(request.getMandateReferenceNumber());
        response.setNameEnquiryRef(request.getNameEnquiryRef());
        response.setDebitKYCLevel(request.getDebitKYCLevel());
        response.setNarration(request.getNarration());
        response.setSessionID(request.getSessionID());
        response.setTransactionLocation(request.getTransactionLocation());
        response.setPaymentReference(request.getPaymentReference());
        response.setTransactionFee(request.getTransactionFee());
        response.setDebitAccountNumber(request.getDebitAccountNumber());
        response.setDebitAccountName(request.getDebitAccountName());
 
       String[] ofsoptions = new String[] { "", "I", "PROCESS", "", "0" };
       String[] credentials = new String[] {options.getOfsuser(), options.getOfspass() };
       List<DataItem> items = new LinkedList<>();
       SimpleDateFormat ndf = new SimpleDateFormat("yyyyMMdd"); 
       Date date = new  Date();
    
       String trandate = ndf.format(date);
       
       
       ofsParam param = new ofsParam();
     
        param.setCredentials(credentials);
           param.setOperation("FUNDS.TRANSFER");
           
           
           param.setVersion("FT.REQ.DIRECT.DEBIT.NIP");
           
           param.setTransaction_id("");
          
           param.setOptions(ofsoptions);
           
          
           DataItem item = new DataItem();
           item.setItemHeader("DEBIT.VALUE.DATE");
           item.setItemValues(new String[] {trandate});
           items.add(item);
           
      
           item = new DataItem();
           item.setItemHeader("CREDIT.VALUE.DATE");
           item.setItemValues(new String[] {trandate});
           items.add(item);
           
           item = new DataItem();
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
           item.setItemValues(new String[] {"payables"});
           items.add(item);
                      
           item = new DataItem();
           item.setItemHeader("DEBIT.ACCT.NO");
           item.setItemValues(new String[] {request.getBeneficiaryAccountNumber()});
           items.add(item);
           
           item = new DataItem();
           item.setItemHeader("REM.REF");
           
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
               item.setItemValues(new String[] {ndf.format(trandate)});
               
               
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
       
       
        }
        catch(UnmarshalException r){
            
           
             respcodes = NIBBsResponseCodes.Format_error;
            response.setResponseCode(respcodes.getCode());
        }
        
        catch (Exception d) {
            
            respcodes = NIBBsResponseCodes.System_malfunction;
            response.setResponseCode(respcodes.getCode());
        }
        
     
        
        return options.ObjectToXML(response);
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

                  ofsParam param = new ofsParam();
       String[] credentials = new String[] {options.getOfsuser(), options.getOfspass() };
       param.setCredentials(credentials);
                  param.setOperation("AC.LOCKED.EVENTS");
                  param.setTransaction_id("");
                  String[] ofsoptions = new String[] { "", "I", "PROCESS", "", "0" };
       param.setOptions(ofsoptions);

                  List<DataItem> items = new LinkedList<>();
       DataItem item = new DataItem();
       item.setItemHeader("ACCOUNT.NUMBER");
                  item.setItemValues(new String[] {amountblockresponse.getTargetAccountNumber()});
                  items.add(item);

                  item = new DataItem();
       item.setItemHeader("ACCOUNT.NAME");
                  item.setItemValues(new String[] {amountblockresponse.getTargetAccountName()});
                  items.add(item);

                  item = new DataItem();
       item.setItemHeader("BANK.VERIFICATION.NUMBER");
                  item.setItemValues(new String[] {amountblockresponse.getTargetBankVerificationNumber()});
                  items.add(item);

                  item = new DataItem();
       item.setItemHeader("LOCKED.AMOUNT");
                  item.setItemValues(new String[] {amountblockresponse.getAmount().toString()});
                  items.add(item);

                  item = new DataItem();
       item.setItemHeader("TRANSACTION.REF");
                  item.setItemValues(new String[] {amountblockresponse.getReferenceCode()});
                  items.add(item);

                  item = new DataItem();
       item.setItemHeader("REASON.CODE");
                  item.setItemValues(new String[] {amountblockresponse.getReasonCode()});
                  items.add(item);

                  item = new DataItem();
       item.setItemHeader("RESPONSE.CODE");
                  item.setItemValues(new String[] {amountblockresponse.getResponseCode()});
                  items.add(item);

                  item = new DataItem();
       item.setItemHeader("SESSION.ID");
                  item.setItemValues(new String[] {amountblockresponse.getSessionID()});
                  items.add(item);

                  item = new DataItem();
       item.setItemHeader("CHANNEL.CODE");
                  item.setItemValues(new String[] {amountblockresponse.getChannelCode()});
                  items.add(item);

                  item = new DataItem();
       item.setItemHeader("DEST.INSTITUITION.CODE");
                  item.setItemValues(new String[] {amountblockresponse.getDestinationInstitutionCode()});
                  items.add(item);

                  item = new DataItem();
       item.setItemHeader("NARRATION");
                  item.setItemValues(new String[] {amountblockresponse.getNarration()});
                  items.add(item);

                  param.setDataItems(items);
                     //ACLK1308680628
                  String ofstr = t24.generateOFSTransactString(param);

       String result = t24.PostMsg(ofstr);

                  if(t24.IsSuccessful(result)){

                      amountblockresponse.setResponseCode("00");

              }
                  else{
                      amountblockresponse.setResponseCode("70");
                      amountblockresponse.setReasonCode("1111");

                  }

               } catch (Exception d) {
                   options.getServiceLogger("service_monitor").LogError(d.getMessage(), d, Level.ERROR);
                   amountblockresponse.setResponseCode("12");
                   amountblockresponse.setReasonCode("1111");
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

            ofsParam param = new ofsParam();
       String[] credentials = new String[] { options.getOfsuser(), options.getOfspass() };
       param.setCredentials(credentials);
                  param.setOperation("AC.LOCKED.EVENTS");
                  param.setTransaction_id(amountunblockresponse.getReferenceCode());
                  String[] ofsoptions = new String[] { "", "R", "PROCESS", "", "0" };
       param.setOptions(ofsoptions);

                  List<DataItem> items = new LinkedList<>();
       DataItem item = new DataItem();
       item.setItemHeader("ACCOUNT.NUMBER");
                  item.setItemValues(new String[] {amountunblockresponse.getTargetAccountNumber()});
                  items.add(item);     


                  param.setDataItems(items);
                     //ACLK1308680628
                  String ofstr = t24.generateOFSTransactString(param);

       String result = t24.PostMsg(ofstr);

                  if(t24.IsSuccessful(result)){

                      amountunblockresponse.setResponseCode("00");

              }
                  else{
                      amountunblockresponse.setResponseCode("70");
                      amountunblockresponse.setReasonCode("1111");

                  }

               } catch (Exception d) {
                   options.getServiceLogger("service_monitor").LogError(d.getMessage(), d, Level.ERROR); 
                   amountunblockresponse.setResponseCode("12");
               }
               return options.ObjectToXML(amountunblockresponse);
           }

           @WebMethod(operationName = "accountblock")
           public String accountblock(@WebParam(name = "AccountBlockIn") String input) {
               AccountBlockResponse response = new AccountBlockResponse();
              
               try{

            AccountBlockRequest request = (AccountBlockRequest) options.XMLToObject(input, new AccountBlockRequest());  

             ofsParam param = new ofsParam();
       String[] credentials = new String[] {options.getOfsuser(), options.getOfspass() };
       param.setCredentials(credentials);
                  param.setOperation("POSTING.RESTRICT");
                  param.setTransaction_id("");
                  String[] ofsoptions = new String[] { "", "I", "PROCESS", "", "0" };
                  param.setOptions(ofsoptions);

                  List<DataItem> items = new LinkedList<>();
       DataItem item = new DataItem();
       item.setItemHeader("ACCOUNT.NUMBER");
                  item.setItemValues(new String[] {request.getTargetAccountNumber()});
                  items.add(item);

                  item = new DataItem();
       item.setItemHeader("ACCOUNT.NAME");
                  item.setItemValues(new String[] {request.getTargetAccountName()});
                  items.add(item);

                  item = new DataItem();
       item.setItemHeader("BANK.VERIFICATION.NUMBER");
                  item.setItemValues(new String[] {request.getTargetBankVerificationNumber()});
                  items.add(item);

                  item = new DataItem();
       item.setItemHeader("REASON.CODE");
                  item.setItemValues(new String[] {request.getReasonCode()});
                  items.add(item);

                  item = new DataItem();
       item.setItemHeader("SESSION.ID");
                  item.setItemValues(new String[] {request.getSessionID()});
                  items.add(item);

                  item = new DataItem();
       item.setItemHeader("CHANNEL.CODE");
                  item.setItemValues(new String[] {request.getChannelCode()});
                  items.add(item);

                  item = new DataItem();
       item.setItemHeader("DEST.INSTITUITION.CODE");
                  item.setItemValues(new String[] {request.getDestinationInstitutionCode()});
                  items.add(item);

                  item = new DataItem();
                  item.setItemHeader("TRANSACTION.REF");
                  item.setItemValues(new String[] {request.getReferenceCode()});
                  items.add(item);



                  item = new DataItem();
                  item.setItemHeader("NARRATION");
                  item.setItemValues(new String[] {request.getNarration()});
                  items.add(item);

                  param.setDataItems(items);
                     //ACLK1308680628
                  String ofstr = t24.generateOFSTransactString(param);

                  String result = t24.PostMsg(ofstr);

                  if(t24.IsSuccessful(result)){

                      response.setResponseCode("00");
      
              }
                  else{
                      response.setResponseCode("70");
                      response.setReasonCode("1111");

                  }

               }catch(Exception d){
                   response.setResponseCode("32");
               }
               return options.ObjectToXML(response);
    }
    
    @WebMethod(operationName = "accountunblock")
    public String accountunblock(@WebParam(name = "AccountUnblockIn") String AccountUnblockIn) {
        AccountUnblockResponse AccountUnblockOut = new AccountUnblockResponse();
        Gson gson = new Gson();
        try{
            
            JSONObject object = XML.toJSONObject(AccountUnblockIn);       
            object = (JSONObject)object.get("AccountUnblockRequest");
            String json = object.toString();
            AccountUnblockRequest request = (AccountUnblockRequest)gson.fromJson(json, AccountUnblockRequest.class);
            
             ofsParam param = new ofsParam();
String[] credentials = new String[] { options.getOfsuser(), options.getOfspass() };
param.setCredentials(credentials);
           param.setOperation("POSTING.RESTRICT");
           param.setTransaction_id(AccountUnblockOut.getReferenceCode());
           String[] ofsoptions = new String[] { "", "R", "PROCESS", "", "0" };
param.setOptions(ofsoptions);
           
           List<DataItem> items = new LinkedList<>();
DataItem item = new DataItem();
item.setItemHeader("ACCOUNT.NUMBER");
           item.setItemValues(new String[] {AccountUnblockOut.getTargetAccountNumber()});
           items.add(item);     

           
           param.setDataItems(items);
              //ACLK1308680628
           String ofstr = t24.generateOFSTransactString(param);

           String result = t24.PostMsg(ofstr);
           
           if(t24.IsSuccessful(result)){
           
               AccountUnblockOut.setResponseCode("00");
              
       }
           else{
               AccountUnblockOut.setResponseCode("70");
               AccountUnblockOut.setReasonCode("1111");
               
           }
            
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
    
   public static String escape(String text){
      
      return text.replace("&", "&amp;").replace("\"", "&quot;").replace("'", "&apos;").replace("<", "&lt;").replace(">", "&gt;");
  }
       
}


