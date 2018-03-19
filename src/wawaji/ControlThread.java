package wawaji;

import java.util.concurrent.CountDownLatch;

import control.GpioControl;
import utils.Constant;
import utils.ToolMethod;
import utils.Constant.Action;

public class ControlThread  implements Runnable {
    private CountDownLatch newLatch = null;
	private GpioControl controler =null;
	public ControlThread(GpioControl controler){
		this.controler = controler;
		this.newLatch = new CountDownLatch(1);
	}
	


	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("**********ControlThread start****************"); 
		synchronized(this){
			try {
				this.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
		}

		System.out.println("**********ControlThread end****************");   
	}

	public void doAction(Action controlAction){
		switch (controlAction) {
		case UP:
			this.controler.upControl(true);
			ToolMethod.wait_time(newLatch, Constant.CONTROL_WAIT_TIME, "CONTROL_UP");
			this.controler.upControl(false);
			break;
		case DOWN:
			this.controler.downControl(true);
			ToolMethod.wait_time(newLatch, Constant.CONTROL_WAIT_TIME, "CONTROL_UP");
			this.controler.downControl(false);
			break;
		case LEFT:
			this.controler.leftControl(true);
			ToolMethod.wait_time(newLatch, Constant.CONTROL_WAIT_TIME, "CONTROL_UP");
			this.controler.leftControl(false);
			break;
		case RIGHT:
			this.controler.rightControl(true);
			ToolMethod.wait_time(newLatch, Constant.CONTROL_WAIT_TIME, "CONTROL_UP");
			this.controler.rightControl(false);
			break;
		case ZHUA:
			this.controler.zhuaControl(true);
			ToolMethod.wait_time(newLatch, Constant.CONTROL_WAIT_TIME, "CONTROL_UP");
			this.controler.zhuaControl(false);
			break;
		case NONE:
			break;
		default:
			break;
		}
		
	}
	
	public void doAction(Action controlAction,boolean controlBoolean){
		switch (controlAction) {
		case UP:
			this.controler.upControl(controlBoolean);
			break;
		case DOWN:
			this.controler.downControl(controlBoolean);
			break;
		case LEFT:
			this.controler.leftControl(controlBoolean);
			break;
		case RIGHT:
			this.controler.rightControl(controlBoolean);
			break;
		case ZHUA:
			this.controler.zhuaControl(controlBoolean);
			break;
		case NONE:
			break;
		default:
			break;
		}
		
	}
	
	
	public void releaseControlThread(){
		if(this.newLatch.getCount()==1){
			this.newLatch.countDown();
		}
		
		synchronized(this){
			try {
				this.notify();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
		}
	}
	
	public CountDownLatch getNewLatch() {
		return newLatch;
	}

	public void setNewLatch(CountDownLatch newLatch) {
		this.newLatch = newLatch;
	}
}
