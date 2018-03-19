package control;

import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;

import utils.Constant.Action;

public class MyGpioPinListenerDigital implements GpioPinListenerDigital {
    private Action inputType = null;
    
    public MyGpioPinListenerDigital(Action inputType) {
    	this.inputType = inputType;
    	
    }


    @Override
    public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
        System.out.println("input stats:"+event.getState()+" this.inputType:"+this.inputType);
    }
}
