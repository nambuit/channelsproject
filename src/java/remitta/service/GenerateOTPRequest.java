/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package remitta.service;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author OLAMIDE
 */

@Getter @Setter 
@XmlRootElement(name = "GenerateOTPRequest")
public class GenerateOTPRequest {
    
    @XmlElement(name = "AccountNumber")
    private String AccountNumber;
}
