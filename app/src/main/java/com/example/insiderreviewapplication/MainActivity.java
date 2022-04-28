package com.example.insiderreviewapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity {

    ImageView buttonEmployee;
    ImageView buttonHr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonEmployee = (ImageView) findViewById(R.id.imageButtonEmployee);
        buttonHr = (ImageView) findViewById(R.id.imageButtonHR);

        Glide
                .with(this)
                .load(R.drawable.employee_image)
                .into(buttonEmployee);
        Glide
                .with(this)
                .load(R.drawable.hr_image)
                .into(buttonHr);

        buttonEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,LoginActivity.class).putExtra("type",1));
            }
        });

        buttonHr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,LoginActivity.class).putExtra("type",0));
            }
        });
    }
}