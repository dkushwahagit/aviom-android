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

    String[] myList=new String[]{"A","b","C"};
    //List<Lead> lstLeads = new ArrayList<Lead>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v=inflater.inflate(R.layout.content_myleads,container,false);
        Toolbar mtoolBar = (Toolbar)v.findViewById(R.id.toolbar);
        myLeadsListView=(ListView)v.findViewById(R.id.myLeadsListView);
        DatabaseHelper db = new DatabaseHelper(getActivity());
       lstLeads=db.getAllLeads();
        MyLeadsListAdapter myLeadsListAdapter=new MyLeadsListAdapter(this.getDummyLeads());
        myLeadsListView.setAdapter(myLeadsListAdapter);
        mtoolBar.setTitle("My Leads");
        return v;
        //return super.onCreateView(inflater, container, savedInstanceState);
    }

    private List<Lead> getDummyLeads(){
       List<Lead> lstLeads1 = new ArrayList<Lead>();
        String Date_Format =  "dd-MM-yyyy";
        DateFormat dateformat=new SimpleDateFormat(Date_Format);

        for(int i=0;i<lstLeads.size();i++){
            Lead ld = new Lead();
            ld.setId(i);
            ld.setCustomername(lstLeads.get(i).getCustomername());
            ld.setPhone(lstLeads.get(i).getPhone());
            ld.setCreated_on(dateformat.format(new Date()).toString());
            lstLeads1.add(ld);
        }
        return lstLeads1;
    }
}
