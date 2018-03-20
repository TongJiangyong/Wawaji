package control;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;

import utils.Constant.Action;

//ʹ��һ���첽���߳�������control��ÿһ�ι���......

public class GpioControl {

    private static final GpioController GPIOCONTROLLER = GpioFactory.getInstance();
    
    public static final GpioPinDigitalOutput outputUp=GPIOCONTROLLER.provisionDigitalOutputPin(RaspiPin.GPIO_02, PinState.LOW);   //��
    public static final GpioPinDigitalOutput outputDown=GPIOCONTROLLER.provisionDigitalOutputPin(RaspiPin.GPIO_03, PinState.LOW);	//��
    public static final GpioPinDigitalOutput outputLeft=GPIOCONTROLLER.provisionDigitalOutputPin(RaspiPin.GPIO_04, PinState.LOW);	//��
    public static final GpioPinDigitalOutput outputRight=GPIOCONTROLLER.provisionDigitalOutputPin(RaspiPin.GPIO_05, PinState.LOW); //��
    public static final GpioPinDigitalOutput outputZhua=GPIOCONTROLLER.provisionDigitalOutputPin(RaspiPin.GPIO_06, PinState.LOW); //צ
    
    
    public static final GpioPinDigitalInput LightIn = GPIOCONTROLLER.provisionDigitalInputPin(RaspiPin.GPIO_00); //������
    public static final GpioPinDigitalOutput LightOut = GPIOCONTROLLER.provisionDigitalOutputPin(RaspiPin.GPIO_07, PinState.HIGH); //���۳�
    

    private Action currentAction = Action.NONE;
    
    public GpioControl(MyGpioPinListenerDigital lightInLister){
    	this.initGPIO(lightInLister);
    }

	private void initGPIO(MyGpioPinListenerDigital lightInLister){
    	//output pin
    	outputUp.setShutdownOptions(true, PinState.LOW, PinPullResistance.OFF);
    	outputDown.setShutdownOptions(true, PinState.LOW, PinPullResistance.OFF);
    	outputLeft.setShutdownOptions(true, PinState.LOW, PinPullResistance.OFF);
    	outputRight.setShutdownOptions(true, PinState.LOW, PinPullResistance.OFF);
    	outputZhua.setShutdownOptions(true, PinState.LOW, PinPullResistance.OFF);
    	//input pin
/*    	inputLightOut.addListener(new MyGpioPinListenerDigital(Action.LIGHTOUT));
    	inputLightIn.addListener(new MyGpioPinListenerDigital(Action.LIGHTIN));*/
    	LightOut.setShutdownOptions(true, PinState.HIGH, PinPullResistance.OFF);
    	lightInLister.setOutputGpio(LightOut);
    	LightIn.addListener(lightInLister);
    	LightIn.setShutdownOptions(true);

    	//inputLightIn.addListener(lightIn);
    	this.resetAction();
    }
    
    public void shutdownGPIO(){
    	GPIOCONTROLLER.shutdown();
    }
    
    public void resetAction(){
    	this.currentAction = Action.NONE;
    }
    
    private void setAllOutputGpioLow(){
		outputUp.low();	
		outputDown.low();
		outputLeft.low();
		outputRight.low();
		try {
			//needed
			Thread.sleep(1);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void upControl(boolean isAction){
    	if(this.getCurrentAction()!=Action.UP){
    		//setAllOutputGpioLow();
    		this.setCurrentAction(Action.UP);
    	}
    	if(isAction){
    		this.setCurrentAction(Action.UP);
    		System.out.println("**********UP****************");
    		outputUp.high();
    	}else{
    		this.resetAction();
    		outputUp.low();	
    	}
    }
    
    public void downControl(boolean isAction){
    	//������ǵ�ǰ�Ķ�������������е�GPIO״̬��������״̬
    	if(this.getCurrentAction()!=Action.DOWN){
    		//setAllOutputGpioLow();
    		this.setCurrentAction(Action.DOWN);
    	}
    	if(isAction){
    		this.setCurrentAction(Action.DOWN);
    		System.out.println("**********DOWN****************");
    		outputDown.high();
    	}else{
    		this.resetAction();
    		outputDown.low();	
    	}	
    }
    
    public void leftControl(boolean isAction){
    	if(this.getCurrentAction()!=Action.LEFT){
    		//setAllOutputGpioLow();
    		this.setCurrentAction(Action.LEFT);
    	}
    	if(isAction){
    		this.setCurrentAction(Action.LEFT);
    		System.out.println("**********LEFT****************");
    		outputLeft.high();
    	}else{
    		this.resetAction();
    		outputLeft.low();	
    	}
    }
    
    public void rightControl(boolean isAction){
    	if(this.getCurrentAction()!=Action.RIGHT){
    		//setAllOutputGpioLow();
    		this.setCurrentAction(Action.RIGHT);
    	}
    	if(isAction){
    		System.out.println("**********RIGHT****************");
    		this.setCurrentAction(Action.RIGHT);
    		outputRight.high();
    	}else{
    		this.resetAction();
    		outputRight.low();	
    	}
    }
    
    public void zhuaControl(boolean isAction){
    	if(this.getCurrentAction()!=Action.ZHUA){
    		//setAllOutputGpioLow();
    		this.setCurrentAction(Action.ZHUA);
    	}
    	if(isAction){
    		System.out.println("**********ZHUA****************");
    		this.setCurrentAction(Action.ZHUA);
    		outputZhua.high();
    	}else{
    		this.resetAction();
    		outputZhua.low();	
    	}
    }
    
    public Action getCurrentAction() {
		return currentAction;
	}

	public void setCurrentAction(Action currentAction) {
		this.currentAction = currentAction;
	}
    
}


