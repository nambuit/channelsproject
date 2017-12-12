/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nibbsnip.service;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author Temitope
 */
@WebService(serviceName = "NIBBSNIPInterface")
public class NIBBSNIPInterface {

    /**
     * This is a sample web service operation
    
    @WebMethod(operationName = "hello")
    public String hello(@WebParam(name = "name") String txt) {
        return "Hello " + txt + " !";
    }
     */
     @WebMethod(operationName = "nameenquirysingleitem")
    public NESingleResponse nameenquirysingleitem(@WebParam(name = "nesinglerequest") NESingleRequest nesinglerequest) {
        NESingleResponse nameEnquiry = new NESingleResponse();
        try{
            
    }
        catch(Exception e){
        nameEnquiry.setResponseCode("32");
    }
        return nameEnquiry;
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
}


