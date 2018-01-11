/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package remitta.service;

import com.google.gson.Gson;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import org.json.JSONObject;
import org.json.XML;
import org.t24.AppParams;
import org.t24.T24Link;

/**
 *
 * @author Temitope
 */
@WebService(serviceName = "RemittaInterface")
public class RemittaInterface {
     AppParams options;  
     T24Link t24;       
     String logfilename = "NIBBSNIPInterface";
    /**
     * This is a sample web service operation
     */
    @WebMethod(operationName = "hello")
    public String hello(@WebParam(name = "name") String txt) {
        return "Hello " + txt + " !";
    }
    

    
        @WebMethod(operationName = "FT01")
    public String FT01(@WebParam(name = "fundstransfer") String fundstransfer) {
        SingleTransferResponse fundstransferresponse = new SingleTransferResponse();

        try {
             JSONObject object = XML.toJSONObject(fundstransfer);       
//    
        object = (JSONObject)object.get("SingleTransferResponse");
//    
   String json = object.toString();
     SingleTransferRequest request = (SingleTransferRequest) options.XMLToObject(fundstransfer,new SingleTransferRequest());
        } catch (Exception d) {
            fundstransferresponse.setResponseCode("12");
        }
        return options.ObjectToXML(fundstransferresponse);
    }
    
            @WebMethod(operationName = "ST01")
    public String ST01(@WebParam(name = "statusrequest") String statusrequest) {
        SingleTransferResponse fundstransferresponse = new SingleTransferResponse();

        try {
             JSONObject object = XML.toJSONObject(statusrequest);       
//    
        object = (JSONObject)object.get("SingleTransferResponse");
//    
   String json = object.toString();
     SingleTransferStatusRequest request = (SingleTransferStatusRequest) options.XMLToObject(statusrequest,new SingleTransferStatusRequest());
        } catch (Exception d) {
            fundstransferresponse.setResponseCode("12");
        }
        return options.ObjectToXML(fundstransferresponse);
    }
    
                @WebMethod(operationName = "AS01")
    public String AS01(@WebParam(name = "accountstatement") String accountstatement) {
        AccountStatementResponse accountstatementresponse = new AccountStatementResponse();

        try {
             JSONObject object = XML.toJSONObject(accountstatement);       
//    
        object = (JSONObject)object.get("AccountStatementResponse");
//    
   String json = object.toString();
     AccountStatementRequest request = (AccountStatementRequest) options.XMLToObject(accountstatement,new AccountStatementRequest());
        } catch (Exception d) {
            accountstatementresponse.setResponseCode("12");
        }
        return options.ObjectToXML(accountstatementresponse);
    }
 
    
                    @WebMethod(operationName = "AB01")
    public String AB01(@WebParam(name = "accountbalance") String accountbalance) {
        AccountBalanceResponse accountbalanceresponse = new AccountBalanceResponse();

        try {
             JSONObject object = XML.toJSONObject(accountbalance);       
//    
        object = (JSONObject)object.get("AccountBalanceResponse");
//    
   String json = object.toString();
     AccountBalanceRequest request = (AccountBalanceRequest) options.XMLToObject(accountbalance,new AccountBalanceRequest());
        } catch (Exception d) {
            accountbalanceresponse.setResponseCode("12");
        }
        return options.ObjectToXML(accountbalanceresponse);
    }
    
    
                        @WebMethod(operationName = "NES01")
    public String NES01(@WebParam(name = "nameenquiry") String nameenquiry) {
        NameEnquiryResponse nameenquiryresponse = new NameEnquiryResponse();

        try {
             JSONObject object = XML.toJSONObject(nameenquiry);       
//    
        object = (JSONObject)object.get("NameEnquiryResponse");
//    
   String json = object.toString();
     NameEnquiryRequest request = (NameEnquiryRequest) options.XMLToObject(nameenquiry,new NameEnquiryRequest());
        } catch (Exception d) {
            nameenquiryresponse.setResponseCode("12");
        }
        return options.ObjectToXML(nameenquiryresponse);
    }
    
    
                            @WebMethod(operationName = "GOR01")
    public String GOR01(@WebParam(name = "otprequest") String otprequest) {
        GenerateOTPResponse otpresponse = new GenerateOTPResponse();

        try {
             JSONObject object = XML.toJSONObject(otprequest);       
//    
        object = (JSONObject)object.get("GenerateOTPResponse");
//    
   String json = object.toString();
     GenerateOTPRequest request = (GenerateOTPRequest) options.XMLToObject(otprequest,new GenerateOTPRequest());
        } catch (Exception d) {
            otpresponse.setResponseCode("12");
        }
        return options.ObjectToXML(otpresponse);
    }
    
                               @WebMethod(operationName = "AOR01")
    public String AOR01(@WebParam(name = "authenticaterequest") String authenticaterequest) {
        AuthenticateOTPResponse authenticateresponse = new AuthenticateOTPResponse();

        try {
             JSONObject object = XML.toJSONObject(authenticaterequest);       
//    
        object = (JSONObject)object.get("AuthenticateOTPResponse");
//    
   String json = object.toString();
     AuthenticateOTPRequest request = (AuthenticateOTPRequest) options.XMLToObject(authenticaterequest,new AuthenticateOTPRequest());
        } catch (Exception d) {
            authenticateresponse.setResponseCode("12");
        }
        return options.ObjectToXML(authenticateresponse);
    }
}
