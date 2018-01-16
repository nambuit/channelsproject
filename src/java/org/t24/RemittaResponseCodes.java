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



 public enum RemittaResponseCodes {
  SUCCESS("00","SUCCESS"),
  INVALID_ACCOUNT("7701","INVALID ACCOUNT"),
  DORMANT_ACCOUNT("7702","DORMANT ACCOUNT"),
  CLOSED_ACCOUNT("7703","CLOSED ACCOUNT"),
  INSUFFICIENT_FUNDS("7704","INSUFFICIENT FUNDS"),
  ACCOUNT_BLOCKED("7705","ACCOUNT BLOCKED"),
  BANK_NOT_ONLINE("7706","BANK NOT ONLINE"),
  DUPLICATE_ACCOUNT("7707","DUPLICATE ACCOUNT"),
  UNKNOWN_ERROR("7799","UNKNOWN_ERROR"),
  UNKNOWN_TRANSACTION("7800","UNKNOWN TRANSACTION"),
  DUPLICATE_TRANSACTION("8000","DUPLICATE_TRANSACTION"),
  ENCRYPTION_ERROR("8001","ENCRYPTION ERROR"),
  BANKING_HOST_IS_DOWN("8003","BANKING HOST IS DOWN"),
  AUTHENTICATION_ERROR("9999","AUTHENTICATION ERROR")
  ;
  
  
  private String code, message;
 
    private RemittaResponseCodes(String value, String message) {
    this.code = value;
    this.message = message;
    }
    
    public String getCode(){
        return code;
    }
    
    public String getMessage(){
        
        return message;
    }
    
  }

