/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.t24;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 *
 * @author dofoleta
 */
public class PGPEncrytionTool {
   
    
    private AppParams options;
    
    public PGPEncrytionTool (AppParams params){
        this.options = params;
    }
    
    private String executeSSMComand(String value, String host, int port) {

        OutputStream out;
        InputStream in = null;

        try {
            byte[] b;
            try (Socket socket = new Socket(host, port)) {
                out = socket.getOutputStream();
                out.write(value.getBytes());
                b = new byte[64096];
                do {
                    in = socket.getInputStream();
                    in.read(b);
                    if (in != null) {
                        break;
                    }
                } while (true);
                out.close();
                in.close();
            }
            return new String(b);
        } catch (Exception ex) {
            String str = "Socket Connection:  Unable to connect to host... It may be down";
            System.out.println(str);
        }
        return null;
    }
    
   public String decrypt(String cipher){
      
      cipher = cipher.replace("ENC", "");
       
    String rawtext = executeSSMComand("DEC"+options.getEncryptionkey()+"#"+cipher, options.getEncryptionserver(), options.getEncryptionport());
       
    return rawtext.replace("DEC<", "<").trim(); 
       
   }
    
      public String encrypt(String rawtext){
    
    rawtext = rawtext.trim();
    String cipher = executeSSMComand("ENC"+rawtext, options.getEncryptionserver(), options.getEncryptionport());
       
    return cipher.replace("ENC", "").trim(); 
       
   }
      
      
      public String generatePublicKey(String username ,String password){
         return  executeSSMComand("GEN"+username+"#"+password, options.getEncryptionserver(), options.getEncryptionport());  
      }
   
}
