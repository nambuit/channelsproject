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
@XmlRootElement(name = "NESingleResponse")
@Getter @Setter
public class NESingleResponse {
    
    @XmlElement(name = "SessionID")
    private String SessionID;
    
    @XmlElement(name = "DestinationInstitutionCode")
    private String DestinationInstitutionCode;
    
    @XmlElement(name = "ChannelCode")
    private String ChannelCode;
    
    @XmlElement(name = "AccountNumber")
    private String AccountNumber;
    
    @XmlElement(name = "AccountName")
    private String AccountName;
    
    @XmlElement(name = "BankVerificationNumber")
    private String BankVerificationNumber;
    
    @XmlElement(name = "KYCLevel")
    private String KYCLevel;
    
    @XmlElement(name = "ResponseCode")
    private String ResponseCode;
    
}
