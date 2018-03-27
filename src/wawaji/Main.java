package wawaji;

import control.GpioControl;
import control.MyGpioPinListenerDigital;
import control.SignalControl;
import utils.Constant.Action;
import utils.ToolMethod;

public class Main {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		ToolMethod.deleteLogFile();
		WorkThread workThread = new WorkThread();
		new Thread(workThread).start();
	}

}
