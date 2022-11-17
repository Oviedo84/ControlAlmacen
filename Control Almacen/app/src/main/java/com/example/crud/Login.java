package com.example.crud;

import static androidx.test.InstrumentationRegistry.getContext;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
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

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {
    private Button button;
    private EditText Username;
    private int privilege = 0;
    private ip object = new ip();
    private String PCip = object.getLocalip();
    private String users = PCip + "/auth";
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        this.init();
    }

    private void init(){
        requestQueue = Volley.newRequestQueue(this);
        button = findViewById(R.id.login_button);
        Username = findViewById(R.id.Username);
        ProgressDialog progressDialog = new ProgressDialog(this);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = Username.getText().toString();
                StringRequest stringRequest = new StringRequest(
                        Request.Method.POST,
                        users,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if(response.equals("1")) {
                                    privilege = Integer.parseInt(response);
                                    mainPage(privilege);
                                }
                                else {
                                    Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                ) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("username", username);
                        return params;
                    }
                };
                requestQueue.add(stringRequest);
            }
        });
    }

    private void mainPage(int privileges){
        if(privileges == 1){
            Intent intent = new Intent(this, Main.class);
            startActivity(intent);
        }
    }
}