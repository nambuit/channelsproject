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
 * @author wumoru
 */
@Getter @Setter
public class TSQuerySingleResponse {
    
    private int SourceInstitutionCode;
    private int ChannelCode;
    private int SessionID;
    private int ResponseCode;
    
}
