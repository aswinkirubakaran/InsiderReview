package com.example.insiderreviewapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.insiderreviewapplication.DataAccessObject.UserDao;
import com.example.insiderreviewapplication.Model.EmployeeModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class EmployeeActivity extends AppCompatActivity {

    String val;
    String name;
    String eId;
    int radioEnvSat;
    int radioJobSat;
    int radioRelationSat;
    int radioWorkLBal;
    String uri;
    String key;
    String prediction;
    String probability;
    JSONObject obj;
    List<String> lt = new ArrayList<>();
    final MediaType jsonContent = MediaType.get("application/json");

    String resultFromService;
    EmployeeModel emp;
    ValueEventListener listener;

    TextView hello;
    TextView warning;
    RadioGroup environmentSatisfaction;
    RadioGroup jobSatisfaction;
    RadioGroup relationshipSatisfaction;
    RadioGroup workLifeBalance;
    Button buttonSubmit;
    ProgressBar progressBar;
    FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = db.getReference("EmployeeDetails");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);
        Bundle extra  = getIntent().getExtras();
        if(extra != null) {
            val = (String) extra.get("User");
        }

        String[] temp = val.split("\\.");
        name = temp[0];
        eId = temp[1];

        hello = (TextView) findViewById(R.id.textWelcome);
        warning = (TextView) findViewById(R.id.textSub);
        environmentSatisfaction = findViewById(R.id.RadioEnvironmentalSatisfaction);
        jobSatisfaction = findViewById(R.id.RadioJobSatisfaction);
        relationshipSatisfaction = findViewById(R.id.RadioRelationshipSatisfaction);
        workLifeBalance = findViewById(R.id.RadioWorkLifeBalance);
        buttonSubmit = findViewById(R.id.buttonSubmit);
        progressBar = findViewById(R.id.progressBar1);

        hello.setText("hello "+name+"!");

        UserDao dao = new UserDao();
        listener = dao.get().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot1) {
                Log.d("TagForCheck","check");
                for(DataSnapshot data : snapshot1.getChildren()){
                    EmployeeModel emp = data.getValue(EmployeeModel.class);
                    if(emp != null){
                        String id = emp.getEmployeeName();
                        if (id.equals(name)) {
                            dao.remove(data.getKey());
                            break;
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    if (dataSnapshot.getKey().equals(name)){

                        lt.add(0,snapshot.child(name).child("Age").getValue().toString());
                        lt.add(1,snapshot.child(name).child("BusinessTravel").getValue().toString());
                        lt.add(2,snapshot.child(name).child("DailyRate").getValue().toString());
                        lt.add(3,snapshot.child(name).child("Department").getValue().toString());
                        lt.add(4,snapshot.child(name).child("DistanceFromHome").getValue().toString());
                        lt.add(5,snapshot.child(name).child("Education").getValue().toString());
                        lt.add(6,snapshot.child(name).child("EducationField").getValue().toString());
                        lt.add(7,snapshot.child(name).child("EmployeeCount").getValue().toString());
                        lt.add(8,snapshot.child(name).child("EmployeeNumber").getValue().toString());
                        lt.add(9,snapshot.child(name).child("Gender").getValue().toString());
                        lt.add(10,snapshot.child(name).child("HourlyRate").getValue().toString());
                        lt.add(11,snapshot.child(name).child("JobInvolvement").getValue().toString());
                        lt.add(12,snapshot.child(name).child("JobLevel").getValue().toString());
                        lt.add(13,snapshot.child(name).child("JobRole").getValue().toString());
                        lt.add(14,snapshot.child(name).child("MaritalStatus").getValue().toString());
                        lt.add(15,snapshot.child(name).child("MonthlyIncome").getValue().toString());
                        lt.add(16,snapshot.child(name).child("MonthlyRate").getValue().toString());
                        lt.add(17,snapshot.child(name).child("NumCompaniesWorked").getValue().toString());
                        lt.add(18,snapshot.child(name).child("Over18").getValue().toString());
                        lt.add(19,snapshot.child(name).child("OverTime").getValue().toString());
                        lt.add(20,snapshot.child(name).child("PercentSalaryHike").getValue().toString());
                        lt.add(21,snapshot.child(name).child("PerformanceRating").getValue().toString());
                        lt.add(22,snapshot.child(name).child("StandardHours").getValue().toString());
                        lt.add(23,snapshot.child(name).child("StockOptionLevel").getValue().toString());
                        lt.add(24,snapshot.child(name).child("TotalWorkingYears").getValue().toString());
                        lt.add(25,snapshot.child(name).child("TrainingTimesLastYear").getValue().toString());
                        lt.add(26,snapshot.child(name).child("YearsAtCompany").getValue().toString());
                        lt.add(27,snapshot.child(name).child("YearsInCurrentRole").getValue().toString());
                        lt.add(28,snapshot.child(name).child("YearsSinceLastPromotion").getValue().toString());
                        lt.add(29,snapshot.child(name).child("YearsWithCurrManager").getValue().toString());

                        break;

                    }
                }
                if(lt.size()==0){
                    warning.setText("Contact Admin");
                    buttonSubmit.setEnabled(false);
                    AlertDialog alertDialog = new AlertDialog.Builder(EmployeeActivity.this)
                            .setTitle("Details Not Available")
                            .setMessage("Contact Admin")
                            .setPositiveButton("close", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    //dialogInterface.dismiss();
                                    startActivity(new Intent(EmployeeActivity.this,MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
                                }
                            })
                            .create();
                    alertDialog.show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedId;
                selectedId = environmentSatisfaction.getCheckedRadioButtonId();
                RadioButton radio1 = findViewById(selectedId);
                selectedId = jobSatisfaction.getCheckedRadioButtonId();
                RadioButton radio2 = findViewById(selectedId);
                selectedId = relationshipSatisfaction.getCheckedRadioButtonId();
                RadioButton radio3 = findViewById(selectedId);
                selectedId = workLifeBalance.getCheckedRadioButtonId();
                RadioButton radio4 = findViewById(selectedId);

                DatabaseReference datum = dao.getRef();
                datum.removeEventListener(listener);

                if(radio1 != null && radio2 != null && radio3 != null && radio4 != null){

                    radioEnvSat = Integer.valueOf(radio1.getText().toString());
                    radioJobSat = Integer.valueOf(radio2.getText().toString());
                    radioRelationSat = Integer.valueOf(radio3.getText().toString());
                    radioWorkLBal = Integer.valueOf(radio4.getText().toString());

                    obj = dataToJson(radioEnvSat, radioJobSat, radioRelationSat, radioWorkLBal);
                    uri = "http://3c0a6988-618b-48d7-a6aa-c690d1db2cea.centralindia.azurecontainer.io/score";
                    key = "sbgndgrIbrCYgdSWNOpveF63QL98GQtZ";

                    try {
                        new BgTask().execute().get();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    //System.out.println(resultFromService);

                    //Non-Ethical way to get data from JSON
                    if(resultFromService != null){
                        String arr[] = resultFromService.split("\\[");
                        arr = arr[1].split("]");
                        arr = arr[0].split(",");
                        String temp[] = arr[1].split("\\s");
                        prediction = temp[2];
                        temp = arr[2].split("\\s");
                        probability = temp[2];
                        probability = probability.substring(0,probability.length()-1);
                    }

                    emp = new EmployeeModel(name,eId,radioEnvSat,radioJobSat,radioRelationSat,radioWorkLBal,Boolean.valueOf(prediction),Double.valueOf(probability));
                    dao.addData(emp);

                    AlertDialog alertDialog = new AlertDialog.Builder(EmployeeActivity.this)
                                .setTitle("Thank You! for Filling out the form")
                                .setMessage("Your response has been recorded")
                                .setPositiveButton("close", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        //dialogInterface.dismiss();
                                        startActivity(new Intent(EmployeeActivity.this,MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
                                    }
                                })
                                .create();
                    alertDialog.show();

                    //last minute hope
                    /*float feedBackScore = (emp.getEnvironmentSatisfaction() + emp.getJobSatisfaction() + emp.relationshipSatisfaction + emp.workLifeBalance)/4;
                    if(!emp.getAttrition()){
                        emp.setAttrition(!(feedBackScore < 2.2));
                    } else {
                        emp.setAttrition(!(feedBackScore < 3));
                    }

                    EmployeeModel emp = new EmployeeModel(name,eId,Integer.valueOf(radio1.getText().toString()),Integer.valueOf(radio2.getText().toString()),Integer.valueOf(radio3.getText().toString()),Integer.valueOf(radio4.getText().toString()),false,0.0);
                    */
                }else{
                    Toast.makeText(getApplicationContext(), "Fill All Required Fields", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    public JSONObject dataToJson(int envSat, int jobSat, int relSat, int workLB){

        JSONObject obj1 = new JSONObject();
        JSONArray dataItems = new JSONArray();
        JSONObject inp = new JSONObject();
        JSONObject globalParameter = new JSONObject();
        try {
            JSONObject item1 = new JSONObject();

            item1.put("Age", lt.get(0));
            item1.put("BusinessTravel", lt.get(1));
            item1.put("DailyRate", lt.get(2));
            item1.put("Department", lt.get(3));
            item1.put("DistanceFromHome", lt.get(4));
            item1.put("Education", lt.get(5));
            item1.put("EducationField", lt.get(6));
            item1.put("EmployeeCount", lt.get(7));
            item1.put("EmployeeNumber", lt.get(8));
            item1.put("EnvironmentSatisfaction", String.valueOf(envSat));
            item1.put("Gender", lt.get(9));
            item1.put("HourlyRate", lt.get(10));
            item1.put("JobInvolvement", lt.get(11));
            item1.put("JobLevel", lt.get(12));
            item1.put("JobRole", lt.get(13));
            item1.put("JobSatisfaction", String.valueOf(jobSat));
            item1.put("MaritalStatus", lt.get(14));
            item1.put("MonthlyIncome", lt.get(15));
            item1.put("MonthlyRate", lt.get(16));
            item1.put("NumCompaniesWorked", lt.get(17));
            item1.put("Over18", lt.get(18));
            item1.put("OverTime", lt.get(19));
            item1.put("PercentSalaryHike", lt.get(20));
            item1.put("PerformanceRating", lt.get(21));
            item1.put("RelationshipSatisfaction", String.valueOf(relSat));
            item1.put("StandardHours", lt.get(22));
            item1.put("StockOptionLevel", lt.get(23));
            item1.put("TotalWorkingYears", lt.get(24));
            item1.put("TrainingTimesLastYear", lt.get(25));
            item1.put("WorkLifeBalance", String.valueOf(workLB));
            item1.put("YearsAtCompany", lt.get(26));
            item1.put("YearsInCurrentRole", lt.get(27));
            item1.put("YearsSinceLastPromotion", lt.get(28));
            item1.put("YearsWithCurrManager", lt.get(29));

            dataItems.put(item1);

            inp.put("WebServiceInput0",dataItems);
            obj1.put("Inputs",inp);

            globalParameter.put("Application Name", "Insider Review");
            obj1.put("GlobalParameters",globalParameter);
        }catch (JSONException e) {
            e.printStackTrace();
        }

        return obj1;
    }

    public class BgTask extends AsyncTask<String,String,String>{
        String rs;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            resultFromService = rs;
            progressBar.setVisibility(View.GONE);
        }

        @Override
        protected String doInBackground(String... strings) {
            try{
                OkHttpClient client = new OkHttpClient();

                RequestBody body = RequestBody.create(jsonContent, String.valueOf(obj));

                Request request = new Request.Builder()
                        .url(uri)
                        .addHeader("Content-Type", "application/json")
                        // Only needed if using authentication
                        .addHeader("Authorization", "Bearer " + key)
                        .post(body)
                        .build();
                Response response = client.newCall(request).execute();
                //System.out.println(response.body().string());
                rs = response.body().string();
                resultFromService = rs;
            }catch (Exception e){
                e.printStackTrace();
            }
            return rs;
        }
    }

}
