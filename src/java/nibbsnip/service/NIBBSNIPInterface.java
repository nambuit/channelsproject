/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nibbsnip.service;

import java.util.ArrayList;
import java.util.List;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author Temitope
 */
@WebService(serviceName = "NIBBSNIPInterface")
public class NIBBSNIPInterface {

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
    

}


