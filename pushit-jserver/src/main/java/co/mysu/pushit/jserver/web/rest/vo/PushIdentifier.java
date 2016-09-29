package co.mysu.pushit.jserver.web.rest.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by emiguel on 28/09/16.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PushIdentifier extends RestResponse {
    private final String pushId;

    public PushIdentifier(String pushId) {
        super(true);
        this.pushId = pushId;
    }
}
