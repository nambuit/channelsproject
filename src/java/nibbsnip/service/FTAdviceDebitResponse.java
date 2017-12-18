/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nibbsnip.service;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author OLAMIDE
 */

@Getter @Setter
@XmlRootElement
public class FTAdviceDebitResponse {
   private String SessionID;
   private String NameEnquiryRef;
   private String DestinationInstitutionCode;
   private String ChannelCode;
   private String DebitAccountName;
   private String DebitAccountNumber;
   private String DebitBankVerificationNumber;
   private String DebitKYCLevel;
   private String BeneficiaryAccountName;
   private String BeneficiaryAccountNumber;
   private String BeneficiaryBankVerificationNumber;
   private String BeneficiaryKYCLevel;
   private String TransactionLocation;
   private String Narration;
   private String PaymentReference;
   private String MandateReferenceNumber;
   private String TransactionFee;
   private BigDecimal Amount;
   private String ResponseCode;
}
