/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.t24;



/**
 *
 * @author Administrator
 */

public class EnquiryResponse {
    private String errormessage;
    private String data;
    
    public void setErrorMessage(String message){
        this.errormessage = message;
    }
    
    public String getErrorMessage(){
        return this.errormessage;
    }
    
    
        public void setData(String text){
        this.data = text;
    }
    
    public String getData(){
        return this.data;
    }
}
