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
 * @author chijiokennamani
 */
@Setter 
@XmlRootElement(name = "FinancialInstitutionListResponse")
public class FinancialInstitutionListResponse {
    
    
    @XmlElement(name = "BatchNumber")
    private String BatchNumber;
    
    @XmlElement(name = "DestinationInstitutionCode")
    private String DestinationInstitutionCode;
    
    @XmlElement(name = "ChannelCode")
    private String ChannelCode;
    
    @XmlElement(name = "NumberOfRecords")
    private int NumberOfRecords;
    
    @XmlElement(name = "ResponseCode")
    private String ResponseCode; 
}
