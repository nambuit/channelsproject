/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nibbsnip.service;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author wumoru
 */
@Getter @Setter
@XmlRootElement(name = "BalanceEnquiryRequest")
public class BalanceEnquiryRequest {
    
    @XmlElement(name = "SessionID")
    private String SessionID;
    
    @XmlElement(name = "DestinationInstitutionCode") 
    private String DestinationInstitutionCode;
    
    @XmlElement(name = "ChannelCode")
    private String ChannelCode;
    
    @XmlElement(name = "AuthorizationCode")
    private String AuthorizationCode;
    
    @XmlElement(name = "TargetAccountName") 
    private String TargetAccountName;
    
    @XmlElement(name = "TargetBankVerificationNumber") 
    private String TargetBankVerificationNumber;
    
    @XmlElement(name = "TargetAccountNumber") 
    private String TargetAccountNumber;
    
}
