package com.example.shop;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class RegistrActivity extends AppCompatActivity {

    private EditText registerName, registerPhoneInput, registerPasswordInput;
    private ProgressDialog loadingBar;
    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registr);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        registerButton = findViewById(R.id.registr_btn);
        registerName = findViewById(R.id.registr_userName_input);
        registerPhoneInput = findViewById(R.id.registr_phone_input);
        registerPasswordInput = findViewById(R.id.registr_password_input);
        
        registerButton.setOnClickListener(v -> CreateAccount());

        loadingBar = new ProgressDialog(this);
    }

    private void CreateAccount() {
        String username = registerName.getText().toString();
        String phone = registerPhoneInput.getText().toString();
        String password = registerPasswordInput.getText().toString();

        if (TextUtils.isEmpty(username)) {
            Toast.makeText(this, "Enter name", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "Enter phone", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Enter password", Toast.LENGTH_SHORT).show();
            return;
        }

        loadingBar.setTitle("Create Account");
        loadingBar.setMessage("Please wait");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        ValidatePhone(username, phone, password);
    }

    private void ValidatePhone(String username, String phone, String password) {

        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!(snapshot.child("Users").child(phone).exists())){

                    HashMap<String, Object> userDataMap = new HashMap<>();
                    userDataMap.put("phone", phone);
                    userDataMap.put("name", username);
                    userDataMap.put("password", password);

                    RootRef.child("Users").child(phone).updateChildren(userDataMap)
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()){

                                    loadingBar.dismiss();
                                    Toast.makeText(RegistrActivity.this, "Успешная регистрация", Toast.LENGTH_SHORT).show();
                                    Intent logintIntent = new Intent(RegistrActivity.this, LoginActivity.class);
                                    startActivity(logintIntent);
                                }
                                else{
                                    loadingBar.dismiss();
                                    Toast.makeText(RegistrActivity.this, "Не удалось зарегестрироваться", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
                else {
                    loadingBar.dismiss();
                    Toast.makeText(RegistrActivity.this, "Phone number already exists", Toast.LENGTH_LONG).show();
                    Intent logintIntent = new Intent(RegistrActivity.this, LoginActivity.class);
                    startActivity(logintIntent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}