package com.example.karat.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.karat.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class GetData extends AppCompatActivity {
    private RequestQueue mRequestQueue;
    private JsonObjectRequest mObjectRequest;
    static ArrayList names = new ArrayList<String>();
    public static ArrayList code = new ArrayList<String>();
    static ArrayList id = new ArrayList<String>();
    static ArrayList address = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sendAndRequestResponse();
        Intent i = new Intent(getApplicationContext(), UpdateDatabase.class);
        startActivity(i);
    }

    private void sendAndRequestResponse() {
        //RequestQueue initialized
        mRequestQueue = Volley.newRequestQueue(this);
        //String Request initialized
        mObjectRequest = new JsonObjectRequest(Request.Method.GET, GetUrl.getFinalUrl(), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                // Process the JSON
                try{
                    // Loop through the array elements
                    JSONObject result = response.getJSONObject("result");
                    JSONArray records = result.getJSONArray("records");
                    for (int i = 0; i <records.length(); i++)
                    {
                        JSONObject object = records.getJSONObject(i);
                        String postalCode = object.getString("postal_code");
                        if (android.text.TextUtils.isDigitsOnly(postalCode)) {
                            String name = object.getString("licensee_name");
                            String num = object.getString("licence_num");
                            String blk = object.getString("block_house_num");
                            String street = object.getString("street_name");
                            String lvl = object.getString("level_num");
                            String unit = object.getString("unit_num");
                            names.add(name);
                            code.add(postalCode);
                            id.add(num);
                            address.add(blk + ", " + street + ", #" + lvl + " - " + unit + ", S" + postalCode);
                        }
                    }
//                    Toast.makeText(getApplicationContext(), "Number of Stores: " + code.size(), Toast.LENGTH_SHORT).show();
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Restart Application", Toast.LENGTH_SHORT).show();;
            }
        });
        mRequestQueue.add(mObjectRequest);
    }
}
