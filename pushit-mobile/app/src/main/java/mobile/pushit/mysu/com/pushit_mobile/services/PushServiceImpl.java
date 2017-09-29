package mobile.pushit.mysu.com.pushit_mobile.services;

import android.content.Context;

import com.android.volley.AuthFailureError;
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

import java.util.HashMap;
import java.util.Map;

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
    public void push(final PushContext context, final String message, final ResponseHandler rspHandler) {
        JSONObject jsonRequest = null;
        try {
            jsonRequest = new JSONObject();
            jsonRequest.put("pushId", context.getPushId())
                    .put("msg", message);
            //jsonRequest = new JSONObject(createJson(context, message));
        } catch (JSONException e) {
            e.printStackTrace();
            rspHandler.error(e.getMessage());
            return;
        }

        StringRequest  request = new StringRequest(Request.Method.POST, context.getUrl(), new Response.Listener<String>(){
        //JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, context.getUrl(), jsonRequest, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(String response) {
                rspHandler.success(response.toString());
            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                rspHandler.error(error.getMessage());
            }
        }){
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> result = new HashMap<>();
                result.put("pushId", context.getPushId());
                result.put("msg", message);
                return result;
            }
        };


        int socketTimeout = 30000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request.setRetryPolicy(policy);

        reqQueue.add(request);
    }

    private String createJson(PushContext context, String message) {
        return "{pushId: '" + context.getPushId() + "', msg: '" + message + "'}";
    }


}
