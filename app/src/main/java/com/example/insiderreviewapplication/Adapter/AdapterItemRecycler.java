package com.example.insiderreviewapplication.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.insiderreviewapplication.DetailsActivity;
import com.example.insiderreviewapplication.Model.EmployeeModel;
import com.example.insiderreviewapplication.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter Recycler for Setting list of values in Recycler View in List Analytics Class
 */

public class AdapterItemRecycler extends RecyclerView.Adapter<AdapterItemRecycler.MyViewHolder> {

    List<EmployeeModel> lt = new ArrayList<>();
    Context ct;

    public AdapterItemRecycler(Context ct){
        this.ct = ct;
    }

    public void setItems(List<EmployeeModel> list){
        lt.clear();
        lt.addAll(list);
    }

    @NonNull
    @Override
    public AdapterItemRecycler.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_row,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterItemRecycler.MyViewHolder holder, int position) {
        holder.itemName.setText(lt.get(position).getEmployeeName());
        holder.itemCode.setText(lt.get(position).getEmployeeId());
        String temp = String.valueOf(lt.get(position).getAttrition());
        temp = temp.equals("false") ? "WILL STAY" : "WILL LEAVE";
        String temp1 = temp;
        holder.itemAttrition.setText(temp);
        double tProb = lt.get(position).getProbability();
        holder.itemProb.setText(String.format("%.3f",tProb));
        holder.itemTouch.setBackgroundResource(temp.equals("WILL LEAVE") ? R.drawable.join_no : R.drawable.join_yes);
        holder.itemTouch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ct.startActivity(new Intent(ct, DetailsActivity.class).putExtra("EmpName",lt.get(holder.getAdapterPosition()).getEmployeeName()).putExtra("id",lt.get(holder.getAdapterPosition()).getEmployeeId()).putExtra("stat",temp1).putExtra("pro",String.format("%.3f",tProb)));
            }
        });
    }

    @Override
    public int getItemCount() {
        return lt.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView itemName;
        TextView itemCode;
        TextView itemAttrition;
        TextView itemProb;
        ConstraintLayout itemTouch;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName = (TextView) itemView.findViewById(R.id.itemName);
            itemCode = (TextView) itemView.findViewById(R.id.itemEmpCode);
            itemAttrition = (TextView) itemView.findViewById(R.id.itemAttrition);
            itemProb = (TextView) itemView.findViewById(R.id.itemProb);
            itemTouch = (ConstraintLayout) itemView.findViewById(R.id.itemTouch);
        }
    }
}
