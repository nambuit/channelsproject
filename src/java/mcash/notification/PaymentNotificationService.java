/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mcash.notification;

import com.google.gson.Gson;
import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.date;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;
import org.apache.log4j.Level;
import org.t24.AppParams;
import org.t24.DBConnector;
import org.t24.PGPEncrytionTool;
import org.t24.T24TAFJLink;



/**
 * REST Web Service
 *
 * @author dogor-Igbosuah
 */
@Path("mcashpaymentnotification")
public class PaymentNotificationService {

    @Context
    private UriInfo context;
    
    
    AppParams options;
    PGPEncrytionTool nipssm;
    Connection conn = null;
    private DBConnector db;
    String logfilename = "NIPClientInterface";
    String logTable = "InlaksNIPWrapperLog";
    String apikey = "";

    /**
     * Creates a new instance of PaymentNotification
     */
    public PaymentNotificationService() {
        
        try {

          options = new AppParams();

            nipssm = new PGPEncrytionTool(options);
             db = new DBConnector(options.getDBserver(), options.getDBuser(), options.getDBpass(), "NIPLogs");

           // t24 = new T24TAFJLink();
       
         } catch (Exception e) {
            //options.getServiceLogger(logfilename).LogError(e.getMessage(), e, Level.FATAL);
        }
    }
    

    /**
     * Retrieves representation of an instance of mcash.notification.PaymentNotification
     * @param payload
     * @return an instance of java.lang.String
     */

    @POST
    @Path("Notify")
    @Produces(MediaType.TEXT_XML)
    
    public String Notify(String payload) {
        Gson gson = new Gson();
        String monthlyTable = "";
    
        try{
        
        PaymentNotification request = (PaymentNotification) options.XMLToObject(payload, new PaymentNotification());
        
        
        List<Object> notifyvalues = new ArrayList<>();
        List<String> notifyheaders = new ArrayList<>();
      
                 
                  notifyheaders.add("MerchantCode");
                  notifyvalues.add(request.getMerchantCode());
               
               
                  notifyheaders.add("MerchantName");
                  notifyvalues.add(request.getMerchantName());
                  
            
              
                  notifyheaders.add("MerchantPhoneNumber");
                  notifyvalues.add(request.getMerchantPhoneNumber());
                  
                 
                  notifyheaders.add("PayerName");
                  notifyvalues.add(request.getPayerName());
                  
                  
                  notifyheaders.add("PayerPhoneNumber");
                  notifyvalues.add(request.getPayerPhoneNumber());
                  
                  
                  notifyheaders.add("ReferenceCode");
                  notifyvalues.add(request.getReferenceCode());
                  
                  
                  notifyheaders.add("RequestorID");
                  notifyvalues.add(request.getRequestorID());
                  
                  
                  notifyheaders.add("Amount");
                  notifyvalues.add(request.getAmount());
                  
                  
                  notifyheaders.add("ResponseCode");
                  notifyvalues.add(request.getResponseCode());
                  
                  
                  notifyheaders.add("SessionID");
                  notifyvalues.add(request.getSessionID());
                  
                  
                  notifyheaders.add("TransactionDate");
                  notifyvalues.add(request.getTransactionDate());
                  
                  SimpleDateFormat df = new SimpleDateFormat("MMMyyyy");
                  Date date = new Date();
                  
                   monthlyTable = df.format(date) + "mCASH_PAYMENTNOTIFICATIONS";
                  
                  String createquery = options.getCreateMcashNotificationTableScript(monthlyTable);

                try {
                    db.Execute(createquery);
                } catch (Exception r) {

                }

                db.insertData(notifyheaders, notifyvalues.toArray(), monthlyTable);
                 
        }
        catch(Exception e){
            
        }
      
      
      return "";
              
              }
    


}       