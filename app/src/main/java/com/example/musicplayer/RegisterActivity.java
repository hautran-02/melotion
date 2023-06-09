package com.example.musicplayer;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.musicplayer.api.UserApi;
import com.example.musicplayer.domain.User;
import com.example.musicplayer.domain.UserMessage;
import com.example.musicplayer.retrofit.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    Button btnRegister;

    EditText edFirstName, edLastName, edPhone, edEmail, edPassword, edConfirmPassword;

    UserApi userApi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        init();
        if(edFirstName.getText().toString().isEmpty() || edLastName.getText().toString().isEmpty() ||
                edEmail.getText().toString().isEmpty() ||edPassword.getText().toString().isEmpty() ||
                edPhone.getText().toString().isEmpty() || edConfirmPassword.getText().toString().isEmpty())
        {
            Toast.makeText(RegisterActivity.this, "Nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
        }else {
            setEvent();
        }
    }

    private void setEvent() {
        btnRegister.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String firstName = edFirstName.getText().toString().trim();
                String lastName = edLastName.getText().toString().trim();
                String phone = edPhone.getText().toString().trim();
                String email = edEmail.getText().toString().trim();
                String password = edPassword.getText().toString().trim();
                String confirmPassword = edConfirmPassword.getText().toString().trim();

                User user = new User(phone, firstName, lastName, email, password);

                System.out.println(password);
                System.out.println(confirmPassword);

                if(!password.equals(confirmPassword)){
                    Toast.makeText(RegisterActivity.this,"Passwords do not match", Toast.LENGTH_SHORT).show();
                }
                else {
                    userApi= RetrofitClient.getInstance().getRetrofit().create(UserApi.class);
                    userApi.register(user).enqueue(new Callback<UserMessage>() {
                        @Override
                        public void onResponse(Call<UserMessage> call, Response<UserMessage> response) {
                            if(response.body().getUser() != null) {
                                System.out.println(response.body().getMessage());
                                Toast.makeText(RegisterActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                            else {
//                                Toast.makeText(RegisterActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                System.out.println(response.body().getMessage());
                                Toast.makeText(RegisterActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onFailure(Call<UserMessage> call, Throwable t) {
                            System.out.println(t);
                        }
                    });
                }
            }
        });
    }

    private void init()
    {
        btnRegister = findViewById(R.id.btnRegister);
        edFirstName = findViewById(R.id.edFirstName);
        edLastName = findViewById(R.id.edLastname);
        edPhone = findViewById(R.id.edPhone);
        edEmail = findViewById(R.id.edEmail);
        edPassword = findViewById(R.id.edPassword);
        edConfirmPassword = findViewById(R.id.edConfirmPassword);
    }
}
