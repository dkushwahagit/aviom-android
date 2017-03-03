package com.aviom.aviomplay.Fragments;


import android.app.DatePickerDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.aviom.aviomplay.Helpers.DatabaseHelper;
import com.aviom.aviomplay.Models.Interaction;
import com.aviom.aviomplay.Models.Lead;
import com.aviom.aviomplay.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Admin on 2017-02-17.
 */

public class LeadDetailsFragment extends Fragment {
    Lead leadsData;
    int leadid=0;
    DatePickerDialog datePickerDialog;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            leadid = bundle.getInt("leadid", 0);
        }
        DatabaseHelper db = new DatabaseHelper(getActivity());
        leadsData=db.getLeadByLeadId(leadid);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int myInt=0;
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            myInt = bundle.getInt("leadid", 0);
        }
        Toast.makeText(getActivity(),myInt + " clicked!", Toast.LENGTH_SHORT).show();
        View v = inflater.inflate(R.layout.fragment_lead_details,container,false);
        TextView _tvCustomername=(TextView)v.findViewById(R.id.customername);
        TextView _tvCustomerLocation=(TextView)v.findViewById(R.id.location);
        TextView _tvLeadSattus=(TextView)v.findViewById(R.id.LeadStatus);
        TextView _tvPhoneNumber=(TextView)v.findViewById(R.id.phonenumber);
        TextView _tvBudget=(TextView)v.findViewById(R.id.budget);
        TextView _tvLeadCreatedDate=(TextView)v.findViewById(R.id.leadcreateddate);
        final TextView _tvRemark=(TextView)v.findViewById(R.id.remarkstext1);
        final TextView _tvAddressText=(TextView)v.findViewById(R.id.addresstext);
         final TextView appointmentdate=(TextView) v.findViewById(R.id.nextAppointmentdate) ;
       final ImageView _ivLeadImage=(ImageView)v.findViewById(R.id.user_profile_photo);
         Button _updateButton=(Button) v.findViewById(R.id.updatebutton) ;
         TextView _tvnextappointmentdatetext=(TextView) v.findViewById(R.id.nextAppointmentdatetext) ;


       final LinearLayout ll = (LinearLayout) v.findViewById(R.id.propertylayout);

        _tvCustomername.setText(leadsData.getCustomername());
        _tvCustomerLocation.setText(leadsData.getLocation());
        _tvPhoneNumber.setText(leadsData.getPhone());
        _tvBudget.setText(leadsData.getBudget());
        _tvLeadCreatedDate.setText(leadsData.getCreated_on());
        _tvnextappointmentdatetext.setText(leadsData.getAppoimentdate());



        if(leadsData.getProperty_identified().equals("0"))
        {
           ll.setVisibility(View.GONE);
            _tvAddressText.setVisibility(View.GONE);
        }
        else
        {
           _tvAddressText.setText(leadsData.getProperty_address());
        }
        if(leadsData.getImage()!=null) {
            final Bitmap bmp = BitmapFactory.decodeByteArray(leadsData.getImage(), 0, leadsData.getImage().length);
            _ivLeadImage.post(new Runnable() {
                @Override
                public void run() {
                    //

                    _ivLeadImage.setImageBitmap(Bitmap.createScaledBitmap(bmp, _ivLeadImage.getWidth(),
                            _ivLeadImage.getHeight(),false));
                    //_ivLeadImage.setImageDrawable(custom_rounded_border);

                }
            });
        }

        if(leadsData.getStatus()==1)
            _tvLeadSattus.setText(" new ");
        else if(leadsData.getStatus()==2)
            _tvLeadSattus.setText(" closed ");
        else if(leadsData.getStatus()==3)
            _tvLeadSattus.setText(" Follow-up ");
        else if(leadsData.getStatus()==4)
            _tvLeadSattus.setText(" Initiated ");
        else if(leadsData.getStatus()==5)
            _tvLeadSattus.setText(" Re-followup ");
        else if(leadsData.getStatus()==6)
            _tvLeadSattus.setText(" Discarder ");
        else if(leadsData.getStatus()==7)
            _tvLeadSattus.setText(" Not interested ");
        Toolbar mtoolBar = (Toolbar)v.findViewById(R.id.toolbar);

        //create spinner
        String[] leadstatus = {"Select Status", "New", "Closed", "Follow-up","Initiated","Re-followup","Discarder","Not interested"};
        final Spinner spinnerLeadstatus = (Spinner) v.findViewById(R.id.spinnerleadstaus);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(v.getContext(), R.layout.spinner_text_item, leadstatus);
        spinnerLeadstatus.setAdapter(arrayAdapter);
        spinnerLeadstatus.setSelection(leadsData.getStatus());

        //create datepicker
        appointmentdate.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day

                // date picker dialog
                datePickerDialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                String formattedDay = (String.valueOf(dayOfMonth));
                                String formattedMonth = (String.valueOf(monthOfYear));

                                if (dayOfMonth < 10) {
                                    formattedDay = "0" + dayOfMonth;
                                }

                                if (monthOfYear < 10) {
                                    formattedMonth = "0" + (monthOfYear+1);
                                }

                                appointmentdate.setText(formattedDay + "-" + formattedMonth + "-" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
            }
        });


        _updateButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
               // Toast.makeText(getActivity()," clicked!", Toast.LENGTH_SHORT).show();
               int leadstat= spinnerLeadstatus.getSelectedItemPosition();

               String remark= _tvRemark.getText().toString();
                DatabaseHelper db = new DatabaseHelper(getActivity());
               Boolean b = db.updateLeadAndCreateNewAppointment(leadid,leadstat,remark);

                if(!appointmentdate.getText().equals("Appointment Date"))
                {
                    DateFormat createonformate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date createondate = new Date();
                   String date=appointmentdate.getText().toString();
                    Interaction interaction = new Interaction();
                    interaction.setPlannedon(appointmentdate.getText().toString());
                    interaction.setLeadid(leadid);
                    interaction.setStatus(leadstat);
                    interaction.setRemarks(remark);
                    interaction.setCreated_on(createonformate.format(createondate).toString());
                    interaction.setPlanned_by(5);
                    long lead_ineracton_id= db.createInteraction(interaction);
                    if(lead_ineracton_id>0)
                    {
                        Toast.makeText(getActivity(),"Successfully updated !", Toast.LENGTH_LONG).show();
                        Fragment fragment = new MyleadsFragement();
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.container, fragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                    }
                    else
                    {
                        Toast.makeText(getActivity(),"Please Try again !", Toast.LENGTH_LONG).show();
                    }


                }


            }
        });


        return v;
        //return super.onCreateView(inflater, container, savedInstanceState);
    }


}
