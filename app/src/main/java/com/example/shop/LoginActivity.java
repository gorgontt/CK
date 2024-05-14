package com.example.shop;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.shop.Admin.AdminCategoryActivity;
import com.example.shop.Model.Users;
import com.example.shop.Prevalent.Prevalent;
import com.example.shop.User.HomeActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rey.material.widget.CheckBox;

import io.paperdb.Paper;

public class LoginActivity extends AppCompatActivity {

    private Button loginButton;
    private EditText loginPhoneInput, loginPasswordInput;
    private ProgressDialog loadingBar;

    private String parentDbName = "Users";
    private CheckBox rememberMe;
    private TextView AdminLink, notAdminLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        loginButton = findViewById(R.id.login_btn);
        loginPhoneInput = findViewById(R.id.login_phone_input);
        loginPasswordInput = findViewById(R.id.login_password_input);
        loadingBar = new ProgressDialog(this);
        rememberMe = findViewById(R.id.login_check_box);
        AdminLink = findViewById(R.id.admin_link);
        notAdminLink = findViewById(R.id.user_link);

        Paper.init(this);

        loginButton.setOnClickListener(v -> loginUser());

        AdminLink.setOnClickListener(v -> {
            AdminLink.setVisibility(View.INVISIBLE);
            notAdminLink.setVisibility(View.VISIBLE);
            loginButton.setText("Login Admin");
            parentDbName = "Admins";
        });

        notAdminLink.setOnClickListener(v -> {
            AdminLink.setVisibility(View.VISIBLE);
            notAdminLink.setVisibility(View.INVISIBLE);
            loginButton.setText("Login");
            parentDbName = "Users";
        });

    }

    private void loginUser() {
        String phone = loginPhoneInput.getText().toString();
        String password = loginPasswordInput.getText().toString();

        if (TextUtils.isEmpty(phone)) {
            loadingBar.dismiss();
            Toast.makeText(this, "Введите номер телефона", Toast.LENGTH_SHORT).show();
        }

        if (TextUtils.isEmpty(password)) {
            loadingBar.dismiss();
            Toast.makeText(this, "Введите пароль", Toast.LENGTH_SHORT).show();
        }
        else {
            loadingBar.setTitle("Вход в приложение");
            loadingBar.setMessage("Пожалуйста, подождите");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            ValidateUser(phone, password);
        }
    }

    private void ValidateUser(String phone, String password) {

        if (rememberMe.isChecked())
        {
            Paper.book().write(Prevalent.UserPhoneKey, phone);
            Paper.book().write(Prevalent.UserPasswordKey, password);
        }

        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(parentDbName).child(phone).exists()){

                    Users usersData = snapshot.child(parentDbName).child(phone).getValue(Users.class);

                    if(usersData.getPhone().equals(phone))
                    {
                        if(usersData.getPassword().equals(password))
                        {
                            if (parentDbName.equals("Users"))
                            {
                                loadingBar.dismiss();
                                Toast.makeText(LoginActivity.this, "Успешный вход", Toast.LENGTH_LONG).show();

                                Intent homeIntent = new Intent(LoginActivity.this, HomeActivity.class);
                                startActivity(homeIntent);


                            } else if (parentDbName.equals("Admins")) {

                            loadingBar.dismiss();
                            Toast.makeText(LoginActivity.this, "Успешный вход", Toast.LENGTH_LONG).show();

                            Intent homeIntent = new Intent(LoginActivity.this, AdminCategoryActivity.class);
                            startActivity(homeIntent);
                            }
                        }
                        else
                        {
                            loadingBar.dismiss();
                            Toast.makeText(LoginActivity.this, "Пароль не подходит", Toast.LENGTH_LONG).show();
                        }
                    }
                }
                else {
                    loadingBar.dismiss();
                    Toast.makeText(LoginActivity.this, "Номер телефона не существует", Toast.LENGTH_LONG).show();
                    Intent registrIntent = new Intent(LoginActivity.this, RegistrActivity.class);
                    startActivity(registrIntent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}