package com.aviom.aviomplay.Activities;

import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.aviom.aviomplay.Fragments.AppointmentsFragement;
import com.aviom.aviomplay.Fragments.HomeFragement;
import com.aviom.aviomplay.Fragments.LeadDetailsFragment;
import com.aviom.aviomplay.Fragments.MyleadsFragement;
import com.aviom.aviomplay.Fragments.NewleadFragement;
import com.aviom.aviomplay.R;

public class MainActivity extends AppCompatActivity {


    private BottomNavigationView bottomViewNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // container=(FrameLayout)findViewById(R.id.container);
        //getSupportFragmentManager().beginTransaction().replace(R.id.container,new TestFragment(),"").commit();
        getSupportFragmentManager().beginTransaction().replace(R.id.container,new HomeFragement(),"").commit();
        bottomViewNavigationView = (BottomNavigationView)findViewById(R.id.bottomNav);
        bottomViewNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
               switch (item.getItemId()){
                   case R.id.action_menu_home:
                       getSupportFragmentManager().beginTransaction().replace(R.id.container,new HomeFragement(),"").commit();
                       break;
                   case R.id.action_menu_newlead:
                       getSupportFragmentManager().beginTransaction().replace(R.id.container,new NewleadFragement(),"").commit();
                       break;
                   case R.id.action_menu_myleads:
                       getSupportFragmentManager().beginTransaction().replace(R.id.container,new MyleadsFragement(),"").commit();
                       break;
                   case R.id.action_menu_appointments:
                       getSupportFragmentManager().beginTransaction().replace(R.id.container,new LeadDetailsFragment(),"").commit();
                       break;
                   case R.id.action_menu_signout:
                    //   getSupportFragmentManager().beginTransaction().replace(R.id.container,new LeadDetailsFragment(),"").commit();
                       break;
               }
                return false;
            }
        });
    }

}
