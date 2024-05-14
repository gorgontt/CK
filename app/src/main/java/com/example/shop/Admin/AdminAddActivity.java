package com.example.shop.Admin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.shop.R;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Objects;

public class AdminAddActivity extends AppCompatActivity {

/*    private String categoryName, Description, Price, Pname, saveCurrentDate, saveCurrentTime, productRandomKey;
    private String downloadImageUrl;
    private ImageView productImage;
    private EditText productName, productDescription, productPrice;
    private Button addNewProductButton;
    private static final int GALLERYPICK = 1;
    private Uri ImageUri;
    private StorageReference ProductImageRef;
    private DatabaseReference ProductsRef;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add);

        init();

        productImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenGallery();
            }
        });

        addNewProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ValidateProductData();
            }
        });
    }

    private void ValidateProductData() {
        Description = productDescription.getText().toString();
        Price = productPrice.getText().toString();
        Pname = productName.getText().toString();

        if(ImageUri == null){
            Toast.makeText(this, "Добавьте изображение товара.", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(Description)){
            Toast.makeText(this, "Добавьте описание товара.", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(Price)){
            Toast.makeText(this, "Добавьте стоимость товара.", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(Pname)){
            Toast.makeText(this, "Добавьте название товара.", Toast.LENGTH_SHORT).show();
        }
        else {
            StoreProductInformation();
        }
    }

    private void StoreProductInformation() {

        loadingBar.setTitle("Загрузка данных");
        loadingBar.setMessage("Пожалуйста, подождите...");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        Calendar calendar = Calendar.getInstance();

        DateFormat currentDate = DateFormat.getDateInstance();
        saveCurrentDate = currentDate.format(calendar.getTime());

        DateFormat currentTime = DateFormat.getTimeInstance();
        saveCurrentTime = currentTime.format(calendar.getTime());

        productRandomKey = saveCurrentDate + saveCurrentTime;

        final StorageReference filePath = ProductImageRef.child(ImageUri.getLastPathSegment() + productRandomKey + ".jpg");

        final UploadTask uploadTask = filePath.putFile(ImageUri);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String message = e.toString();
                Toast.makeText(AdminAddActivity.this, "Ошибка: " + message, Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(AdminAddActivity.this, "Изображение успешно загружено.", Toast.LENGTH_SHORT).show();

                uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if(!task.isSuccessful()){
                            throw task.getException();
                        }
                        downloadImageUrl = filePath.getDownloadUrl().toString();
                        return filePath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if(task.isSuccessful()){
                            downloadImageUrl = task.getResult().toString();

                            Toast.makeText(AdminAddActivity.this, "Фото сохранено", Toast.LENGTH_SHORT).show();

                            SaveProductInfoToDatabase();
                        }
                    }
                });
            }
        });
    }

    private void SaveProductInfoToDatabase() {
        HashMap<String, Object> productMap = new HashMap<>();

        productMap.put("pid", productRandomKey);
        productMap.put("date", saveCurrentDate);
        productMap.put("time", saveCurrentTime);
        productMap.put("description", Description);
        productMap.put("image", downloadImageUrl);
        productMap.put("category", categoryName);
        productMap.put("price", Price);
        productMap.put("pname", Pname);

        ProductsRef.child(productRandomKey).updateChildren(productMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){

                            loadingBar.dismiss();
                            Toast.makeText(AdminAddActivity.this, "Товар добавлен", Toast.LENGTH_SHORT).show();

                            Intent loginIntent = new Intent(AdminAddActivity.this, AdminCategoryActivity.class);
                            startActivity(loginIntent);
                        }
                        else {
                            String message = task.getException().toString();
                            Toast.makeText(AdminAddActivity.this, "Ошибка: "+ message, Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();

                        }
                    }
                });
    }

    private void OpenGallery() {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent,GALLERYPICK);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GALLERYPICK && resultCode == RESULT_OK && data != null){
            ImageUri = data.getData();
            productImage.setImageURI(ImageUri);
        }
    }

    private void init() {
        categoryName = getIntent().getExtras().get("category").toString();
        productImage = findViewById(R.id.select_image);
        productName = findViewById(R.id.add_et_name);
        productDescription = findViewById(R.id.add_et_description);
        productPrice = findViewById(R.id.add_et_price);
        addNewProductButton = findViewById(R.id.add_btn);
        ProductImageRef = FirebaseStorage.getInstance().getReference().child("Product Images");
        ProductsRef = FirebaseDatabase.getInstance().getReference().child("Products");
        loadingBar = new ProgressDialog(this);

    }

 */
    private String categoryName, Description, Pname, Price, saveCurrentDate, saveCurrentTime, productRandomKey;

    private String downloadImageUrl;
    private ImageView productImage;
    private EditText productName, productDescription, productPrice;
    private Button addNewProductButton;
    private static final int GALLERYPICK = 1;
    private Uri ImageUri;
    private StorageReference ProductImageRef;
    private DatabaseReference ProductsRef;
    private ProgressDialog loadingBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_add);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        init();
        
        productImage.setOnClickListener(v -> OpenGallery());

        addNewProductButton.setOnClickListener(v -> ValidateProductData());

    }




    private void ValidateProductData() {

        Description = productDescription.getText().toString();
        Pname = productName.getText().toString();
        Price = productPrice.getText().toString();

        if (ImageUri == null) {
            Toast.makeText(this, "Добавьте фото", Toast.LENGTH_SHORT).show();

        }else if (TextUtils.isEmpty(Description)) {
            Toast.makeText(this, "Добавьте описание!", Toast.LENGTH_SHORT).show();

        }else if (TextUtils.isEmpty(Price)) {
            Toast.makeText(this, "Добавьте цену!", Toast.LENGTH_SHORT).show();

        }else if (TextUtils.isEmpty(Pname)) {
            Toast.makeText(this, "Добавьте название!", Toast.LENGTH_SHORT).show();

        }else {
            StoreProductInformation();
        }
    }




    private void StoreProductInformation() {

        loadingBar.setTitle("Загрузка");
        loadingBar.setMessage("Пожалуйста, подождите");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();


        Calendar calendar = Calendar.getInstance();

        DateFormat currentDate = DateFormat.getDateInstance();
        saveCurrentDate = currentDate.format(calendar.getTime());

        DateFormat currentTime = DateFormat.getTimeInstance();
        saveCurrentTime = currentTime.format(calendar.getTime());

        productRandomKey = saveCurrentDate + saveCurrentTime;

        final StorageReference filePath = ProductImageRef.child(ImageUri.getLastPathSegment() + productRandomKey + ".jpg");

        final UploadTask uploadTask = filePath.putFile(ImageUri);


        uploadTask.addOnFailureListener(e -> {

            loadingBar.dismiss();
            String message = e.toString();
            Toast.makeText(AdminAddActivity.this, "Ошибка " + message, Toast.LENGTH_SHORT).show();

        }).addOnSuccessListener(taskSnapshot -> {
            Toast.makeText(AdminAddActivity.this, "Изображение загружено", Toast.LENGTH_SHORT).show();

            Task<Uri> uriTask = uploadTask.continueWithTask(task -> {

                if (!task.isSuccessful()) {
                    throw Objects.requireNonNull(task.getException());
                }
                downloadImageUrl = filePath.getDownloadUrl().toString();
                //downloadImageUrl =taskSnapshot.getMetadata().getReference().getDownloadUrl().toString();
                return filePath.getDownloadUrl();

            }).addOnCompleteListener(task -> {

                if (task.isSuccessful()) {
                    Toast.makeText(AdminAddActivity.this, "Фото сохранено", Toast.LENGTH_SHORT).show();

                    SaveProductInfoToDatabase();
                }
            });
        });
    }



    private void SaveProductInfoToDatabase() {

        HashMap<String, Object> productMap = new HashMap<>();

        productMap.put("pid", productRandomKey);
        productMap.put("date", saveCurrentDate);
        productMap.put("time", saveCurrentTime);
        productMap.put("description", Description);
        productMap.put("image", downloadImageUrl);
        productMap.put("category", categoryName);
        productMap.put("price", Price);
        productMap.put("productName", Pname);

        ProductsRef.child(productRandomKey).updateChildren(productMap).addOnCompleteListener(task -> {
            if (task.isSuccessful()){

                loadingBar.dismiss();

                Intent homeIntent = new Intent(AdminAddActivity.this, AdminCategoryActivity.class);
                startActivity(homeIntent);

                Toast.makeText(AdminAddActivity.this, "Товар добавлен", Toast.LENGTH_LONG).show();
            }
            else {
                loadingBar.dismiss();

                String message = Objects.requireNonNull(task.getException()).toString();
                Toast.makeText(AdminAddActivity.this, "Ошибка: " + message, Toast.LENGTH_SHORT).show();

            }
        });

    }


    private void OpenGallery() {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, GALLERYPICK);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERYPICK && resultCode == RESULT_OK && data != null){
            ImageUri = data.getData();
            productImage.setImageURI(ImageUri);
        }
    }

    private void init() {
        categoryName = Objects.requireNonNull(Objects.requireNonNull(getIntent().getExtras()).get("Category")).toString();

        productImage = findViewById(R.id.select_image);
        productName = findViewById(R.id.add_et_name);
        productDescription = findViewById(R.id.add_et_description);
        productPrice = findViewById(R.id.add_et_price);
        addNewProductButton = findViewById(R.id.add_btn);

        ProductImageRef = FirebaseStorage.getInstance().getReference().child("Product Images");

        ProductsRef = FirebaseDatabase.getInstance().getReference().child("Products");


        loadingBar = new ProgressDialog(this);
    }

}