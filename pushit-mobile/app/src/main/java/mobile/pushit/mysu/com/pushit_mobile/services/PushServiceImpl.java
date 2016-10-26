package mobile.pushit.mysu.com.pushit_mobile.services;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import mobile.pushit.mysu.com.pushit_mobile.vo.PushContext;
import mobile.pushit.mysu.com.pushit_mobile.vo.rest.PushRequest;

/**
 * Created by EdwinT on 18/10/2016.
 */

public class PushServiceImpl implements PushService {

    private RequestQueue reqQueue;

    public PushServiceImpl(Context appContext) {
        reqQueue = Volley.newRequestQueue(appContext);
    }

    @Override
    public void push(PushContext context, final String message, final ResponseHandler rspHandler) {
        JSONObject jsonRequest = null;
        try {
            jsonRequest = new JSONObject(createJson(context, message));
        } catch (JSONException e) {
            e.printStackTrace();
            rspHandler.error(e.getMessage());
            return;
        }
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, context.getUrl(), jsonRequest, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                rspHandler.success(message);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                rspHandler.error(error.getMessage());
            }
        });


        int socketTimeout = 30000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request.setRetryPolicy(policy);

        reqQueue.add(request);
    }

    private String createJson(PushContext context, String message) {
        return "{'pushId': '" + context.getPushId() + "', 'msg': '" + message + "'}";
    }


}
