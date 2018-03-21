/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nibbsnip.service;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.Getter;


/**
 *
 * @author wumoru
 */
@Getter
@XmlRootElement(name = "TSQuerySingleResponse")
public class TSQuerySingleResponse_client {
    
    @XmlElement(name = "SourceInstitutionCode")
    private String SourceInstitutionCode;
    
    @XmlElement(name = "ChannelCode")
    private String ChannelCode;
    
    @XmlElement(name = "SessionID")
    private String SessionID;
    
    @XmlElement(name = "ResponseCode")
    private String ResponseCode;
    
}
