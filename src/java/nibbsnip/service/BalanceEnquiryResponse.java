/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nibbsnip.service;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author OLAMIDE
 */
@Getter @Setter

public class BalanceEnquiryResponse {
   private String SessionID;
   private String DestinationInstitutionCode;
   private String ChannelCode;
   private String AuthorizationCode;
   private String TargetAccountName;
   private String TargetBankVerificationNumber;
   private String TargetAccountNumber;
   private BigDecimal AvailableBalance;
   private String ResponseCode;
}
