
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bankone.service;

import java.io.File;
import java.lang.Thread.State;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.naming.InitialContext;
import logger.FileLoggerHandler;


import static logger.MainWatch.watchDirectoryPath;
import logger.WebServiceLogger;
import org.apache.log4j.Level;

/**
 *
 * @author Temitope
 */
@WebService(serviceName = "BankOneInterface")
public class BankOneInterface
{
    private T24Link t24;
    private String Ofsuser;
    private String Ofspass;
    private String ImageBase;
    private String ISOofsSource;
    private String LogDir;
    private String listeningDir;
     Thread watcherthread = new Thread();
            
    
       
     
    
    

    public BankOneInterface()
{
    try
    {

    //    ServletContext se = getServletContext();
        // PropertyConfigurator.configure("PrimeraGateway.properties");

        //  logger.error("Test Error");
        javax.naming.Context ctx = (javax.naming.Context)new InitialContext().lookup("java:comp/env");
        String Host = (String)ctx.lookup("HOST");
        int port = Integer.parseInt((String)ctx.lookup("PORT"));
        String OFSsource = (String)ctx.lookup("OFSsource");
        Ofsuser = (String)ctx.lookup("OFSuser");
        Ofspass = (String)ctx.lookup("OFSpass");
        ImageBase = (String)ctx.lookup("ImageBase");
        t24 = new T24Link(Host, port, OFSsource);
        ISOofsSource = (String)ctx.lookup("ISO_OFSsource");
        LogDir = (String)ctx.lookup("LogDir");
         listeningDir = (String)ctx.lookup("ISOLogListenerDir");
       
       

       // servicelogger.LogInfo("Initialized Successfully",Level.INFO);
      //  isologger = new WebServiceLogger(LogDir,"atm_monitor");
        
    }
    catch (Exception e)
    {
       
        
        this.getServiceLogger("service_monitor").LogError(e.getMessage(), e, Level.FATAL);
    }
}

    @WebMethod(operationName = "GetAccounts")
    public List<AccountDetails> GetAccounts(@WebParam(name = "InstitutionCode") String InstitutionCode, @WebParam(name = "CustomerID") String CustID) {
        //ResponseObject response = new ResponseObject();
         List<AccountDetails> alldetails = new ArrayList<>();
        try{
   
        //Mnemonic Product Account Id CLASS-POSNEG Ccy Account Officer
           
        //   Gson gson = new Gson(); 

         ArrayList<List<String>> result = t24.getOfsData("ACCOUNTS$PRIMERA", Ofsuser, Ofspass, "CUSTOMER:EQ=" + CustID);

List<String> headers = result.get(0);
           
                if(headers.size()!=result.get(1).size()){
               
               throw new Exception(result.get(1).get(0));
           }
           
       for(int i = 1; i<result.size();i++){
           AccountDetails details = new AccountDetails();

details.setAccountName(result.get(i).get(headers.indexOf("AccountName")).replace("\"", "").trim());
        details.setAccountNumber(result.get(i).get(headers.indexOf("Account Id")).replace("\"", "").trim());
        details.setCurrency(result.get(i).get(headers.indexOf("Ccy")).replace("\"", "").trim()); 
        details.setProductDescription(result.get(i).get(headers.indexOf("Product")).replace("\"", "").trim()); 
      //  details.setCustomerName(result.get(i).get(headers.indexOf("CustomerName")).replace("\"", "").trim()); 
     
           String availbalance = result.get(i).get(headers.indexOf("AvailableBalance")).replace("\"", "").trim();
availbalance = availbalance.trim().isEmpty()?"0.00":availbalance;
        
         String ledgerbalance = result.get(i).get(headers.indexOf("LedgerBalance")).replace("\"", "").trim();
ledgerbalance = ledgerbalance.trim().isEmpty()?"0.00":ledgerbalance;
        
        
          String status = result.get(i).get(headers.indexOf("Status")).replace("\"", "").trim();

status = status.trim().isEmpty()?"ACTIVE":"CLOSED";
          details.setAccountStatus(status);
          details.setAvailableBalance(BigDecimal.valueOf(Double.parseDouble(availbalance))); 
          details.setLedgerBalance(BigDecimal.valueOf(Double.parseDouble(ledgerbalance))); 
            
           String officer = result.get(i).get(headers.indexOf("Account Officer")).replace("\"", "").trim();

ArrayList<List<String>> data = t24.getOfsData("%USER", Ofsuser, Ofspass, "DEPARTMENT.CODE:EQ=" + officer.trim());

List<String> dataheaders = data.get(0);
           
           if(dataheaders.size()==data.get(1).size()){
               
               	
           details.setAccountOfficer(data.get(1).get(dataheaders.indexOf("User Name")).replace("\"", "").trim()); 

           }
           else{
               details.setAccountOfficer("");
           }
     
     
     
          details.setPhoneNo(result.get(i).get(headers.indexOf("PhoneNo")).replace("\"", "").trim());
          
        
          
        
          details.setInstitutionCode(InstitutionCode);
           details.setIsSuccessful(true);
       alldetails.add(details);
      
       }
        }
        catch(Exception d){
            this.getServiceLogger("service_monitor").LogError(d.getMessage(), d, Level.ERROR); 
          AccountDetails details = new AccountDetails();
details.setIsSuccessful(false);
          details.setMessage(d.getMessage());
          alldetails.add(details);
        }
         return alldetails;
    }



     @WebMethod(operationName = "GetAccountByAccountNo")
    public AccountDetails GetAccountByAccountNo(@WebParam(name = "InstitutionCode") String InstitutionCode, @WebParam(name = "AccountNumber") String acctNo) {
        //ResponseObject response = new ResponseObject();
        AccountDetails details = new AccountDetails(); 
       try{       
        //Mnemonic Product Account Id CLASS-POSNEG Ccy Account Officer
           
        //   Gson gson = new Gson(); 
           ArrayList<List<String>> result = t24.getOfsData("ACCOUNTS$PRIMERA", Ofsuser, Ofspass, "@ID:EQ=" + acctNo.trim());
List<String> headers = result.get(0);
           
              if(headers.size()!=result.get(1).size()){
               
               throw new Exception(result.get(1).get(0));
           }

        details.setAccountName(result.get(1).get(headers.indexOf("AccountName")).replace("\"", "").trim());
        details.setAccountNumber(result.get(1).get(headers.indexOf("Account Id")).replace("\"", "").trim());
        details.setCurrency(result.get(1).get(headers.indexOf("Ccy")).replace("\"", "").trim()); 
        details.setProductDescription(result.get(1).get(headers.indexOf("Product")).replace("\"", "").trim()); 
      //  details.setCustomerName(result.get(1).get(headers.indexOf("CustomerName")).replace("\"", "").trim()); 
      
        String availbalance = result.get(1).get(headers.indexOf("AvailableBalance")).replace("\"", "").trim();
availbalance = availbalance.trim().isEmpty()?"0.00":availbalance;
        
         String ledgerbalance = result.get(1).get(headers.indexOf("LedgerBalance")).replace("\"", "").trim();
ledgerbalance = ledgerbalance.trim().isEmpty()?"0.00":ledgerbalance;
        
          String status = result.get(1).get(headers.indexOf("Status")).replace("\"", "").trim();

status = status.trim().isEmpty()?"ACTIVE":"CLOSED";
          details.setAccountStatus(status);
          details.setAvailableBalance(BigDecimal.valueOf(Double.parseDouble(availbalance))); 
              details.setLedgerBalance(BigDecimal.valueOf(Double.parseDouble(ledgerbalance))); 
              
                String officer = result.get(1).get(headers.indexOf("Account Officer")).replace("\"", "").trim();

ArrayList<List<String>> data = t24.getOfsData("%USER", Ofsuser, Ofspass, "DEPARTMENT.CODE:EQ=" + officer.trim());

List<String> dataheaders = data.get(0);
           
           if(dataheaders.size()==data.get(1).size()){
               
               	
           details.setAccountOfficer(data.get(1).get(dataheaders.indexOf("User Name")).replace("\"", "").trim()); 

           }
           else{
               details.setAccountOfficer("");
           }
          details.setPhoneNo(result.get(1).get(headers.indexOf("PhoneNo")).replace("\"", "").trim());
          details.setInstitutionCode(InstitutionCode);
          details.setIsSuccessful(true);
      
        }
        catch(Exception d){
           this.getServiceLogger("service_monitor").LogError(d.getMessage(), d, Level.ERROR); 
          details.setIsSuccessful(false);
          details.setMessage(d.getMessage());
        }
         return details;
    }


     @WebMethod(operationName = "GetBalance")
    public AccountBalance GetBalance(@WebParam(name = "InstitutionCode") String InstitutionCode, @WebParam(name = "AccountNumber") String acctNo) {
        //ResponseObject response = new ResponseObject();
        AccountBalance balance = new AccountBalance(); 
       try{       
        //Mnemonic Product Account Id CLASS-POSNEG Ccy Account Officer
           
         //  Gson gson = new Gson(); 
           ArrayList<List<String>> result = t24.getOfsData("ACCOUNTS$PRIMERA", Ofsuser, Ofspass, "@ID:EQ=" + acctNo.trim());
List<String> headers = result.get(0);
           
              if(headers.size()!=result.get(1).size()){
               
               throw new Exception(result.get(1).get(0));
           }

       
           String availbalance = result.get(1).get(headers.indexOf("AvailableBalance")).replace("\"", "").trim();
availbalance = availbalance.trim().isEmpty() ? "0.00" : availbalance;

           String ledgerbalance = result.get(1).get(headers.indexOf("LedgerBalance")).replace("\"", "").trim();
ledgerbalance = ledgerbalance.trim().isEmpty() ? "0.00" : ledgerbalance;
           
           balance.setAvailableBalance(BigDecimal.valueOf(Double.parseDouble(availbalance))); 
           balance.setLedgerBalance(BigDecimal.valueOf(Double.parseDouble(ledgerbalance)));          
           
        balance.setAccountNumber(result.get(1).get(headers.indexOf("Account Id")).replace("\"", "").trim());
    
        balance.setCurrency(result.get(1).get(headers.indexOf("Ccy")).replace("\"", "").trim());
        balance.setInstitutionCode(InstitutionCode);
        balance.setIsSuccessful(true);
        }
        catch(Exception d){
          this.getServiceLogger("service_monitor").LogError(d.getMessage(), d, Level.ERROR); 
          balance.setIsSuccessful(false);
          balance.setMessage(d.getMessage());
        }
         return balance;
    }


    @WebMethod(operationName = "GetCustomer")
    public Customer GetCustomer(@WebParam(name = "InstitutionCode") String InstitutionCode, @WebParam(name = "CustomerID") String CustomerID) {
        //ResponseObject response = new ResponseObject();
        Customer cust = new Customer(); 
       try{       
        //Mnemonic Product Account Id CLASS-POSNEG Ccy Account Officer
           
          // Gson gson = new Gson(); 
           ArrayList<List<String>> result = t24.getOfsData("%CUSTOMER$PRIMERA", Ofsuser, Ofspass, "@ID:EQ=" + CustomerID.trim());
List<String> headers = result.get(0);
           
              if(headers.size()!=result.get(1).size()){
               
               throw new Exception(result.get(1).get(0));
           }

        cust.setFirstName(result.get(1).get(headers.indexOf("FirstName")).replace("\"", "").trim());
        cust.setMiddleName(result.get(1).get(headers.indexOf("MiddleName")).replace("\"", "").trim());
        cust.setLastName(result.get(1).get(headers.indexOf("LastName")).replace("\"", "").trim());
        cust.setCustomerNo(result.get(1).get(headers.indexOf("Customer No")).replace("\"", "").trim()); 
        cust.setNationality(result.get(1).get(headers.indexOf("Nationality")).replace("\"", "").trim()); 
        cust.setResidence(result.get(1).get(headers.indexOf("Residence")).replace("\"", "").trim()); 
        cust.setCustomerCategory(result.get(1).get(headers.indexOf("Sector")).replace("\"", "").trim()); 
        cust.setCustomerStatus(result.get(1).get(headers.indexOf("CustomerStatus")).replace("\"", "").trim());
        cust.setIndustry(result.get(1).get(headers.indexOf("Industry")).replace("\"", "").trim());
         cust.setPhoneNo(result.get(1).get(headers.indexOf("PhoneNo")).replace("\"", "").trim());
         String address = result.get(1).get(headers.indexOf("Address")).replace("\"", "").trim();
String street = result.get(1).get(headers.indexOf("Street")).replace("\"", "").trim();
String town = result.get(1).get(headers.indexOf("Town")).replace("\"", "").trim();
String country = result.get(1).get(headers.indexOf("Country")).replace("\"", "").trim();
String postcode = result.get(1).get(headers.indexOf("PostCode")).replace("\"", "").trim();
cust.setAddressLine1(street+" "+address);
         cust.setAddressLine2(town+" "+country);
         cust.setAddressLine3(postcode);
        cust.setInstitutionCode(InstitutionCode);
        cust.setIsSuccessful(true);
        }
        catch(Exception d){
            this.getServiceLogger("service_monitor").LogError(d.getMessage(), d, Level.ERROR); 
           cust.setIsSuccessful(false);
          cust.setMessage(d.getMessage());
        }
         return cust;
    }


     @WebMethod(operationName = "GetPassport")
    public CustomerPassport GetPassport(@WebParam(name = "InstitutionCode") String InstitutionCode, @WebParam(name = "CustomerID") String CustomerID) {
        //ResponseObject response = new ResponseObject();
        CustomerPassport passport = new CustomerPassport(); 
       try{       
//        //  result = t24.getOfsData("%IM.IMAGE.TYPE","INPUTT","DEBENSON","@ID:EQ="+CustomerID.trim());
//       ArrayList<List<String>> result  = t24.getOfsData("PRIMERA$IMAGE",Ofsuser,Ofspass,"IMAGE.REFERENCE:EQ="+CustomerID.trim()+",IMAGE.TYPE:EQ=PHOTOS,IMAGE.APPLICATION:EQ=CUSTOMER");
//       List<String>  headers = result.get(0);  
//       
//          if(headers.size()!=result.get(1).size()){
//               
//               throw new Exception(result.get(1).get(0));
//           }
//       
//     String path =  result.get(1).get(headers.indexOf("Path")).replace("\"", "").trim().trim();
//     String file =  result.get(1).get(headers.indexOf("IMAGE")).replace("\"", "").trim().trim();
//     
//     if(path.startsWith(".")){
//         path = path.substring(1, path.length());
//     }
//     path = path.replace("/", "\\");
//     String filepath = ImageBase+path+file;
    String filepath = ImageBase + "\\Photo\\" + CustomerID.trim() + ".png";
File fi = new File(filepath);
byte[] fileContent = Files.readAllBytes(fi.toPath());
passport.setPhoto(fileContent);
        passport.setCustomerNo(CustomerID);
        passport.setInstitutionCode(InstitutionCode);
        passport.setIsSuccessful(true);
        }
        catch(Exception d){
            this.getServiceLogger("service_monitor").LogError(d.getMessage(), d, Level.ERROR); 
           passport.setIsSuccessful(false);
          passport.setMessage(d.getMessage());
           
        }
         return passport;
    }


        @WebMethod(operationName = "Retreivesignature")
    public CustomerSignature Retreivesignature(@WebParam(name = "InstitutionCode") String InstitutionCode, @WebParam(name = "CustomerID") String CustomerID) {
        //ResponseObject response = new ResponseObject();
        CustomerSignature signature = new CustomerSignature(); 
       try{  
           
//           ArrayList<List<String>> result  = t24.getOfsData("PRIMERA$IMAGE",Ofsuser,Ofspass,"IMAGE.REFERENCE:EQ="+CustomerID.trim()+",IMAGE.TYPE:EQ=SIGNATURES,IMAGE.APPLICATION:EQ=CUSTOMER");
//       List<String>  headers = result.get(0);  
//       
//          if(headers.size()!=result.get(1).size()){
//               
//               throw new Exception(result.get(1).get(0));
//           }
//       
//     String path =  result.get(1).get(headers.indexOf("Path")).replace("\"", "").trim().trim();
//     String file =  result.get(1).get(headers.indexOf("IMAGE")).replace("\"", "").trim().trim();
     
//     if(path.startsWith(".")){
//         path = path.substring(1, path.length());
//     }
//     path = path.replace("/", "\\");

     String filepath = ImageBase + "\\Signature\\" + CustomerID.trim() + ".png";

File fi = new File(filepath);
byte[] fileContent = Files.readAllBytes(fi.toPath());

signature.setSignature(fileContent);
        signature.setCustomerNo(CustomerID);
        signature.setInstitutionCode(InstitutionCode);
        signature.setIsSuccessful(true);
        }
        catch(Exception d){
           this.getServiceLogger("service_monitor").LogError(d.getMessage(), d, Level.ERROR); 
          signature.setIsSuccessful(false);
          signature.setMessage(d.getMessage());
        }
         return signature;
    } 



     @WebMethod(operationName = "GetAccountsByPhoneNo")
    public List<AccountDetails> GetAccountsByPhoneNo(@WebParam(name = "InstitutionCode") String InstitutionCode, @WebParam(name = "PhoneNo") String PhoneNo) {
        //ResponseObject response = new ResponseObject();
         List<AccountDetails> alldetails = new ArrayList<>();
        try{
        
        //Mnemonic Product Account Id CLASS-POSNEG Ccy Account Officer
           
         //  Gson gson = new Gson(); 
           ArrayList<List<String>> result = t24.getOfsData("%CUSTOMER$PRIMERA", Ofsuser, Ofspass, "TEL.MOBILE:EQ=" + PhoneNo.trim());
List<String> headers = result.get(0);
String Customer = result.get(1).get(headers.indexOf("Customer No")).replace("\"", "").trim().trim();
result = t24.getOfsData("ACCOUNTS$PRIMERA",Ofsuser, Ofspass,"CUSTOMER:EQ="+Customer.trim());
           headers = result.get(0);  
           
              if(headers.size()!=result.get(1).size()){
               
               throw new Exception(result.get(1).get(0));
           }
           
       for(int i = 1; i<result.size();i++){
           AccountDetails details = new AccountDetails();
details.setAccountName(result.get(i).get(headers.indexOf("AccountName")).replace("\"", "").trim());
        details.setCurrency(result.get(i).get(headers.indexOf("Ccy")).replace("\"", "").trim()); 
        details.setAccountNumber(result.get(i).get(headers.indexOf("Account Id")).replace("\"", "").trim());
        details.setProductDescription(result.get(i).get(headers.indexOf("Product")).replace("\"", "").trim()); 
     //   details.setCustomerName(result.get(i).get(headers.indexOf("CustomerName")).replace("\"", "").trim()); 
    
        String availbalance = result.get(i).get(headers.indexOf("AvailableBalance")).replace("\"", "").trim();
availbalance = availbalance.trim().isEmpty()?"0.00":availbalance;
        
         String ledgerbalance = result.get(i).get(headers.indexOf("LedgerBalance")).replace("\"", "").trim();
ledgerbalance = ledgerbalance.trim().isEmpty()?"0.00":ledgerbalance;
        
          String status = result.get(i).get(headers.indexOf("Status")).replace("\"", "").trim();

status = status.trim().isEmpty()?"ACTIVE":"CLOSED";
          details.setAccountStatus(status);
          details.setAvailableBalance(BigDecimal.valueOf(Double.parseDouble(availbalance))); 
              details.setLedgerBalance(BigDecimal.valueOf(Double.parseDouble(ledgerbalance))); 
              
String officer = result.get(i).get(headers.indexOf("Account Officer")).replace("\"", "").trim();

ArrayList<List<String>> data = t24.getOfsData("%USER", Ofsuser, Ofspass, "DEPARTMENT.CODE:EQ=" + officer.trim());

List<String> dataheaders = data.get(0);
           
           if(dataheaders.size()==data.get(1).size()){
               
               	
           details.setAccountOfficer(data.get(1).get(dataheaders.indexOf("User Name")).replace("\"", "").trim()); 

           }
           else{
               details.setAccountOfficer("");
           }
              
              
          details.setPhoneNo(result.get(i).get(headers.indexOf("PhoneNo")).replace("\"", "").trim());
          details.setInstitutionCode(InstitutionCode);
          details.setIsSuccessful(true);
       alldetails.add(details);
       
       }
        }
        catch(Exception d){
            this.getServiceLogger("service_monitor").LogError(d.getMessage(), d, Level.ERROR); 
            AccountDetails details = new AccountDetails();
details.setIsSuccessful(false);
          details.setMessage(d.getMessage());
          alldetails.add(details);
        }
         return alldetails;
    }



    @WebMethod(operationName = "GetCustomerTransactions")
    public List<Transaction> GetCustomerTransactions(@WebParam(name = "InstitutionCode") String InstitutionCode, @WebParam(name = "AccountNumber") String AccountNo, @WebParam(name = "Startdate") String Startdate, @WebParam(name = "Enddate") String Enddate) {
        //ResponseObject response = new ResponseObject();
        
         List<Transaction> txns = new ArrayList<>();
        try{  
            
            SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
SimpleDateFormat ndf = new SimpleDateFormat("yyyyMMdd");
Date Start = sdf.parse(Startdate);
Date End = sdf.parse(Enddate);

Startdate = ndf.format(Start);
            Enddate = ndf.format(End);   
            
        //Mnemonic Product Account Id CLASS-POSNEG Ccy Account Office         
           //Gson gson = new Gson(); 
           ArrayList<List<String>> result = t24.getOfsData("%STMT.ENTRY.PRIMERA", Ofsuser, Ofspass, "ACCOUNT.NUMBER:EQ=" + AccountNo.trim() + ",VALUE.DATE:RG=" + Startdate + " " + Enddate);
List<String> headers = result.get(0);
           
              if(headers.size()!=result.get(1).size()){
               
               throw new Exception(result.get(1).get(0));
           }
           
       for(int i = 1; i<result.size();i++){
           Transaction txn = new Transaction();
txn.setAmount(result.get(i).get(headers.indexOf("Amount Lccy")).replace("\"", "").trim());
        txn.setCurrency(result.get(i).get(headers.indexOf("CCY")).replace("\"", "").trim()); 
        txn.setReference(result.get(i).get(headers.indexOf("Reference")).replace("\"", "").trim()); 
        txn.setTransactionID(result.get(i).get(headers.indexOf("@ID")).replace("\"", "").trim()); 
        txn.setValueDate(result.get(i).get(headers.indexOf("Value date")).replace("\"", "").trim()); 
        txn.setInstitutionCode(InstitutionCode);
        txn.setIsSuccessful(true);
       txns.add(txn);
       }
        }
        catch(Exception d){
            this.getServiceLogger("service_monitor").LogError(d.getMessage(), d, Level.ERROR); 
         Transaction txn = new Transaction();
txn.setIsSuccessful(false);
          txn.setMessage(d.getMessage());
          txns.add(txn);
        }
         return txns;
    }



 @WebMethod(operationName = "PlaceLien")
    public LienDetails PlaceLien(@WebParam(name = "LienDetails") LienDetails details) {
  
       try{  
           
           
            SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
SimpleDateFormat ndf = new SimpleDateFormat("yyyyMMdd");

Date Start = sdf.parse(details.getFromDate());
Date End = sdf.parse(details.getToDate());

details.setFromDate(ndf.format(Start));
            details.setToDate(ndf.format(End));  
           
           
           
           ofsParam param = new ofsParam();
String[] credentials = new String[] { Ofsuser, Ofspass };
param.setCredentials(credentials);
           param.setOperation("AC.LOCKED.EVENTS");
           param.setTransaction_id("");
           String[] options = new String[] { "", "I", "PROCESS", "", "0" };
param.setOptions(options);
           
           List<DataItem> items = new LinkedList<>();
DataItem item = new DataItem();
item.setItemHeader("ACCOUNT.NUMBER");
           item.setItemValues(new String[] {details.getAccountNumber()});
           items.add(item);
           
           item = new DataItem();
item.setItemHeader("FROM.DATE");
           item.setItemValues(new String[] {details.getFromDate()});
           items.add(item);
           
           item = new DataItem();
item.setItemHeader("TO.DATE");
           item.setItemValues(new String[] {details.getToDate()});
           items.add(item);
           
           item = new DataItem();
item.setItemHeader("LOCKED.AMOUNT");
           item.setItemValues(new String[] {details.getAmount()});
           items.add(item);
           
           item = new DataItem();
item.setItemHeader("TRANSACTION.REF");
           item.setItemValues(new String[] {details.getReferenceNo()});
           items.add(item);
           
           item = new DataItem();
item.setItemHeader("DESCRIPTION");
           item.setItemValues(new String[] {details.getDescription()});
           items.add(item);
           
           param.setDataItems(items);
              //ACLK1308680628
           String ofstr = t24.generateOFSTransactString(param);

String result = t24.PostMsg(ofstr);
           
           if(t24.IsSuccessful(result)){
           
               details.setReferenceNo(result.split("/")[0]);
               details.setIsSuccessful(true);
               details.setMessage("Lien placed Successfully");
       }
           else{
               details.setReferenceNo("");
               details.setIsSuccessful(false);
                details.setMessage(result.split("/")[3]);
           }
           

          
        }
        catch(Exception d){
         this.getServiceLogger("service_monitor").LogError(d.getMessage(), d, Level.ERROR);    
         details.setReferenceNo("");
         details.setIsSuccessful(false);
         details.setMessage(d.getMessage());
         
        }
       return details;  
    } 


    @WebMethod(operationName = "UnPlaceLien")
    public LienDetails UnPlaceLien(@WebParam(name = "LienDetails") LienDetails details) {
  
       try{  
           
           ofsParam param = new ofsParam();
String[] credentials = new String[] { Ofsuser, Ofspass };
param.setCredentials(credentials);
           param.setOperation("AC.LOCKED.EVENTS");
           param.setTransaction_id(details.getReferenceNo());
           String[] options = new String[] { "", "R", "PROCESS", "", "0" };
param.setOptions(options);
           
           List<DataItem> items = new LinkedList<>();
DataItem item = new DataItem();
item.setItemHeader("ACCOUNT.NUMBER");
           item.setItemValues(new String[] {details.getAccountNumber()});
           items.add(item);     

           
           param.setDataItems(items);
              //ACLK1308680628
           String ofstr = t24.generateOFSTransactString(param);

String result = t24.PostMsg(ofstr);
           
           if(t24.IsSuccessful(result)){
           
               details.setReferenceNo(result.split("/")[0]);
               details.setIsSuccessful(true);
               details.setMessage("Lien Unplaced Successfully");
       }
           else{
               details.setReferenceNo("");
               details.setIsSuccessful(false);
               details.setMessage(result.split("/")[3]);
           }
           

         
        }
        catch(Exception d){
            this.getServiceLogger("service_monitor").LogError(d.getMessage(), d, Level.ERROR); 
                details.setReferenceNo("");
         details.setIsSuccessful(false);
         details.setMessage(d.getMessage()); 
        }
          return details;
    } 


     @WebMethod(operationName = "UpdatePhoneNo")
    public ResponseObject UpdatePhoneNo(@WebParam(name = "InstitutionCode") String InstitutionCode, @WebParam(name = "CustomerID") String CustomerID, @WebParam(name = "PhoneNo") String PhoneNo) {
             
            ResponseObject details = new ResponseObject();
       try{  
           
           details.setInstitutionCode(InstitutionCode);
           ofsParam param = new ofsParam();
String[] credentials = new String[] { Ofsuser, Ofspass };
param.setCredentials(credentials);
           param.setOperation("CUSTOMER");
           param.setTransaction_id(CustomerID);
           String[] options = new String[] { "", "I", "PROCESS", "", "0" };
param.setOptions(options);
           
           List<DataItem> items = new LinkedList<>();
DataItem item = new DataItem();
item.setItemHeader("TEL.MOBILE");
           item.setItemValues(new String[] {PhoneNo.trim()});
           items.add(item);     

           
           param.setDataItems(items);
      
           String ofstr = t24.generateOFSTransactString(param);

String result = t24.PostMsg(ofstr);
           
           if(t24.IsSuccessful(result)){
           
              
               details.setIsSuccessful(true);
               details.setMessage("PhoneNo Updated Successfully");
       }
           else{
               
               details.setIsSuccessful(false);
               details.setMessage(result.split("/")[3]);
           }
           

         
        }
        catch(Exception d){
           this.getServiceLogger("service_monitor").LogError(d.getMessage(), d, Level.ERROR); 
         details.setIsSuccessful(false);
         details.setMessage(d.getMessage()); 
        }
        return details;  
    } 



     @WebMethod(operationName = "GetProducts")
    public List<Product> GetProducts(@WebParam(name = "InstitutionCode") String InstitutionCode) {
        //ResponseObject response = new ResponseObject();
        
         List<Product> products = new ArrayList<>();
        try{  

           ArrayList<List<String>> result = t24.getOfsData("%CATEGORY", Ofsuser, Ofspass, "@ID:RG=6000 6999");
List<String> headers = result.get(0);
                      
       for(int i = 1; i<result.size();i++){
           Product txn = new Product();
txn.setInstitutionCode(InstitutionCode);
        txn.setProductCode(result.get(i).get(headers.indexOf("@ID")).replace("\"", "").trim()); 
        txn.setProductDescription(result.get(i).get(headers.indexOf("DESCRIPTION")).replace("\"", "").trim()); 
        txn.setProductName(result.get(i).get(headers.indexOf("SHORT.NAME")).replace("\"", "").trim()); 
        txn.setIsSuccessful(true);
        products.add(txn);
       }
       
       
       result = t24.getOfsData("%CATEGORY",Ofsuser,Ofspass,"@ID:RG=1001 1999");
       headers = result.get(0);
                      
       for(int i = 1; i<result.size();i++){
           Product txn = new Product();
txn.setInstitutionCode(InstitutionCode);
        txn.setProductCode(result.get(i).get(headers.indexOf("@ID")).replace("\"", "").trim()); 
        txn.setProductDescription(result.get(i).get(headers.indexOf("DESCRIPTION")).replace("\"", "").trim()); 
        txn.setProductName(result.get(i).get(headers.indexOf("SHORT.NAME")).replace("\"", "").trim()); 
        txn.setIsSuccessful(true);
        products.add(txn);
       }
       
       
        }
        catch(Exception d){
            this.getServiceLogger("service_monitor").LogError(d.getMessage(), d, Level.ERROR); 
        Product txn = new Product();
txn.setIsSuccessful(false);
          txn.setMessage(d.getMessage());
          products.add(txn);
        }
         return products;
    }



private WebServiceLogger getServiceLogger(String filename){
    
    return new WebServiceLogger(LogDir,filename);
}




        @WebMethod(operationName = "ProcessISO")
    public ISOResponse ProcessISO(@WebParam(name = "ISOMessage") String ISOMessage) {
        //ResponseObject response = new ResponseObject();
        ISOResponse message = new ISOResponse(); 
       try{       
        //Mnemonic Product Account Id CLASS-POSNEG Ccy Account Officer
         if(watcherthread.getState() == State.NEW){
             
             watcherthread  = new Thread(){
          public void watchlogdir() {
            
              try{
                       watchDirectoryPath(new File(listeningDir).toPath(),new FileLoggerHandler(new WebServiceLogger(LogDir,"atm_monitor")));
              } catch(Exception v)
              {
               getServiceLogger("service_monitor").LogError(v.getMessage(), v, Level.ERROR); 

              }
          }
      };
             
             
                         watcherthread.start(); 
         }
        
        ISOMessage = ISOMessage.substring(4);
          // Gson gson = new Gson(); 
           String result = t24.PostMsg(ISOMessage, ISOofsSource);

        message.setISOMessage(result);
        message.setIsSuccessful(true);

        }
        catch(Exception d){
            this.getServiceLogger("service_monitor").LogError(d.getMessage(), d, Level.ERROR); 
           message.setIsSuccessful(false);
          message.setErrorMessgae(d.getMessage());
        }
         return message;
    }   
    
    
}

