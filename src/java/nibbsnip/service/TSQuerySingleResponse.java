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
@Getter @Setter
@XmlRootElement
public class TSQuerySingleResponse {
    
    private String SourceInstitutionCode;
    private String ChannelCode;
    private String SessionID;
    private String ResponseCode;
    
}
