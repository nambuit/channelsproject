/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mcash.notification;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.Setter;
/**
 *
 * @author cahamefula
 */
@Getter @Setter
@XmlRootElement(name = "PaymentNotification")
public class PaymentNotification {
    
    @XmlElement(name = "SessionID")
   private String SessionID;
    
    @XmlElement(name = "RequestorID")
   private String RequestorID;
    
   
   @XmlElement(name = "PayerPhoneNumber")
   private String PayerPhoneNumber;
   
   @XmlElement(name = "PayerName")
   private String PayerName;
   
   @XmlElement(name = "MerchantCode")
   private String MerchantCode;
   
   @XmlElement(name = "MerchantName")
   private String MerchantName;
   
    @XmlElement(name = "MerchantPhoneNumber")
   private String MerchantPhoneNumber;
    
    @XmlElement(name = "Amount")
   private String Amount;
    
    @XmlElement(name = "ReferenceCode")
   private String ReferenceCode;
    
    @XmlElement(name = "TransactionDate")
   private String TransactionDate;
    
    @XmlElement(name = "ResponseCode")
   private String ResponseCode;
    
}
