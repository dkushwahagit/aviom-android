package com.aviom.aviomplay.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;

import com.aviom.aviomplay.R;

/**
 * Created by Admin on 2017-02-17.
 */

public class NewleadFragement extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.content_newlead,container,false);
        Toolbar mtoolBar = (Toolbar)v.findViewById(R.id.toolbar);
        //Set the title
        mtoolBar.setTitle("Add New Lead");
        //Set autocomplete Budget values
        String[] budget ={"Select Budget","0-3 Lacs","3.1 - 5 Lacs","Above 5 Lacs"};
        String[] leadstatus ={"Select Status","New","Follow up","Closed"};
        Spinner spinnerBudget = (Spinner) v.findViewById(R.id.spinBudget);
        Spinner spinnerLeadstatus=(Spinner)v.findViewById(R.id.leadStatus);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(v.getContext(), R.layout.spinner_text_item ,budget);
        spinnerBudget.setAdapter(arrayAdapter);
        ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(v.getContext(), R.layout.spinner_text_item ,leadstatus);
        spinnerLeadstatus.setAdapter(arrayAdapter1);
        return v;
        //return super.onCreateView(inflater, container, savedInstanceState);
    }
}
