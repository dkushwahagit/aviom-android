package com.aviom.aviomplay.Fragments;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.aviom.aviomplay.Helpers.DatabaseHelper;
import com.aviom.aviomplay.Models.Lead;
import com.aviom.aviomplay.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Admin on 2017-02-17.
 */

public class NewleadFragement extends Fragment {
    String imagePath;
    ImageView imageupload;

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
        final Spinner spinnerBudget = (Spinner) v.findViewById(R.id.spinnerBudget);
        final Spinner spinnerLeadstatus=(Spinner)v.findViewById(R.id.spinnerleadStatus);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(v.getContext(), R.layout.spinner_text_item ,budget);
        spinnerBudget.setAdapter(arrayAdapter);
        ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(v.getContext(), R.layout.spinner_text_item ,leadstatus);
        spinnerLeadstatus.setAdapter(arrayAdapter1);



        final EditText propertyAddress= (EditText)v.findViewById(R.id.etPropertyAddress);
        final EditText customerName= (EditText)v.findViewById(R.id.etCustomerName);
        final EditText customerPhone= (EditText)v.findViewById(R.id.etPhone);
        final EditText customerCity= (EditText)v.findViewById(R.id.etLocation);
        final EditText remarks= (EditText)v.findViewById(R.id.etRemarks);
        final TextView uploadimage=(TextView)v.findViewById(R.id.imageView);
        imageupload=(ImageView)v.findViewById(R.id.imageuplod) ;
        propertyAddress.setVisibility(View.GONE);
        Button btn_submit=(Button) v.findViewById(R.id.btn_create);
        final Switch onOffSwitch = (Switch)  v.findViewById(R.id.propertyidentified);


        //image popup
        uploadimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              showImagePopup();
            }
        });



        //hide ans show property address EditText on switch button on and off
        onOffSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.v("Switch State=", ""+isChecked);
                if(isChecked) {
                    propertyAddress.setVisibility(View.VISIBLE);

                }
                else

                    propertyAddress.setVisibility(View.GONE);

            }

        });

//submit lead data
        btn_submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                DatabaseHelper db = new DatabaseHelper(getActivity());

                DateFormat createonformate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date createondate = new Date();
                DateFormat leadiddateformate = new SimpleDateFormat("yyyyMMdd");
                Date dateobj = new Date();
                String mystring = "" + leadiddateformate.format(dateobj).toString();
                String arr[] = mystring.split(" ", 2);
                String date = arr[0];
                int count =db.getLeadByCreateON(date);
             //   Toast.makeText(getActivity(),count+"",Toast.LENGTH_LONG).show();
                int userid=5;
                Lead lead = new Lead();
                lead.setCustomername(customerName.getText().toString());
                lead.setPhone(customerPhone.getText().toString());
                lead.setLocation(customerCity.getText().toString());
                lead.setBudget(spinnerBudget.getSelectedItem().toString());
                lead.setRemarks(remarks.getText().toString());
                lead.setCreated_on(createonformate.format(createondate).toString());
                lead.setLeadid(userid+"-"+leadiddateformate.format(dateobj).toString()+"-0"+(count+1));
                if(propertyAddress.isShown())
                {
                    lead.setProperty_address(propertyAddress.getText().toString());
                    lead.setProperty_identified("1");
                }
                else
                {
                    lead.setProperty_address("");
                    lead.setProperty_identified("0");

                }
                lead.setStatus(spinnerLeadstatus.getSelectedItemPosition());
                lead.setLatitude("000.000");
                lead.setLongitude("13.00000");

                db.createLead(lead);

                // TODO Auto-generated method stub

            }
        });
        return v;
        //return super.onCreateView(inflater, container, savedInstanceState);
    }
    //image popup

    public void showImagePopup() {
        /*if (checker.lacksPermissions(PERMISSIONS_READ_STORAGE)) {
            startPermissionsActivity(PERMISSIONS_READ_STORAGE);
        } */
            // File System.
            final Intent galleryIntent = new Intent();
            galleryIntent.setType("image/*");
            galleryIntent.setAction(Intent.ACTION_PICK);

            // Chooser of file system options.
            final Intent chooserIntent = Intent.createChooser(galleryIntent,"select image to upload");
            startActivityForResult(chooserIntent, 1010);

    }

  /* @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 1010) {
            if (data == null) {
                // Snackbar.make(parentView, R.string.string_unable_to_pick_image, Snackbar.LENGTH_INDEFINITE).show();
                return;
            }
            Uri selectedImageUri = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = (getActivity()).getContentResolver().query(selectedImageUri, filePathColumn, null, null, null);

            if (cursor != null) {
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imagePath = cursor.getString(columnIndex);
            //    Toast.makeText(getActivity(),imagePath,Toast.LENGTH_LONG).show();
              //  Picasso.with(getActivity()).load(new File(imagePath))
                   //     .into(imageupload);

                //  Snackbar.make(parentView, R.string.string_reselect, Snackbar.LENGTH_LONG).show();
                cursor.close();

                //  textView.setVisibility(View.GONE);
            //    imageupload.setVisibility(View.VISIBLE);
            } else {
                //  textView.setVisibility(View.VISIBLE);
             //   imageupload.setVisibility(View.GONE);
                //   Snackbar.make(parentView, R.string.string_unable_to_load_image, Snackbar.LENGTH_LONG).show();
            }
        }
    }*/
}
