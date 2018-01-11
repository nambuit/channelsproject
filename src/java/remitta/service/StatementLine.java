/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package remitta.service;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author wumoru
 */
@Getter @Setter
@XmlRootElement(name = "StatementLine")
public class StatementLine {
    
    @XmlElement(name = "TransactionDate")
     private String TransactionDate;
    
    @XmlElement(name = "Narration")
     private String Narration;
    
    @XmlElement(name = "Amount")
     private BigDecimal Amount;
    
    @XmlElement(name = "Currency")
     private String Currency;
    
    @XmlElement(name = "CRDR")
     private String CRDR;
    
}
