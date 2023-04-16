package com.example.cybershieldadmin;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    Context context;
    ArrayList<Report> list;
    DatabaseReference ChildFoundDB ;
    FirebaseStorage firebaseStorage;

    public MyAdapter(Context context, ArrayList<Report> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.item,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Report report= list.get(position);
        Picasso.get().load(report.getImage()).placeholder(R.drawable.user).into(holder.image);
        holder.name.setText(report.getName());
        holder.age.setText(report.getAge());
        holder.gender.setText(report.getGender());
        holder.date.setText(report.getDate());
        holder.time.setText(report.getTime());
        holder.city.setText(report.getCity());
        holder.address.setText(report.getAddress());
        holder.additionaladd.setText(report.getAdditionaladd());
        holder.number.setText(report.getNumber());
        ChildFoundDB= FirebaseDatabase.getInstance().getReference().child("Completed Report");
        firebaseStorage= FirebaseStorage.getInstance();
        holder.complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "kkk", Toast.LENGTH_SHORT).show();

                OneModel users=new OneModel(report.image, report.name, report.age, report.gender, report.date,
                        report.time, report.city, report.address, report.additionaladd, report.number);
                ChildFoundDB.push().setValue(users);
                
            }
        });



    }

    @Override
    public int getItemCount() {
        return list.size();

    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView name,age,gender,date,time,city,address,additionaladd,number,complete;
        ImageView image;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name);
            age=itemView.findViewById(R.id.age);
            gender=itemView.findViewById(R.id.gender);
            date=itemView.findViewById(R.id.date);
            time=itemView.findViewById(R.id.time);
            city=itemView.findViewById(R.id.city);
            address=itemView.findViewById(R.id.address);
            additionaladd=itemView.findViewById(R.id.additionaladd);
            number=itemView.findViewById(R.id.number);
            image= itemView.findViewById(R.id.image);
            name=itemView.findViewById(R.id.name);
            complete=itemView.findViewById(R.id.Complete1);
        }
    }
}
