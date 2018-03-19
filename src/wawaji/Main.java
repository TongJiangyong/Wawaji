package wawaji;

import control.GpioControl;
import control.MyGpioPinListenerDigital;
import control.SignalControl;
import utils.Constant.Action;

public class Main {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		
		WorkThread workThread = new WorkThread();
		new Thread(workThread).start();
	}

}
