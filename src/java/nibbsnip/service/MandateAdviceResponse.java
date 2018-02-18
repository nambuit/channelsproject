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
 * @author chijiokennamani
 */
@Setter
@XmlRootElement(name = "MandateAdviceResponse")
public class MandateAdviceResponse {
    
    @XmlElement(name = "SessionID")
    private String SessionID;
    
    @XmlElement(name = "DestinationInstitutionCode")
    private String DestinationInstitutionCode;
    
    @XmlElement(name = "ChannelCode")
    private String ChannelCode;
    
    @XmlElement(name = "MandateReferenceNumber")
    private String MandateReferenceNumber;
    
    @XmlElement(name = "Amount")
    private BigDecimal Amount;
    
    @XmlElement(name = "DebitAccountName")
    private String DebitAccountName;
    
    @XmlElement(name = "DebitAccountNumber")
    private String DebitAccountNumber;
    
    @XmlElement(name = "DebitBankVerificationName")
    private String DebitBankVerificationName;
    
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
    
    @XmlElement(name = "ResponseCode")
    private String ResponseCode; 
}
