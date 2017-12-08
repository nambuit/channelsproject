/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nibbsofis.service;

import javax.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Temitope
 */
@Getter @Setter
@XmlRootElement
public class FundTransferNotificationRequest {
  private String SessionID;
  private String RequestorID;
  private String OFICode;
  private String RequestorPhoneNumber;
  private String OFIName;
  private String DebitAmount;
  private String DebitAccountNumber;
  private String DebitAccountName;
  private ToAccountList ToAccountList;
  private String TransactionReference;
  private String TransactionDateTime;
  
}
