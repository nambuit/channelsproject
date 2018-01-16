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
    @WebService(serviceName = "BankOneInterface")
       public class RemittaInterface{

               AppParams options;  
         T24Link t24;       
         String logfilename = "NIBBSNIPInterface";
         RemittaResponseCodes respcode;



       public RemittaInterface(){
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

       
       


  @WebMethod(operationName = "FT01")
        public String FT01(@WebParam(name = "fundstransfer") String fundstransfer) {
            SingleTransferResponse fundstransferresponse = new SingleTransferResponse();

            try {

         SingleTransferRequest request = (SingleTransferRequest) options.XMLToObject(fundstransfer,new SingleTransferRequest());
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        SimpleDateFormat ndf = new SimpleDateFormat("yyyyMMdd");

      Date trandate = sdf.parse(request.getTransactionDate());
       
      
      request.setTransactionDate(ndf.format(trandate));


           
           
           ofsParam param = new ofsParam();
          String[] credentials = new String[] {options.getOfsuser(), options.getOfspass() };
            param.setCredentials(credentials);
           param.setOperation("AC.LOCKED.EVENTS");
           param.setTransaction_id("");
           String[] ofsoptions = new String[] { "", "I", "PROCESS", "", "0" };
          param.setOptions(ofsoptions);
           
           List<DataItem> items = new LinkedList<>();
           DataItem item = new DataItem();
           item.setItemHeader("DATE");
           item.setItemValues(new String[] {request.getTransactionDate()});
           items.add(item);
           
           item = new DataItem();
           item.setItemHeader("DEBIT.AMOUNT");
           item.setItemValues(new String[] {request.getAmount().toString()});
           items.add(item);
           
           item = new DataItem();
           item.setItemHeader("REFERENCE");
           item.setItemValues(new String[] {request.getTransRef()});
           items.add(item);
           
           
            String ofstr = t24.generateOFSTransactString(param);

            String result = t24.PostMsg(ofstr);
           
           if(t24.IsSuccessful(result)){
               fundstransferresponse.setTransRef(result.split("/")[0]);
               
               respcode = RemittaResponseCodes.SUCCESS;
          
               fundstransferresponse.setResponseCode(respcode.getCode());
               fundstransferresponse.setResponseText(respcode.getMessage());
       }
           else{
                respcode = RemittaResponseCodes.UNKNOWN_ERROR;
               
               
                fundstransferresponse.setResponseCode(respcode.getCode());
               fundstransferresponse.setResponseText(respcode.getMessage());
              
           }
           
           param.setDataItems(items);
            } catch (Exception d) {
                fundstransferresponse.setResponseCode("12");
            }
            return options.ObjectToXML(fundstransferresponse);
        }
        
        
        
        

                @WebMethod(operationName = "ST01")
        public String ST01(@WebParam(name = "statusrequest") String statusrequest) {
            SingleTransferResponse fundstransferresponse = new SingleTransferResponse();

            try {

         SingleTransferStatusRequest request = (SingleTransferStatusRequest) options.XMLToObject(statusrequest,new SingleTransferStatusRequest());
            } catch (Exception d) {
                fundstransferresponse.setResponseCode("12");
            }
            return options.ObjectToXML(fundstransferresponse);
        }

        @WebMethod(operationName = "AS01")
        public String AS01(@WebParam(name = "accountstatement") String accountstatement) {
            AccountStatementResponse accountstatementresponse = new AccountStatementResponse();
       
            try{       
        //Mnemonic Product Account Id CLASS-POSNEG Ccy Account Officer
             AccountStatementRequest request = (AccountStatementRequest) options.XMLToObject(accountstatement,new AccountStatementRequest());
        //   Gson gson = new Gson(); 
           ArrayList<List<String>> result = t24.getOfsData("ENQUIRYNAME",options.getOfsuser(), options.getOfspass(), "@ID:EQ=" + request.getAccountNumber().trim());
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
        String Amount = result.get(i).get(headers.indexOf("Amount")).replace("\"", "").trim();
        stmt.setAmount(BigDecimal.valueOf(Double.parseDouble(Amount))); 
        stmt.setCRDR(result.get(i).get(headers.indexOf("CrDr")).replace("\"", "").trim());
        stmt.setNarration(result.get(i).get(headers.indexOf("Narration")).replace("\"", "").trim());
       stmt.setTransactionDate(result.get(i).get(headers.indexOf("Date")).replace("\"", "").trim());
        stmt.setCurrency(result.get(i).get(headers.indexOf("Currency")).replace("\"", "").trim());
        stmts.add(stmt);
        
          }
        
        accountstatementresponse.setStatementLine(stmts.toArray(a));
        respcode = RemittaResponseCodes.SUCCESS;
        accountstatementresponse.setResponseCode(respcode.getCode());
               
            } catch (Exception d) {
               
                options.getServiceLogger(logfilename).LogError(logfilename, d, Level.ERROR);
                 respcode = RemittaResponseCodes.UNKNOWN_ERROR;
                 accountstatementresponse.setResponseCode(respcode.getCode());
            }
            return options.ObjectToXML(accountstatementresponse);
        }


          @WebMethod(operationName = "AB01")
        public String AB01(@WebParam(name = "accountbalance") String accountbalance) {
            AccountBalanceResponse accountbalanceresponse = new AccountBalanceResponse();

            try {

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
     
         AuthenticateOTPRequest request = (AuthenticateOTPRequest) options.XMLToObject(authenticaterequest,new AuthenticateOTPRequest());
            } catch (Exception d) {
                authenticateresponse.setResponseCode("12");
            }
            return options.ObjectToXML(authenticateresponse);
        }
    }

