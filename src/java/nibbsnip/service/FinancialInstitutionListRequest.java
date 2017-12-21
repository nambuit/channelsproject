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
@Getter @Setter 
@XmlRootElement(name = "FinancialInstitutionListRequest")
public class FinancialInstitutionListRequest {
    
    @XmlElement(name = "BatchNumber")
    private String BatchNumber;
    
    @XmlElement(name = "NumberOfRecords")
    private int NumberOfRecords;
    
    @XmlElement(name = "ChannelCode")
    private String ChannelCode;
    
    @XmlElement(name = "TransactionLocation")
    private String TransactionLocation;
    
    @XmlElement(name = "InstitutionCode")
    private String InstitutionCode;
    
    @XmlElement(name = "InstitutionName")
    private String InstitutionName;
}
