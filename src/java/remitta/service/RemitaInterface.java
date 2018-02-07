    /*
     * To change this license header, choose License Headers in Project Properties.
     * To change this template file, choose Tools | Templates
     * and open the template in the editor.
     */
    package remitta.service;


import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import org.apache.log4j.Level;
import org.t24.AppParams;
import org.t24.DataItem;
import org.t24.RemittaResponseCodes;
import org.t24.T24Link;
import org.t24.T24TAFCLink;
import org.t24.T24TAFJLink;
import org.t24.ofsParam;


    /**
     *
     * @author Temitope
     */
    @WebService(serviceName = "RemitaInterface")
       public class RemitaInterface{

               AppParams options;  
         T24Link t24;       
         String logfilename = "RemittaInterface";
         RemittaResponseCodes respcode;



       public RemitaInterface(){
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

    /**
     * Web service operation
     * @param fundstransfer
     * @return 
     */
  @WebMethod(operationName = "FT01")
        public SingleTransferResponse FT01(@WebParam(name = "fundstransfer") SingleTransferRequest fundstransfer) {
            SingleTransferResponse fundstransferresponse = new SingleTransferResponse();

            try {

       
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        SimpleDateFormat ndf = new SimpleDateFormat("yyyyMMdd");

      Date trandate = sdf.parse(fundstransfer.getTransactionDate());
       
       String[] ofsoptions = new String[] { "", "I", "PROCESS", "", "0" };
       String[] credentials = new String[] {options.getOfsuser(), options.getOfspass() };
        List<DataItem> items = new LinkedList<>();
       
      fundstransfer.setTransactionDate(ndf.format(trandate));
     
    //  ArrayList<List<String>> data = t24.getOfsData("%FUNDS.TRANSFER",options.getOfsuser(), options.getOfspass(), "REM.REF:EQ=" + fundstransfer.getTransRef().trim());
     
      String[] vofsoptions = new String[] { "", "I", "VALIDATE", "", "0" };

               ofsParam param = new ofsParam();
               param.setCredentials(credentials);
               param.setOperation("REMITA.FT.REF.TABLE");
               param.setOptions(vofsoptions);
               
               
               param.setTransaction_id(fundstransfer.getTransRef());
               
               param.setDataItems(items);
               
                String ofs = t24.generateOFSTransactString(param);

                String output = t24.PostMsg(ofs);
                 
                if(output.indexOf("T24.ID")>0){
                    
                    fundstransferresponse.setTransRef(fundstransfer.getTransRef());
                    fundstransferresponse.setTransactionDate(sdf.format(trandate));
                    respcode =  RemittaResponseCodes.DUPLICATE_TRANSACTION;
                    fundstransferresponse.setResponseCode(respcode.getCode());
                    fundstransferresponse.setResponseText(respcode.getMessage());
                    return fundstransferresponse;
                }
                   
                   
                   
                   
           param = new ofsParam();            
           
           param.setCredentials(credentials);
           param.setOperation("FUNDS.TRANSFER");
           
           
           param.setVersion("AC.REMITA.FT01");
           
           param.setTransaction_id("");
          
           param.setOptions(ofsoptions);
           
          
           DataItem item = new DataItem();
           item.setItemHeader("DEBIT.VALUE.DATE");
           item.setItemValues(new String[] {fundstransfer.getTransactionDate()});
           items.add(item);
           
      
           item = new DataItem();
           item.setItemHeader("CREDIT.VALUE.DATE");
           item.setItemValues(new String[] {fundstransfer.getTransactionDate()});
           items.add(item);
           
           item = new DataItem();
           item.setItemHeader("DEBIT.CURRENCY");
           item.setItemValues(new String[] {fundstransfer.getCurrency()});
           items.add(item);
           
           item = new DataItem();
           item.setItemHeader("CREDIT.CURRENCY");
           item.setItemValues(new String[] {fundstransfer.getCurrency()});
           items.add(item);
           
           item = new DataItem();
           item.setItemHeader("DEBIT.AMOUNT");
           item.setItemValues(new String[] {fundstransfer.getAmount()});
           items.add(item);
           
           item = new DataItem();
           item.setItemHeader("DEBIT.ACCT.NO");
           item.setItemValues(new String[] {fundstransfer.getDebitAccount()});
           items.add(item);
                      
           item = new DataItem();
           item.setItemHeader("CREDIT.ACCT.NO");
           item.setItemValues(new String[] {fundstransfer.getCreditAccount()});
           items.add(item);
           
           item = new DataItem();
           item.setItemHeader("REM.REF");
           
           if(fundstransfer.getNarration().length()>65){
               fundstransfer.setNarration(fundstransfer.getNarration().substring(65));
           }
           
           item.setItemValues(new String[] {fundstransfer.getNarration()});
           items.add(item);
           
//           item = new DataItem();
//           item.setItemHeader("DEBIT.THEIR.REF");
//           item.setItemValues(new String[] {fundstransfer.getNarration()});
//           items.add(item);
//           
//           item = new DataItem();
//           item.setItemHeader("CREDIT.THEIR.REF");
//           item.setItemValues(new String[] {fundstransfer.getNarration()});
//           items.add(item);
           
           param.setDataItems(items);
           
           String ofstr = t24.generateOFSTransactString(param);

           String result = t24.PostMsg(ofstr);
           
           if(t24.IsSuccessful(result)){
              fundstransferresponse.setTransRef(fundstransfer.getTransRef());
               
               items.clear();
               param = new ofsParam();
               param.setCredentials(credentials);
               param.setOperation("REMITA.FT.REF.TABLE");
               param.setOptions(ofsoptions);
               
               
               param.setTransaction_id(fundstransfer.getTransRef());
               
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

                result = t24.PostMsg(ofstr);
               
                 if(t24.IsSuccessful(result)){
                     
                 }
                 
               
               respcode = RemittaResponseCodes.SUCCESS;
               fundstransferresponse.setTransactionDate(sdf.format(trandate));
               fundstransferresponse.setResponseCode(respcode.getCode());
               fundstransferresponse.setResponseText(respcode.getMessage());
       }
           else{
               if(result.contains("/")){
                respcode = options.getRemittaCode(result.split("/")[3]);
                
                if(respcode==RemittaResponseCodes.UNKNOWN_ERROR){
                    throw new Exception (result);
                }
                  fundstransferresponse.setResponseCode(respcode.getCode());
               fundstransferresponse.setResponseText(respcode.getMessage());
               }
               else{
                   throw new Exception (result);
               }
               
              
              
           }
           
           param.setDataItems(items);
            } catch (Exception d) {
                respcode = RemittaResponseCodes.UNKNOWN_ERROR;
               
               
                fundstransferresponse.setResponseCode(respcode.getCode());
               fundstransferresponse.setResponseText(d.getMessage());
            }
            return fundstransferresponse;
        }
        
        
           /**
     * Web service operation
     * @param statusrequest
     * @return 
     */
        @WebMethod(operationName = "ST01")
        public SingleTransferResponse ST01(@WebParam(name = "statusrequest") SingleTransferStatusRequest statusrequest) {
            SingleTransferResponse fundstransferresponse = new SingleTransferResponse();

            try {
            
            
             
                   String[] vofsoptions = new String[] { "", "I", "VALIDATE", "", "0" };
                        String[] credentials = new String[] {options.getOfsuser(), options.getOfspass() };
                             List<DataItem> items = new LinkedList<>();

               ofsParam param = new ofsParam();
               param.setCredentials(credentials);
               param.setOperation("REMITA.FT.REF.TABLE");
               param.setOptions(vofsoptions);
               
               
               param.setTransaction_id(statusrequest.getTransRef());
               
               param.setDataItems(items);
               
                String ofs = t24.generateOFSTransactString(param);

                String output = t24.PostMsg(ofs);
                 
                if(output.indexOf("T24.ID")>0){
                     respcode = RemittaResponseCodes.SUCCESS;
                       fundstransferresponse.setResponseCode(respcode.getCode());
                    fundstransferresponse.setResponseText(respcode.getMessage());
                    fundstransferresponse.setTransRef(statusrequest.getTransRef());
                    
                    SimpleDateFormat ndf = new SimpleDateFormat("yyyyMMdd");
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");    
                    int sdd =   output.indexOf("TXN.DATE:1:1=");
                    String datestr = output.substring(sdd,sdd+21);
//                    
                    datestr = datestr.substring(datestr.length()-8, datestr.length());
//                    
                    Date  date =   ndf.parse(datestr.trim());
                    
                    fundstransferresponse.setTransactionDate(sdf.format(date));
                                       
                }
                else{
                        respcode = RemittaResponseCodes.UNKNOWN_TRANSACTION;
                        fundstransferresponse.setTransactionDate("");
                        fundstransferresponse.setResponseCode(respcode.getCode());
                        fundstransferresponse.setResponseText(respcode.getMessage());
                        fundstransferresponse.setTransRef(statusrequest.getTransRef());
                }
                   
             
          
        
            } catch (Exception d) {
               respcode = RemittaResponseCodes.UNKNOWN_ERROR;
              fundstransferresponse.setResponseCode(respcode.getCode());
               fundstransferresponse.setResponseText(d.getMessage());
            }
            return fundstransferresponse;
        }
        
        
                  /**
     * Web service operation
     * @param accountstatement
     * @return 
     */

        @WebMethod(operationName = "AS01")
        public AccountStatementResponse AS01(@WebParam(name = "accountstatement") AccountStatementRequest accountstatement) {
      
        AccountStatementResponse accountstatementresponse = new AccountStatementResponse();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat ndf = new SimpleDateFormat("yyyyMMdd");
        
        
            try{       
        //Mnemonic Product Account Id CLASS-POSNEG Ccy Account Officer
       

      
        //   Gson gson = new Gson(); 
           Date statementdate = sdf.parse(accountstatement.getStatementDate());
           accountstatement.setStatementDate(ndf.format(statementdate));
           ArrayList<List<String>> result = t24.getOfsData("STMT.STATEMENT.REQUEST.REMITA.AS01",options.getOfsuser(), options.getOfspass(), "ACCOUNT.NUMBER:EQ=" + accountstatement.getAccountNumber().trim()+",VALUE.DATE:EQ="+accountstatement.getStatementDate());
           List<String> headers = result.get(0);
           
              if(headers.size()!=result.get(1).size()){
               
               throw new Exception(result.get(1).get(0));
           }
          
              Statement [] a = new Statement[result.size()-1];
              
         List<Statement> stmts = new ArrayList<>();

         
         
          for(int i = 1; i<result.size();i++){
         
        accountstatementresponse.setResponseCode("00");
        accountstatementresponse.setResponseText("Transaction Completed");
        Statement stmt =  new Statement();
        String Amount = result.get(i).get(headers.indexOf("Amount Lccy")).replace("\"", "").trim().replace(",", "");
        stmt.setAmount(BigDecimal.valueOf(Double.parseDouble(Amount))); 
        String crdr = stmt.getAmount().doubleValue()<0?"DR":"CR";
        stmt.setCRDR(crdr);
        stmt.setNarration(result.get(i).get(headers.indexOf("Narration")).replace("\"", "").trim());
        
        String datetime = result.get(i).get(headers.indexOf("DATE.TIME")).replace("\"", "").trim();
       stmt.setTransactionDate(datetime);
        stmt.setCurrency(result.get(i).get(headers.indexOf("Currency")).replace("\"", "").trim());
        stmts.add(stmt);
        
          }
        
        accountstatementresponse.setStatementLine(stmts.toArray(a));
        respcode = RemittaResponseCodes.SUCCESS;
        accountstatementresponse.setResponseCode(respcode.getCode());
               
            } catch (Exception d) {
               
                options.getServiceLogger(logfilename).LogError(logfilename, d, Level.ERROR);
                 respcode = RemittaResponseCodes.UNKNOWN_ERROR;
                 accountstatementresponse.setResponseText(d.getMessage());
                  accountstatementresponse.setResponseCode(respcode.getCode());
            }
            return accountstatementresponse;
        }

        
           /**
     * Web service operation
     * @param accountbalance
     * @return 
     */
          @WebMethod(operationName = "AB01")
        public AccountBalanceResponse AB01(@WebParam(name = "accountbalance") AccountBalanceRequest accountbalance) {
            AccountBalanceResponse accountbalanceresponse = new AccountBalanceResponse();

            try {

       //  AccountBalanceRequest request = (AccountBalanceRequest) options.XMLToObject(accountbalance,new AccountBalanceRequest());
         

     
        //   Gson gson = new Gson(); 
           Date today = new Date();
           
           ArrayList<List<String>> result = t24.getOfsData("ACCT.BALANCE.REMITA.AB01",options.getOfsuser(), options.getOfspass(), "@ID:EQ=" + accountbalance.getAccountNumber());
           List<String> headers = result.get(0);
           
              if(headers.size()!=result.get(1).size()){
               
               throw new Exception(result.get(1).get(0));
           }
          
         
        accountbalanceresponse.setResponseCode("00");
        accountbalanceresponse.setResponseText("Transaction Completed");
      
        String Amount = result.get(1).get(headers.indexOf("Amount")).replace("\"", "").trim().replace(",", "");
        accountbalanceresponse.setAmount(BigDecimal.valueOf(Double.parseDouble(Amount))); 
        accountbalanceresponse.setCurrency(result.get(1).get(headers.indexOf("Currency")).replace("\"", "").trim());
     
        accountbalanceresponse.setAccountName(result.get(1).get(headers.indexOf("AccountName")).replace("\"", "").trim());        
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        
        accountbalanceresponse.setBalanceDate(sdf.format(today));
        
        respcode = RemittaResponseCodes.SUCCESS;
       
         
         
            } catch (Exception d) {
                
             if(d.getMessage().contains("found that matched the selection criteria")){
                 respcode =  RemittaResponseCodes.INVALID_ACCOUNT;
                  accountbalanceresponse.setResponseText(respcode.getMessage());
              accountbalanceresponse.setResponseCode(respcode.getCode());
             }
             else{  
              respcode =  RemittaResponseCodes.UNKNOWN_ERROR;
              accountbalanceresponse.setResponseText(d.getMessage());
              accountbalanceresponse.setResponseCode(respcode.getCode());
             }
            }
            return accountbalanceresponse;
        }

      /**
     * Web service operation
     * @param nameenquiry
     * @return 
     */
     @WebMethod(operationName = "NES01")
     public NameEnquiryResponse NES01(@WebParam(name = "nameenquiry") NameEnquiryRequest nameenquiry) {
            NameEnquiryResponse nameenquiryresponse = new NameEnquiryResponse();

            try {
          
           // NameEnquiryRequest request = (NameEnquiryRequest) options.XMLToObject(nameenquiry,new NameEnquiryRequest());
               
             //   Gson gson = new Gson(); 
           
           
           ArrayList<List<String>> result = t24.getOfsData("NAME.ENQ.REQ.REMITA.NES01",options.getOfsuser(), options.getOfspass(), "@ID:EQ=" + nameenquiry.getAccountNumber());
           List<String> headers = result.get(0);
           
              if(headers.size()!=result.get(1).size()){
               
               throw new Exception(result.get(1).get(0));
           }
          
         
        nameenquiryresponse.setResponseCode("00");
        nameenquiryresponse.setResponseText("Transaction Completed");
      
        nameenquiryresponse.setAccountNumber(result.get(1).get(headers.indexOf("AccountNumber")).replace("\"", "").trim());
        nameenquiryresponse.setAccountName(result.get(1).get(headers.indexOf("AccountName")).replace("\"", "").trim());        
                
        respcode = RemittaResponseCodes.SUCCESS;
 
            } catch (Exception d) {
                
                 respcode = RemittaResponseCodes.UNKNOWN_ERROR;
                nameenquiryresponse.setResponseCode(respcode.getCode());
                 nameenquiryresponse.setResponseText(d.getMessage());
                 
            }
            return nameenquiryresponse;
        }

     
      /**
     * Web service operation
     * @param otprequest
     * @return 
     */
       @WebMethod(operationName = "GOR01")
        public GenerateOTPResponse GOR01(@WebParam(name = "otprequest") GenerateOTPRequest otprequest) {
            GenerateOTPResponse otpresponse = new GenerateOTPResponse();

            try {
    
       //  GenerateOTPRequest request = (GenerateOTPRequest) options.XMLToObject(otprequest,new GenerateOTPRequest());
        
            //   Gson gson = new Gson(); 
           
           
           ArrayList<List<String>> result = t24.getOfsData("GENERATE.OTP.REQ.REMITA.GOR01",options.getOfsuser(), options.getOfspass(), "@ID:EQ=" + otprequest.getAccountNumber());
           List<String> headers = result.get(0);
           
              if(headers.size()!=result.get(1).size()){
               
               throw new Exception(result.get(1).get(0));
           }
          
         
        otpresponse.setResponseCode("00");
        otpresponse.setResponseText("Transaction Completed"); 
        otpresponse.setPhoneNumber(result.get(1).get(headers.indexOf("PhoneNumber")).replace("\"", "").trim());
        otpresponse.setAccountClass(result.get(1).get(headers.indexOf("AccountClass")).replace("\"", "").trim());        
        
        respcode = RemittaResponseCodes.SUCCESS;
            } catch (Exception d) {
              respcode = RemittaResponseCodes.UNKNOWN_ERROR;
                otpresponse.setResponseCode(respcode.getCode());
                 otpresponse.setResponseText(d.getMessage());
            }
            return otpresponse;
        }
       
       


      /**
     * Web service operation
     * @param request
     * @return 
     */
        @WebMethod(operationName = "A0R01")
        public AuthenticateOTPResponse A0R01(@WebParam(name = "authotprequest") AuthenticateOTPRequest request) {
            AuthenticateOTPResponse authenticateresponse = new AuthenticateOTPResponse();

            try {
     
       //  AuthenticateOTPRequest request = (AuthenticateOTPRequest) options.XMLToObject(authenticaterequest,new AuthenticateOTPRequest());
            //   Gson gson = new Gson(); 
           
           
           ArrayList<List<String>> result = t24.getOfsData("AUTHENTICATE.OTP.REQ.REMITA.GOR01",options.getOfsuser(), options.getOfspass(), "@ID:EQ=" + request.getAccountNumber());
           List<String> headers = result.get(0);
           
              if(headers.size()!=result.get(1).size()){
               
               throw new Exception(result.get(1).get(0));
           }
          
         
        authenticateresponse.setResponseCode("00");
        authenticateresponse.setResponseText("Transaction Completed"); 
        
        respcode = RemittaResponseCodes.SUCCESS;
            
            } catch (Exception d) {
            respcode = RemittaResponseCodes.UNKNOWN_ERROR;
                authenticateresponse.setResponseCode(respcode.getCode());
                 authenticateresponse.setResponseText(d.getMessage());
            }
            return authenticateresponse;
        }
       
 

    }

