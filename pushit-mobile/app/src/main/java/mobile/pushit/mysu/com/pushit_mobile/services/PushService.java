package mobile.pushit.mysu.com.pushit_mobile.services;

import mobile.pushit.mysu.com.pushit_mobile.vo.PushContext;

/**
 * Created by EdwinT on 18/10/2016.
 */

public interface PushService {

    void push(PushContext context, String message, ResponseHandler rspHandler);
}
