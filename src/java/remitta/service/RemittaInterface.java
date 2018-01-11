/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package remitta.service;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import org.t24.AppParams;

/**
 *
 * @author Temitope
 */
@WebService(serviceName = "RemittaInterface")
public class RemittaInterface {

    /**
     * This is a sample web service operation
     */
    @WebMethod(operationName = "hello")
    public String hello(@WebParam(name = "name") String txt) {
        
  
        
        return "Hello " + txt + " !";
        
    }
    
    
