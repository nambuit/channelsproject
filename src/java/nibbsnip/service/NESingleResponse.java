/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nibbsnip.service;

import javax.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author wumoru
 */
@XmlRootElement
@Getter @Setter
public class NESingleResponse {
    
    private String SessionID;
    private String DestinationInstitutionCode;
    private String ChannelCode;
    private String AccountNumber;
    private String AccountName;
    private String BankVerificationNumber;
    private String KYCLevel;
    private String ResponseCode;
    
}
