/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bankone.service;

import javax.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Temitope
 */
@Getter @Setter 
@XmlRootElement
public class ISOResponse {
    
    private Boolean  IsSuccessful;
    private String ISOMessage;
    private String ErrorMessgae;

}
