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
public class Header {
    
    @XmlElement(name = "BatchNumber")
    private String BatchNumber;
    
    @XmlElement(name = "NumberOfRecords")
    private int NumberOfRecords;
    
    @XmlElement(name = "ChannelCode")
    private String ChannelCode;
    
    @XmlElement(name = "TransactionLocation")
    private String TransactionLocation;
    
}
