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
