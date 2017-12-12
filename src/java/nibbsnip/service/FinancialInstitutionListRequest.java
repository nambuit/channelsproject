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
public class FinancialInstitutionListRequest {
    private String BatchNumber;
    private int NumberOfRecords;
    private String ChannelCode;
    private String TransactionLocation;
    private String InstitutionCode;
    private String InstitutionName;
}
