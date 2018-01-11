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
@XmlRootElement(name = "SingleTransferRequest")
public class SingleTransferRequest {
    
    @XmlElement(name = "TransRef")
    private String TransRef;
    
    @XmlElement(name = "TransactionDate")
    private String TransactionDate;
    
    @XmlElement(name = "DebitAccount")
    private String DebitAccount;
    
    @XmlElement(name = "CreditAccount")
    private String CreditAccount;
    
    @XmlElement(name = "Currency")
    private String Currency;
    
    @XmlElement(name = "Amount")
    private BigDecimal Amount;
    
    @XmlElement(name = "Narration")
    private String Narration;
    
    
}
