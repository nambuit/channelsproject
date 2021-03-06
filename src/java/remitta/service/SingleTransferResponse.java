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
 * @author wumoru
 */

@Getter @Setter
@XmlRootElement(name = "SingleTransferResponse")
public class SingleTransferResponse {
    
     @XmlElement(name = "TransRef")
     private String TransRef;
     
     @XmlElement(name = "TransactionDate")
     private String TransactionDate;
     
     @XmlElement(name = "ResponseCode")
     private String ResponseCode;
     
     @XmlElement(name = "ResponseText")
     private String ResponseText;
     
     
    
}
