package com.aviom.aviomplay.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.aviom.aviomplay.Fragments.MyleadsFragement;
import com.aviom.aviomplay.Models.Lead;
import com.aviom.aviomplay.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 2017-02-18.
 */

public class MyLeadsListAdapter extends BaseAdapter {

    List<Lead> leadList = new ArrayList<Lead>();
    public MyLeadsListAdapter(List<Lead> leadList){
        this.leadList=leadList;
    }

    @Override
    public int getCount() {
        return leadList.size();
    }

    @Override
    public Lead getItem(int position) {
        return leadList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View myView= LayoutInflater.from(parent.getContext()).inflate(R.layout.leads_list_item,parent,false);
        TextView _tvLeadCreatedOn = (TextView)myView.findViewById(R.id.tvLeadCreatedOn);
        TextView _tvLeadCustomerName = (TextView)myView.findViewById(R.id.tvCustomername);
        TextView _tvLeadPhoneNumber = (TextView)myView.findViewById(R.id.tvPhonNumber);
        TextView _tvLeadSattus = (TextView)myView.findViewById(R.id.tvLeadStatus);
        Lead lead=leadList.get(position);
        _tvLeadCreatedOn.setText(lead.getCreated_on());
        _tvLeadCustomerName.setText(lead.getCustomername());
        _tvLeadPhoneNumber.setText(lead.getPhone());
        _tvLeadSattus.setText(lead.getStatus()==1?"new":"Open");
        //tv.setText(mArrayList[position]);
        return myView;
    }
}
