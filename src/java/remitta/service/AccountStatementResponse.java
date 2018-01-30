/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package remitta.service;



import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author wumoru
 */
@Getter @Setter

public class AccountStatementResponse {
    

     private  String ResponseCode;
    

     private String ResponseText;
    

    private Statement []  StatementLine;
    
    

}
