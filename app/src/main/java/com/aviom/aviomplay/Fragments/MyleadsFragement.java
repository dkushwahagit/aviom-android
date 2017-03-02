package com.aviom.aviomplay.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.aviom.aviomplay.Adapters.MyLeadsListAdapter;
import com.aviom.aviomplay.Helpers.DatabaseHelper;
import com.aviom.aviomplay.Models.Lead;
import com.aviom.aviomplay.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Created by Admin on 2017-02-17.
 */

public class MyleadsFragement extends Fragment {


    private ListView myLeadsListView;
    List<Lead> lstLeads;
    List<Lead> lstLeads1;
    Lead ld;
    String[] myList=new String[]{"A","b","C"};
    //List<Lead> lstLeads = new ArrayList<Lead>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v=inflater.inflate(R.layout.content_myleads,container,false);
        Toolbar mtoolBar = (Toolbar)v.findViewById(R.id.toolbar);
        Button _viewbtn=(Button)v.findViewById(R.id.view_btn);
        myLeadsListView=(ListView)v.findViewById(R.id.myLeadsListView);
        DatabaseHelper db = new DatabaseHelper(getActivity());
        lstLeads=db.getAllLeads();
        MyLeadsListAdapter myLeadsListAdapter=new MyLeadsListAdapter(this.getDummyLeads());
        myLeadsListView.setAdapter(myLeadsListAdapter);

        mtoolBar.setTitle("My Leads");

        // myLeadsListView

      /*  _viewbtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                 Toast.makeText(getActivity(),"clciked", Toast.LENGTH_LONG).show();

               // Fragment fragment = new MyleadsFragement();
                //  FragmentManager fragmentManager =.getSupportFragmentManager();
                //  FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                //fragmentTransaction.replace(R.id.container, fragment);
                //fragmentTransaction.addToBackStack(null);
                //fragmentTransaction.commit();
            }

        });*/
        return v;
        //return super.onCreateView(inflater, container, savedInstanceState);
    }

    private List<Lead> getDummyLeads(){
        List<Lead> lstLeads1 = new ArrayList<Lead>();
        String Date_Format =  "dd-MM-yyyy";
        DateFormat dateformat=new SimpleDateFormat(Date_Format);

        for(int i=0;i<lstLeads.size();i++){
            ld = new Lead();
            ld.setId(lstLeads.get(i).getId());
            ld.setCustomername(lstLeads.get(i).getCustomername());
            ld.setImage(lstLeads.get(i).getImage());
            ld.setPhone(lstLeads.get(i).getPhone());
            ld.setBudget(lstLeads.get(i).getBudget());
            ld.setStatus(lstLeads.get(i).getStatus());
            ld.setLocation(lstLeads.get(i).getLocation());
            ld.setCreated_on(dateformat.format(new Date()).toString());
            ld.setAppoimentdate(lstLeads.get(i).getAppoimentdate());
            lstLeads1.add(ld);
        }
        return lstLeads1;
    }

}
