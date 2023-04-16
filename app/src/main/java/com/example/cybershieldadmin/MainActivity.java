package com.example.cybershieldadmin;
import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import java.util.Locale;
public class MainActivity extends AppCompatActivity {
    String[] items={"English","اردو"};
    AutoCompleteTextView autoCompleteTxt;
    ArrayAdapter<String> adapterItems;
    LinearLayout linearLayout,linearLayout2,linearLayout3,linearLayout7,linearLayout8,linearLayout9,linearLayout13;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadlocal();
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        autoCompleteTxt=findViewById(R.id.spinner);
        linearLayout=findViewById(R.id.one);
        linearLayout2=findViewById(R.id.Two);
        linearLayout3=findViewById(R.id.Three);
        linearLayout7=findViewById(R.id.seven);
        linearLayout8=findViewById(R.id.eight);
        linearLayout9=findViewById(R.id.nine);
        linearLayout13=findViewById(R.id.thirteen);

        adapterItems= new ArrayAdapter<String>(this,R.layout.list_item,items);
        autoCompleteTxt.setAdapter(adapterItems);
        autoCompleteTxt.setThreshold(100);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(MainActivity.this, One.class);
                startActivity(intent);
            }
        });
        linearLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(MainActivity.this, Two.class);
                startActivity(intent);
            }
        });
        linearLayout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(MainActivity.this, Three.class);
                startActivity(intent);
            }
        });
        linearLayout7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(MainActivity.this, Seven.class);
                startActivity(intent);
            }
        });linearLayout8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(MainActivity.this, Eight.class);
                startActivity(intent);
            }
        });
        linearLayout9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(MainActivity.this, Nine.class);
                startActivity(intent);
            }
        });linearLayout13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(MainActivity.this, Thirteen.class);
                startActivity(intent);
            }
        });



        autoCompleteTxt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String selectlanguage= adapterView.getItemAtPosition(position).toString();
                if (selectlanguage.equals("English")) {
                    setLocal(MainActivity.this,"en");
                    recreate();
                } else if (selectlanguage.equals("اردو")) {
                    setLocal(MainActivity.this,"ur");
                    recreate();
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
        setLocal(MainActivity.this,lang);
    }
}