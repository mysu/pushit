package co.mysu.pushit.jserver.web.vo;

import lombok.Data;

/**
 * Created by emiguel on 28/09/16.
 */
@Data
public class Connection extends WSocketMessage {
    private final boolean success;
    @Override
    public MessageType getMessageType() {
        return MessageType.CONNECTION;
    }
}
