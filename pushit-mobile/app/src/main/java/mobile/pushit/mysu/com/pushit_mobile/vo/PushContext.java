package mobile.pushit.mysu.com.pushit_mobile.vo;

import java.lang.reflect.Constructor;

/**
 * Created by EdwinT on 18/10/2016.
 */
public class PushContext {
    private String url;
    private String pushId;

    public PushContext(String pushId, String url) {
        this.pushId = pushId;
        this.url = url;
    }

    public String getUrl() {
        return url;
    }


    public String getPushId() {
        return pushId;
    }
}
