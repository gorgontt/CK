package com.example.shop.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.shop.R;

public class AdminCategoryActivity extends AppCompatActivity {

    private ImageView img_1, img_2, img_3, img_4;
    private ImageView img_5, img_6, img_7, img_8;
    private ImageView img_9, img_10, img_11, img_12;
    private ImageView img_13, img_14, img_15, img_16;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_category);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        init();

        img_1.setOnClickListener(v -> {
            Intent intent = new Intent(AdminCategoryActivity.this, AdminAddActivity.class);
            intent.putExtra("Category", "img_1");
            startActivity(intent);
        });

        img_2.setOnClickListener(v -> {
            Intent intent = new Intent(AdminCategoryActivity.this, AdminAddActivity.class);
            intent.putExtra("Category", "img_2");
            startActivity(intent);
        });

        img_3.setOnClickListener(v -> {
            Intent intent = new Intent(AdminCategoryActivity.this, AdminAddActivity.class);
            intent.putExtra("Category", "img_3");
            startActivity(intent);
        });

        img_4.setOnClickListener(v -> {
            Intent intent = new Intent(AdminCategoryActivity.this, AdminAddActivity.class);
            intent.putExtra("Category", "img_4");
            startActivity(intent);
        });

        img_5.setOnClickListener(v -> {
            Intent intent = new Intent(AdminCategoryActivity.this, AdminAddActivity.class);
            intent.putExtra("Category", "img_5");
            startActivity(intent);
        });

        img_6.setOnClickListener(v -> {
            Intent intent = new Intent(AdminCategoryActivity.this, AdminAddActivity.class);
            intent.putExtra("Category", "img_6");
            startActivity(intent);
        });
        img_7.setOnClickListener(v -> {
            Intent intent = new Intent(AdminCategoryActivity.this, AdminAddActivity.class);
            intent.putExtra("Category", "img_7");
            startActivity(intent);
        });

        img_8.setOnClickListener(v -> {
            Intent intent = new Intent(AdminCategoryActivity.this, AdminAddActivity.class);
            intent.putExtra("Category", "img_8");
            startActivity(intent);
        });

        img_9.setOnClickListener(v -> {
            Intent intent = new Intent(AdminCategoryActivity.this, AdminAddActivity.class);
            intent.putExtra("Category", "img_9");
            startActivity(intent);
        });

        img_10.setOnClickListener(v -> {
            Intent intent = new Intent(AdminCategoryActivity.this, AdminAddActivity.class);
            intent.putExtra("Category", "img_10");
            startActivity(intent);
        });
        img_11.setOnClickListener(v -> {
            Intent intent = new Intent(AdminCategoryActivity.this, AdminAddActivity.class);
            intent.putExtra("Category", "img_11");
            startActivity(intent);
        });

        img_12.setOnClickListener(v -> {
            Intent intent = new Intent(AdminCategoryActivity.this, AdminAddActivity.class);
            intent.putExtra("Category", "img_12");
            startActivity(intent);
        });

        img_13.setOnClickListener(v -> {
            Intent intent = new Intent(AdminCategoryActivity.this, AdminAddActivity.class);
            intent.putExtra("Category", "img_13");
            startActivity(intent);
        });

        img_14.setOnClickListener(v -> {
            Intent intent = new Intent(AdminCategoryActivity.this, AdminAddActivity.class);
            intent.putExtra("Category", "img_14");
            startActivity(intent);
        });
        img_15.setOnClickListener(v -> {
            Intent intent = new Intent(AdminCategoryActivity.this, AdminAddActivity.class);
            intent.putExtra("Category", "img_15");
            startActivity(intent);
        });

        img_16.setOnClickListener(v -> {
            Intent intent = new Intent(AdminCategoryActivity.this, AdminAddActivity.class);
            intent.putExtra("Category", "img_16");
            startActivity(intent);
        });

    }

    private void init() {
        img_1 = findViewById(R.id.img_1);
        img_2 = findViewById(R.id.img_2);
        img_3 = findViewById(R.id.img_3);
        img_4 = findViewById(R.id.img_4);

        img_5 = findViewById(R.id.img_1_1);
        img_6 = findViewById(R.id.img_2_2);
        img_7 = findViewById(R.id.img_3_3);
        img_8 = findViewById(R.id.img_4_4);

        img_9 = findViewById(R.id.img_1_11);
        img_10 = findViewById(R.id.img_2_22);
        img_11 = findViewById(R.id.img_3_33);
        img_12 = findViewById(R.id.img_4_44);

        img_13 = findViewById(R.id.img_1_111);
        img_14 = findViewById(R.id.img_2_222);
        img_15 = findViewById(R.id.img_3_333);
        img_16 = findViewById(R.id.img_4_444);
    }
}