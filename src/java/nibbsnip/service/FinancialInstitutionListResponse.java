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
 * @author chijiokennamani
 */
@Getter @Setter 
@XmlRootElement
public class FinancialInstitutionListResponse {
    private String BatchNumber;
    private String DestinationInstitutionCode;
    private String ChannelCode;
    private int NumberOfRecords;
    private String ResponseCode; 
}
