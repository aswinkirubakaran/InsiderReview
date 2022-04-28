package com.example.insiderreviewapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.insiderreviewapplication.Adapter.AdapterItemRecycler;
import com.example.insiderreviewapplication.DataAccessObject.UserDao;
import com.example.insiderreviewapplication.Model.EmployeeModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ListAnalyticsActivity extends AppCompatActivity {

    int val;
    RecyclerView items;
    UserDao dao;
    AdapterItemRecycler adapterItemRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_analytics);
        Bundle extra  = getIntent().getExtras();
        if(extra != null) {
            val = (int) extra.getInt("Filter");
        }

        /**
         * 0 == All Items
         * 1 == Joining List
         * 2 == Not Joining List
         */

        items = (RecyclerView) findViewById(R.id.recyclerItems);

        adapterItemRecycler = new AdapterItemRecycler(this);
        items.setAdapter(adapterItemRecycler);
        items.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        dao = new UserDao();

        if (val == 0) {
            dao.get().addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    List<EmployeeModel> lt = new ArrayList<>();
                    for(DataSnapshot data : snapshot.getChildren()){
                        EmployeeModel emp = data.getValue(EmployeeModel.class);
                        lt.add(emp);
                    }
                    adapterItemRecycler.setItems(lt);
                    adapterItemRecycler.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }else if(val == 1){
            dao.get().addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    List<EmployeeModel> lt = new ArrayList<>();
                    for(DataSnapshot data : snapshot.getChildren()){
                        EmployeeModel emp = data.getValue(EmployeeModel.class);
                        if(!emp.getAttrition()){
                            lt.add(emp);
                        }
                    }
                    adapterItemRecycler.setItems(lt);
                    adapterItemRecycler.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }else if(val == 2){
            dao.get().addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    List<EmployeeModel> lt = new ArrayList<>();
                    for(DataSnapshot data : snapshot.getChildren()){
                        EmployeeModel emp = data.getValue(EmployeeModel.class);

                        if(emp != null){
                            if (emp.getAttrition()) {
                                lt.add(emp);
                            }
                        }
                    }
                    adapterItemRecycler.setItems(lt);
                    adapterItemRecycler.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

    }
}