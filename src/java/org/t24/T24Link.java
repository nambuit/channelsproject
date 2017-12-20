/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.t24;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Temitope
 */
public interface T24Link  {
    
    public String generateOFSTransactString(ofsParam param) throws Exception;
    
    public ArrayList<List<String>> getOfsData (String EnquiryName,String Username,String Password,String Filters) throws Exception;
    
    public Boolean IsSuccessful(String ofsresposne);
    
    public String PostMsg(String sOFS)throws Exception;
    
   public String PostMsg(String sOFS, String ofssource) throws Exception;
    
}
