package com.aviom.aviomplay.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;

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

public class LeadDetailsFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_lead_details,container,false);
        Toolbar mtoolBar = (Toolbar)v.findViewById(R.id.toolbar);
        return v;
        //return super.onCreateView(inflater, container, savedInstanceState);
    }
}
