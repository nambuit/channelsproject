/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package remitta.service;


import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author wumoru
 */
@Getter @Setter

public class AccountStatementRequest {
    

     private String AccountNumber;
     

      private String StatementDate;
      
      private String CompanyCode;
    
    
    
}
