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
public class FTSingleDebitRequest {
    
    private int SessionID;
    private int NameEnquiryRef;
    private int DestinationInstitutionCode;
    private int ChannelCode;
    private String DebitAccountName;
    private int DebitAccountNumber;
    private int DebitBankVerificationNumber;
    private int DebitKYCLevel;
    private String BeneficiaryAccountName;
    private int BeneficiaryAccountNumber;
    private int BeneficiaryBankVerificationNumber;
    private int BeneficiaryKYCLevel;
    private double TransactionLocation;
    private String Narration;
    private int PaymentReference;
    private int MandateReferenceNumber;
    private double TransactionFee;
    private double Amount;
    
}
