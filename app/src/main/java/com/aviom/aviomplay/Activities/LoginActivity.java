package com.aviom.aviomplay.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.aviom.aviomplay.Helpers.DatabaseHelper;

import com.aviom.aviomplay.Models.User;
import com.aviom.aviomplay.R;

import java.io.File;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        DatabaseHelper db = new DatabaseHelper(getApplicationContext());User u= new User();
        u.setPassword("hioshiv");
        u.setUsername("soshiv");
        u.setCreatedOn("2017");
        u.setSatus(1);
        u.setId(12);
        db.createUser(u);
       // db.getUser(1).getUsername();
      //  Log.d("username=",db.getUser(1).getUsername());
      ///  Log.d("get it",db.getUser(1)+"");
     /*   File database=getApplicationContext().getDatabasePath("aviomplay.db");


        if (!database.exists()) {
            // Database does not exist so copy it from assets here
            Log.i("Database", "Not Found");
        } else {
            Log.i("Database", "Found");
        }*/

    }
    public void Login(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);


    }
}
