package com.example.donationfinal;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

public class RequetCustom  extends StringRequest {

    public RequetCustom(String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(url, listener, errorListener);
    }

}
