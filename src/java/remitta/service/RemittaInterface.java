    /*
     * To change this license header, choose License Headers in Project Properties.
     * To change this template file, choose Tools | Templates
     * and open the template in the editor.
     */
    package remitta.service;


    import javax.jws.WebMethod;
    import javax.jws.WebParam;
    import org.apache.log4j.Level;

    import org.json.JSONObject;
    import org.json.XML;
    import org.t24.AppParams;
    import org.t24.T24Link;
    import org.t24.T24TAFCLink;
    import org.t24.T24TAFJLink;


    /**
     *
     * @author Temitope
     */
       public class RemittaInterface{

               AppParams options;  
         T24Link t24;       
         String logfilename = "NIBBSNIPInterface";




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

            try {

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

