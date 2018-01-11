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
@XmlRootElement(name = "GenerateOTPResponse")
public class GenerateOTPResponse {
    
    @XmlElement(name = "ResponseCode")
    private String ResponseCode;
    
    @XmlElement(name = "ResponseText")
    private String ResponseText;
    
    @XmlElement(name = "AccountClass")
    private String AccountClass;
    
    @XmlElement(name = "PhoneNumber")
    private String PhoneNumber;
    
}
