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
public class AmountUnblockResponse {
   private String SessionID;
   private String DestinationInstitutionCode;
   private String ChannelCode;
   private String ReferenceCode;
   private String TargetAccountName;
   private String TargetBankVerificationNumber;
   private String TargetAccountNumber;
   private String ReasonCode;
   private String Narration;
   private BigDecimal Amount;
   private String ResponseCode;
}
