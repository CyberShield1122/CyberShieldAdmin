package com.example.cybershieldadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class AdminLogin extends AppCompatActivity {
    String[] items2={"English","اردو"};
    String userpassword="";
    AutoCompleteTextView autoCompleteTxt2;
    Button login;
    EditText username,password;
    ArrayAdapter<String> adapterItems2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        getSupportActionBar().hide();
        autoCompleteTxt2=findViewById(R.id.langu);
        username =findViewById(R.id.username);
        password =findViewById(R.id.password);
        login=findViewById(R.id.login);
        adapterItems2= new ArrayAdapter<String>(this,R.layout.list_item,items2);
        autoCompleteTxt2.setAdapter(adapterItems2);
        autoCompleteTxt2.setThreshold(1000);
        final ProgressBar progressBar=findViewById(R.id.progressbar);
        autoCompleteTxt2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String selectlanguage= adapterView.getItemAtPosition(position).toString();
                if (selectlanguage.equals("English")) {
                    setLocal(AdminLogin.this,"en");
                    recreate();
                } else if (selectlanguage.equals("اردو")) {
                    setLocal(AdminLogin.this,"ur");
                    recreate();
                }
            }
        });
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("Admin");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String A =snapshot.child("usernamepassword").getValue().toString();
                userpassword=A;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usernamevalue=username.getText().toString();
                String passwordvalue=password.getText().toString();

                if(usernamevalue.matches("")){
                    Toast.makeText(AdminLogin.this, "Enter User Name", Toast.LENGTH_SHORT).show();
                }
                else if(passwordvalue.matches("")){
                    Toast.makeText(AdminLogin.this, "Enter Password", Toast.LENGTH_SHORT).show();
                }
                else{
                    String usernamepassword= usernamevalue+passwordvalue;
                    DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("Admin");
                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String A =snapshot.child("usernamepassword").getValue().toString();
                            userpassword=A;
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                    if(userpassword.matches(usernamepassword)){
                        Intent intent=new Intent(AdminLogin.this,MainActivity.class);
                        startActivity(intent);
                        finish();


                    }else if(userpassword.matches("")){
                        Toast.makeText(AdminLogin.this, "Check your Internet Connection", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(AdminLogin.this, "Invalid Inputs", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    public void setLocal(Activity activity, String langCode){
        Locale local=new Locale(langCode);
        local.setDefault(local);
        Resources resources=activity.getResources();
        Configuration config =resources.getConfiguration();
        config.setLocale(local);
        resources.updateConfiguration(config,resources.getDisplayMetrics());
        SharedPreferences.Editor editor=getSharedPreferences("Settings",MODE_PRIVATE).edit();
        editor.putString("My Lang",langCode);
        editor.apply();
    }
    public void loadlocal(){
        SharedPreferences sharedPreferences= getSharedPreferences("Settings",Activity.MODE_PRIVATE);
        String lang=sharedPreferences.getString("My Lang","");
        setLocal(AdminLogin.this,lang);
    }
}