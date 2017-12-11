/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nibbsnip.service;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author wumoru
 */
@Getter @Setter
public class BalanceEnquiryRequest {
    
    private int SessionID;
    private int DestinationInstitutionCode;
    private int ChannelCode;
    private int AuthorizationCode;
    private String TargetAccountName;
    private int TargetBankVerificationNumber;
    private int TargetAccountNumber;
    
}
