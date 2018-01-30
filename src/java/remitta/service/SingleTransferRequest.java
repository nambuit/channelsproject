/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package remitta.service;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author wumoru
 */

@Getter @Setter

public class SingleTransferRequest {

    private String TransRef;
    

    private String TransactionDate;
    

    private String DebitAccount;
  
    private String CreditAccount;
    
  
    private String Currency;
    
 
    private String Amount;
  
    private String Narration;
    
    
}
