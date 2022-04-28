package com.example.insiderreviewapplication.DataAccessObject;

import com.example.insiderreviewapplication.Model.EmployeeModel;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

/**
 * Data Access Object for Having Common Reference of FireBase Output table throughout Project
 */

public class UserDao {

    DatabaseReference databaseReference;

    public UserDao(){
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference(UserDao.class.getSimpleName());
    }

    public DatabaseReference getRef(){
        return databaseReference;
    }

    public Task<Void> addData(EmployeeModel employeeModel){
        return databaseReference.push().setValue(employeeModel);
    }

    public Task<Void> remove(String key){
        return databaseReference.child(key).removeValue();
    }

    public Query get() {
        return databaseReference.orderByKey();
    }

}
