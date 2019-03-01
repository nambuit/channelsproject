
package org.t24;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import ofs.service.OFSService;
import ofs.service.OFSServicePortType;
import ofs.service.ServiceResponse;


/**
 *
 * @author Temitope
 */
public class T24TAFJLink implements T24Link {
 
    
    
          @Override
          public String PostMsg(String sOFS) throws Exception {

        try {
     String resp;
     
     OFSService service = new OFSService();
     OFSServicePortType ofs = service.getOFSServiceHttpSoap11Endpoint();
     ServiceResponse response = ofs.invoke(sOFS);
     resp = response.getResponses().get(0);
           return resp;
 
        } catch (Exception ex) {

          throw (ex);
        }   
    }
    
          
           @Override
           public String PostMsg(String sOFS, String ofssource) throws Exception {

        try {
         String resp = "";
       
            return resp;
        } catch (Exception ex) {

          throw (ex);
        }   
    }
          
          
          @Override
    public Boolean IsSuccessful(String ofsresposne){
      
      try{
      return ((!ofsresposne.isEmpty()) && ofsresposne.split("/")[2].startsWith("1") );
      }
      catch(Exception s){
          return false;
      }
  }
           
           
          @Override
           public ArrayList<List<String>> getOfsData(String EnquiryName,String Username,String Password,String Filters, String compcode) throws Exception{
          ArrayList<List<String>> records  = new ArrayList<>();
         String result = "";
          
           
     try{      
   String message = "ENQUIRY.SELECT,,"+Username+"/"+Password+"/"+compcode+","+EnquiryName+","+Filters;

    result = this.PostMsg(message);
   
   String [] lines = result.split(",\"");
   
   String headerline = lines[0];
  List<String> headers = new ArrayList<>();
  
   for(String header:headerline.split("/")){
       try{
           headers.add(header.split("::")[1]);
       }
       catch(Exception d){
           headers.add(header.split(":")[1]);
       }
   }
   
   records.add(headers);
   
   for(int i=1;i<lines.length;i++){
       records.add(Arrays.asList(lines[i].split("\"\t\"")));
   }
   return records;
     }
     catch(Exception d){
    List<String> headers = new ArrayList<>();
    headers.add(result);
         records.add(headers);
         
         return records;
     }
       }
    
           @Override
            public String generateOFSTransactString(ofsParam param)
        {
            StringBuilder output = new StringBuilder();

           output.append(param.getOperation().toUpperCase()).append(',');
            
           if(param.getVersion()==null){
               param.setVersion("");
           }
           
           // output.append(param.getVersion());

            output.append(param.getVersion().toUpperCase());
            
            String options = String.join("/", param.getOptions());

            output.append(options.toUpperCase()).append(",");

            String credentials = String.join("/", param.getCredentials());
            
            String CompanyCode = '/'+param.getCompanyCode();
            
            output.append(credentials).append(CompanyCode).append(",");
            
                             if(param.getTransaction_id()==null){
               param.setTransaction_id("");
           }

            output.append(param.getTransaction_id()).append(",");

            param.getDataItems().stream().forEach((dataitem) -> {
                Boolean isMultivalue = dataitem.getItemValues().length > 1;

                int valuecount = 1;

                for(String value : dataitem.getItemValues())
                {
                    output.append(dataitem.getItemHeader()).append((isMultivalue ? ":" + valuecount + "=" + value : "=" + value)).append(",");

                    valuecount = valuecount + 1;
                }
        });

            String result = output.toString();

            result = result.substring(0,result.length()-1);
           

            return result;
        }

    @Override
    public ArrayList<List<String>> getOfsData(String fundstransfer, String ofsuser, String ofspass, String string) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
          
}
