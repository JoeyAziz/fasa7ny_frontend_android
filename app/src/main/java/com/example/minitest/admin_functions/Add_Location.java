package com.example.minitest.admin_functions;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.minitest.R;
import com.example.minitest.main.AdminHome;
import com.example.minitest.main.Home;
import com.example.minitest.main.network;
import com.example.minitest.user.Login;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class Add_Location extends AppCompatActivity {
    Button add_loc_button;

    EditText country;
    EditText city;
    EditText name;
    EditText budget;
    EditText lon;
    EditText lat;

    String requestBody;
    String result_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__location);

        //FIELDS
        country = findViewById(R.id.country_field);
        city = findViewById(R.id.city_field);
        name = findViewById(R.id.location_name_field);
        budget = findViewById(R.id.budget_field);
        lon = findViewById(R.id.long_field);
        lat = findViewById(R.id.lat_field);

        //VALIDATE HERE

        //ADD_LOCATION BUTTON
        add_loc_button = (Button) findViewById(R.id.add_loc_button);
        add_loc_button.setOnClickListener(new View.OnClickListener() {//ON CLICK
            @Override
            public void onClick(View view) {
                createLocation(country.getText().toString(), city.getText().toString(), name.getText().toString(),
                        Double.parseDouble(budget.getText().toString()), Double.parseDouble(lon.getText().toString()), Double.parseDouble(lat.getText().toString()) );
            }
        });
    }

    private void createLocation(String country, String city, String name, double budget, double lon, double lat){
        RequestQueue queue = Volley.newRequestQueue(this);
        //INPUT URL ROUTE
        String url = network.getBackend_ip() +"admin/location/create";
        try {
            //JSON OBJECT TO BE SENT IN ROUTE
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("country", country);
            jsonBody.put("city",  city);
            jsonBody.put("name",  name);
            jsonBody.put("budget",  budget);
            jsonBody.put("lat",  lat);
            jsonBody.put("lon",  lon);
            requestBody = jsonBody.toString();

            // Request a string response from the provided URL.
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>()
                    {
                        @Override
                        public void onResponse(String response) {
                            Toast.makeText(getApplicationContext(),"Location Created",Toast.LENGTH_SHORT).show();
                        }
                    }, new Response.ErrorListener()
            {
                @Override
                public void onErrorResponse(VolleyError error)
                {
                    Toast.makeText(getApplicationContext(),"Failed Transaction",Toast.LENGTH_SHORT).show();
                }
            })
            {
                @Override
                public String getBodyContentType() {
                    return String.format("application/json; charset=utf-8");
                }
                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return requestBody == null ? null : requestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        return null;
                    }
                }
            };
            // Add the request to the RequestQueue.
            queue.add(stringRequest);
        }catch (JSONException e){}

    }
}
