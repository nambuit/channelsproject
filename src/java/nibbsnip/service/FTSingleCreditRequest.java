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
public class FTSingleCreditRequest {
    private int SessionID;
    private int NameEnquiryRef;
    private int DestinationInstitutionCode;
    private int ChannelCode;
    private String BeneficiaryAccountName;
    private int BeneficiaryAccountNumber;
    private int BeneficiaryBankVerificationNumber;
    private int BeneficiaryKYCLevel;
    private int OriginatorAccountName;
    private int OriginatorAccountNumber;
    private int OriginatorBankVerificationNumber;
    private int OriginatorKYCLevel;
    private double TransactionLocation;
    private int Narration;
    private String PaymentReference;
    private double Amount;
    
    
    
    
    
}
