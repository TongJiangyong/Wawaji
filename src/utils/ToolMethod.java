package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.PrintStream;
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
    
    public static void deleteLogFile(){
		File f = new File(Constant.OUTPUTFILE);  // 输入要删除的文件位置
		if(f.exists())
		    f.delete();
    }
    
    public static void useFileOutPut(){
        try {
        	if(Constant.FILE_OUTPUT ==null){
        		Constant.FILE_OUTPUT =new PrintStream(Constant.OUTPUTFILE); // 创建文件输出流2
        	}
        	
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.setOut(Constant.FILE_OUTPUT);// 设置使用新的输出流
    }
    
    public static void useStandOutPut(){
        System.setOut(Constant.STAND_OUTPUT);// 设置使用新的输出流
    }
    
    public static void printToScreen(String logInfo){
    	useStandOutPut();
    	System.out.println(logInfo);
    	useFileOutPut();
    }
   
}
