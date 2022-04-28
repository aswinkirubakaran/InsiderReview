package com.example.insiderreviewapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DetailsActivity extends AppCompatActivity {

    TextView detailName;
    TextView detailAge;
    TextView detailEId;
    TextView detailStatus;
    TextView detailProb;
    TextView detailGender;
    TextView detailMarital;
    TextView detailJobRole;
    TextView detailDept;
    TextView detailExp;
    TextView detailPerformance;

    String nm;
    String id;
    String stat;
    String pro;

    List<String> lt = new ArrayList<>();
    FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = db.getReference("EmployeeDetails");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Bundle extra  = getIntent().getExtras();
        if(extra != null) {
            nm = (String) extra.get("EmpName");
            id = (String) extra.get("id");
            stat = (String) extra.get("stat");
            pro = (String)extra.get("pro");
        }

        detailName = (TextView) findViewById(R.id.detailName);
        detailAge = (TextView) findViewById(R.id.detailAge);
        detailEId = (TextView) findViewById(R.id.textEId);
        detailStatus = (TextView) findViewById(R.id.detailStatus);
        detailProb = (TextView) findViewById(R.id.detailProb);
        detailGender = (TextView) findViewById(R.id.detailGender);
        detailMarital = (TextView) findViewById(R.id.detailMarital);
        detailJobRole = (TextView) findViewById(R.id.detailJobRole);
        detailDept = (TextView) findViewById(R.id.displayDept);
        detailExp = (TextView) findViewById(R.id.detailExp);
        detailPerformance = (TextView) findViewById(R.id.detailPerformance);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    if (dataSnapshot.getKey().equals(nm)){
                        lt.add(0,snapshot.child(nm).child("Age").getValue().toString());
                        lt.add(1,snapshot.child(nm).child("Department").getValue().toString());
                        lt.add(2,snapshot.child(nm).child("Gender").getValue().toString());
                        lt.add(3,snapshot.child(nm).child("JobRole").getValue().toString());
                        lt.add(4,snapshot.child(nm).child("MaritalStatus").getValue().toString());
                        lt.add(5,snapshot.child(nm).child("TotalWorkingYears").getValue().toString());
                        lt.add(6,snapshot.child(nm).child("PerformanceRating").getValue().toString());
                        break;

                    }
                }
                if(lt.size() != 0){
                    detailName.setText(nm);
                    detailAge.setText(lt.get(0));
                    detailEId.setText(id);
                    detailStatus.setText(stat);
                    detailStatus.setTextColor(stat.equals("WILL STAY") ? getResources().getColor(R.color.green_secondary) : getResources().getColor(R.color.red_secondary));
                    detailProb.setText(pro);
                    detailGender.setText(lt.get(2));
                    detailMarital.setText(lt.get(4));
                    detailJobRole.setText(lt.get(3));
                    detailDept.setText(lt.get(1));
                    detailExp.setText(lt.get(5));
                    detailPerformance.setText(lt.get(6));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), ""+error, Toast.LENGTH_SHORT).show();
            }
        });

    }
}