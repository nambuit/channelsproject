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
 * @author chijiokennamani
 */
@Getter @Setter 
public class FinancialInstitutionListResponse {
    private String BatchNumber;
    private String DestinationInstitutionCode;
    private Integer ChannelCode;
    private Integer NumberOfRecords;
    private String ResponseCode; 
}
