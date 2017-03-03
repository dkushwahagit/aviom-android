package com.aviom.aviomplay.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.aviom.aviomplay.Helpers.Constants;
import com.aviom.aviomplay.Helpers.DatabaseHelper;

import com.aviom.aviomplay.Helpers.RequestInterface;
import com.aviom.aviomplay.Models.User;
import com.aviom.aviomplay.R;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    AppCompatButton button;
    private EditText username;
    private EditText pass;
    private TextView uidtext;
    private SharedPreferences pref;
    private ArrayList<User> muser;
    public static final String PREFS_PRIVATE = "PREFS_PRIVATE";
    private SharedPreferences prefsPrivate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        pref = this.getPreferences(0);
        pass=(EditText)findViewById(R.id.password);
        username=(EditText)findViewById(R.id.username);
        // uidtext=(TextView)findViewById(R.id.uid);
        String uniqueID = Settings.Secure.getString(this.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        //  uidtext.setText(uniqueID);
        Log.d("uuid=",uniqueID);
        button=(AppCompatButton) findViewById(R.id.btn_login);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                // Intent i = new Intent(getApplicationContext(),AddNewLeadActivity.class);
                // startActivity(i);

                String user = username.getText().toString().trim();
                String password = pass.getText().toString().trim();

                // Check for empty data in the form
                if (!user.isEmpty() && !password.isEmpty()) {
                    //   progress.setVisibility(View.VISIBLE);

                    loginProcess(user,password);
                } else {
                    // Prompt user to enter credentials
                    Toast.makeText(getApplicationContext(),
                            "Please enter the correct credentials!", Toast.LENGTH_LONG)
                            .show();
                }

            }
        });
        prefsPrivate = getSharedPreferences(LoginActivity.PREFS_PRIVATE, Context.MODE_PRIVATE);
        if(prefsPrivate.getBoolean(Constants.IS_LOGGED_IN,false)){
            //  fragment = new LeadsFragment();
            //   Toast.makeText(getApplicationContext(),""+prefsPrivate.getBoolean(Constants.IS_LOGGED_IN, true), Toast.LENGTH_LONG).show();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }

    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                //
                //   Toast.makeText(getApplicationContext(), "Appointment", Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void loginProcess(String user,String password){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RequestInterface requestInterface = retrofit.create(RequestInterface.class);
        String uniqueID =Settings.Secure.getString(this.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        // Toast.makeText(getApplicationContext(),uniqueID, Toast.LENGTH_SHORT).show();
        String deviceid="7895755987abc";
        //  Toast.makeText(getActivity(),id +" clicked!", Toast.LENGTH_SHORT).show();
        Call<JsonObject> call = requestInterface.loginCheck(deviceid,user,password);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject object = response.body();
                String uniqueID = UUID.randomUUID().toString();
                String error = object.get("ERROR") + "";
                //  JsonArray error=object.getAsJsonArray("ERROR");
                //  mResponseObserver.onFinish(leadsData);
                Log.e("soshiv", "" + object);

                if (error.equals("false")) {
                    JsonArray leadsDetials = object.getAsJsonArray("RESPONSE_DATA");
                    Gson gson = new Gson();
                    Type type = new TypeToken<List<User>>() {
                    }.getType();
                    List<User> lista = gson.fromJson(leadsDetials, type);
                    prefsPrivate = getSharedPreferences(LoginActivity.PREFS_PRIVATE, Context.MODE_PRIVATE);
                    SharedPreferences.Editor privateEdit = prefsPrivate.edit();
                    privateEdit.putString(Constants.NAME,lista.get(0).getUsername() );
                   // privateEdit.putString(Constants.UNIQUE_ID,lista.get(0).getus());
                 //   privateEdit.putString(Constants.MOBILE,lista.get(0).getUser_Contact() );
                 //   privateEdit.putString(Constants.PROFILE_PIC,lista.get(0).getUser_Profile_Pic());



                    privateEdit.putBoolean(Constants.IS_LOGGED_IN,true);
                    privateEdit.commit();
                    // SharedPreferences.Editor editor = pref.edit();
                    // editor.putBoolean(Constants.IS_LOGGED_IN, true);
                    // editor.putString(Constants.NAME, lista.get(0).getUsername());
                    // editor.putString(Constants.UNIQUE_ID, lista.get(0).getUnique_id());
                    // editor.apply();


                    goToProfile();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"please enter correct details", Toast.LENGTH_SHORT).show();
                }

                // hidepDialog();
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

                Log.d("onFailure", t.toString());
                Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();
            }







        });

    }
    private void goToProfile(){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }
    private void logout() {
        SharedPreferences.Editor editor = prefsPrivate.edit();
        editor.putBoolean(Constants.IS_LOGGED_IN,false);
        editor.putString(Constants.EMAIL,"");
        editor.putString(Constants.NAME,"");
        editor.putString(Constants.UNIQUE_ID,"");
        editor.apply();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        // goToLogin();
    }
}
