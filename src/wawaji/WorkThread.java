package wawaji;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CountDownLatch;

import control.GpioControl;
import control.MyGpioPinListenerDigital;
import control.SignalControl;
import utils.Constant;
import utils.Constant.Action;
import utils.ToolMethod;

public class WorkThread  implements Runnable {
	private GpioControl controler =null;
	private SignalControl signalControl = null;
	private ControlThread controlThread = null; 
	private MyGpioPinListenerDigital lightInListenr  = null;
	public WorkThread() {
		// TODO Auto-generated constructor stub
		System.out.println("**********systerm start****************");
		this.lightInListenr = new MyGpioPinListenerDigital(Action.LIGHTIN);
		this.signalControl = new SignalControl();
		this.controler = new GpioControl(lightInListenr);
		controlThread = new ControlThread(this.controler);
		this.ctrlCHandler();
		System.out.println("**********video start****************");
		//this.startVedio();
	}
	
	@Override
	public void run() {
		new Thread(this.controlThread).start();
		this.signalControl.sigLogin();
		this.signalControl.setWorkThread(this);
		synchronized(this){
			try {
				this.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
		}
		this.controler.shutdownGPIO();
        System.out.println("**********systerm end****************");   
	}
	
	public void releaseThread(){
		this.signalControl.leaveChannel();
        System.out.println("**********leaveChannel success****************");   
		this.controlThread.releaseControlThread();
        System.out.println("**********releaseControlThread success****************");   
		//this.endVedio();
		synchronized(this){
			try {
				this.notify();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
		}
	}
	
	//deal with exit
    private class ExitHandler extends Thread {  
        public ExitHandler() {  
            super("Exit Handler");  
        }  
        public void run() {  
            System.out.println("ctrlC Set exit");   
            releaseThread();
        }  
    }  
    
    public void ctrlCHandler() {  
        Runtime.getRuntime().addShutdownHook(new ExitHandler());  
    }  
    
    public void doControlAction(Action controlAction){
    	if (this.controlThread.getNewLatch().getCount()==1){
    		this.controlThread.getNewLatch().countDown();
    	}
    	this.controlThread.setNewLatch(new CountDownLatch(1));
    	this.controlThread.doAction(controlAction);
    }
	
	
    public void startVedio(){
    	ToolMethod.exec("linux command1");
    }
    public void endVedio(){
    	ToolMethod.exec("linux command2");  	
    }
    
	
	
	public void handlerCommand(String command){
		
	}
	
	public SignalControl getSignalControl() {
		return signalControl;
	}

	public ControlThread getControlThread() {
		return controlThread;
	}
	
	
	private void test() throws InterruptedException{
		//∏© ”
		System.out.println("**********up****************");
		controler.upControl(true);
		Thread.sleep(1000);		
		System.out.println("**********down****************");
		controler.downControl(true);
		Thread.sleep(1000);
		System.out.println("**********right****************");
		controler.rightControl(true);
		Thread.sleep(1000);
		System.out.println("**********left****************");
		controler.leftControl(true);
		Thread.sleep(1000);
		System.out.println("**********zhua****************");
		controler.zhuaControl(true);
		controler.shutdownGPIO();
		
		while(true){
			Thread.sleep(1000);
		}
	}

	
	public void checkResult() {
		// TODO Auto-generated method stub
		System.out.println("**********check catch Result****************");
		this.delayCheck();
		
	}

    private void delayCheck() {  
        Timer timer = new Timer();  
        timer.schedule(new TimerTask() {  
            public void run() {  
            	if(lightInListenr.isCatched()){
            		System.out.println("**********get wawa****************");
            		signalControl.sigChanneltMsg(Constant.RESULT_COMMAND_TRUE);
            	}else{
            		System.out.println("**********get none****************");
            		signalControl.sigChanneltMsg(Constant.RESULT_COMMAND_FALSE);
            	}
            	lightInListenr.setCatched(false);
            }  
        }, Constant.RESULT_WAIT_TIME);
    }  


}
