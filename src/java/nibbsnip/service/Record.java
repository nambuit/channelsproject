/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nibbsnip.service;

import javax.xml.bind.annotation.XmlElement;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author inlaks-root
 */
@Getter @Setter 
public class Record {
    
     @XmlElement(name = "InstitutionCode")
    private String InstitutionCode;
    
    @XmlElement(name = "InstitutionName")
    private String InstitutionName;
    
    @XmlElement(name = "Category")
    private String Category;
}
