package control;

import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;

import utils.Constant;
import utils.Constant.Action;

public class MyGpioPinListenerDigital implements GpioPinListenerDigital {
    private Action inputType = null;
    private GpioPinDigitalOutput lightOut = null; //¹âÑÛ³ö
    private boolean isCatched = false;
    public MyGpioPinListenerDigital(Action inputType) {
    	this.inputType = inputType;
    	
    }


    @Override
    public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
        System.out.println("input stats:"+event.getState()+" this.inputType:"+this.inputType);
        if(event.getState()==PinState.HIGH){
        	lightOut.low();
        }else if(event.getState()==PinState.LOW){
        	lightOut.high();
        	isCatched = true;
        }
    }


	public boolean isCatched() {
		return isCatched;
	}


	public void setCatched(boolean isCatched) {
		this.isCatched = isCatched;
	}


	public void setOutputGpio(GpioPinDigitalOutput lightout) {
		this.lightOut = lightout;	
	}


}
