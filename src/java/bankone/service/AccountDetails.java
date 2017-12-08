/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bankone.service;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Temitope
 */
@Getter @Setter

public class AccountDetails {

 private String AccountName;
 private String AccountNumber;
 private String Currency;
 private String ProductDescription;
 private String AccountOfficer;
 private String PhoneNo;
 private String InstitutionCode;
 private Boolean IsSuccessful;
 private String Message;
 private String AccountStatus;
 private BigDecimal LedgerBalance;
 private BigDecimal AvailableBalance;
  
}
