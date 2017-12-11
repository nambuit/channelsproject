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
 * @author chijiokennamani
 */
@Getter @Setter
public class MandateAdviceRequest {
    private String SessionID;
    private String DestinationInstitutionCode;
    private Integer ChannelCode;
    private String MandateReferenceNumber;
    private BigDecimal Amount;
    private String DebitAccountName;
    private String DebitAccountNumber;
    private String DebitBankVerificationName;
    private String DebitKYCLevel;
    private String BeneficiaryAccountName; 
    private String BeneficiaryAccountNumber;
    private String BeneficiaryBankVerificationNumber;
    private String BeneficiaryKYCLevel; 
}
