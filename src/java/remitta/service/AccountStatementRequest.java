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
@XmlRootElement(name = "AccountStatementRequest")
public class AccountStatementRequest {
    
     @XmlElement(name = "AccountNumber")
     private String AccountNumber;
     
      @XmlElement(name = "StatementDate")
      private String StatementDate;
    
    
    
}
