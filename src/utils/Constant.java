package utils;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.RaspiPin;

public class Constant {
    public enum Action {  
  	  NONE,UP, DOWN, LEFT, RIGHT,ZHUA,LIGHTIN,LIGHTOUT,START
    }
    public static final int CONTROL_WAIT_TIME = 500;
    public static final int SIG_WAIT_TIME = 20000;
    public static final String APP_ID = "d954a23118b94ecd9dcdb67b0718d077";
    public static final String SIG_NAME = "wawaji_user";
	public static Object WORKTHREAD_LOCK=new Object();
    public static String CHANNEL_NAME = "test_channel_name";

}
