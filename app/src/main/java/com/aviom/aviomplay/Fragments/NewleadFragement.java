package com.aviom.aviomplay.Fragments;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.aviom.aviomplay.Activities.MainActivity;
import com.aviom.aviomplay.Helpers.DatabaseHelper;
import com.aviom.aviomplay.Models.Interaction;
import com.aviom.aviomplay.Models.Lead;
import com.aviom.aviomplay.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Admin on 2017-02-17.
 */

public class NewleadFragement extends Fragment implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener {
    String imagePath;
    ImageView imageupload;
    private GoogleApiClient mGoogleApiClient;
    private LocationManager locationManager;
    private LocationManager mLocationManager;
    String lat,lon;
    private Location mLocation;
    Activity mActivity;
    DatePickerDialog datePickerDialog;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if ((resultCode == RESULT_OK && requestCode == 1010)||(resultCode == RESULT_OK && requestCode == 1)) {
            if (data == null) {
                // Snackbar.make(parentView, R.string.string_unable_to_pick_image, Snackbar.LENGTH_INDEFINITE).show();
                return;
            }
            Uri selectedImageUri = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = mActivity.getContentResolver().query(selectedImageUri, filePathColumn, null, null, null);

            if (cursor != null) {
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imagePath = cursor.getString(columnIndex);

                Picasso.with(mActivity).load(new File(imagePath))
                        .into(imageupload);


                //  Snackbar.make(parentView, R.string.string_reselect, Snackbar.LENGTH_LONG).show();
                cursor.close();

                //  textView.setVisibility(View.GONE);
               // imageView.setVisibility(View.VISIBLE);
            } else {

                //  textView.setVisibility(View.VISIBLE);
              //  imageView.setVisibility(View.GONE);
                //   Snackbar.make(parentView, R.string.string_unable_to_load_image, Snackbar.LENGTH_LONG).show();
            }
        }

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = (MainActivity) this.getActivity();
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        mLocationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        checkLocation();
    }

    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.content_newlead, container, false);
        Toolbar mtoolBar = (Toolbar) v.findViewById(R.id.toolbar);
        //Set the title
        mtoolBar.setTitle("Add New Lead");
        //Set autocomplete Budget values
        String[] budget = {"Select Budget", "0-3 Lacs", "3.1 - 5 Lacs", "Above 5 Lacs"};
        String[] leadstatus = {"Select Status", "New", "Closed", "Follow-up","Initiated","Re-followup","Discarder","Not interested"};
        final Spinner spinnerBudget = (Spinner) v.findViewById(R.id.spinnerBudget);
        final Spinner spinnerLeadstatus = (Spinner) v.findViewById(R.id.spinnerleadStatus);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(v.getContext(), R.layout.spinner_text_item, budget);
        spinnerBudget.setAdapter(arrayAdapter);
        ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(v.getContext(), R.layout.spinner_text_item, leadstatus);
        spinnerLeadstatus.setAdapter(arrayAdapter1);


        final EditText propertyAddress = (EditText) v.findViewById(R.id.etPropertyAddress);
        final EditText customerName = (EditText) v.findViewById(R.id.etCustomerName);
        final EditText customerPhone = (EditText) v.findViewById(R.id.etPhone);
        final EditText customerCity = (EditText) v.findViewById(R.id.etLocation);
        final EditText remarks = (EditText) v.findViewById(R.id.etRemarks);
        final TextView uploadimage = (TextView) v.findViewById(R.id.imageView);
        final TextView appointmentdate=(TextView) v.findViewById(R.id.nextAppointmentdate) ;
        imageupload = (ImageView) v.findViewById(R.id.imageuplod);
        propertyAddress.setVisibility(View.GONE);
        Button btn_submit = (Button) v.findViewById(R.id.btn_create);
        final Switch onOffSwitch = (Switch) v.findViewById(R.id.propertyidentified);


        //appontmentdate select

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
                                // set day of month , month and year value in the edit text
                                String formattedDay = (String.valueOf(dayOfMonth));
                                String formattedMonth = (String.valueOf(monthOfYear+1));

                                if (dayOfMonth < 10) {
                                    formattedDay = "0" + dayOfMonth;
                                }

                                if (monthOfYear < 10) {
                                    formattedMonth = "0" + (monthOfYear+1);
                                }

                                appointmentdate.setText(formattedDay + "-" + (formattedMonth) + "-" + year);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
            }
        }
        );






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
                Log.v("Switch State=", "" + isChecked);
                if (isChecked) {
                    propertyAddress.setVisibility(View.VISIBLE);

                } else

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
                int count = db.getLeadByCreateON(date);
                //   Toast.makeText(getActivity(),count+"",Toast.LENGTH_LONG).show();
                int userid = 5;
                Lead lead = new Lead();
                lead.setCustomername(customerName.getText().toString());
                lead.setPhone(customerPhone.getText().toString());
                lead.setLocation(customerCity.getText().toString());
                lead.setBudget(spinnerBudget.getSelectedItem().toString());
                lead.setRemarks(remarks.getText().toString());
                lead.setCreated_on(createonformate.format(createondate).toString());
                lead.setLeadid(userid + "-" + leadiddateformate.format(dateobj).toString() + "-0" + (count + 1));
                if (propertyAddress.isShown()) {
                    lead.setProperty_address(propertyAddress.getText().toString());
                    lead.setProperty_identified("1");
                } else {
                    lead.setProperty_address("");
                    lead.setProperty_identified("0");

                }
                lead.setStatus(spinnerLeadstatus.getSelectedItemPosition());
                lead.setLatitude(lat);
                lead.setLongitude(lon);
               if(imagePath!=null) {
                   Bitmap b = BitmapFactory.decodeFile(imagePath);
                   ByteArrayOutputStream bos = new ByteArrayOutputStream();
                   b.compress(Bitmap.CompressFormat.JPEG, 100, bos);
                   byte[] img = bos.toByteArray();
                   lead.setImage(img);
               }

               long lead_id= db.createLead(lead);
             //   Toast.makeText(getActivity(), a+"sos", Toast.LENGTH_SHORT).show();
                Interaction interaction = new Interaction();
                interaction.setPlannedon(appointmentdate.getText().toString());
                interaction.setLeadid(lead_id);
                interaction.setStatus(spinnerLeadstatus.getSelectedItemPosition());
                interaction.setRemarks(remarks.getText().toString());
                interaction.setCreated_on(createonformate.format(createondate).toString());
                interaction.setPlanned_by(userid);
               long lead_ineracton_id= db.createInteraction(interaction);
               Fragment fragment = new MyleadsFragement();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                // TODO Auto-generated method stub

            }
        });
        return v;
        //return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }
    //image popup

    public void showImagePopup() {
       /* final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo"))
                {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                    startActivityForResult(intent, 1);
                }
                else if (options[item].equals("Choose from Gallery"))
                {
                    Intent intent = new   Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 1010);

                }
                else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();*/
        /*if (checker.lacksPermissions(PERMISSIONS_READ_STORAGE)) {
            startPermissionsActivity(PERMISSIONS_READ_STORAGE);
        } */
        // File System.
       final Intent galleryIntent = new Intent();
        galleryIntent.setType("image/*");
        galleryIntent.setAction(Intent.ACTION_PICK);

        // Chooser of file system options.
        final Intent chooserIntent = Intent.createChooser(galleryIntent, "select image to upload");
        startActivityForResult(chooserIntent, 1010);

    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {


      //  startLocationUpdates();

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if(mLocation == null){

        }
        if (mLocation != null) {

          lat=String.valueOf(mLocation.getLatitude());
           lon=String.valueOf(mLocation.getLongitude());
          //  Toast.makeText(getActivity(), String.valueOf(mLocation.getLatitude())+String.valueOf(mLocation.getLongitude()), Toast.LENGTH_SHORT).show();

        } else {
            lat="00.000000";
            lon="00.000000";
           // Toast.makeText(getActivity(), "Location not Detected", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {

    }
    private boolean checkLocation() {
        if(!isLocationEnabled())
            showAlert();
        return isLocationEnabled();
    }
    private void showAlert() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setTitle("Enable Location")
                .setMessage("Your Locations Settings is set to 'Off'.\nPlease Enable Location to " +
                        "use this app")
                .setPositiveButton("Location Settings", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {

                        Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(myIntent);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {

                    }
                });
        dialog.show();
    }
    private boolean isLocationEnabled() {
        locationManager = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }
}
