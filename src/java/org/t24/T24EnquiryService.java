/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.t24;

import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import org.apache.log4j.Level;

/**
 *
 * @author Administrator
 */
@WebService(serviceName = "T24EnquiryService")
public class T24EnquiryService {

    /**
     * This is a sample web service operation
     */
    
    AppParams options;  
         T24Link t24;       
         String logfilename = "T24EnquiryService";
       



       public T24EnquiryService(){
       try
        {

            options = new AppParams();



            t24 = "TAFJ".equals(options.getT24Framework().trim().toUpperCase())? new T24TAFJLink():
                  new T24TAFCLink(options.getHost(), options.getPort(), options.getOFSsource()); 

        }
        catch (Exception e)
        {   
            options.getServiceLogger(logfilename).LogError(e.getMessage(), e, Level.FATAL);
        }


        }
    
    
    
    @WebMethod(operationName = "getEnquiryResult")
    public  EnquiryResponse  getEnquiryResult(@WebParam(name = "enquiryName") String txt,@WebParam(name = "company") String comp,@WebParam(name = "filter") String filter) {
       EnquiryResponse response = new EnquiryResponse();
               
        try{
            
         ArrayList<List<String>>  result = t24.getOfsData(txt.replace("(", "%"),options.getOfsuser(), options.getOfspass(),filter,comp);
        
        List<List<Field>> fields = new ArrayList<>();
    
        if(result.size()==1){
            throw new Exception(result.get(0).get(0));
        }
    
      List<String> headers = result.get(0);
           
              if(headers.size()!=result.get(1).size()){
               
               throw new Exception(result.get(1).get(0));
           }
          
            


         
         
          for(int i = 1; i<result.size();i++){
              
          List<Field> records = new ArrayList<>();
          
        List<String> record = result.get(i);
        
          for(int ii = 1; ii<record.size();ii++){ 
              Field field = new Field();
              
              field.setFieldName(headers.get(ii));
              field.setFieldValue(record.get(ii).replace("\"", "").trim().replace(",", ""));
             
              records.add(field);
          }
          
          fields.add(records);
          
      
        
          }
    Gson gson = new Gson();
    
    
       response.setData(gson.toJson(fields));
        
       return response;
        
        }
        catch(Exception d){
            response.setErrorMessage(d.getMessage());
            return response;
        }
    }
}
