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
@XmlRootElement(name = "AccountBalanceResponse")
public class AccountBalanceResponse {
    
    @XmlElement(name = "ResponseCode")
      private String ResponseCode;
    
    @XmlElement(name = "ResponseText")
      private String ResponseText;
    
    @XmlElement(name = "AccountName")
      private String AccountName;
    
    @XmlElement(name = "BalanceDate")
      private String BalanceDate;
    
    @XmlElement(name = "Amount")
      private BigDecimal Amount;
    
    @XmlElement(name = "Currency")
      private String Currency;
}
