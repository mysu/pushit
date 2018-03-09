package co.mysu.pushit.jserver.web.vo;

abstract public class WSocketMessage {
	public static enum MessageType{
		PUSH_ITEM,
		CONNECTION;
	}
	
	abstract public MessageType getMessageType();
}
