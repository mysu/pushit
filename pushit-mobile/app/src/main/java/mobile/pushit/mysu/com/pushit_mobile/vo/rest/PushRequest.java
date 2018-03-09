package mobile.pushit.mysu.com.pushit_mobile.vo.rest;

/**
 * Created by emiguel on 18/10/16.
 */

public class PushRequest {
    private String pushId;
    private String msg;

    public PushRequest(String pushId, String msg) {
        this.pushId = pushId;
        this.msg = msg;
    }

    public String getPushId() {
        return pushId;
    }

    public String getMsg() {
        return msg;
    }
}
