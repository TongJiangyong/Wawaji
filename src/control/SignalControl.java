package control;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import io.agora.signal.Signal;
import io.agora.signal.Signal.LoginSession.Channel;
import utils.Constant;
import utils.Constant.Action;
import utils.ToolMethod;
import wawaji.WorkThread;

public class SignalControl {

	private Signal sig = null;
	private Signal.LoginSession sigSession = null;
	private Channel channel = null;
	private WorkThread workThread = null;
	public SignalControl(){
        this.sig = new Signal(Constant.APP_ID);
	}
	
	
    public void setWorkThread(WorkThread workThread) {
		this.workThread = workThread;
	}


	public void sigLogin(){
    	System.out.println("******************sigLogin**********************");
        final CountDownLatch loginLatch = new CountDownLatch(1);
        //final CountDownLatch logoutLatch = new CountDownLatch(1);
        sig.login(Constant.SIG_NAME, "_no_need_token", new Signal.LoginCallback() {
            @Override
            public void onLoginSuccess(final Signal.LoginSession session, int uid) {
            	System.out.println("******************sigLogin success**********************");
            	sigSession = session;
                loginLatch.countDown();
            }

            @Override
            public void onLogout(Signal.LoginSession session, int ecode) {
            	System.out.println("******************sigLogout success**********************");
            }
            
        });
        ToolMethod.wait_time(loginLatch, Constant.SIG_WAIT_TIME, "loginLatch");
        if(loginLatch.getCount()!=1){
    		this.joinChannel(Constant.CHANNEL_NAME);    	
        }else{
        	System.out.println("******************joinChannel error**********************");
        	this.leaveChannel();
        }

    }
    
    public void leaveChannel(){
    	if(this.channel!=null){
    		this.channel.channelLeave();
    	}
    	if(this.sigSession!=null){
    		this.sigSession.logout();
    	}
    }
    
    public void joinChannel(String channelName) {
        final CountDownLatch channelJoindLatch = new CountDownLatch(1);
        this.channel = this.sigSession.channelJoin(channelName, new Signal.ChannelCallback() {
            @Override
            public void onChannelJoined(Signal.LoginSession session, Signal.LoginSession.Channel channel) {
            	System.out.println("******************onChannelJoined sucess**********************");
            	channelJoindLatch.countDown();
            }

            @Override
            public void onChannelUserList(Signal.LoginSession session, Signal.LoginSession.Channel channel, List<String> users, List<Integer> uids) {
            
            }


            @Override
            public void onMessageChannelReceive(Signal.LoginSession session, Signal.LoginSession.Channel channel, String account, int uid, String msg) {
                 System.out.println("********"+account + ":" + msg+"********");
                 infoDeal(msg);
            }

            @Override
            public void onChannelUserJoined(Signal.LoginSession session, Signal.LoginSession.Channel channel, String account, int uid) {
            	System.out.println("******************onChannelUserJoined uid:"+uid+"**********************");
            }

            @Override
            public void onChannelUserLeaved(Signal.LoginSession session, Signal.LoginSession.Channel channel, String account, int uid) {
            	System.out.println("******************onChannelUserLeaved uid:"+uid+"**********************");
            }

            @Override
            public void onChannelLeaved(Signal.LoginSession session, Signal.LoginSession.Channel channel, int ecode) {
            	System.out.println("******************onChannelLeaved**********************");
            }

        });
        ToolMethod.wait_time(channelJoindLatch, Constant.SIG_WAIT_TIME, "channelJoindLatch");
    }
    
    
    public void sigChanneltMsg(String command){
/*    	this.sigSession.messageInstantSend(opponentName, command, new Signal.MessageCallback(){
          @Override
          public void onMessageSendSuccess(Signal.LoginSession session) {
              System.out.println("******************onMessageSendSuccess**********************");

          }

          @Override
          public void onMessageSendError(Signal.LoginSession session, int ecode) {
        	  System.out.println("******************onMessageSendError**********************");
          }
      });*/
  	  System.out.println("***************sigChanneltMsg**************"+command+"***********");
        this.channel.messageChannelSend(command);
  }
    
    public void infoDeal(String msg){
        JsonParser parser = new JsonParser();
        JsonElement jElem = parser.parse(msg);
        JsonObject obj = jElem.getAsJsonObject();
        jElem = obj.get("type");
        String type = jElem.getAsString();
        System.out.println("***********TYPE:"+type+"***********");
        if(type.equals("CONTROL")){
            jElem = obj.get("data");
            String data = jElem.getAsString();
            System.out.println("***********data:"+data+"***********");
            if(data.equals("up")){
            	jElem = obj.get("pressed");	
            	boolean pressed = jElem.getAsBoolean();
                System.out.println("***********up pressed:"+pressed+"***********");
            	this.workThread.getControlThread().doAction(Action.UP,pressed);
            }else if(data.equals("down")){
            	jElem = obj.get("pressed");	
            	boolean pressed = jElem.getAsBoolean();
                System.out.println("***********down pressed:"+pressed+"***********");
            	this.workThread.getControlThread().doAction(Action.DOWN,pressed); 	
            }else if(data.equals("left")){
            	jElem = obj.get("pressed");	
            	boolean pressed = jElem.getAsBoolean();
                System.out.println("***********left pressed:"+pressed+"***********");
            	this.workThread.getControlThread().doAction(Action.LEFT,pressed);
            }else if(data.equals("right")){
            	jElem = obj.get("pressed");	
            	boolean pressed = jElem.getAsBoolean();
                System.out.println("***********right pressed:"+pressed+"***********");
            	this.workThread.getControlThread().doAction(Action.RIGHT,pressed);
            }
            
        }else if(type.equals("CATCH")){
            System.out.println("***********CATCH:***********");
        	this.workThread.getControlThread().doAction(Action.ZHUA);
        }else{
            System.out.println("***********other control***********");
        }
    }
}
