/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.t24;

/**
 *
 * @author Temitope
 */



 public enum NIBBsResponseCodes {
  SUCCESS("00","Approved or completed successfully"),
  Status_unknown("01","Status unknown, please wait for settlement report"),
  Invalid_Sender("03","Invalid Sender"),
  Do_not_honor("05","Do not honor"),
  Dormant_Account("06","Dormant Account"),
  Invalid_Account("07","Invalid Account"),
  Account_Name_Mismatch("08","Account Name Mismatch"),
  Request_processing_in_progress("09","Request processing in progress"),
  Invalid_transaction("12","Invalid transaction"),
  Invalid_Amount("13","Invalid Amount"),
  Invalid_Batch_Number("14","Invalid Batch Number"),
  Invalid_Session_or_Record_ID("15","Invalid Session or Record ID"),
  Unknown_Bank_Code("16","Unknown Bank Code"),
  Invalid_Channel("17","Invalid Channel"),
  Wrong_Method_Call("18","Wrong Method Call"),
  No_action_taken("21","No action taken"),
  Unable_to_locate_record("25","Unable to locate record"),
  Duplicate_record("26","Duplicate record"),
  Format_error("30","Format error"),
  Contact_sending_bank("35","Contact sending bank"),
  No_sufficient_funds("51","No sufficient funds"),
  Transaction_not_permitted_to_sender("57","Transaction not permitted to sender"),
  Transaction_not_permitted_on_channel("58","Transaction not permitted on channel"),
  Transfer_limit_Exceeded("61","Transfer limit Exceeded"),
  Security_violation("63","Security violation"),
  Exceeds_withdrawal_frequency("65","Exceeds withdrawal frequency"),
  Response_received_too_late("68","Response received too late"),
  Unsuccessful_Account_Amount_block("69","Unsuccessful Account/Amount block"),
  Unsuccessful_Account_Amount_unblock("70","Unsuccessful Account/Amount unblock"),
  Empty_Mandate_Reference_Number("71","Empty Mandate Reference Number"),
  Beneficiary_Bank_not_available("91","Beneficiary Bank not available"),
  Routing_error("92","Routing error"),
  Duplicate_transaction("94","Duplicate transaction"),
  System_malfunction("96","System malfunction"),
  Timeout("97","Timeout waiting for response from destination")
  ;
  
  
  private String code, message;
 
    private NIBBsResponseCodes(String value, String message) {
    this.code = value;
    this.message = message;
    }
    
    public String getCode(){
        return code;
    }
    
    public String getMessage(){
        
        return message;
    }
    
       public String getInlaksCode(){
        
        return "INK_NIP_"+code;
    }
    
   public NIBBsResponseCodes getResponseObject(String code){
       NIBBsResponseCodes respcode; 
        switch(code.trim()){
           
            default: 
                 respcode = NIBBsResponseCodes.System_malfunction;
            break;
            
            case "00":
              
            respcode = NIBBsResponseCodes.SUCCESS;
            
            break;
            
            
            case "01":
              
            respcode = NIBBsResponseCodes.Status_unknown;
            
            break;
            
            case "03":
              
            respcode = NIBBsResponseCodes.Invalid_Sender;
            
            break;
            
            case "05":
              
            respcode = NIBBsResponseCodes.Do_not_honor;
            
            break;
            
            case "07":
              
            respcode = NIBBsResponseCodes.Invalid_Account;
            
            break;
            
            case "08":
              
            respcode = NIBBsResponseCodes.Account_Name_Mismatch;
            
            break;
            
            case "09":
              
            respcode = NIBBsResponseCodes.Request_processing_in_progress;
            
            break;
            
            case "12":
              
            respcode = NIBBsResponseCodes.Invalid_transaction;
            
            break;

            case "13":
              
            respcode = NIBBsResponseCodes.Invalid_Amount;
            
            break;
            
            case "14":
              
            respcode = NIBBsResponseCodes.Invalid_Batch_Number;
            
            break;

           case "15":
              
            respcode = NIBBsResponseCodes.Invalid_Session_or_Record_ID;
            
            break;
            
            case "16":
              
            respcode = NIBBsResponseCodes.Unknown_Bank_Code;
            
            break;
            
            case "17":
              
            respcode = NIBBsResponseCodes.Invalid_Channel;
            
            break;
            
            case "18":
              
            respcode = NIBBsResponseCodes.Wrong_Method_Call;
            
            break;
            
            case "21":
              
            respcode = NIBBsResponseCodes.No_action_taken;
            
            break;
            
            case "25":
              
            respcode = NIBBsResponseCodes.Unable_to_locate_record;
            
            break;
            
            case "26":
              
            respcode = NIBBsResponseCodes.Duplicate_record;
            
            break;
            
            case "30":
              
            respcode = NIBBsResponseCodes.Format_error;
            
            break;
            
            case "35":
              
            respcode = NIBBsResponseCodes.Contact_sending_bank;
            
            break;
            
            case "51":
              
            respcode = NIBBsResponseCodes.No_sufficient_funds;
            
            break;
            
            case "57":
              
            respcode = NIBBsResponseCodes.Transaction_not_permitted_to_sender;
            
            break;
            
            case "58":
              
            respcode = NIBBsResponseCodes.Transaction_not_permitted_on_channel;
            
            break;
            
            case "61":
              
            respcode = NIBBsResponseCodes.Transfer_limit_Exceeded;
            
            break;
            
            case "63":
              
            respcode = NIBBsResponseCodes.Security_violation;
            
            break;
            
            case "65":
              
            respcode = NIBBsResponseCodes.Exceeds_withdrawal_frequency;
            
            break;

            case "69":
              
            respcode = NIBBsResponseCodes.Unsuccessful_Account_Amount_block;
            
            break;
 
            case "70":
              
            respcode = NIBBsResponseCodes.Unsuccessful_Account_Amount_unblock;
            
            break;
            
            case "71":
              
            respcode = NIBBsResponseCodes.Empty_Mandate_Reference_Number;
            
            break;

            case "91":
              
            respcode = NIBBsResponseCodes.Beneficiary_Bank_not_available;
            
            break;

            case "92":
              
            respcode = NIBBsResponseCodes.Routing_error;
            
            break;
            
            case "94":
              
            respcode = NIBBsResponseCodes.Duplicate_transaction;
            
            break;
            
            case "96":
              
            respcode = NIBBsResponseCodes.System_malfunction;
            
            break;
            
            case "97":
              
            respcode = NIBBsResponseCodes.Timeout;
            
            break;

        }
        
        return respcode;
    }
    
    
  }

