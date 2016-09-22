package co.mysu.pushit.jserver.web.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by emiguel on 19/09/16.
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class PushItem extends WSocketMessage{
    private final String msg;

	@Override
	public MessageType getMessageType() {
		return MessageType.PUSH_ITEM;
	}
}
