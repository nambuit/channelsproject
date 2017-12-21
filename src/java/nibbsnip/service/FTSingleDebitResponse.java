/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nibbsnip.service;

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
@XmlRootElement(name = "FTSingleDebitResponse")
public class FTSingleDebitResponse {
    
   @XmlElement(name = "SessionID")
   private String SessionID;
   
    
   @XmlElement(name = "NameEnquiryRef")
   private String NameEnquiryRef;
   
   @XmlElement(name = "DestinationInstitutionCode") 
   private String DestinationInstitutionCode;
   
   @XmlElement(name = "ChannelCode")
   private String ChannelCode;
   
   @XmlElement(name = "DebitAccountName")
   private String DebitAccountName;
   
   @XmlElement(name = "DebitAccountNumber")
   private String DebitAccountNumber;
   
   @XmlElement(name = "DebitBankVerificationNumber")
   private String DebitBankVerificationNumber;
   
   @XmlElement(name = "DebitKYCLevel")
   private String DebitKYCLevel;
   
   @XmlElement(name = "BeneficiaryAccountName")
   private String BeneficiaryAccountName;
   
   @XmlElement(name = "BeneficiaryAccountNumber")
   private String BeneficiaryAccountNumber;
   
   @XmlElement(name = "BeneficiaryBankVerificationNumber")
   private String BeneficiaryBankVerificationNumber;
   
   @XmlElement(name = "BeneficiaryKYCLevel")
   private String BeneficiaryKYCLevel;
   
   @XmlElement(name = "TransactionLocation")
   private String TransactionLocation;
   
   @XmlElement(name = "Narration")
   private String Narration;
   
   @XmlElement(name = "PaymentReference") 
   private String PaymentReference;
   
   @XmlElement(name = "MandateReferenceNumber") 
   private String MandateReferenceNumber;
   
   @XmlElement(name = "TransactionFee") 
   private BigDecimal TransactionFee;
   @XmlElement(name = "Amount") 
   private BigDecimal Amount;
   
   @XmlElement(name = "ResponseCode") 
   private String ResponseCode;
    
}
