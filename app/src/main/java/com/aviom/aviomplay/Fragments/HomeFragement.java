package com.aviom.aviomplay.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aviom.aviomplay.R;

/**
 * Created by Admin on 2017-02-17.
 */

public class HomeFragement extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.content_home,container,false);
        //return super.onCreateView(inflater, container, savedInstanceState);
    }
}
