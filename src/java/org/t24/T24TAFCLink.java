
package org.t24;

import com.jbase.jremote.JConnection;
import com.jbase.jremote.JDynArray;
import com.jbase.jremote.JFile;
import com.temenos.tocf.t24ra.T24Connection;
import com.temenos.tocf.t24ra.T24DefaultConnectionFactory;
import com.temenos.tocf.t24ra.T24Exception;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import javax.imageio.ImageIO;


/**
 *
 * @author Temitope
 */
public class T24TAFCLink implements T24Link {
    private String host; int port;
    private JConnection ctx;
    private T24Connection cfx;
    private String OFSsource;
    
    public T24TAFCLink(String Host, int Port,String ofSsouurce){
       this.host = Host;
       this.port = Port;
       this.OFSsource = ofSsouurce;
    }
    
    
    
    @Override
       public String PostMsg(String sOFS) throws Exception {

        try {
         String resp;
        Properties props;
       T24DefaultConnectionFactory connectionFactory = new T24DefaultConnectionFactory();
        connectionFactory.setPort(port);
        connectionFactory.setHost(host);
        props = new Properties();
        props.setProperty("env.OFS_SOURCE", OFSsource);//"GCS");//
        connectionFactory.setConnectionProperties(props);
            T24Connection t24Connection = connectionFactory.getConnection();
            //String req = "OFS";
			//main line for ofs 
            resp = t24Connection.processOfsRequest(sOFS);
            t24Connection.close();
            return resp;
        } catch (T24Exception ex) {

          throw (ex);
        }   
    }
    
           @Override
           public String PostMsg(String sOFS, String ofssource) throws Exception {

        try {
         String resp;
        Properties props;
       T24DefaultConnectionFactory connectionFactory = new T24DefaultConnectionFactory();
        connectionFactory.setPort(port);
        connectionFactory.setHost(host);
        props = new Properties();
        props.setProperty("env.OFS_SOURCE", ofssource);//"GCS");//
        connectionFactory.setConnectionProperties(props);
            T24Connection t24Connection = connectionFactory.getConnection();
            //String req = "OFS";
			//main line for ofs 
            resp = t24Connection.processOfsRequest(sOFS);
            t24Connection.close();
            return resp;
        } catch (T24Exception ex) {

          throw (ex);
        }   
    }
       
       
 
       
          @Override
           public ArrayList<List<String>> getOfsData(String EnquiryName,String Username,String Password,String Filters, String compcode) throws Exception{
          ArrayList<List<String>> records  = new ArrayList<>();
           
     try{      
   String message = "ENQUIRY.SELECT,,"+Username+"/"+Password+"/"+compcode+","+EnquiryName+","+Filters;

   String result = this.PostMsg(message);
   
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
         throw(d);
     }
       }
    
    
     public  HashMap<String,Integer> getFieldMapping(String filename, String coreFolder) throws Exception{
       
         try{
            
         HashMap<String,Integer>  mymapping = new HashMap<>();
         
        String  tempfilename = filename.replace(".", "-");
         
         if(tempfilename.split("-")[0].trim().length()>1){
             
             String [] parts = tempfilename.split("-");
             
             parts[0] = "F";
             
          filename =   String.join(".", parts);
           
         }
    
        
        JFile insertfile = ctx.open(coreFolder);
             JDynArray insertRecord = insertfile.read("I_"+filename);  
    
        
    
       
        for(int i=1;i<insertRecord.getNumberOfAttributes();i++){
          
           
            String record = insertRecord.get(i); 
            
             if(record.startsWith("*")||record.startsWith("!")){
                 continue;
             }
             
             
          if (record.contains(".")){  
            String [] lines = record.split(",", 2);
            
            for(String line:lines){
                
             String [] values = line.split(" TO ", 2);
            if(values.length > 1){        
            String test = values[1].split(",")[0].trim();
             
            mymapping.put(values[0].replace("EQU", "").trim(), Integer.parseInt(test));
            }
            }
          }
               
            
        }
               
        
        return mymapping;
        }
        catch(Exception d){
            throw(d);
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
            
            output.append(param.getVersion());


            String options = String.join("/", param.getOptions());

            output.append(options.toUpperCase()).append(",");

            String credentials = String.join("/", param.getCredentials());
            
            output.append(credentials).append(",");
            
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
  public Boolean IsSuccessful(String ofsresposne){
      
      try{
      return ((!ofsresposne.isEmpty()) && ofsresposne.split("/")[2].startsWith("1") );
      }
      catch(Exception s){
          return false;
      }
  }
    
    
    public byte[] extractBytes (String ImageName) throws IOException {
 // open image
 File imgPath = new File(ImageName);
 BufferedImage bufferedImage = ImageIO.read(imgPath);

 // get DataBufferBytes from Raster
 WritableRaster raster = bufferedImage .getRaster();
 DataBufferByte data   = (DataBufferByte) raster.getDataBuffer();

 return ( data.getData() );
}

    @Override
    public ArrayList<List<String>> getOfsData(String fundstransfer, String ofsuser, String ofspass, String string) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}
