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
}
