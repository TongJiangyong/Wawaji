package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class ToolMethod {
	
    public static void wait_time(CountDownLatch x, int tInMS, String accountName) {
        try {
            x.await(tInMS, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            System.out.println(accountName+":"+e);
        }
        if (x.getCount() == 1) {
        	System.out.println(accountName+":"+"ERROR STATUS");
        }
    }
    
    /**
     * 
       		String pwdString = exec("pwd").toString();  
            String netsString = exec("netstat -nat|grep -i \"80\"|wc -l").toString(); 
     */
    public static Object exec(String cmd) {  
        try {  
            String[] cmdA = { "/bin/sh", "-c", cmd};  
            Process process = Runtime.getRuntime().exec(cmdA);  
            LineNumberReader br = new LineNumberReader(new InputStreamReader(  
                    process.getInputStream()));  
            StringBuffer sb = new StringBuffer();  
            String line;  
         while ((line = br.readLine()) != null) {  
                System.out.println(line);  
                sb.append(line).append("\n");  
            }  
            return sb.toString();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return null;  
    }  
   
}
