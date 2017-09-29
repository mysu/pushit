package co.mysu.pushit.jserver.web.vo;

import lombok.*;
import lombok.experimental.Tolerate;

/**
 * Created by emiguel on 19/09/16.
 */
@Data
@Builder
@EqualsAndHashCode(callSuper=true)
public class PushItem extends WSocketMessage{

	private String msg;
	private String pushId;

	@Tolerate
	PushItem(){}

	@Override
	public MessageType getMessageType() {
		return MessageType.PUSH_ITEM;
	}
}
