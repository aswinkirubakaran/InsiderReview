package com.example.insiderreviewapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    int type;
    String user;
    String pass;
    ConstraintLayout bg;
    EditText userName;
    EditText password;
    Button logIn;
    HashMap<String,String> empMap = new HashMap<>();
    HashMap<String,String> hrMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Bundle extra  = getIntent().getExtras();
        if(extra != null) {
            type = extra.getInt("type");
        }

        bg = (ConstraintLayout) findViewById(R.id.loginBg);
        userName = (EditText) findViewById(R.id.editTextUser);
        password = (EditText) findViewById(R.id.editTextPassword);
        logIn = (Button) findViewById(R.id.buttonLogin);

        if(type==1){
            bg.setBackgroundResource(R.drawable.emp_bg);
        }else if(type==0){
            bg.setBackgroundResource(R.drawable.hr_bg);
        }else{
            bg.setBackground(null);
        }

        setData();

        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user = userName.getText().toString();
                pass = password.getText().toString();
                Intent intent;
                // 1 = Employee, 0 = HR
                if(type == 1 && empMap.containsKey(user) && empMap.containsValue(pass)){
                    intent = new Intent(LoginActivity.this,EmployeeActivity.class);
                    intent.putExtra("User",pass);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                }else if(type == 0 && hrMap.containsKey(user) && hrMap.containsValue(pass)){
                    startActivity(new Intent(LoginActivity.this,AdminActivity.class));
                    Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(), "Enter a valid credentials", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setData() {
        empMap.clear();
        hrMap.clear();

        empMap.put("Liam","liam.E001");
        empMap.put("Emma","emma.E002");
        empMap.put("Tim","tim.E003");
        empMap.put("Noah","noah.E004");
        empMap.put("Lucas","lucas.E005");
        empMap.put("Scarlet","scarlet.E006");
        empMap.put("Hannah","hannah.E007");
        empMap.put("Luna","luna.E008");
        empMap.put("Travis","travis.E009");
        empMap.put("Kylie","kylie.E010");
        empMap.put("Taylor","taylor.E011");
        empMap.put("Justin","justin.E012");
        empMap.put("Selena","selena.E013");
        empMap.put("Charlie","charlie.E014");
        empMap.put("Drake","drake.E015");
        empMap.put("Abel","abel.E016");
        empMap.put("Eminem","eminem.E017");
        empMap.put("Shawn","shawn.E019");
        empMap.put("Camila","camil.Ea020");
        empMap.put("Ariana","ariana.E021");
        empMap.put("Billie","billie.E022");
        empMap.put("Michael","michael.E020");

        hrMap.put("Admin1","admin1");
        hrMap.put("Admin2","admin2");
    }
}