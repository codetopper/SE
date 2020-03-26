package com.example.karat.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class GetUrl extends AppCompatActivity {
    private RequestQueue tRequestQueue;
    private JsonObjectRequest tObjectRequest;
    private static String finalUrl;
    private String url = "https://data.gov.sg/api/action/datastore_search?resource_id=3561a136-4ee4-4029-a5cd-ddf591cce643";

    private static void setFinalUrl(String final_url) {
        finalUrl = final_url;
    }
    public static String getFinalUrl() {
        return finalUrl;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findTotal();
        Intent i = new Intent(getApplicationContext(), GetData.class);
        startActivity(i);
    }

    public void findTotal() {
        //RequestQueue initialized
        tRequestQueue = Volley.newRequestQueue(this);
        //String Request initialized
        tObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                // Process the JSON
                try{
                    JSONObject result = response.getJSONObject("result");
                    String total = result.getString("total");
                    setFinalUrl(url + "&limit=" + total);
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("url gg","Error :" + error.toString());
            }
        });
        tRequestQueue.add(tObjectRequest);
    }
}

