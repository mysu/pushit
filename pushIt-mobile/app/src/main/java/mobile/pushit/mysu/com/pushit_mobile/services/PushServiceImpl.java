package mobile.pushit.mysu.com.pushit_mobile.services;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import mobile.pushit.mysu.com.pushit_mobile.vo.PushContext;

/**
 * Created by EdwinT on 18/10/2016.
 */

public class PushServiceImpl implements PushService {

    private RequestQueue reqQueue;

    public PushServiceImpl(Context appContext) {
        reqQueue = Volley.newRequestQueue(appContext);
    }

    @Override
    public void push(PushContext context, String message, final ResponseHandler rspHandler) {
        StringRequest request = new StringRequest(Request.Method.POST, context.getUrl(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                rspHandler.success(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                rspHandler.error(error.getMessage());
            }
        });

        reqQueue.add(request);
    }


}
