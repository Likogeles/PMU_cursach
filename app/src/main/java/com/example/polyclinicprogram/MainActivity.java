package com.example.polyclinicprogram;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {
    //private static final String url = "http://10.0.2.2:5000";
//    private static final String url = "192.168.1.38:5000";
    //private static final String url = "https://jsonplaceholder.typicode.com/photos";

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent patientListActivity = new Intent(this, PatientListActivity.class);
        startActivity(patientListActivity);

//        textView = findViewById(R.id.label);
//        textView.setText("123");
//        getApi();
    }

//    String str;
//
//    private String getApi(){
//        System.out.println("Try to connect...");
//        str = "Was trying";
//
//        RequestQueue mRequestQueue = Volley.newRequestQueue(this);
//
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response)
//                    {
//                        //str = "Success: " + response.toString();
//                        System.out.println(response);
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error)
//            {
//                //str = "Error: " + error.toString();
//            }
//        }
//        );
//
//        mRequestQueue.add(stringRequest);
//
//        textView.setText(str);
//        System.out.println("Success: " + str);
//
//        return str;
//    }
}
