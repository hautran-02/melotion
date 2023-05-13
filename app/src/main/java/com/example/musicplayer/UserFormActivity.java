package com.example.musicplayer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.musicplayer.domain.User;

public class UserFormActivity extends AppCompatActivity {

    User data, obj;
    Boolean isEditForm;
    EditText edFirstName, edLastName, edEmail, edPhone, edPassword;
    TextView tvTitle;
    Button btnCancel, btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_user);

        init();
    }

    private void setEvent() {
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submit();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void loadData(){
        if(data != null){
            isEditForm = true;
            tvTitle.setText(getResources().getString(R.string.edit_user));
            edFirstName.setText(data.getFirst_name());
            edLastName.setText(data.getLast_name());
            edEmail.setText(data.getEmail());
            edPhone.setText(data.getPhone());
            edPassword.setText(data.getPassword());
            btnSubmit.setText(getResources().getString(R.string.add));
        } else {
            isEditForm = false;
            tvTitle.setText(getResources().getString(R.string.add_user));
            edFirstName.setText("");
            edLastName.setText("");
            edEmail.setText("");
            edPhone.setText("");
            edPassword.setText("");
            btnSubmit.setText(getResources().getString(R.string.add));
        }
    }

    private void init() {
        edFirstName = findViewById(R.id.edFirstName);
        edLastName = findViewById(R.id.edLastname);
        edEmail = findViewById(R.id.edEmail);
        edPhone = findViewById(R.id.edPhone);
        edPassword = findViewById(R.id.edPassword);
        tvTitle = findViewById(R.id.tvTitleUser);
        btnCancel = findViewById(R.id.btnUserCancel);
        btnSubmit = findViewById(R.id.btnUserSubmit);

        Intent intent = getIntent();
        data = (User) intent.getSerializableExtra("data");

        setEvent();
        loadData();
    }

    private void submit(){
        obj.setFirst_name(String.valueOf(edFirstName.getText()));
        obj.setLast_name(String.valueOf(edLastName.getText()));
        obj.setEmail(String.valueOf(edEmail.getText()));
        obj.setPhone(String.valueOf(edPhone.getText()));
        obj.setPassword(String.valueOf(edPassword.getText()));

        if(isEditForm){
            edit();
        } else {
            add();
        }
    }

    private void edit(){
        Toast.makeText(getApplicationContext(), "Edit User", Toast.LENGTH_SHORT).show();
    }

    private void add() {
        Toast.makeText(getApplicationContext(), "Add User", Toast.LENGTH_SHORT).show();
    }
}
