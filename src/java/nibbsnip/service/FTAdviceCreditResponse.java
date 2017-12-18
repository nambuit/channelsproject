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
public class FTAdviceCreditResponse {
   private String SessionID;
   private String NameEnquiryRef;
   private String DestinationInstitutionCode;
   private String ChannelCode;
   private String BeneficiaryAccountName;
   private String BeneficiaryAccountNumber;
   private String BeneficiaryVerificationNumber;
   private String BeneficiaryBankVerificationNumber;
   private String BeneficiaryKYCLevel;
   private String OriginatorAccountName;
   private String OriginatorAccountNumber;
   private String OriginatorBankVerificationNumber;
   private String OriginatorKYCLevel;
   private String TransactionLocation;
   private String Narration;
   private String PaymentReference;
   private BigDecimal Amount;
   private String ResponseCode;
}
