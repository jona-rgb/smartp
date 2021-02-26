package com.example.passwort;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.widget.ToolbarWidgetWrapper;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ClipData;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.acl.Group;
import java.util.Random;

public class Activity_2 extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    ImageButton refresh;
    EditText anwendung;
    EditText benutzername;
    EditText passwort;
    Button submit;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Group e1;
    Toolbar toolbar;
    Dialog mydialog;
    Button ja;
    Button nein;
    TextView sicher;


    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String text1 = "anwendung1";
    public static final String text2 = "benutzername1";
    public static final String text3 = "passwort1";
    public static final String text4 = "anwendung2";
    public static final String text5 = "benutzername2";
    public static final String text6 = "passwort2";
    public static final String text7 = "anwendung3";
    public static final String text8 = "benutzername3";
    public static final String text9 = "passwort3";

    private String t1;
    private String t2;
    private String t3;
    private String t4;
    private String t5;
    private String t6;
    private String t7;
    private String t8;
    private String t9;




    @SuppressLint({"CutPasteId", "ResourceType"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);


        refresh = findViewById(R.id.refresh);
        anwendung = findViewById(R.id.anwendung);
        benutzername = findViewById(R.id.benutzername);
        passwort = findViewById(R.id.passwort);
        submit = findViewById(R.id.submit);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        e1 = findViewById(R.id.applikationen);
        mydialog = new Dialog(this);


        toolbar = findViewById(R.id.mytoolbar);
        setSupportActionBar(toolbar);
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_open,R.string.navigation_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);



        loadData("Spotify");
        loadData("Google");
        loadData("Premium-SIM");
        //update("Spotify");




        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passwort.setText(generieren());
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){

                ShowPopup(v);
                //anwendung.setText("");
                //benutzername.setText("");
                //passwort.setText("");

            }
        });
    }

    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }

    public void ShowPopup(View view) {
        mydialog.setContentView(R.layout.custompopup);

        ja = (Button) mydialog.findViewById(R.id.ja);
        nein = (Button) mydialog.findViewById(R.id.nein);
        //sicher = (TextView) mydialog.findViewById(R.id.sicher);

        nein.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mydialog.dismiss();
                //Toast.makeText(this ,"Speichervorgang abgebrochen", Toast.LENGTH_SHORT).show();
            }
        });
        ja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(anwendung.getText().toString().equals("Spotify"))
                    saveData("Spotify");
                else if(anwendung.getText().toString().equals("Google"))
                    saveData("Google");
                else if(anwendung.getText().toString().equals("Premium-SIM"))
                    saveData("Premium-SIM");

                mydialog.dismiss();
            }
        });
        mydialog.show();

    }

    public String generieren() {
        int leftLimit = 33; // letter 'a'
        int rightLimit = 125; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        return buffer.toString();
    }

    public void saveData(String s){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        SharedPreferences.Editor editor =  sharedPreferences.edit();

        switch (s) {
            case "Spotify":
                editor.putString(text1, anwendung.getText().toString());
                editor.putString(text2, benutzername.getText().toString());
                editor.putString(text3, passwort.getText().toString());
                Toast.makeText(this,"Data saved", Toast.LENGTH_SHORT).show();
                break;
            case "Google":
                editor.putString(text4, anwendung.getText().toString());
                editor.putString(text5, benutzername.getText().toString());
                editor.putString(text6, passwort.getText().toString());
                Toast.makeText(this,"Data saved", Toast.LENGTH_SHORT).show();
                break;
            case "Premium-SIM":
                editor.putString(text7, anwendung.getText().toString());
                editor.putString(text8, benutzername.getText().toString());
                editor.putString(text9, passwort.getText().toString());
                Toast.makeText(this,"Data saved", Toast.LENGTH_SHORT).show();
                break;
        }

        editor.apply();


    }

    public void loadData(String s) {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);

        switch (s) {
            case "Spotify":
                t1 = sharedPreferences.getString(text1, "");
                t2 = sharedPreferences.getString(text2, "");
                t3 = sharedPreferences.getString(text3, "");
                break;
            case "Google":
                t4 = sharedPreferences.getString(text4, "");
                t5 = sharedPreferences.getString(text5, "");
                t6 = sharedPreferences.getString(text6, "");
                break;
            case "Premium-SIM":
                t7 = sharedPreferences.getString(text7, "");
                t8 = sharedPreferences.getString(text8, "");
                t9 = sharedPreferences.getString(text9, "");
                break;
        }
    }

    public void update(String s) {

        switch (s) {
            case "Spotify":
                anwendung.setText(t1);
                benutzername.setText(t2);
                passwort.setText(t3);
                break;
            case "Google":
                anwendung.setText(t4);
                benutzername.setText(t5);
                passwort.setText(t6);
                break;
            case "Premium-SIM":
                anwendung.setText(t7);
                benutzername.setText(t8);
                passwort.setText(t9);
                break;
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.spotify:
                loadData("Spotify");
                update("Spotify");
                break;
            case R.id.google:
                loadData("Google");
                update("Google");
                break;
            case R.id.premiumsim:
                loadData("Premium-SIM");
                update("Premium-SIM");
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }
}
