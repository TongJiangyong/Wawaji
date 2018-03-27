package utils;

import java.io.PrintStream;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.RaspiPin;

public class Constant {
    public enum Action {  
  	  NONE,UP, DOWN, LEFT, RIGHT,ZHUA,LIGHTIN,LIGHTOUT,START
    }
    public static final int CONTROL_WAIT_TIME = 500;
    public static final int ZHUA_WAIT_TIME = 50;
    public static final int RESULT_WAIT_TIME = 8000;
    public static final int SIG_WAIT_TIME = 20000;
    public static final String APP_ID = "d954a23118b94ecd9dcdb67b0718d077";
    public static final String SIG_NAME = "wawaji";
	public static Object WORKTHREAD_LOCK=new Object();
    public static String CHANNEL_NAME = "channel_name";
    
    
    //command
    public static String RESULT_COMMAND_FALSE = "{\"type\":\"RESULT\",\"result\":false}";
    public static String RESULT_COMMAND_TRUE = "{\"type\":\"RESULT\",\"result\":true}";
    
    
    //output
    public static PrintStream STAND_OUTPUT = System.out;// 保存原输出流
    public static PrintStream FILE_OUTPUT = null;// 保存原输出流
    public static String OUTPUTFILE = "./log.txt";
}
