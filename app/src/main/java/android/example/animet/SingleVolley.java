package android.example.animet;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class SingleVolley {
    private static  SingleVolley ourInstance;
    private RequestQueue requestQueue;
    private static Context context;

    private SingleVolley(Context cont) {
        context = cont;
        requestQueue = getRequestQueue();

    }
    public static synchronized SingleVolley getInstance(Context context) {
        if (ourInstance == null) {
            ourInstance = new SingleVolley(context);
        }
        return ourInstance;
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }


}
