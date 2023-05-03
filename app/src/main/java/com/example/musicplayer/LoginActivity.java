package com.example.musicplayer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    TextView tvToRegister;
    Button btnLogin;
    EditText edPhone, edPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();
        setEvent();
    }

    private void setEvent() {
        tvToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = edPhone.getText().toString().trim();
                String password = edPassword.getText().toString().trim();

                System.out.println(phone);
                System.out.println(password);

                if(phone.equals("1") && password.equals("1")){
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    private void init() {
        tvToRegister = findViewById(R.id.tvToRegister);
        btnLogin = findViewById(R.id.btnLogin);
        edPhone = findViewById(R.id.edLoginPhone);
        edPassword = findViewById(R.id.edLoginPw);
    }
}
