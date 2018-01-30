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

public class Statement {
    
 
     private String TransactionDate;
    
  
     private String Narration;
    
 
     private BigDecimal Amount;
  
     private String Currency;
    
  
     private String CRDR;
    
}
