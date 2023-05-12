package com.example.musicplayer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.musicplayer.domain.User;

public class EditUserActivity extends AppCompatActivity {

    User user;
    EditText edFirstName, edLastName, edEmail, edPhone, edPassword;

    Button btnCancel, btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

        init();
    }

    private void setEvent() {
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Submit");
            }
        });
    }

    private void loadData(){
        edFirstName.setText(user.getFirst_name());
        edLastName.setText(user.getLast_name());
        edEmail.setText(user.getEmail());
        edPhone.setText(user.getPhone());
        edPassword.setText(user.getPassword());
    }

    private void init() {
        edFirstName = findViewById(R.id.edFirstName);
        edLastName = findViewById(R.id.edLastName);
        edEmail = findViewById(R.id.edEmail);
        edPhone = findViewById(R.id.edPhone);
        edPassword = findViewById(R.id.edPassword);
        btnCancel = findViewById(R.id.btnEditUserCancel);
        btnSubmit = findViewById(R.id.btnEditUserSubmit);

        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("data");

        setEvent();
        loadData();
    }
}
