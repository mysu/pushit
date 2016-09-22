package co.mysu.pushit.jserver.web.vo;

abstract public class WSocketMessage {
	public static enum MessageType{
		PUSH_ITEM
	}
	
	abstract public MessageType getMessageType();
}
