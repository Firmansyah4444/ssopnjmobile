package com.android.ssopnj;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PageLoginUtama extends AppCompatActivity {

    EditText email, password;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SharedPreferences session=getSharedPreferences("session_login", MODE_PRIVATE);

        if (session.contains("email"))
        {
            finish();
            Intent intent=new Intent(getApplicationContext(), ssoActivity.class);
            startActivity(intent);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_login_utama);

        email=(EditText)findViewById(R.id.email);
        password=(EditText)findViewById(R.id.password);
        login=(Button)findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        cek_form(email);
        cek_form(password);

    }

    void login()
    {
        if(email.getText().length()<1)
        {
            email.setBackgroundResource(R.drawable.login_form_error);
        }
        if(password.getText().length()<1)
        {
            password.setBackgroundResource(R.drawable.login_form_error);
        }
        else
        {
            kirim_data();
        }
    }

    void kirim_data()
    {
        final SharedPreferences.Editor session=getSharedPreferences("session_login", MODE_PRIVATE).edit();
        String url="http:\\192.168.43.86\\sso_pnj\\resources\\views\\auth\\login";
        StringRequest respon=new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            String status=jsonObject.getString("status");
                            if (status.equals("sukses"))
                            {
                                String nama=jsonObject.getString("nama");
                                session.putString("email", email.getText().toString());
                                session.putString("password", password.getText().toString());
                                session.putString("nama", nama);
                                Intent intent=new Intent(getApplicationContext(), ssoActivity.class);
                                startActivity(intent);
                            }
                            else if (status.equals("gagal"))
                            {
                                Toast.makeText(getApplicationContext(), "Email atau password" +
                                        "tidak benar", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            protected Map<String, String>getParams(){
                Map<String, String> kirim_form=new HashMap<String, String>();
                kirim_form.put("email", email.getText().toString());
                kirim_form.put("password", password.getText().toString());
                return kirim_form;
            }
        };
        RequestQueue requestQueue=Volley.newRequestQueue(PageLoginUtama.this);
        requestQueue.add(respon);
    }

    void cek_form(final EditText editText)
    {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(count<1)
                {
                    editText.setBackgroundResource(R.drawable.login_form_error);
                }
                else
                {
                    editText.setBackgroundResource(R.drawable.login_form_background);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

}