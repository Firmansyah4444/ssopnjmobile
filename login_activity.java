package com.android.ssopnj;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

import static com.android.ssopnj.R.layout.login_layout;

public class login_activity extends AppCompatActivity {
    private Bundle savedInstanceState;
    private Button signonbutton;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(login_layout);
        signonbutton = findViewById(R.id.signonbutton);
        
        signonbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(login_activity.this, ssoActivity.class);
                startActivity(intent);
                finish();
                                            }
                                        }

        );
    }
}
