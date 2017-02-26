package com.aviom.aviomplay.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.aviom.aviomplay.Adapters.MyLeadsListAdapter;
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

    String[] myList=new String[]{"A","b","C"};
    //List<Lead> lstLeads = new ArrayList<Lead>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v=inflater.inflate(R.layout.content_myleads,container,false);
        Toolbar mtoolBar = (Toolbar)v.findViewById(R.id.toolbar);
        myLeadsListView=(ListView)v.findViewById(R.id.myLeadsListView);

        MyLeadsListAdapter myLeadsListAdapter=new MyLeadsListAdapter(this.getDummyLeads());
        myLeadsListView.setAdapter(myLeadsListAdapter);
        mtoolBar.setTitle("My Leads");
        return v;
        //return super.onCreateView(inflater, container, savedInstanceState);
    }

    private List<Lead> getDummyLeads(){
        List<Lead> lstLeads = new ArrayList<Lead>();
        String Date_Format =  "dd-MM-yyyy";
        DateFormat dateformat=new SimpleDateFormat(Date_Format);

        for(int i=0;i<5;i++){
            Lead ld = new Lead();
            ld.setId(i);
            ld.setCustomername("Lead Customer "+Integer.toString(i));
            ld.setPhone(Integer.toString(32454453*(i+1)));
            ld.setCreated_on(dateformat.format(new Date()).toString());
            if(i<3) {
                ld.setStatus(1);
            }else{
                ld.setStatus(2);
            }
            lstLeads.add(ld);
        }
        return lstLeads;
    }
}
