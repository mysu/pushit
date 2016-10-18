package mobile.pushit.mysu.com.pushit_mobile.services;

/**
 * Created by EdwinT on 18/10/2016.
 */
public interface ResponseHandler {
    void success(String response);

    void error(String message);
}
