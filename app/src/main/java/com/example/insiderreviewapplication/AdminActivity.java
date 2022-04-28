package com.example.insiderreviewapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.insiderreviewapplication.DataAccessObject.UserDao;
import com.example.insiderreviewapplication.Model.EmployeeModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdminActivity extends AppCompatActivity {

    ProgressBar pJoin;
    ProgressBar pNotJoin;
    TextView tJoin;
    TextView tNotJoin;
    Button analytics;

    UserDao dao;
    //join means will not leave
    List<EmployeeModel> ltJoin;
    List<EmployeeModel> ltNotJoin;
    int count;
    int join;
    int notJoin;

    public String filter = "Filter";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        pJoin = (ProgressBar) findViewById(R.id.progressBarJoin);
        pNotJoin = (ProgressBar) findViewById(R.id.progressBarNotJoin);
        tJoin = (TextView) findViewById(R.id.textJoinP);
        tNotJoin = (TextView) findViewById(R.id.textNotJoinP);
        analytics = (Button) findViewById(R.id.buttonAnalytics);

        dao = new UserDao();
        dao.get().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                count = 0;
                notJoin = 0;
                join = 0;
                ltJoin = new ArrayList<>();
                ltNotJoin = new ArrayList<>();
                for (DataSnapshot data : snapshot.getChildren()){
                    EmployeeModel emp = data.getValue(EmployeeModel.class);
                    if(emp!=null){
                        boolean val = emp.getAttrition();
                        count +=1;
                        if(!val){
                            //join means will not leave
                            ltJoin.add(emp);
                        }else{
                            ltNotJoin.add(emp);
                        }
                    }
                }
                if(ltJoin.size() != 0){
                    join = (ltJoin.size() * 100) / count;
                }
                if(ltNotJoin.size() != 0){
                    notJoin = (ltNotJoin.size() * 100) / count;
                }

                tJoin.setText(join+"%");
                pJoin.setProgress(join);
                tNotJoin.setText(notJoin+"%");
                pNotJoin.setProgress(notJoin);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), ""+error, Toast.LENGTH_SHORT).show();
            }
        });

        pJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ltJoin.size() != 0){
                    startActivity(new Intent(AdminActivity.this, ListAnalyticsActivity.class).putExtra(filter, 1));
                }else{
                    Toast.makeText(getApplicationContext(), "No Data Available", Toast.LENGTH_SHORT).show();
                }
            }
        });

        pNotJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ltNotJoin.size() != 0){
                    startActivity(new Intent(AdminActivity.this, ListAnalyticsActivity.class).putExtra(filter, 2));
                }else{
                    Toast.makeText(getApplicationContext(), "No Data Available", Toast.LENGTH_SHORT).show();
                }
            }
        });

        analytics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminActivity.this,ListAnalyticsActivity.class).putExtra(filter,0));
            }
        });
    }
}